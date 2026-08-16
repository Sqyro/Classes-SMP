package sqyro.classessmp.entities.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;
import sqyro.classessmp.entities.ClassSummonEntity;

import java.util.Comparator;
import java.util.List;

public class HostileTargetGoal extends TargetGoal {
    private final ClassSummonEntity summonEntity;
    private final double range;

    public HostileTargetGoal(ClassSummonEntity summonEntity, double range) {
        super(summonEntity, false);
        this.summonEntity = summonEntity;
        this.range = range;
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = summonEntity.getOwner();

        if (owner == null || !owner.isAlive()) {
            return false;
        }

        if (summonEntity.getTarget() != null && summonEntity.getTarget().isAlive()) {
            return false;
        }

        LivingEntity target = findTarget(owner);

        if (target == null) {
            return false;
        }

        summonEntity.setTarget(target);
        return true;
    }

    @Nullable
    private LivingEntity findTarget(LivingEntity owner) {
        AABB searchBox = owner.getBoundingBox().inflate(range);

        List<Monster> monsters = summonEntity.level().getEntitiesOfClass(Monster.class, searchBox, monster -> monster.isAlive() && summonEntity.shouldAttackTarget(monster));

        return monsters.stream().min(Comparator.comparingDouble(monster -> monster.distanceToSqr(summonEntity))).orElse(null);
    }
}