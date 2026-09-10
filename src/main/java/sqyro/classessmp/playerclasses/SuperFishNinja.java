package sqyro.classessmp.playerclasses;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.items.ClassesItems;

import java.util.List;

public class SuperFishNinja extends PlayerClass {
    public static final Identifier DAMAGE_MODIFIER_ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "ninja_damage");

    public static final int KNIFE_EXTRA_DAMAGE = 3;

    public static final String NINJA_PULL_ID = "ninja_pull";
    public static final int NINJA_PULL_COOLDOWN = 100;
    public static final int NINJA_PULL_RANGE = 30;
    public static final int NINJA_PULL_DURATION = 80;
    public static final double NINJA_PULL_STOP_DISTANCE = 2.0D;
    public static final double NINJA_PULL_ACCELERATION = 0.3D;
    public static final double NINJA_PULL_MAX_SPEED = 4D;
    public static final double NINJA_PULL_IMPACT_RADIUS = 5D;
    public static final float NINJA_PULL_IMPACT_DAMAGE = 10F;

    private Vec3 ninjaPullTarget;
    private int ninjaPullTicks;

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

        if (ninjaPullTarget == null || ninjaPullTicks <= 0) {
            return;
        }

        Vec3 toTarget = ninjaPullTarget.subtract(Player.position());

        if (toTarget.lengthSqr() <= NINJA_PULL_STOP_DISTANCE * NINJA_PULL_STOP_DISTANCE) {
            ninjaPullImpact();
            return;
        }

        Vec3 acceleration = toTarget.normalize().scale(NINJA_PULL_ACCELERATION);

        Vec3 velocity = Player.getDeltaMovement().add(acceleration);

        if (velocity.lengthSqr() > NINJA_PULL_MAX_SPEED * NINJA_PULL_MAX_SPEED) {
            velocity = velocity.normalize().scale(NINJA_PULL_MAX_SPEED);
        }

        Player.setDeltaMovement(velocity);
        Player.hurtMarked = true;


        ninjaPullTicks--;

        if (ninjaPullTicks <= 0) {
            stopNinjaPull();
        }
    }

    @Override
    public void onRespawn() {

    }

    @Override
    public void onKill(Entity Target) {

    }

    @Override
    public void onKeybind1() {
        stopNinjaPull();

        if (isOnCooldown(NINJA_PULL_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Ninja Pull, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(NINJA_PULL_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class {} activated Ninja Pull", Player.getName().getString(), this.getID());
        setCooldown(NINJA_PULL_ID, NINJA_PULL_COOLDOWN);

        Vec3 startPos = Player.getEyePosition(1.0F);
        Vec3 endPos = startPos.add(Player.getViewVector(1.0F).scale(NINJA_PULL_RANGE));

        BlockHitResult hit = Player.level().clip(new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, Player));

        if (hit.getType() != HitResult.Type.BLOCK) {
            return;
        }

        Vec3 normal = new Vec3(hit.getDirection().getStepX(), hit.getDirection().getStepY(), hit.getDirection().getStepZ());

        ninjaPullTarget = hit.getLocation().add(normal.scale(0.6D));
        ninjaPullTicks = NINJA_PULL_DURATION;

        Player.setNoGravity(true);

        setCooldown(NINJA_PULL_ID, NINJA_PULL_COOLDOWN);
    }

    private void stopNinjaPull() {
        ninjaPullTarget = null;
        ninjaPullTicks = 0;
        Player.setNoGravity(false);
    }

    private void ninjaPullImpact() {
        Vec3 impactPosition = ninjaPullTarget != null ? ninjaPullTarget : Player.position();
        AABB impactBox = new AABB(impactPosition.x, impactPosition.y, impactPosition.z, impactPosition.x, impactPosition.y, impactPosition.z).inflate(NINJA_PULL_IMPACT_RADIUS);

        List<LivingEntity> targets = Player.level().getEntitiesOfClass(LivingEntity.class, impactBox, target -> target != Player && target.isAlive() && !target.isSpectator());
        DamageSource damageSource = Player.damageSources().playerAttack(Player);

        Player.level().sendParticles(ParticleTypes.EXPLOSION, Player.getX(), Player.getY(), Player.getZ(), 20, 0.5, 0.5, 0.5, 0.1);
        Player.level().playSound(null, Player.getX(), Player.getY(), Player.getZ(), SoundEvents.MACE_SMASH_GROUND, SoundSource.PLAYERS);

        for (LivingEntity target : targets) {
            target.hurt(damageSource, NINJA_PULL_IMPACT_DAMAGE);
        }

        stopNinjaPull();
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