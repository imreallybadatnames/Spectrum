package de.dafuqs.spectrum.entity.render;

import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.helpers.ColorHelper;
import net.fabricmc.api.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.util.math.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import org.joml.*;

@Environment(EnvType.CLIENT)
public class LightMineEntityRenderer extends EntityRenderer<LightMineEntity> {

    public LightMineEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }
    
    @Override
    public void render(LightMineEntity mine, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
	
		var age = mine.age;
        var alpha = MathHelper.clamp(1 - MathHelper.lerp(tickDelta, mine.getVanishingProgress(age - 1), mine.getVanishingProgress(age)), 0F, 1F);
        var scaleFactor = MathHelper.sin((age + tickDelta) / 8F) / 6F + mine.getScaleOffset();
	
		matrices.multiply(this.dispatcher.getRotation());
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180f));
		matrices.scale(scaleFactor, scaleFactor, 1);
		matrices.translate(-0.5F, -0.5F, 0);
	
		var consumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentCull(getTexture(mine)));
		var matrix = matrices.peek();
		var positions = matrix.getPositionMatrix();
	
		Vector3f color = ColorHelper.colorIntToVec(mine.getColor());
		consumer.vertex(positions, 0, 0, 0).color(color.x(), color.y(), color.z(), alpha).texture(0, 1).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(matrix, 0, 1, 0);
		consumer.vertex(positions, 1, 0, 0).color(color.x(), color.y(), color.z(), alpha).texture(1, 1).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(matrix, 0, 1, 0);
		consumer.vertex(positions, 1, 1, 0).color(color.x(), color.y(), color.z(), alpha).texture(1, 0).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(matrix, 0, 1, 0);
		consumer.vertex(positions, 0, 1, 0).color(color.x(), color.y(), color.z(), alpha).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(matrix, 0, 1, 0);
	
		matrices.pop();
	
		super.render(mine, yaw, tickDelta, matrices, vertexConsumers, light);
	}
    
    @Override
    public Identifier getTexture(LightMineEntity entity) {
        return entity.getTexture();
    }
    
}
