package sqyro.classessmp.entities.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;

import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.entities.CardProjectileEntity;

public class CardProjectileRenderer extends EntityRenderer<CardProjectileEntity, EntityRenderState> {
    protected CardProjectileModel model;

    public CardProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CardProjectileModel(context.bakeLayer(CardProjectileModel.CARD));
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void submit(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        poseStack.pushPose();

        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));

        collector.submitModelPart(
                model.root(),
                poseStack,
                RenderTypes.entityCutoutNoCull(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/entity/card_projectile_texture.png")),
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                null
        );

        poseStack.popPose();

        super.submit(state, poseStack, collector, cameraState);
    }
}
