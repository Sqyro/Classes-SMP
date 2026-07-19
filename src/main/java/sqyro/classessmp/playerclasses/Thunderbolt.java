package sqyro.classessmp.playerclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.effect.ClassesEffects;
import sqyro.classessmp.particle.ClassesParticles;
import sqyro.classessmp.sounds.ClassesSounds;

import java.util.List;
import java.util.Optional;

public class Thunderbolt extends PlayerClass {
    public static final String THUNDERSTORM_ID = "thunderstorm";
    public static final int THUNDERSTORM_COOLDOWN = 1200;
    public static final int THUNDERSTORM_DURATION = 200;
    public static final int THUNDERSTORM_RADIUS = 50;
    public static final float THUNDERSTORM_DAMAGE = 3f;
    public static final int THUNDERSTORM_HIT_PLAYER_CHANCE = 50;

    private int thunderstormTicks = 0;
    private LivingEntity stormTarget = null;

    public static final String LIGHTNING_DASH_ID = "lightning_dash";
    public static final int LIGHTNING_DASH_COOLDOWN = 100;
    public static final float LIGHTNING_DASH_STRENGTH = 1.1f;

    public static final String CHAIN_LIGHTNING_ID = "chain_lightning";
    public static final int CHAIN_LIGHTNING_COOLDOWN = 400;
    public static final float CHAIN_LIGHTNING_DAMAGE = 3;
    public static final int CHAIN_LIGHTNING_SHOCKED_DURATION = 60;
    public static final float CHAIN_LIGHTNING_RANGE = 20;
    public static final float CHAIN_LIGHTNING_BOUNCE_RANGE = 5;

    public Thunderbolt(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "thunderbolt";
    }

    @Override
    public void onTick() {
        tickCooldowns();

        if (thunderstormTicks <= 0) {
            return;
        }

        thunderstormTicks--;

        if (thunderstormTicks % 5 == 0) {
            Vec3 start = Player.getEyePosition();
            Vec3 end = start.add(Player.getLookAngle().scale(CHAIN_LIGHTNING_RANGE));

            LivingEntity newTarget = getEntityHit(Player.level(), Player, start, end);

            if (newTarget != null) {
                stormTarget = newTarget;
            }

            spawnLightning();
        }
    }

    @Override
    public void onRespawn() {

    }

