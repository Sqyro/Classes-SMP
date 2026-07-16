package sqyro.classessmp.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import sqyro.classessmp.client.FreezeClientCache;
import sqyro.classessmp.client.FreezingRenderState;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void classessmp$extractFreezing(LivingEntity Entity, LivingEntityRenderState State, float tickDelta, CallbackInfo callbackInfo) {
        ((FreezingRenderState) State).classessmp$setFreezing(FreezeClientCache.isFrozen(Entity.getId()));
    }

    @ModifyExpressionValue(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;multiply(II)I"))
    private int classessmp$freezeTint(int Color, LivingEntityRenderState State) {
        if (((FreezingRenderState) State).classessmp$isFreezing()) {
            return ARGB.multiply(Color, ARGB.color(255, 120, 200, 255));
        }

        return Color;
    }
}