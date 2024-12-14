package de.dafuqs.spectrum.blocks.crystallarieum;

import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.*;
import net.minecraft.client.render.model.json.*;
import net.minecraft.client.util.math.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;

@Environment(EnvType.CLIENT)
public class CrystallarieumBlockEntityRenderer<T extends CrystallarieumBlockEntity> implements BlockEntityRenderer<T> {

	@SuppressWarnings("unused")
    public CrystallarieumBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
	}
	
	@Override
	public void render(CrystallarieumBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		MinecraftClient client = MinecraftClient.getInstance();
		ItemStack inkStorageStack = entity.getStack(CrystallarieumBlockEntity.INK_STORAGE_STACK_SLOT_ID);
		if(!inkStorageStack.isEmpty()) {
			matrices.push();
			
			float time = entity.getWorld().getTime() % 50000 + tickDelta;
			double height = 1 + Math.sin((time) / 8.0) / 6.0; // item height
			
			matrices.translate(0.5, 1.0 + height, 0.5);
			matrices.multiply(client.getBlockEntityRenderDispatcher().camera.getRotation());
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
			MinecraftClient.getInstance().getItemRenderer().renderItem(inkStorageStack, ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
			matrices.pop();
		}
		
		ItemStack catalystStack = entity.getStack(CrystallarieumBlockEntity.CATALYST_SLOT_ID);
		if (!catalystStack.isEmpty()) {
			matrices.push();
			
			int count = catalystStack.getCount();
			if (count > 0) {
				matrices.translate(0.65, 0.95, 0.65);
				matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
				matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(70));
				MinecraftClient.getInstance().getItemRenderer().renderItem(catalystStack, ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
				
				if (count > 4) {
					matrices.translate(0.45, 0.0, 0.01);
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(140));
					MinecraftClient.getInstance().getItemRenderer().renderItem(catalystStack, ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
					
					if (count > 16) {
						matrices.translate(0.2, 0.5, 0.01);
						matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(100));
						MinecraftClient.getInstance().getItemRenderer().renderItem(catalystStack, ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
						
						if (count > 32) {
							matrices.translate(-0.55, 0.0, 0.01);
							matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(40));
							MinecraftClient.getInstance().getItemRenderer().renderItem(catalystStack, ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
							
							if (count > 48) {
								matrices.translate(0.6, 0.0, 0.01);
								matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(170));
								MinecraftClient.getInstance().getItemRenderer().renderItem(catalystStack, ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
							}
						}
					}
				}
			}
			
			matrices.pop();
		}
	}
	
	@Override
	public int getRenderDistance() {
		return 16;
	}
	
}
