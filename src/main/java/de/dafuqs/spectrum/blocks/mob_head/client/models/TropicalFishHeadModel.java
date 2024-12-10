package de.dafuqs.spectrum.blocks.mob_head.client.models;

import de.dafuqs.spectrum.blocks.mob_head.client.*;
import net.fabricmc.api.*;
import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.*;
import net.minecraft.util.*;
import net.minecraft.util.math.ColorHelper;

@Environment(EnvType.CLIENT)
public class TropicalFishHeadModel extends SpectrumSkullModel {
	
	private static final Identifier PATTERN_TEXTURE = Identifier.of("textures/entity/fish/tropical_a_pattern_1.png");
	protected final ModelPart pattern;
	
	public TropicalFishHeadModel(ModelPart root, ModelPart pattern) {
		super(root);
		this.pattern = pattern.getChild(EntityModelPartNames.HEAD);
	}
	
	public static ModelData getModelData(Dilation dilation) {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		
		ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -3.0F, -3.0F, 2.0F, 3.0F, 6.0F, dilation), ModelTransform.NONE);
		head.addChild("tail", ModelPartBuilder.create().uv(22, -6).cuboid(0.0F, -3.0F, 3.0F, 0.0F, 3.0F, 6.0F, dilation), ModelTransform.NONE);
		head.addChild("right_fin", ModelPartBuilder.create().uv(2, 16).cuboid(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, dilation), ModelTransform.of(-1.0F, -1.0F, 0.0F, 0.0F, ((float) Math.PI / 4F), 0.0F));
		head.addChild("left_fin", ModelPartBuilder.create().uv(2, 12).cuboid(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, dilation), ModelTransform.of(1.0F, -1.0F, 0.0F, 0.0F, (-(float) Math.PI / 4F), 0.0F));
		head.addChild("top_fin", ModelPartBuilder.create().uv(10, -5).cuboid(0.0F, -6.0F, -3.0F, 0.0F, 3.0F, 6.0F, dilation), ModelTransform.NONE);
		
		return modelData;
	}
	
	public static TexturedModelData getTexturedModelData() {
		return TexturedModelData.of(getModelData(Dilation.NONE), 32, 32);
	}
	
	public static TexturedModelData getTexturedModelDataPattern() {
		return TexturedModelData.of(getModelData(new Dilation(0.008F)), 32, 32);
	}
	
	@Override
	public void setHeadRotation(float animationProgress, float yaw, float pitch) {
		super.setHeadRotation(animationProgress, yaw, pitch);
		this.pattern.yaw = yaw * ROTATION_VEC;
		this.pattern.pitch = pitch * ROTATION_VEC;
	}
	
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, VertexConsumerProvider vertexConsumerProvider, int light, int overlay, int argb) {
		float scale = getScale();
		matrices.scale(scale, scale, scale);

		int alpha = ColorHelper.Argb.getAlpha(argb);
		int red = ColorHelper.Argb.withAlpha(alpha, DyeColor.RED.getEntityColor());
		this.render(matrices, vertices, light, overlay, red);
		
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(PATTERN_TEXTURE));
		int blue = ColorHelper.Argb.withAlpha(alpha, DyeColor.BLUE.getEntityColor());
		this.pattern.render(matrices, vertexConsumer, light, overlay, blue);
	}
	
}
