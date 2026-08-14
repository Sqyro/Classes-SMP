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

	private final ModelPart body;
	private final ModelPart hat;
	private final ModelPart head;
	private final ModelPart right_ear;
	private final ModelPart left_ear;
	private final ModelPart lower_nose;
	private final ModelPart upper_nose;
	private final ModelPart left_eyebrow;
	private final ModelPart right_eyebrow;
	private final ModelPart left_arm;
	private final ModelPart lower_left_arm;
	private final ModelPart right_arm;
	private final ModelPart lower_right_arm;
	private final ModelPart tail;
	private final ModelPart upper_body;
	private final ModelPart lower_body;
	private final ModelPart left_leg;
	private final ModelPart lower_left_leg;
	private final ModelPart right_leg;
	private final ModelPart lower_right_leg;

	private final KeyframeAnimation walkingAnimation;
	private final KeyframeAnimation idlingAnimation;

	public ElephantModel(ModelPart root) {
		super(root);

		this.body = root.getChild("body");
		this.hat = this.body.getChild("hat");
		this.head = this.body.getChild("head");
		this.right_ear = this.body.getChild("right_ear");
		this.left_ear = this.body.getChild("left_ear");
		this.lower_nose = this.body.getChild("lower_nose");
		this.upper_nose = this.body.getChild("upper_nose");
		this.left_eyebrow = this.body.getChild("left_eyebrow");
		this.right_eyebrow = this.body.getChild("right_eyebrow");
		this.left_arm = this.body.getChild("left_arm");
		this.lower_left_arm = this.left_arm.getChild("lower_left_arm");
		this.right_arm = this.body.getChild("right_arm");
		this.lower_right_arm = this.right_arm.getChild("lower_right_arm");
		this.tail = this.body.getChild("tail");
		this.upper_body = this.body.getChild("upper_body");
		this.lower_body = this.body.getChild("lower_body");
		this.left_leg = this.body.getChild("left_leg");
		this.lower_left_leg = this.left_leg.getChild("lower_left_leg");
		this.right_leg = this.body.getChild("right_leg");
		this.lower_right_leg = this.right_leg.getChild("lower_right_leg");

		this.walkingAnimation = ElephantAnimations.walking.bake(root);
		this.idlingAnimation = ElephantAnimations.idle.bake(root);
	}
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-0.1833F, -1.8468F, -0.5665F));

		PartDefinition hat = body.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(50, 84).addBox(-2.75F, 0.75F, -2.75F, 5.5F, 1.0F, 5.5F, CubeDeformation.NONE)
		.texOffs(74, 84).addBox(-2.0F, -3.25F, -2.0F, 4.0F, 4.0F, 4.0F, CubeDeformation.NONE), PartPose.offset(-2.5667F, -15.6532F, 0.8165F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 49).addBox(-6.0F, -5.55F, 1.1054F, 12.0F, 8.0F, 9.0F, CubeDeformation.NONE), PartPose.offset(0.1833F, -8.6032F, -3.5389F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(90, 84).addBox(-3.75F, -3.0F, 2.0F, 3.0F, 3.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-4.25F, 2.45F, -4.6446F, 0.0F, 0.4363F, 0.0F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(64, 92).addBox(-3.0F, -2.5F, 1.75F, 1.5F, 2.0F, 5.25F, CubeDeformation.NONE), PartPose.offsetAndRotation(-4.5F, -4.55F, -5.0446F, -1.5708F, 0.4363F, 0.0F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(90, 99).mirror().addBox(0.75F, -3.0F, 2.0F, 3.0F, 3.0F, 4.0F, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(4.25F, 2.45F, -4.6446F, 0.0F, -0.4363F, 0.0F));

		PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(55, 98).mirror().addBox(1.5F, -2.5F, 1.75F, 1.5F, 2.0F, 5.25F, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(4.5F, -4.55F, -5.0446F, -1.5708F, -0.4363F, 0.0F));

		PartDefinition right_ear = body.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(76, 14).addBox(-6.0F, -5.0F, -0.5F, 12.0F, 10.0F, 1.0F, CubeDeformation.NONE), PartPose.offset(12.1833F, -9.1532F, 2.0665F));

		PartDefinition left_ear = body.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(24, 84).addBox(-6.0F, -5.0F, -0.5F, 12.0F, 10.0F, 1.0F, CubeDeformation.NONE), PartPose.offset(-11.8167F, -9.1532F, 2.0665F));

		PartDefinition lower_nose = body.addOrReplaceChild("lower_nose", CubeListBuilder.create(), PartPose.offset(0.1833F, 1.7774F, -8.556F));

		PartDefinition cube_r5 = lower_nose.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(76, 0).addBox(1.0F, -6.0F, -6.0F, 4.0F, 4.0F, 10.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-3.0F, 0.0694F, 4.1225F, 1.309F, 0.0F, 0.0F));

		PartDefinition upper_nose = body.addOrReplaceChild("upper_nose", CubeListBuilder.create(), PartPose.offset(0.1833F, -5.9583F, -4.6405F));

		PartDefinition cube_r6 = upper_nose.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 66).addBox(0.0F, -7.0F, -6.0F, 6.0F, 6.0F, 10.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-3.0F, 1.8051F, 3.707F, 0.8727F, 0.0F, 0.0F));

		PartDefinition left_eyebrow = body.addOrReplaceChild("left_eyebrow", CubeListBuilder.create(), PartPose.offset(-4.1359F, -11.8076F, -2.2042F));

		PartDefinition cube_r7 = left_eyebrow.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(32, 66).addBox(-3.0F, -6.5F, -5.0F, 3.1F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.1808F, 5.7544F, 5.0207F, 0.0854F, -0.0181F, 0.2698F));

		PartDefinition right_eyebrow = body.addOrReplaceChild("right_eyebrow", CubeListBuilder.create(), PartPose.offset(4.5025F, -11.8076F, -2.2042F));

		PartDefinition cube_r8 = right_eyebrow.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(76, 25).addBox(-0.1F, -6.5F, -5.0F, 3.1F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.1808F, 5.7544F, 5.0207F, 0.0854F, 0.0181F, -0.2698F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(-13.2488F, 3.0362F, 1.5665F));

		PartDefinition cube_r9 = left_arm.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(32, 68).addBox(4.0F, -8.0F, -2.0F, 8.0F, 8.0F, 8.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-7.5679F, -2.1895F, -2.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition lower_left_arm = left_arm.addOrReplaceChild("lower_left_arm", CubeListBuilder.create(), PartPose.offset(-1.1082F, 4.3631F, 0.0F));

		PartDefinition cube_r10 = lower_left_arm.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(78, 48).addBox(5.0F, -8.0F, -1.0F, 6.0F, 10.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-8.4597F, 1.1974F, -2.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(13.6154F, 3.0362F, 1.5665F));

		PartDefinition cube_r11 = right_arm.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 98).mirror().addBox(-12.0F, -8.0F, -2.0F, 8.0F, 8.0F, 8.0F, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(7.5679F, -2.1895F, -2.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition lower_right_arm = right_arm.addOrReplaceChild("lower_right_arm", CubeListBuilder.create(), PartPose.offset(1.1082F, 4.3631F, 0.0F));

		PartDefinition cube_r12 = lower_right_arm.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(78, 48).mirror().addBox(-11.0F, -8.0F, -1.0F, 6.0F, 10.0F, 6.0F, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(8.4597F, 1.1974F, -2.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.1833F, 18.4273F, 9.2591F));

		PartDefinition cube_r13 = tail.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(78, 92).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 7.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -1.3305F, -0.6926F, 0.48F, 0.0F, 0.0F));

		PartDefinition upper_body = body.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 29).addBox(-9.0F, -4.0F, -6.0F, 18.0F, 8.0F, 12.0F, CubeDeformation.NONE), PartPose.offset(0.1833F, -2.1532F, 1.5665F));

		PartDefinition lower_body = body.addOrReplaceChild("lower_body", CubeListBuilder.create().texOffs(0, 0).addBox(-11.5F, -7.0F, -7.5F, 23.0F, 14.0F, 15.0F, CubeDeformation.NONE), PartPose.offset(0.1833F, 8.8468F, 1.0665F));

		PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(42, 49).addBox(-4.5F, -4.7904F, -3.7903F, 9.0F, 5.0F, 9.0F, CubeDeformation.NONE), PartPose.offset(-5.3167F, 20.6372F, 0.8568F));

		PartDefinition lower_left_leg = left_leg.addOrReplaceChild("lower_left_leg", CubeListBuilder.create().texOffs(42, 54).addBox(-4.5F, -2.256F, -3.6812F, 9.0F, 5.0F, 9.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 2.4655F, -0.1091F));

		PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(60, 29).addBox(-4.5F, -4.8818F, -4.2458F, 9.0F, 5.0F, 9.0F, CubeDeformation.NONE), PartPose.offset(5.6833F, 20.7286F, 1.3123F));

		PartDefinition lower_right_leg = right_leg.addOrReplaceChild("lower_right_leg", CubeListBuilder.create().texOffs(60, 34).addBox(-4.5F, -2.3208F, -4.0831F, 9.0F, 5.0F, 9.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 2.4389F, -0.1627F));
		return LayerDefinition.create(modelData, 128, 128);
	}

	@Override
	public void setupAnim(ElephantRenderState state) {
		super.setupAnim(state);

		this.walkingAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 2f, 2.5f);
		this.idlingAnimation.apply(state.idleAnimationState, state.ageInTicks, 1f);
	}
}