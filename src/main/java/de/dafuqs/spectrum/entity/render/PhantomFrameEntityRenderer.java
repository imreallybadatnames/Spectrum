package de.dafuqs.spectrum.entity.render;

import de.dafuqs.spectrum.entity.*;
import de.dafuqs.spectrum.entity.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.item.*;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.*;
import net.minecraft.client.util.*;
import net.minecraft.client.util.math.*;
import net.minecraft.component.type.*;
import net.minecraft.item.*;
import net.minecraft.item.map.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

public class PhantomFrameEntityRenderer<T extends PhantomFrameEntity> extends ItemFrameEntityRenderer<T> {

	public static final ModelIdentifier NORMAL_FRAME_MODEL_IDENTIFIER = new ModelIdentifier(Identifier.ofVanilla("item_frame"), "map=false");
	public static final ModelIdentifier MAP_FRAME_MODEL_IDENTIFIER = new ModelIdentifier(Identifier.ofVanilla("item_frame"), "map=true");
	public static final ModelIdentifier GLOW_FRAME_MODEL_IDENTIFIER = new ModelIdentifier(Identifier.ofVanilla("glow_item_frame"), "map=false");
	public static final ModelIdentifier MAP_GLOW_FRAME_MODEL_IDENTIFIER = new ModelIdentifier(Identifier.ofVanilla("glow_item_frame"), "map=true");

	private final MinecraftClient client = MinecraftClient.getInstance();
	private final ItemRenderer itemRenderer;

	public PhantomFrameEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	protected int getBlockLight(T entity, BlockPos blockPos) {
		return entity.getType() == SpectrumEntityTypes.GLOW_PHANTOM_FRAME ? Math.max(5, super.getBlockLight(entity, blockPos)) : super.getBlockLight(entity, blockPos);
	}

	@Override
	public void render(T entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
		if (this.hasLabel(entity)) {
			this.renderLabelIfPresent(entity, entity.getDisplayName(), matrixStack, vertexConsumerProvider, light, tickDelta);
		}
		
		matrixStack.push();
		
		Direction direction = entity.getHorizontalFacing();
		Vec3d vec3d = this.getPositionOffset(entity, tickDelta);
		matrixStack.translate(-vec3d.getX(), -vec3d.getY(), -vec3d.getZ());
		double d = 0.46875D;
		matrixStack.translate((double) direction.getOffsetX() * d, (double) direction.getOffsetY() * d, (double) direction.getOffsetZ() * d);
		matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getPitch()));
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - entity.getYaw()));
		boolean isInvisible = entity.isInvisible();
		ItemStack itemStack = entity.getHeldItemStack();
		if (!isInvisible) {
			BlockRenderManager blockRenderManager = this.client.getBlockRenderManager();
			BakedModelManager bakedModelManager = blockRenderManager.getModels().getModelManager();
			ModelIdentifier modelIdentifier = this.getModelId(entity, itemStack);
			matrixStack.push();
			matrixStack.translate(-0.5D, -0.5D, -0.5D);
			blockRenderManager.getModelRenderer().render(matrixStack.peek(), vertexConsumerProvider.getBuffer(TexturedRenderLayers.getEntitySolid()), null, bakedModelManager.getModel(modelIdentifier), 1.0F, 1.0F, 1.0F, light, OverlayTexture.DEFAULT_UV);
			matrixStack.pop();
		}
		
		if (!itemStack.isEmpty()) {
			MapIdComponent mapIdComponent = entity.getMapId(itemStack);
			if (isInvisible) {
				matrixStack.translate(0.0D, 0.0D, 0.5D);
			} else {
				matrixStack.translate(0.0D, 0.0D, 0.4375D);
			}
			
			int renderLight = entity.shouldRenderAtMaxLight() ? LightmapTextureManager.MAX_LIGHT_COORDINATE : light;
			
			int j = mapIdComponent != null ? entity.getRotation() % 4 * 2 : entity.getRotation();
			matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) j * 360.0F / 8.0F));
			if (mapIdComponent != null) {
				matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
				matrixStack.scale(0.0078125F, 0.0078125F, 0.0078125F);
				matrixStack.translate(-64.0F, -64.0F, 0.0F);
				MapState mapState = FilledMapItem.getMapState(mapIdComponent, entity.getWorld());
				matrixStack.translate(0.0F, 0.0F, -1.0F);
				if (mapState != null) {
					int k = this.getLight(entity, 15728850, renderLight);
					MinecraftClient.getInstance().gameRenderer.getMapRenderer().draw(matrixStack, vertexConsumerProvider, mapIdComponent, mapState, true, k);
				}
			} else {
				int l = this.getLight(entity, 15728880, renderLight);
				matrixStack.scale(0.5F, 0.5F, 0.5F);
				this.itemRenderer.renderItem(itemStack, ModelTransformationMode.FIXED, l, OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider, entity.getWorld(), entity.getId());
			}
		}

		matrixStack.pop();
	}
	
	private ModelIdentifier getModelId(T entity, ItemStack stack) {
		boolean bl = entity.getType() == SpectrumEntityTypes.GLOW_PHANTOM_FRAME;
		if (stack.isOf(Items.FILLED_MAP)) {
			return bl ? MAP_GLOW_FRAME_MODEL_IDENTIFIER : MAP_FRAME_MODEL_IDENTIFIER;
		} else {
			return bl ? GLOW_FRAME_MODEL_IDENTIFIER : NORMAL_FRAME_MODEL_IDENTIFIER;
		}
	}


}
