package sqyro.classessmp.playerclasses;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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

import java.util.List;
import java.util.Optional;

public class SigeonPex extends PlayerClass {
    public static final String SIGEON_ROOTING_ID = "sigeon_rooting";
    public static final int SIGEON_ROOTING_COOLDOWN = 800;
    public static final int SIGEON_ROOTING_RANGE = 20;
    public static final int SIGEON_ROOTING_DURATION = 100;

    public SigeonPex(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "sigeon_pex";
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onRespawn() {

    }

    @Override
    public void onKeybind1() {
        if (isOnCooldown(SIGEON_ROOTING_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Sigeon Rooting, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(SIGEON_ROOTING_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class {} activated Sigeon Rooting", Player.getName().getString(), this.getID());
        setCooldown(SIGEON_ROOTING_ID, SIGEON_ROOTING_COOLDOWN);

        ServerLevel level = Player.level();

        Vec3 Start = Player.getEyePosition();
        Vec3 Direction = Player.getLookAngle();

        Vec3 MaxEnd = Start.add(Direction.scale(SIGEON_ROOTING_RANGE));
        BlockHitResult blockHit = level.clip(new ClipContext(Start, MaxEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, Player));
        Vec3 End = blockHit.getType() == HitResult.Type.BLOCK ? blockHit.getLocation() : MaxEnd;

        LivingEntity hitEntity = getEntityHit(level, Player, Start, End);

        if (hitEntity != null) {
            Vec3 hitPos = hitEntity.position().add(0, hitEntity.getBbHeight() / 2, 0);
            End = hitPos;
            hitEntity.addEffect(new MobEffectInstance(ClassesEffects.ROOTING, SIGEON_ROOTING_DURATION, 0));
            ClassesSMP.LOGGER.info("{} hit {} with Sigeon Rooting", Player.getName().getString(), hitEntity.getName().getString());
        }

        double Length = Start.distanceTo(End);

        for (int i = 0; i < Length; i++) {
            double Position = i / Length;
            Vec3 particlePos = Start.lerp(End, Position);
            level.sendParticles(ParticleTypes.WAX_ON, particlePos.x, particlePos.y, particlePos.z, 16, 0.12, 0.12, 0.12, 0.01);
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

    }

    @Override
    public void endAttack() {

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
