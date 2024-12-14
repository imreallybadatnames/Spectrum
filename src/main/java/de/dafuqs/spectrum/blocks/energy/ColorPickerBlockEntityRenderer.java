package de.dafuqs.spectrum.blocks.energy;

import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.*;
import net.minecraft.client.render.model.json.*;
import net.minecraft.client.util.math.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;

@Environment(EnvType.CLIENT)
public class ColorPickerBlockEntityRenderer<T extends ColorPickerBlockEntity> implements BlockEntityRenderer<T> {
	
	@SuppressWarnings("unused")
    public ColorPickerBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
	}
	
	@Override
	public void render(ColorPickerBlockEntity blockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
		MinecraftClient client = MinecraftClient.getInstance();
		// The item on top
		ItemStack stack = blockEntity.getStack(0);
		ItemStack stack2 = blockEntity.getStack(1);
		// lying on top
		if (!stack.isEmpty()) {
			matrixStack.push();
			matrixStack.translate(0.5, 0.7, 0.6);
			matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
			matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
			MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.GROUND, light, overlay, matrixStack, vertexConsumerProvider, blockEntity.getWorld(), 0);
			matrixStack.pop();
		}
		// floating in air
		if (!stack2.isEmpty()) {
			matrixStack.push();
			
			float time = blockEntity.getWorld().getTime() % 50000 + tickDelta;
			double height = Math.sin((time) / 8.0) / 6.0; // item height
			
			matrixStack.translate(0.5, 1.0 + height, 0.5);
			matrixStack.multiply(client.getBlockEntityRenderDispatcher().camera.getRotation());
			matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
			MinecraftClient.getInstance().getItemRenderer().renderItem(stack2, ModelTransformationMode.GROUND, light, overlay, matrixStack, vertexConsumerProvider, blockEntity.getWorld(), 0);
			matrixStack.pop();
		}
	}
	
}
