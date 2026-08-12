package sqyro.classessmp.entities.rendering;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

public class LaserChickenModel extends EntityModel<LaserChickenRenderState> {
	public static final ModelLayerLocation LASER_CHICKEN = new ModelLayerLocation(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "laser_chicken"), "main");
	private final ModelPart chicken_itself;
	private final ModelPart head;
	private final ModelPart bill;
	private final ModelPart chin;
	private final ModelPart body;
	private final ModelPart left_wing;
	private final ModelPart right_wing;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart laser_turret;
	private final ModelPart right_side;
	private final ModelPart left_side;
	private final ModelPart base;
	private final ModelPart turret;

	private final KeyframeAnimation walkingAnimation;
	private final KeyframeAnimation idlingAnimation;
	private final KeyframeAnimation attackingAnimation;

	public LaserChickenModel(ModelPart root) {
		super(root);
		this.chicken_itself = root.getChild("chicken_itself");
		this.head = this.chicken_itself.getChild("head");
		this.bill = this.chicken_itself.getChild("bill");
		this.chin = this.chicken_itself.getChild("chin");
		this.body = this.chicken_itself.getChild("body");
		this.left_wing = this.chicken_itself.getChild("left_wing");
		this.right_wing = this.chicken_itself.getChild("right_wing");
		this.left_leg = this.chicken_itself.getChild("left_leg");
		this.right_leg = this.chicken_itself.getChild("right_leg");
		this.laser_turret = root.getChild("laser_turret");
		this.right_side = this.laser_turret.getChild("right_side");
		this.left_side = this.laser_turret.getChild("left_side");
		this.base = this.laser_turret.getChild("base");
		this.turret = this.laser_turret.getChild("turret");

		this.walkingAnimation = LaserChickenAnimations.walk.bake(root);
		this.idlingAnimation = LaserChickenAnimations.idle.bake(root);
		this.attackingAnimation = LaserChickenAnimations.attack.bake(root);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition chicken_itself = modelPartData.addOrReplaceChild("chicken_itself", CubeListBuilder.create(), PartPose.offset(-1.5F, 15.0F, -4.5F));

		PartDefinition head = chicken_itself.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(1.5999F, 0.0F, -0.4256F));

		PartDefinition bill = chicken_itself.addOrReplaceChild("bill", CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, CubeDeformation.NONE), PartPose.offset(1.5999F, 0.0F, -0.4256F));

		PartDefinition chin = chicken_itself.addOrReplaceChild("chin", CubeListBuilder.create().texOffs(14, 4).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE), PartPose.offset(1.5999F, 0.0F, -0.4256F));

		PartDefinition body = chicken_itself.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.5999F, 1.0F, 3.5744F, 1.5708F, 0.0F, 0.0F));

		PartDefinition left_wing = chicken_itself.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, CubeDeformation.NONE), PartPose.offset(5.5999F, -2.0F, 3.5744F));

		PartDefinition right_wing = chicken_itself.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, CubeDeformation.NONE), PartPose.offset(-2.4001F, -2.0F, 3.5744F));

		PartDefinition left_leg = chicken_itself.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(2.5999F, 4.0F, 4.5744F));

		PartDefinition right_leg = chicken_itself.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(-0.4001F, 4.0F, 4.5744F));

		PartDefinition laser_turret = modelPartData.addOrReplaceChild("laser_turret", CubeListBuilder.create(), PartPose.offset(0.0F, 20.5F, -1.0F));

		PartDefinition right_side = laser_turret.addOrReplaceChild("right_side", CubeListBuilder.create().texOffs(46, 1).addBox(-0.5F, 0.5F, -1.5F, 1.5F, 0.5F, 3.0F, CubeDeformation.NONE), PartPose.offset(-4.0F, -8.5F, 0.0F));

		PartDefinition cube_r1 = right_side.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(46, 1).addBox(-2.5F, -2.0F, -1.0F, 3.0F, 0.5F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.5F, 1.5F, -0.5F, 0.0F, 0.0F, -1.5708F));

		PartDefinition left_side = laser_turret.addOrReplaceChild("left_side", CubeListBuilder.create().texOffs(46, 1).addBox(-1.0F, 0.5F, -1.5F, 1.5F, 0.5F, 3.0F, CubeDeformation.NONE), PartPose.offset(4.0F, -8.5F, 0.0F));

		PartDefinition cube_r2 = left_side.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(46, 1).addBox(-2.5F, -2.0F, -1.0F, 3.0F, 0.5F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(2.0F, 1.5F, -0.5F, 0.0F, 0.0F, -1.5708F));

		PartDefinition base = laser_turret.addOrReplaceChild("base", CubeListBuilder.create().texOffs(46, 1).addBox(-3.0F, -12.0F, -1.5F, 6.0F, 1.0F, 3.0F, CubeDeformation.NONE)
		.texOffs(46, 1).addBox(-2.75F, -12.5F, -1.25F, 5.5F, 0.5F, 2.5F, CubeDeformation.NONE)
		.texOffs(47, 2).addBox(-2.5F, -12.75F, -1.0F, 5.0F, 0.25F, 2.0F, CubeDeformation.NONE)
		.texOffs(48, 2).addBox(-1.0F, -13.0F, -1.0F, 2.0F, 0.25F, 2.0F, CubeDeformation.NONE)
		.texOffs(50, 3).addBox(-0.5F, -14.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 3.5F, 0.0F));

		PartDefinition cube_r3 = base.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(46, 1).addBox(-3.0F, -0.25F, -1.5F, 1.0F, 0.25F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -12.5F, -4.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r4 = base.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(47, 1).addBox(-3.0F, -0.25F, -1.5F, 1.0F, 0.25F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -12.5F, -1.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r5 = base.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(45, 0).addBox(-3.0F, -0.5F, -1.75F, 1.0F, 0.5F, 3.5F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -12.0F, -4.25F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r6 = base.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(46, 0).addBox(-3.0F, -0.5F, -1.75F, 1.0F, 0.5F, 3.5F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -12.0F, -0.75F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r7 = base.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(45, 0).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 1.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -11.0F, -4.5F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r8 = base.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(46, 0).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 1.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -11.0F, -0.5F, 0.0F, 1.5708F, 0.0F));

		PartDefinition turret = laser_turret.addOrReplaceChild("turret", CubeListBuilder.create(), PartPose.offset(0.0F, -10.5F, 0.0F));

		PartDefinition cube_r9 = turret.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(44, 4).addBox(0.0F, -2.75F, 2.5F, 0.25F, 1.5F, 4.0F, CubeDeformation.NONE)
		.texOffs(44, 4).addBox(-2.25F, -2.75F, 2.5F, 0.25F, 1.5F, 4.0F, CubeDeformation.NONE)
		.texOffs(52, 26).addBox(-2.0F, -3.0F, 2.5F, 2.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.0F, -2.5F, -9.5F, -0.1963F, 0.0F, 0.0F));

		PartDefinition cube_r10 = turret.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(43, 4).addBox(-0.75F, -1.285F, 2.65F, 1.5F, 0.25F, 3.95F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -2.25F, -9.7F, -0.1963F, 0.0F, 0.0F));

		PartDefinition cube_r11 = turret.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(43, 4).addBox(-0.75F, -1.2F, 2.6F, 1.5F, 0.25F, 3.95F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -4.5F, -9.2F, -0.1963F, 0.0F, 0.0F));

		PartDefinition cube_r12 = turret.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(38, 0).addBox(-2.5F, -2.5F, -1.5F, 5.0F, 3.0F, 8.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -2.0F, -1.5F, -0.1963F, 0.0F, 0.0F));

		PartDefinition cube_r13 = turret.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(50, 3).addBox(-0.5F, -1.0F, 0.05F, 1.0F, 1.25F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -0.25F, -0.5F, -0.1963F, 0.0F, 0.0F));
		return LayerDefinition.create(modelData, 64, 32);
	}

	@Override
	public void setupAnim(LaserChickenRenderState state) {
		super.setupAnim(state);
		this.setHeadAngles(state.xRot, state.yRot);

		this.walkingAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 2f, 2.5f);
		this.idlingAnimation.apply(state.idleAnimationState, state.ageInTicks, 1f);
		this.attackingAnimation.apply(state.attackAnimationState, state.ageInTicks, 1f);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = Math.clamp(headYaw, -30.0F, 30.0F);
		headPitch = Math.clamp(headPitch, -25.0F, 45.0F);

		this.head.xRot = headYaw * 0.017453292F;
		this.head.yRot = headPitch * 0.017453292F;
		this.bill.xRot = headYaw * 0.017453292F;
		this.bill.yRot = headPitch * 0.017453292F;
		this.chin.xRot = headYaw * 0.017453292F;
		this.chin.yRot = headPitch * 0.017453292F;
	}
}