    @Override
    public void onKeybind1() {
        if (isOnCooldown(THUNDERSTORM_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Thunder Storm, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(THUNDERSTORM_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class: {} activated Thunder Storm", Player.getName().getString(), this.getID());
        setCooldown(THUNDERSTORM_ID, THUNDERSTORM_COOLDOWN);
        Player.level().playSound(null, Player.getX(), Player.getY(), Player.getZ(), ClassesSounds.THUNDERSTORM, SoundSource.PLAYERS);

        thunderstormTicks = THUNDERSTORM_DURATION;
    }

    @Override
    public void onKeybind2() {
        if (isOnCooldown(LIGHTNING_DASH_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Lightning Dash, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(LIGHTNING_DASH_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class: {} activated Lightning Dash", Player.getName().getString(), this.getID());
        setCooldown(LIGHTNING_DASH_ID, LIGHTNING_DASH_COOLDOWN);

        Vec3 direction = Player.getLookAngle().normalize();
        Player.push(direction.x * LIGHTNING_DASH_STRENGTH, direction.y * LIGHTNING_DASH_STRENGTH * 0.6f + 0.3f, direction.z * LIGHTNING_DASH_STRENGTH);

        Player.level().playSound(null, Player.getX(), Player.getY(), Player.getZ(), ClassesSounds.LIGHTNING_DASH, SoundSource.PLAYERS);
        Player.level().sendParticles(ClassesParticles.LIGHTNING_PARTICLE, Player.getX(), Player.getY(), Player.getZ(), 50, 0.5, 0.5, 0.5, 0.1);

        Player.hurtMarked = true;
    }

    @Override
    public void onKeybind3() {
        if (isOnCooldown(CHAIN_LIGHTNING_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Chain Lightning, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(CHAIN_LIGHTNING_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class: {} activated Chain Lightning", Player.getName().getString(), this.getID());
        setCooldown(CHAIN_LIGHTNING_ID, CHAIN_LIGHTNING_COOLDOWN);

        Player.level().playSound(null, Player.getX(), Player.getY(), Player.getZ(), ClassesSounds.CHAIN_LIGHTNING, SoundSource.PLAYERS);

        Vec3 Start = Player.getEyePosition();
        Vec3 Direction = Player.getLookAngle();

        Vec3 MaxEnd = Start.add(Direction.scale(CHAIN_LIGHTNING_RANGE));
        BlockHitResult blockHit = Player.level().clip(new ClipContext(Start, MaxEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, Player));
        Vec3 End = blockHit.getType() == HitResult.Type.BLOCK ? blockHit.getLocation() : MaxEnd;

        LivingEntity Target = getEntityHit(Player.level(), Player, Start, End);

        if (Target == null) {
            spawnLightningParticles(Player.level(), Start, End);
            return;
        }

        Vec3 targetPos = Target.position().add(0, Target.getBbHeight() / 2, 0);

        spawnLightningParticles(Player.level(), Start, targetPos);

        DamageSource fireDamage = Player.damageSources().inFire();
        Target.hurtServer(Player.level(), fireDamage, CHAIN_LIGHTNING_DAMAGE);
        Target.addEffect(new MobEffectInstance(ClassesEffects.SHOCKED, CHAIN_LIGHTNING_SHOCKED_DURATION, 0));

        AABB Area = Target.getBoundingBox().inflate(CHAIN_LIGHTNING_BOUNCE_RANGE);
        List<LivingEntity> nearbyEntities = Player.level().getEntitiesOfClass(LivingEntity.class, Area, entity -> entity != Player && entity != Target);

        for (LivingEntity Entity : nearbyEntities) {
            Vec3 entityPos = Entity.position().add(0, Entity.getBbHeight() / 2, 0);

            spawnLightningParticles(Player.level(), targetPos, entityPos);
            Entity.hurtServer(Player.level(), fireDamage, CHAIN_LIGHTNING_DAMAGE);
            Entity.addEffect(new MobEffectInstance(ClassesEffects.SHOCKED, CHAIN_LIGHTNING_SHOCKED_DURATION, 0));
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

    private void spawnLightning() {
        ServerLevel level = Player.level();
        DamageSource damage = Player.damageSources().lightningBolt();

        boolean hitTracked = stormTarget != null && stormTarget.isAlive() && Player.getRandom().nextInt(0, 100) <= THUNDERSTORM_HIT_PLAYER_CHANCE;

        if (hitTracked) {
            strike(level, stormTarget.position(), damage, stormTarget);
            return;
        }

        double angle = Player.getRandom().nextDouble() * Math.PI * 2;
        double distance = Player.getRandom().nextDouble() * THUNDERSTORM_RADIUS;

        double x = Player.getX() + Math.cos(angle) * distance;
        double z = Player.getZ() + Math.sin(angle) * distance;

        BlockPos pos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, new BlockPos((int)x, 0, (int)z));

        strike(level, Vec3.atCenterOf(pos), damage, null);
    }

    private void strike(ServerLevel level, Vec3 pos, DamageSource damage, @Nullable LivingEntity directTarget) {
        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level, EntitySpawnReason.TRIGGERED);

        if (lightning != null) {
            lightning.moveOrInterpolateTo(pos);
            lightning.setCause(Player);
            level.addFreshEntity(lightning);
        }

        if (directTarget != null) {
            directTarget.hurtServer(level, damage, THUNDERSTORM_DAMAGE);
            return;
        }

        AABB area = new AABB(pos, pos).inflate(2);

        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, area, e -> e != Player)) {
            entity.hurtServer(level, damage, THUNDERSTORM_DAMAGE);
        }
    }

    private void spawnLightningParticles(ServerLevel level, Vec3 start, Vec3 end) {
        double distance = start.distanceTo(end);

        for (int i = 0; i < distance * 4; i++) {
            double progress = i / (distance * 4);
            Vec3 pos = start.lerp(end, progress);

            level.sendParticles(ClassesParticles.LIGHTNING_PARTICLE, pos.x, pos.y, pos.z, 1, 0.05, 0.05, 0.05, 0.01);
        }
    }
}