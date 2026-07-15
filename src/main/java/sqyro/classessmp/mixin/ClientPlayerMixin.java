package sqyro.classessmp.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sqyro.classessmp.effect.ClassesEffects;

@Mixin(LocalPlayer.class)
public class ClientPlayerMixin {
    @Inject(method = "aiStep", at = @At("TAIL"))
    private void classessmp$freezePhysics(CallbackInfo callbackInfo) {
        LocalPlayer Player = (LocalPlayer)(Object)this;

        if (Player.hasEffect(ClassesEffects.FREEZING)) {
            Player.setDeltaMovement(Vec3.ZERO);
            Player.setOnGround(true);
        }
    }
}