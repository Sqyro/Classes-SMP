package sqyro.classessmp.entities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import sqyro.classessmp.items.SpawnerItem;

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
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!this.level().isClientSide() && this.isOwnedBy(player)) {
            if (player.isShiftKeyDown() && stack.getItem() instanceof SpawnerItem spawnerItem && spawnerItem.isFor(this.getType())) {
                this.clearAttackTarget();
                return InteractionResult.SUCCESS;
            }

            this.setOrderedToSit(!this.isOrderedToSit());
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    public void clearAttackTarget() {
        this.setTarget(null);
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