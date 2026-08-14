package sqyro.classessmp.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sqyro.classessmp.client.GUI.screen.CaseRollScreen;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(method = "renderHotbarAndDecorations", at = @At("HEAD"), cancellable = true)
    private void classessmp$hideHotbar(GuiGraphics graphics, DeltaTracker deltaTracker, CallbackInfo callbackInfo) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen instanceof CaseRollScreen) {
            callbackInfo.cancel();
        }
    }
}
