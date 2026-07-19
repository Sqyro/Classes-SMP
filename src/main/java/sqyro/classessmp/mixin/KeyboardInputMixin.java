package sqyro.classessmp.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sqyro.classessmp.effect.ClassesEffects;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void classessmp$freezeInput(CallbackInfo callbackInfo) {
        Minecraft Instance = Minecraft.getInstance();

        ClientInputAccessor Accessor = (ClientInputAccessor) this;

        if (Instance.player != null && Instance.player.hasEffect(ClassesEffects.FREEZING)) {
            Accessor.setKeyPresses(Input.EMPTY);
            Accessor.setMoveVector(Vec2.ZERO);
        }

        if (Instance.player.hasEffect(ClassesEffects.SHOCKED)) {
            Input keys = Accessor.getKeyPresses();
            Accessor.setKeyPresses(new Input(keys.forward(), keys.backward(), keys.left(), keys.right(), false, keys.shift(), false));
        }
    }
}