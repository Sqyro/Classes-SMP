package sqyro.classessmp.entities.goal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import sqyro.classessmp.entities.LaserChickenEntity;

import java.util.EnumSet;

public class LaserAttackGoal extends Goal {
    public static final double LASER_RANGE = 16.0D;
    public static final float LASER_DAMAGE = 8.0F;

    private final LaserChickenEntity chicken;

    private int attackTime;
    private int cooldown;

    public LaserAttackGoal(LaserChickenEntity chicken) {
        this.chicken = chicken;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.chicken.getTarget();

        return target != null && target.isAlive() && this.chicken.shouldAttackTarget(target) && this.chicken.distanceToSqr(target) > LASER_RANGE;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.chicken.getTarget();

        return target != null && target.isAlive() && this.chicken.shouldAttackTarget(target) && this.chicken.distanceToSqr(target) > LASER_RANGE;
    }

    @Override
    public void start() {
        this.attackTime = 0;
        this.cooldown = 0;

        this.chicken.getNavigation().stop();

        LivingEntity target = this.chicken.getTarget();

        if (target != null) {
            this.chicken.getLookControl().setLookAt(target, 90.0F, 90.0F);
        }
    }

    @Override
    public void stop() {
        this.attackTime = 0;
        this.cooldown = 0;

        this.chicken.setActiveAttackTarget(0);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.chicken.getTarget();

        if (target == null || !target.isAlive() || !this.chicken.shouldAttackTarget(target)) {
            this.chicken.setTarget(null);
            return;
        }

        this.chicken.getNavigation().stop();
        this.chicken.getLookControl().setLookAt(target, 90.0F, 90.0F);

        if (!this.chicken.hasLineOfSight(target)) {
            return;
        }

        if (this.cooldown > 0) {
            this.cooldown--;
            return;
        }

        this.attackTime++;

        if (this.attackTime >= this.chicken.getAttackDuration()) {
            ServerLevel serverLevel = getServerLevel(this.chicken);

            target.hurtServer(serverLevel, this.chicken.damageSources().mobAttack(this.chicken), LASER_DAMAGE);
            this.chicken.triggerLaserAttack(target);

            this.attackTime = 0;
            this.cooldown = this.chicken.getAttackCooldown();
        }
    }
}