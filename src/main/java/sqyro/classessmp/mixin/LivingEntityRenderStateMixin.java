package sqyro.classessmp.mixin;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import sqyro.classessmp.client.FreezingRenderState;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateMixin implements FreezingRenderState {
    private boolean classessmp$isFreezing;

    @Override
    public void classessmp$setFreezing(boolean Freezing) {
        this.classessmp$isFreezing = Freezing;
    }

    @Override
    public boolean classessmp$isFreezing() {
        return this.classessmp$isFreezing;
    }
}
