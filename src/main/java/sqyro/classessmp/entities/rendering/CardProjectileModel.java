package sqyro.classessmp.entities.rendering;


import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

public class CardProjectileModel extends EntityModel<EntityRenderState> {
	public static final ModelLayerLocation CARD = new ModelLayerLocation(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "card_projectile"), "main");

	private final ModelPart card;

	public CardProjectileModel(ModelPart root) {
		super(root);
		this.card = root.getChild("card");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition card = modelPartData.addOrReplaceChild("card", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -0.0F, -3.0F, 4.0F, 1.0F, 6.0F, CubeDeformation.NONE), PartPose.offset(0.0F, -1.0F, 0.0F));
		return LayerDefinition.create(modelData, 32, 32);
	}
}