package sqyro.classessmp.entities.rendering;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

public class ElephantModel extends EntityModel<ElephantRenderState> {
	public static final ModelLayerLocation ELEPHANT = new ModelLayerLocation(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "elephant"), "main");
	private final ModelPart legs;
	private final ModelPart leg2;
	private final ModelPart leg3;
	private final ModelPart leg4;
	private final ModelPart leg1;
	private final ModelPart group;
	private final ModelPart ohren;
	private final ModelPart rüssel;
	private final ModelPart schwanz;
	private final ModelPart body;

	private final KeyframeAnimation walkingAnimation;
	private final KeyframeAnimation idlingAnimation;

	public ElephantModel(ModelPart root) {
		super(root);
        this.legs = root.getChild("legs");
		this.leg2 = this.legs.getChild("leg2");
		this.leg3 = this.legs.getChild("leg3");
		this.leg4 = this.legs.getChild("leg4");
		this.leg1 = this.legs.getChild("leg1");
		this.group = root.getChild("group");
		this.ohren = this.group.getChild("ohren");
		this.rüssel = this.group.getChild("rüssel");
		this.schwanz = root.getChild("schwanz");
		this.body = root.getChild("body");

		this.walkingAnimation = ElephantAnimations.walking.bake(root);
		this.idlingAnimation = ElephantAnimations.idle.bake(root);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition legs = modelPartData.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));

		PartDefinition leg2 = legs.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(14, 17).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(2.0F, -1.5F, 4.0F));

		PartDefinition leg3 = legs.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 25).addBox(-3.0F, -0.5F, -1.0F, 3.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(-2.0F, -1.5F, -4.0F));

		PartDefinition leg4 = legs.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(12, 25).addBox(-2.0F, 0.5F, -1.0F, 3.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(-3.0F, -2.5F, 5.0F));

		PartDefinition leg1 = legs.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(24, 25).addBox(-1.6F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(0.6F, -2.0F, -3.5F));

		PartDefinition group = modelPartData.addOrReplaceChild("group", CubeListBuilder.create(), PartPose.offset(0.0F, 25.0F, 12.0F));

		PartDefinition cube_r1 = group.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 17).addBox(-3.0F, -5.0F, -1.0F, 4.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.55F, -7.0F, -4.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition ohren = group.addOrReplaceChild("ohren", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -4.0F));

		PartDefinition cube_r2 = ohren.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(14, 33).addBox(-3.0F, -4.0F, -1.0F, 4.0F, 4.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-4.8F, -2.0F, 0.5F, 0.0F, -0.3054F, 0.0F));

		PartDefinition cube_r3 = ohren.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(6, 33).addBox(-3.0F, -4.0F, -1.0F, 4.0F, 4.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(3.6F, -2.0F, -0.3F, 0.0F, 0.4363F, 0.0F));

		PartDefinition rüssel = group.addOrReplaceChild("rüssel", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r4 = rüssel.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(32, 17).addBox(-2.0F, -6.0F, -2.0F, 2.0F, 5.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.5F, -1.0F, 1.7F, 0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r5 = rüssel.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(26, 17).addBox(-2.0F, -6.0F, -2.0F, 2.0F, 5.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.5F, -4.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition schwanz = modelPartData.addOrReplaceChild("schwanz", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, -8.0F));

		PartDefinition cube_r6 = schwanz.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 33).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.0F, 0.0F, 1.5F, -0.48F, 0.0F, 0.0F));

		PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -9.0F, -5.0F, 7.0F, 5.0F, 12.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 24.0F, 0.0F));
		return LayerDefinition.create(modelData, 64, 64);
	}

	@Override
	public void setupAnim(ElephantRenderState state) {
		super.setupAnim(state);

		this.walkingAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 2f, 2.5f);
		this.idlingAnimation.apply(state.idleAnimationState, state.ageInTicks, 1f);
	}
}