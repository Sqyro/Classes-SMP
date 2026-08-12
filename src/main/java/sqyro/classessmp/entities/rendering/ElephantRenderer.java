package sqyro.classessmp.entities.rendering;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.entities.ElephantEntity;

public class ElephantRenderer extends MobRenderer<ElephantEntity, ElephantRenderState, ElephantModel> {
    public ElephantRenderer(EntityRendererProvider.Context context) {
        super(context, new ElephantModel(context.bakeLayer(ElephantModel.ELEPHANT)), 0.75f);
    }

    @Override
    public Identifier getTextureLocation(ElephantRenderState livingEntityRenderState) {
        return Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/entity/elephant/elephant_texture.png");
    }

    @Override
    public ElephantRenderState createRenderState() {
        return new ElephantRenderState();
    }
}