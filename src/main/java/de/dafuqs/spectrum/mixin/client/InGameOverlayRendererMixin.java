package de.dafuqs.spectrum.mixin.client;

import com.mojang.blaze3d.systems.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.cca.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.hud.*;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.*;
import net.minecraft.client.util.math.*;
import net.minecraft.screen.*;
import net.minecraft.util.math.*;
import org.joml.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {
    
    @Inject(method = "renderOverlays(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At(value = "HEAD"))
    private static void spectrum$renderPrimordialFire(MinecraftClient client, MatrixStack matrices, CallbackInfo ci) {
        if (!client.player.isSpectator()) {
            if (OnPrimordialFireComponent.isOnPrimordialFire(client.player)) {
                renderPrimordialFireOverlay(client, matrices);
            }
        }
    }
    
    @Inject(method = "renderFireOverlay(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At(value = "HEAD"), cancellable = true)
    private static void spectrum$cancelFireOverlayWithPrimordialFire(MinecraftClient client, MatrixStack matrices, CallbackInfo ci) {
        if (OnPrimordialFireComponent.isOnPrimordialFire(client.player)) {
            ci.cancel();
        }
    }

    // [VanillaCopy] uses different texture for fire overlay
    @Unique
    private static void renderPrimordialFireOverlay(MinecraftClient client, MatrixStack matrices) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        RenderSystem.depthFunc(519);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        Sprite sprite = client.getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(SpectrumCommon.locate("block/primordial_fire_1"));
        RenderSystem.setShaderTexture(0, sprite.getAtlasId());
        float f = sprite.getMinU();
        float g = sprite.getMaxU();
        float h = (f + g) / 2.0F;
        float i = sprite.getMinV();
        float j = sprite.getMaxV();
        float k = (i + j) / 2.0F;
        float l = sprite.getAnimationFrameDelta();
        float m = MathHelper.lerp(l, f, h);
        float n = MathHelper.lerp(l, g, h);
        float o = MathHelper.lerp(l, i, k);
        float p = MathHelper.lerp(l, j, k);
        float q = 1.0F;
        
        for (int r = 0; r < 2; ++r) {
            matrices.push();
            float s = -0.5F;
            float t = 0.5F;
            float u = -0.5F;
            float v = 0.5F;
            float w = -0.5F;
            matrices.translate((float) (-(r * 2 - 1)) * 0.24F, -0.3F, 0.0F);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) (r * 2 - 1) * 10.0F));
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            bufferBuilder.vertex(matrix4f, -0.5F, -0.5F, -0.5F).texture(n, p).color(1.0F, 1.0F, 1.0F, 0.9F);
            bufferBuilder.vertex(matrix4f, 0.5F, -0.5F, -0.5F).texture(m, p).color(1.0F, 1.0F, 1.0F, 0.9F);
            bufferBuilder.vertex(matrix4f, 0.5F, 0.5F, -0.5F).texture(m, o).color(1.0F, 1.0F, 1.0F, 0.9F);
            bufferBuilder.vertex(matrix4f, -0.5F, 0.5F, -0.5F).texture(n, o).color(1.0F, 1.0F, 1.0F, 0.9F);
            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
            matrices.pop();
        }
        
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(515);
		
    }
    
}
