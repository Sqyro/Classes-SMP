package sqyro.classessmp.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.ClassesDataComponents;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.core.PlayerClassHolder;

import java.util.function.Consumer;

public class BloodSwordItem extends ClassRestrictedItem {
    public BloodSwordItem(Properties properties) {
        super(properties);
    }

    private static final Identifier DAMAGE_MODIFIER_ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "blood_sword_damage");

    @Override
    public String getRequiredClass() {
        return "blood_sword";
    }

    public static BloodSwordData getData(ItemStack Stack) {
        return Stack.getOrDefault(ClassesDataComponents.BLOOD_SWORD_DATA, new BloodSwordData());
    }

    @Override
    public void appendHoverText(ItemStack Stack, Item.TooltipContext Context, TooltipDisplay Display, Consumer<Component> textConsumer, TooltipFlag Flag) {
        super.appendHoverText(Stack, Context, Display, textConsumer, Flag);
        BloodSwordData Data = getData(Stack);

        textConsumer.accept(Component.literal("Bonus Damage: " + Data.getBonusDamage()).withStyle(ChatFormatting.DARK_RED));
    }

    public static boolean canUse(ServerPlayer Player, ItemStack Stack) {
        PlayerClass playerClass = ((PlayerClassHolder) Player).getPlayerClass();

        return Stack.getItem() instanceof BloodSwordItem && playerClass != null && playerClass.getID().equals("blood_sword");
    }

    public static void beginAttack(ServerPlayer Player, ItemStack Stack) {
        AttributeInstance attackDamage = Player.getAttribute(Attributes.ATTACK_DAMAGE);

        if (attackDamage == null) {
            return;
        }

        attackDamage.removeModifier(DAMAGE_MODIFIER_ID);
        int Bonus = getData(Stack).getBonusDamage();

        if (Bonus > 0) {
            attackDamage.addTransientModifier(new AttributeModifier(DAMAGE_MODIFIER_ID, Bonus, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    public static void endAttack(ServerPlayer Player) {
        AttributeInstance attackDamage = Player.getAttribute(Attributes.ATTACK_DAMAGE);

        if (attackDamage != null) {
            attackDamage.removeModifier(DAMAGE_MODIFIER_ID);
        }
    }
}