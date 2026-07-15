package sqyro.classessmp.playerclasses;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.effect.ClassesEffects;

import java.util.List;
import java.util.Optional;

public class SnowScorpion extends PlayerClass {
    private static final double ICE_PRISON_RANGE = 20.0;
    private static final int ICE_PRISON_HIT_EFFECT_DURATION_SECONDS = 5;
    private static final int ICE_PRISON_MISS_EFFECT_DURATION_SECONDS = 1;

    public SnowScorpion(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "snow_scorpion";
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onAttack(Entity Target) {

    }

    @Override
    public void onKeybind1() {
        ClassesSMP.LOGGER.info("{} of class: {} activated Ice Pull", Player.getName().getString(), this.getID());
    }

    @Override
    public void onKeybind2() {
        ServerLevel level = Player.level();

        Vec3 Start = Player.getEyePosition();
        Vec3 Direction = Player.getLookAngle();

        Vec3 End = Start.add(Direction.scale(ICE_PRISON_RANGE));

        for (int i = 0; i < ICE_PRISON_RANGE; i++) {
            double Position = i / ICE_PRISON_RANGE;
            Vec3 particlePos = Start.lerp(End, Position);
            level.sendParticles(ParticleTypes.SNOWFLAKE, particlePos.x, particlePos.y, particlePos.z, 4, 0.12, 0.12, 0.12, 0.01);
        }

        LivingEntity hitEntity = getEntityHit(level, Player, Start, End);

        if (hitEntity != null) {
            hitEntity.addEffect(new MobEffectInstance(ClassesEffects.FREEZING, ICE_PRISON_HIT_EFFECT_DURATION_SECONDS * 20, 0));
            ClassesSMP.LOGGER.info("{} hit {} with Ice Prison", Player.getName().getString(), hitEntity.getName().getString());
        } else {
            Player.addEffect(new MobEffectInstance(ClassesEffects.FREEZING, ICE_PRISON_MISS_EFFECT_DURATION_SECONDS * 20, 0));
        }

        ClassesSMP.LOGGER.info("{} of class: {} activated Ice Prison", Player.getName().getString(), this.getID());
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