package sqyro.classessmp.mixin;

import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sqyro.classessmp.effect.ClassesEffects;

@Mixin(Mob.class)
public class MobMixin {
    @Inject(method = "serverAiStep", at = @At("HEAD"), cancellable = true)
    private void classessmp$stopMobAI(CallbackInfo callbackInfo) {
        if (((Mob)(Object)this).hasEffect(ClassesEffects.FREEZING) || ((Mob)(Object)this).hasEffect(ClassesEffects.ROOTING)) {
            callbackInfo.cancel();
        }
    }
}