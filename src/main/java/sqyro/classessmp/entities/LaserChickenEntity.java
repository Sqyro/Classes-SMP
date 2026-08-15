package sqyro.classessmp.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import sqyro.classessmp.entities.goal.HostileTargetGoal;
import sqyro.classessmp.entities.goal.LaserAttackGoal;

public class LaserChickenEntity extends ClassSummonEntity {
    private static final int ATTACK_TIME = 40;
    private static final int ATTACK_COOLDOWN = 20;

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimationState = new AnimationState();

    private static final EntityDataAccessor<Integer> DATA_ID_ATTACK_TARGET = SynchedEntityData.defineId(LaserChickenEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> DATA_ID_ATTACK = SynchedEntityData.defineId(LaserChickenEntity.class, EntityDataSerializers.INT);

    private int clientSideAttack = 0;

    public LaserChickenEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LaserAttackGoal(this));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0, 8.0F, 4.0F));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HostileTargetGoal(this, 10.0D));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.3D);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(0);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);

        builder.define(DATA_ID_ATTACK_TARGET, 0);
        builder.define(DATA_ID_ATTACK, 0);
    }


    public void setActiveAttackTarget(int id) {
        this.entityData.set(DATA_ID_ATTACK_TARGET, id);
    }

    @Nullable
    public LivingEntity getActiveAttackTarget() {
        int targetId = this.entityData.get(DATA_ID_ATTACK_TARGET);

        if (targetId == 0) {
            return null;
        }

        Entity entity = this.level().getEntity(targetId);

        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity;
        }

        return null;
    }

    public void triggerLaserAttack(LivingEntity target) {
        this.setActiveAttackTarget(target.getId());

        this.entityData.set(DATA_ID_ATTACK, this.entityData.get(DATA_ID_ATTACK) + 1);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);

        if (DATA_ID_ATTACK.equals(accessor) && this.level().isClientSide()) {
            int attack = this.entityData.get(DATA_ID_ATTACK);

            if (attack != this.clientSideAttack) {
                this.clientSideAttack = attack;

                this.attackAnimationState.start(this.tickCount);

                LivingEntity target = this.getActiveAttackTarget();

                if (target != null) {
                    this.spawnLaserShot(target);
                }
            }
        }
    }

    private void spawnLaserShot(LivingEntity target) {
        Vec3 start = this.getEyePosition();
        Vec3 end = target.getEyePosition();

        Vec3 difference = end.subtract(start);

        double distance = difference.length();

        if (distance <= 0.001D) {
            return;
        }

        Vec3 direction = difference.normalize();

        double spacing = 0.15D;

        for (double d = 0.0D; d < distance; d += spacing) {
            Vec3 position = start.add(direction.scale(d));
            this.level().addParticle(ParticleTypes.END_ROD, position.x, position.y, position.z, 0.0D, 0.0D, 0.0D);
        }
    }

    public int getAttackDuration() {
        return ATTACK_TIME;
    }

    public int getAttackCooldown() {
        return ATTACK_COOLDOWN;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.CHICKEN_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.CHICKEN_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.CHICKEN_DEATH;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }
}