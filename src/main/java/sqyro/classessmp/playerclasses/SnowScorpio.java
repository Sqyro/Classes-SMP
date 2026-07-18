package sqyro.classessmp.playerclasses;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.effect.ClassesEffects;
import sqyro.classessmp.particle.ClassesParticles;
import sqyro.classessmp.sounds.ClassesSounds;

import java.util.List;
import java.util.Optional;

public class SnowScorpio extends PlayerClass {
    private static final int ABILITY_MISS_EFFECT_DURATION = 20;

    private static final String ICE_PULL_ID = "ice_pull";
    private static final String ICE_PRISON_ID = "ice_prison";

    public static final int ICE_PULL_COOLDOWN = 140;
    public static final int ICE_PRISON_COOLDOWN = 300;

    private static final double ICE_PULL_STRENGTH = 0.7;
    private static final double ICE_PULL_RANGE = 5;

    private static final double ICE_PRISON_RANGE = 40.0;
    private static final int ICE_PRISON_HIT_EFFECT_DURATION = 50;

    public SnowScorpio(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "snow_scorpio";
    }

    @Override
    public void onTick() {
        this.tickCooldowns();
    }

    @Override
    public void onRespawn() {
        getCooldowns().clear();
    }

    @Override
    public void onKeybind1() {
        if (isOnCooldown(ICE_PULL_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Ice Pull, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(ICE_PULL_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class: {} activated Ice Pull", Player.getName().getString(), this.getID());
        setCooldown(ICE_PULL_ID, ICE_PULL_COOLDOWN);

        ServerLevel Level = Player.level();
        boolean hitSomething = false;

        AABB Area = Player.getBoundingBox().inflate(ICE_PULL_RANGE);

        for (int i = 0; i < ICE_PULL_RANGE * 50; i++) {
            Vec3 Offset = new Vec3(Level.random.nextDouble() * 2 - 1, Level.random.nextDouble() * 2 - 1, Level.random.nextDouble() * 2 - 1);
            Offset = Offset.normalize().scale(Level.random.nextDouble() * ICE_PULL_RANGE);
            Level.sendParticles(ClassesParticles.ICE_STORM_PARTICLE, Player.getX() + Offset.x, Player.getY() + 1 + Offset.y, Player.getZ() + Offset.z, 1, 0, 0, 0, 0);
        }

        List<LivingEntity> Targets = Level.getEntitiesOfClass(LivingEntity.class, Area, Entity -> Entity != Player);

        for (LivingEntity Target : Targets) {
            hitSomething = true;
            Vec3 Direction = Player.position().subtract(Target.position()).normalize();
            Target.setDeltaMovement(Direction.scale(ICE_PULL_STRENGTH));
            Target.hurtMarked = true;
            Level.sendParticles(ParticleTypes.SNOWFLAKE, Target.getX(), Target.getY() + Target.getBbHeight() / 2, Target.getZ(), 10, 0.3, 0.5, 0.3, 0.05);
        }

        if (!hitSomething) {
            Player.addEffect(new MobEffectInstance(ClassesEffects.FREEZING, ABILITY_MISS_EFFECT_DURATION, 0));
        } else {
            Level.playSound(null, Player.getX(), Player.getY(), Player.getZ(), ClassesSounds.ICE_PULL, SoundSource.PLAYERS, 1f, 1f);
        }

    }

    @Override
    public void onKeybind2() {
        if (isOnCooldown(ICE_PRISON_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Ice Prison, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(ICE_PRISON_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class {} activated Ice Prison", Player.getName().getString(), this.getID());
        setCooldown(ICE_PRISON_ID, ICE_PRISON_COOLDOWN);

        ServerLevel level = Player.level();

        Vec3 Start = Player.getEyePosition();
        Vec3 Direction = Player.getLookAngle();

        Vec3 MaxEnd = Start.add(Direction.scale(ICE_PRISON_RANGE));
        BlockHitResult blockHit = level.clip(new ClipContext(Start, MaxEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, Player));
        Vec3 End = blockHit.getType() == HitResult.Type.BLOCK ? blockHit.getLocation() : MaxEnd;

        LivingEntity hitEntity = getEntityHit(level, Player, Start, End);

        if (hitEntity != null) {
            Vec3 hitPos = hitEntity.position().add(0, hitEntity.getBbHeight() / 2, 0);
            End = hitPos;
            hitEntity.addEffect(new MobEffectInstance(ClassesEffects.FREEZING, ICE_PRISON_HIT_EFFECT_DURATION, 0));
            ClassesSMP.LOGGER.info("{} hit {} with Ice Prison", Player.getName().getString(), hitEntity.getName().getString());
        } else {
            Player.addEffect(new MobEffectInstance(ClassesEffects.FREEZING, ABILITY_MISS_EFFECT_DURATION, 0));
        }

        double Length = Start.distanceTo(End);

        for (int i = 0; i < Length; i++) {
            double Position = i / Length;
            Vec3 particlePos = Start.lerp(End, Position);
            level.sendParticles(ClassesParticles.ICE_PARTICLE, particlePos.x, particlePos.y, particlePos.z, 16, 0.12, 0.12, 0.12, 0.01);
        }

    }

    private LivingEntity getEntityHit(ServerLevel Level, Player player, Vec3 StartPos, Vec3 EndPos) {
        AABB hitBox = player.getBoundingBox().expandTowards(EndPos.subtract(StartPos)).inflate(1.0);

        List<Entity> Entities = Level.getEntities(player, hitBox, Entity -> Entity.isPickable() && Entity instanceof LivingEntity);

        LivingEntity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;

        for (Entity thisEntity : Entities) {
            AABB entityHitBox = thisEntity.getBoundingBox().inflate(0.3);
            Optional<Vec3> hitPos = entityHitBox.clip(StartPos, EndPos);

            if (hitPos.isPresent()) {
                double Distance = StartPos.distanceTo(hitPos.get());

                if (Distance < closestDistance) {
                    closestDistance = Distance;
                    closestEntity = (LivingEntity) thisEntity;
                }
            }
        }

        return closestEntity;
    }
}