package de.dafuqs.spectrum.blocks.item_bowl;

import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.*;
import net.minecraft.client.render.model.json.*;
import net.minecraft.client.util.math.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;

@Environment(EnvType.CLIENT)
public class ItemBowlBlockEntityRenderer implements BlockEntityRenderer<ItemBowlBlockEntity> {
	
	final double radiant = Math.toRadians(360.0F);
	
	@SuppressWarnings("unused")
    public ItemBowlBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
	}
	
	@Override
	public void render(ItemBowlBlockEntity blockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
		ItemStack stack = blockEntity.getStack(0);
		if (!stack.isEmpty()) {
			float time = blockEntity.getWorld().getTime() % 50000 + tickDelta;
			
			matrixStack.push();
			double currentRadiant = radiant + (radiant * (time / 16.0) / 8.0F);
			double height = Math.sin((time + currentRadiant) / 8.0) / 7.0; // item height
			matrixStack.translate(0.5, 0.8 + height, 0.5); // position offset
			matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(time * 2)); // item stack rotation
			MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.GROUND, light, overlay, matrixStack, vertexConsumerProvider, blockEntity.getWorld(), 0);
			matrixStack.pop();
		}
	}
	
}