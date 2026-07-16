package sqyro.classessmp.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sqyro.classessmp.effect.ClassesEffects;
import sqyro.classessmp.network.ClassesNetworking;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    private boolean classessmp$wasFrozen;

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void classessmp$freezeEntity(CallbackInfo callbackInfo) {
        LivingEntity Entity = (LivingEntity)(Object)this;

        boolean Frozen = Entity.hasEffect(ClassesEffects.FREEZING);

        if (Frozen != classessmp$wasFrozen) {
            classessmp$wasFrozen = Frozen;
            if (!Entity.level().isClientSide() && Entity.level() instanceof ServerLevel serverLevel) {
                ClassesNetworking.sendFreezeSync(serverLevel, Entity, Frozen);
            }
        }

        if (Entity.hasEffect(ClassesEffects.FREEZING)) {
            Entity.setDeltaMovement(Vec3.ZERO);
            Entity.setJumping(false);
            Entity.fallDistance = 0;

            Entity.setYRot(Entity.yRotO);
            Entity.setXRot(Entity.xRotO);

            Entity.setYHeadRot(Entity.yRotO);
            Entity.setYBodyRot(Entity.yRotO);
        }
    }
}