package sqyro.classessmp.entities.rendering;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.entities.LaserChickenEntity;

public class LaserChickenRenderer extends MobRenderer<LaserChickenEntity, LaserChickenRenderState, LaserChickenModel> {
    public LaserChickenRenderer(EntityRendererProvider.Context context) {
        super(context, new LaserChickenModel(context.bakeLayer(LaserChickenModel.LASER_CHICKEN)), 0.75f);
    }

    @Override
    public Identifier getTextureLocation(LaserChickenRenderState livingEntityRenderState) {
        return Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/entity/laser_chicken_texture.png");
    }

    @Override
    public LaserChickenRenderState createRenderState() {
        return new LaserChickenRenderState();
    }

    @Override
    public void extractRenderState(LaserChickenEntity livingEntity, LaserChickenRenderState livingEntityRenderState, float f) {
        super.extractRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.idleAnimationState.copyFrom(livingEntity.idleAnimationState);
        livingEntityRenderState.attackAnimationState.copyFrom(livingEntity.attackAnimationState);
    }
}
