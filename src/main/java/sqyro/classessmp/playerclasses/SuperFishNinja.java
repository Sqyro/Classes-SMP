package sqyro.classessmp.playerclasses;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.items.ClassesItems;

public class SuperFishNinja extends PlayerClass {
    public static final Identifier DAMAGE_MODIFIER_ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "ninja_damage");

    public static final int KNIFE_EXTRA_DAMAGE = 3;

    public static final String NINJA_INVISIBILITY_ID = "invisibility";
    public static final int NINJA_INVISIBILITY_COOLDOWN = 2000;

    public static final String NINJA_TELEPORT_ID = "ninja_teleport";
    public static final int NINJA_TELEPORT_COOLDOWN = 100;

    public SuperFishNinja(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "superfishninja";
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onRespawn() {

    }

    @Override
    public void onKill(Entity Target) {

    }

    @Override
    public void onKeybind1() {

    }

    @Override
    public void onKeybind2() {

    }

    @Override
    public void onKeybind3() {

    }

    @Override
    public void beginAttack(Entity Target) {
        ItemStack itemInHand = Player.getItemInHand(InteractionHand.MAIN_HAND);
        AttributeInstance attackDamage = Player.getAttribute(Attributes.ATTACK_DAMAGE);

        if (itemInHand == ItemStack.EMPTY) {
            return;
        }

        if (attackDamage == null) {
            return;
        }

        attackDamage.removeModifier(DAMAGE_MODIFIER_ID);

        if (!(itemInHand.is(ClassesItems.KNIVES))) {
            return;
        }

        Player.displayClientMessage(Component.literal("Extra Damage " + KNIFE_EXTRA_DAMAGE).withStyle(ChatFormatting.GREEN), true);
        attackDamage.addTransientModifier(new AttributeModifier(DAMAGE_MODIFIER_ID, KNIFE_EXTRA_DAMAGE, AttributeModifier.Operation.ADD_VALUE));
    }

    @Override
    public void endAttack() {
        AttributeInstance attackDamage = Player.getAttribute(Attributes.ATTACK_DAMAGE);

        if (attackDamage != null) {
            attackDamage.removeModifier(DAMAGE_MODIFIER_ID);
        }
    }
}
