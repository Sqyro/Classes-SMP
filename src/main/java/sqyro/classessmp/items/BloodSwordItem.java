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
import sqyro.classessmp.network.ClassesNetworking;
import sqyro.classessmp.particle.ClassesParticles;
import sqyro.classessmp.sounds.ClassesSounds;

import java.util.function.Consumer;

public class BloodSwordItem extends ClassRestrictedItem implements KillCountingSword {
    public static final int MAX_KILLS = 15;

    public BloodSwordItem(Properties properties) {
        super(properties);
    }

    @Override
    public String getRequiredClass() {
        return "blood_sword";
    }

    public static KillCountingSwordData getData(ItemStack Stack) {
        return Stack.getOrDefault(ClassesDataComponents.KILL_COUNTING_SWORD_DATA, new KillCountingSwordData());
    }

    @Override
    public void appendHoverText(ItemStack Stack, Item.TooltipContext Context, TooltipDisplay Display, Consumer<Component> textConsumer, TooltipFlag Flag) {
        super.appendHoverText(Stack, Context, Display, textConsumer, Flag);
        KillCountingSwordData Data = getData(Stack);

        textConsumer.accept(Component.literal("Bonus Damage: " + Data.getKillCount()).withStyle(ChatFormatting.DARK_RED));
    }

    @Override
    public boolean canUse(ServerPlayer Player, ItemStack Stack) {
        PlayerClass playerClass = ((PlayerClassHolder) Player).getPlayerClass();

        return Stack.getItem() instanceof BloodSwordItem && playerClass != null && playerClass.getID().equals(getRequiredClass());
    }

    @Override
    public void onKill(ServerPlayer Player, LivingEntity Target, ItemStack Weapon) {
        KillCountingSwordData Data = BloodSwordItem.getData(Weapon);
        if (Data.getKillCount() < MAX_KILLS) {
            KillCountingSwordData newData = Data.addKill(Target.getUUID());

            Weapon.set(ClassesDataComponents.KILL_COUNTING_SWORD_DATA, newData);

            ClassesNetworking.sendBloodAmount(Player, newData.getKillCount());

            if (newData.getKillCount() > Data.getKillCount()) {
                Weapon.set(ClassesDataComponents.KILL_COUNTING_SWORD_DATA, newData);

                Player.level().sendParticles(ClassesParticles.BLOOD_SPLATTER_PARTICLE, Target.getX(), Target.getY() + 1.0, Target.getZ(), 80, 0.5, 0.8, 0.5, 0.1);
                Player.level().playSound(null, Player.getX(), Player.getY(), Player.getZ(), ClassesSounds.BLOOD_SWORD_UPGRADE, SoundSource.PLAYERS, 1.0f, 1.5f);
            }
        }
    }
}