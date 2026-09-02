package sqyro.classessmp.playerclasses;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.items.ClassesItems;

public class SuperFishNinja extends PlayerClass {
    public static final Identifier DAMAGE_MODIFIER_ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "ninja_damage");

    public static final int KNIFE_EXTRA_DAMAGE = 3;

    public static final String NINJA_PULL_ID = "ninja_pull";
    public static final int NINJA_PULL_COOLDOWN = 200;

    public SuperFishNinja(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "superfishninja";
    }

    @Override
    public void onTick() {
        Player.addEffect(new MobEffectInstance(MobEffects.SPEED, 40, 1, false, false));
    }

    @Override
    public void onRespawn() {

    }

    @Override
    public void onKill(Entity Target) {

    }

    @Override
    public void onKeybind1() {
        if (isOnCooldown(NINJA_PULL_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Ninja Pull, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(NINJA_PULL_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class {} activated Ninja Pull", Player.getName().getString(), this.getID());
        setCooldown(NINJA_PULL_ID, NINJA_PULL_COOLDOWN);

        Vec3 startPos = Player.getEyePosition(1.0F);
        Vec3 endPos = startPos.add(Player.getViewVector(1.0F).scale(12.0D));

        BlockHitResult hit = Player.level().clip(new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, Player));

        if (hit.getType() == HitResult.Type.BLOCK) {
            //BlockPos blockPos = hit.getBlockPos();

            Player.push(endPos);
        }
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
