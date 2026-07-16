package sqyro.classessmp.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sqyro.classessmp.effect.ClassesEffects;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Inject(method = "turnPlayer", at = @At("HEAD"), cancellable = true)
    private void classessmp$stopMouseLook(double d, CallbackInfo callbackInfo) {
        Minecraft Instance = Minecraft.getInstance();
        if (Instance.player.hasEffect(ClassesEffects.FREEZING)) {
            callbackInfo.cancel();
        }
    }
}