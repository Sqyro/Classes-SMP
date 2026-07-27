package sqyro.classessmp.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import sqyro.classessmp.core.ClassesDataComponents;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.core.PlayerClassHolder;
import sqyro.classessmp.sounds.ClassesSounds;

import java.util.function.Consumer;

public class ClownSwordItem extends ClassRestrictedItem implements KillCountingSword {
    public static final int MAX_KILLS = 10;

    public ClownSwordItem(Properties properties) {
        super(properties);
    }

    @Override
    protected String getRequiredClass() {
        return "clown";
    }

    public static KillCountingSwordData getData(ItemStack Stack) {
        return Stack.getOrDefault(ClassesDataComponents.KILL_COUNTING_SWORD_DATA, new KillCountingSwordData());
    }

    @Override
    public void appendHoverText(ItemStack Stack, Item.TooltipContext Context, TooltipDisplay Display, Consumer<Component> textConsumer, TooltipFlag Flag) {
        super.appendHoverText(Stack, Context, Display, textConsumer, Flag);
        KillCountingSwordData Data = getData(Stack);

        textConsumer.accept(Component.literal("Kills: " + Data.getKillCount()).withStyle(ChatFormatting.DARK_BLUE));
        textConsumer.accept(Component.literal("Attack Damage: " + getData(Stack).getKillCount() / 2).withStyle(ChatFormatting.BLUE));
        textConsumer.accept(Component.literal("Movement Speed: " + (float)((getData(Stack).getKillCount() + 1) / 2) / 100).withStyle(ChatFormatting.BLUE));
    }

    @Override
    public boolean canUse(ServerPlayer Player, ItemStack Stack) {
        PlayerClass playerClass = ((PlayerClassHolder) Player).getPlayerClass();

        return Stack.getItem() instanceof ClownSwordItem && playerClass != null && playerClass.getID().equals(getRequiredClass());
    }

    @Override
    public void onKill(ServerPlayer Player, LivingEntity Target, ItemStack Weapon) {
        KillCountingSwordData Data = ClownSwordItem.getData(Weapon);
        if (Data.getKillCount() < MAX_KILLS) {
            KillCountingSwordData newData = Data.addKill(Target.getUUID());

            Weapon.set(ClassesDataComponents.KILL_COUNTING_SWORD_DATA, newData);

            if (newData.getKillCount() > Data.getKillCount()) {
                Weapon.set(ClassesDataComponents.KILL_COUNTING_SWORD_DATA, newData);

                Player.level().playSound(null, Player.getX(), Player.getY(), Player.getZ(), ClassesSounds.CLOWN_KILL, SoundSource.PLAYERS, 1.0f, 1.0f);
            }
        }
    }
}
