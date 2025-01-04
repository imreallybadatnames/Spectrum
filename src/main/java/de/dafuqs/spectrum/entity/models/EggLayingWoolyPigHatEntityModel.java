package de.dafuqs.spectrum.entity.models;

import de.dafuqs.spectrum.entity.entity.*;
import net.fabricmc.api.*;
import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.*;

@Environment(EnvType.CLIENT)
public class EggLayingWoolyPigHatEntityModel extends EntityModel<EggLayingWoolyPigEntity> {
	
	private final ModelPart torso;
	private final ModelPart head;
	
	public EggLayingWoolyPigHatEntityModel(ModelPart root) {
		super();
		this.torso = root.getChild("torso");
		this.head = torso.getChild("head");
	}
	
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData torso = modelPartData.addChild("torso", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		
		torso.addChild("head", ModelPartBuilder.create()
				.uv(45, 0).cuboid(-5.02F, -7.0F, -7.0F, 10.0F, 7.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -13.0F, -7.0F));
		
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
	public void setAngles(EggLayingWoolyPigEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.pitch = this.headPitchModifier;
		this.head.yaw = netHeadYaw * 0.017453292F;
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