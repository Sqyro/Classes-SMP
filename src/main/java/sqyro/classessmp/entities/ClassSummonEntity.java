package sqyro.classessmp.entities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class ClassSummonEntity extends TamableAnimal {
    protected ClassSummonEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {

        if (target != null && !this.shouldAttackTarget(target)) {
            target = null;
        }

        super.setTarget(target);
    }

    public boolean shouldAttackTarget(LivingEntity target) {
        if (!target.isAlive()) {
            return false;
        }

        if (target == this) {
            return false;
        }

        if (this.isOwnedBy(target)) {
            return false;
        }

        if (target instanceof TamableAnimal tamable && this.isTame() && tamable.isTame() && this.getOwner() != null && tamable.getOwner() != null && this.getOwner().equals(tamable.getOwner())) {
            return false;
        }

        return true;
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
