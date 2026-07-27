package sqyro.classessmp.mixin;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import sqyro.classessmp.client.FreezingRenderState;
import sqyro.classessmp.client.RootingRenderState;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateMixin implements FreezingRenderState, RootingRenderState {
    private boolean classessmp$isFreezing;
    private boolean classessmp$isRooting;

    @Override
    public void classessmp$setFreezing(boolean Freezing) {
        this.classessmp$isFreezing = Freezing;
    }

    @Override
    public boolean classessmp$isFreezing() {
        return this.classessmp$isFreezing;
    }

    @Override
    public void classessmp$setRooting(boolean Rooting) {
        this.classessmp$isRooting = Rooting;
    }

    @Override
    public boolean classessmp$isRooting() {
        return this.classessmp$isRooting;
    }
}
