package sqyro.classessmp.playerclasses;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.core.SavedData.PlayerClassSavedData;
import sqyro.classessmp.core.SavedData.PlayerClassSavedDataGetter;

import java.util.List;
import java.util.Optional;

public class AncientWarden extends PlayerClass {
    private boolean heardNoiseThisTick = false;
    private int noiseDecayTimer = 0;
    private int extraDamage;

    public static final int NOISE_METER_MAX_VALUE = 128;
    public static final int NOISE_DETECTION_RADIUS = 40;

    private static final Identifier DAMAGE_MODIFIER_ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "ancient_warden_damage");

    private static final String NOISE_METER_ID = "noise_meter";
    public static final int NOISE_METER_COOLDOWN = 1200;
    public static final int NOISE_METER_INCREASE = 96;

    private static final String BLINDING_ID = "blinding";
    public static final int BLINDING_COOLDOWN = 800;
    public static final int BLINDING_NOISE_CONSUMPTION = 10;
    public static final int BLINDING_RADIUS = 50;
    public static final int BLINDING_DURATION = 160;

    private static final String SONIC_BOOM_ID = "sonic_boom";
    public static final int SONIC_BOOM_COOLDOWN = 800;
    public static final int SONIC_BOOM_FOOD_CONSUMPTION = 8;
    public static final int SONIC_BOOM_NOISE_CONSUMPTION = 56;
    public static final int SONIC_BOOM_RANGE = 15;
    public static final int SONIC_BOOM_DAMAGE = 8;
    public static final float SONIC_BOOM_KNOCKBACK = 1.5f;

    public AncientWarden(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "ancient_warden";
    }

    @Override
    public void onTick() {
        tickCooldowns();

        PlayerClassSavedData data = PlayerClassSavedDataGetter.get(Player.level());

        if (!heardNoiseThisTick) {
            noiseDecayTimer++;

            if (noiseDecayTimer >= 20) {
                data.addNoise(Player, -1);
                noiseDecayTimer = 0;
            }
        } else {
            noiseDecayTimer = 0;
        }

        heardNoiseThisTick = false;
    }

    @Override
    public void onRespawn() {

    }

    @Override
    public void onKill(Entity Target) {

    }

    @Override
    public void onKeybind1() {
        if (isOnCooldown(NOISE_METER_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Noise Meter, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(NOISE_METER_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class {} activated Noise Meter", Player.getName().getString(), this.getID());
        setCooldown(NOISE_METER_ID, NOISE_METER_COOLDOWN);

        PlayerClassSavedDataGetter.get(Player.level()).addNoise(Player, NOISE_METER_INCREASE);
        Player.level().playSound(null, Player.getX(), Player.getY(), Player.getZ(), SoundEvents.WARDEN_ANGRY, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    public void onKeybind2() {
        if (isOnCooldown(BLINDING_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Blinding, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(BLINDING_ID));
            return;
        }

        if (Player.gameMode() != GameType.CREATIVE) {
            if (PlayerClassSavedDataGetter.get(Player.level()).getNoiseMeter(Player.getUUID()) < BLINDING_NOISE_CONSUMPTION) {
                return;
            }

            PlayerClassSavedDataGetter.get(Player.level()).addNoise(Player, -BLINDING_NOISE_CONSUMPTION);
        }

        ClassesSMP.LOGGER.info("{} of class {} activated Blinding", Player.getName().getString(), this.getID());
        setCooldown(BLINDING_ID, BLINDING_COOLDOWN);

        AABB Area = Player.getBoundingBox().inflate(BLINDING_RADIUS);

        List<LivingEntity> entities = Player.level().getEntitiesOfClass(LivingEntity.class, Area, entity -> entity != Player);

        for (LivingEntity entity : entities) {
            entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, BLINDING_DURATION, 0, false, true));
        }

        Player.level().playSound(null, Player.getX(), Player.getY(), Player.getZ(), SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.PLAYERS, 3.0F, 1.0F);
        Player.level().sendParticles(ParticleTypes.SONIC_BOOM, Player.getX(), Player.getY(), Player.getZ(), 1, 0, 0, 0, 0);
    }

    @Override
    public void onKeybind3() {
        if (isOnCooldown(SONIC_BOOM_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Sonic Boom, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(SONIC_BOOM_ID));
            return;
        }

        if (Player.gameMode() != GameType.CREATIVE) {
            if (Player.getFoodData().getFoodLevel() < SONIC_BOOM_FOOD_CONSUMPTION || PlayerClassSavedDataGetter.get(Player.level()).getNoiseMeter(Player.getUUID()) < SONIC_BOOM_NOISE_CONSUMPTION) {
                return;
            }

            Player.getFoodData().eat(-SONIC_BOOM_FOOD_CONSUMPTION, 0);
            PlayerClassSavedDataGetter.get(Player.level()).addNoise(Player, -SONIC_BOOM_NOISE_CONSUMPTION);
        }

        ClassesSMP.LOGGER.info("{} of class {} activated Sonic Boom", Player.getName().getString(), this.getID());
        setCooldown(SONIC_BOOM_ID, SONIC_BOOM_COOLDOWN);

        Vec3 Start = Player.getEyePosition();
        Vec3 End = Start.add(Player.getLookAngle().scale(SONIC_BOOM_RANGE));

        LivingEntity Target = getEntityHit(Player.level(), Player, Start, End);

        for (double i = 0; i < SONIC_BOOM_RANGE; i += 0.5) {
            Vec3 Pos = Start.add(Player.getLookAngle().scale(i));

            Player.level().sendParticles(ParticleTypes.SONIC_BOOM, Pos.x, Pos.y, Pos.z, 1, 0, 0, 0, 0);
        }

        Player.level().playSound(null, Player.getX(), Player.getY(), Player.getZ(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 3.0F, 1.0F);

        if (Target != null) {
            Target.hurtServer(Player.level(), Player.damageSources().sonicBoom(Player), SONIC_BOOM_DAMAGE);

            Vec3 knockback = Player.getLookAngle().normalize().scale(SONIC_BOOM_KNOCKBACK);
            Target.push(knockback.x, 0.5, knockback.z);
            Target.hurtMarked = true;
        }
    }

    @Override
    public void beginAttack(Entity Target) {
        if (!(Target instanceof LivingEntity)) {
            return;
        }

        AttributeInstance attackDamage = Player.getAttribute(Attributes.ATTACK_DAMAGE);

        if (attackDamage == null) {
            return;
        }

        attackDamage.removeModifier(DAMAGE_MODIFIER_ID);

        extraDamage = getNoiseExtraDamage();
        ClassesSMP.LOGGER.info("{} of class {} dealt {} extra damage (Noise {})", Player.getName().getString(), this.getID(), extraDamage, PlayerClassSavedDataGetter.get(Player.level()).getNoiseMeter(Player.getUUID()));

        attackDamage.addTransientModifier(new AttributeModifier(DAMAGE_MODIFIER_ID, extraDamage, AttributeModifier.Operation.ADD_VALUE));
    }

    @Override
    public void endAttack() {
        AttributeInstance attackDamage = Player.getAttribute(Attributes.ATTACK_DAMAGE);

        if (attackDamage != null) {
            attackDamage.removeModifier(DAMAGE_MODIFIER_ID);
        }
    }

    private int getNoiseExtraDamage() {
        int currentNoise = PlayerClassSavedDataGetter.get(Player.level()).getNoiseMeter(Player.getUUID());

        if (currentNoise >= NOISE_METER_MAX_VALUE * 0.75f) {
            return 5;
        } else if (currentNoise >= NOISE_METER_MAX_VALUE * 0.5f) {
            return 3;
        } else if (currentNoise >= NOISE_METER_MAX_VALUE * 0.25f) {
            return 1;
        } else {
            return 0;
        }
    }

    public void hearNoise(int Amount) {
        heardNoiseThisTick = true;
        PlayerClassSavedDataGetter.get(Player.level()).addNoise(Player, Amount);
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