package de.dafuqs.spectrum.entity.models;

import de.dafuqs.spectrum.entity.entity.*;
import net.fabricmc.api.*;
import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.*;
import net.minecraft.util.math.*;

@Environment(EnvType.CLIENT)
public class EggLayingWoolyPigEntityModel extends EntityModel<EggLayingWoolyPigEntity> {
	
	protected final ModelPart torso;
	protected final ModelPart head;
	protected final ModelPart left_foreleg;
	protected final ModelPart right_foreleg;
	protected final ModelPart left_backleg;
	protected final ModelPart right_backleg;
	
	public EggLayingWoolyPigEntityModel(ModelPart root) {
		super();
		this.torso = root.getChild("torso");
		this.head = torso.getChild(EntityModelPartNames.HEAD);
		this.left_foreleg = torso.getChild("left_foreleg");
		this.right_foreleg = torso.getChild("right_foreleg");
		this.left_backleg = torso.getChild("left_backleg");
		this.right_backleg = torso.getChild("right_backleg");
	}
	
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData torso = modelPartData.addChild("torso", ModelPartBuilder.create()
				.uv(42, 47).cuboid(-5.0F, -14.0F, -7.0F, 10.0F, 8.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		
		ModelPartData head = torso.addChild("head", ModelPartBuilder.create()
				.uv(56, 24).cuboid(-4.0F, -6.0F, -6.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
				.uv(0, 44).cuboid(-2.5F, -2.0F, -7.0F, 5.0F, 3.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 73).cuboid(2.5F, -8.0F, -5.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
				.uv(4, 73).cuboid(-3.5F, -8.0F, -5.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -13.0F, -7.0F));
		
		head.addChild("ear_r1", ModelPartBuilder.create()
				.uv(0, 0).cuboid(7.0964F, -13.0939F, -10.5F, 3.0F, 7.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 10.0F, 6.0F, 0.0F, 0.0F, -0.3927F));
		
		head.addChild("ear_r2", ModelPartBuilder.create()
				.uv(0, 32).cuboid(-10.0964F, -13.0939F, -10.5F, 3.0F, 7.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 10.0F, 6.0F, 0.0F, 0.0F, 0.3927F));
		
		torso.addChild("left_foreleg", ModelPartBuilder.create()
				.uv(54, 69).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -6.0F, -4.0F));
		
		torso.addChild("right_foreleg", ModelPartBuilder.create()
				.uv(38, 69).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, -6.0F, -4.0F));
		
		torso.addChild("left_backleg", ModelPartBuilder.create()
				.uv(0, 61).cuboid(-3.0F, -2.0F, -3.0F, 5.0F, 6.0F, 6.0F, new Dilation(0.0F))
				.uv(61, 40).cuboid(-3.0F, 4.0F, 0.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -8.0F, 5.0F));
		
		torso.addChild("right_backleg", ModelPartBuilder.create()
				.uv(39, 34).cuboid(-2.0F, -2.0F, -3.0F, 5.0F, 6.0F, 6.0F, new Dilation(0.0F))
				.uv(0, 12).cuboid(0.0F, 4.0F, 0.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, -8.0F, 5.0F));
		
		torso.addChild("tail", ModelPartBuilder.create()
				.uv(22, 61).cuboid(-2.5F, -15.0F, 7.0F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, -1.0F));
		
		return TexturedModelData.of(modelData, 128, 128);
	}
	
	private float headPitchModifier;
	
	@Override
	public void animateModel(EggLayingWoolyPigEntity entity, float limbAngle, float limbDistance, float tickDelta) {
		super.animateModel(entity, limbAngle, limbDistance, tickDelta);
		this.head.pivotY = -13.0F + entity.getNeckAngle(tickDelta) * 9.0F;
		this.headPitchModifier = entity.getHeadAngle(tickDelta);
	}
	
	@Override
	public void setAngles(EggLayingWoolyPigEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.head.pitch = this.headPitchModifier;
		this.head.yaw = headYaw * 0.017453292F;
		this.right_backleg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
		this.left_backleg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
		this.right_foreleg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
		this.left_foreleg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
	}
	
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		if (child) {
			matrices.scale(0.6f, 0.6f, 0.6f);
			matrices.translate(0, 1, 0);
		}
		torso.render(matrices, vertices, light, overlay, color);
	}
	
}