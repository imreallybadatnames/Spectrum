package de.dafuqs.spectrum.entity.render;

import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.entity.models.*;
import de.dafuqs.spectrum.entity.variants.*;
import de.dafuqs.spectrum.registries.client.*;
import net.fabricmc.api.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.*;
import net.minecraft.client.util.math.*;

@Environment(EnvType.CLIENT)
public class LizardHornsFeatureRenderer<T extends LizardEntity> extends FeatureRenderer<T, LizardEntityModel<T>> {
    
    public LizardHornsFeatureRenderer(FeatureRendererContext<T, LizardEntityModel<T>> context) {
        super(context);
    }
    
    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T lizard, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        
        LizardHornVariant horns = lizard.getHorns();
        if (horns != LizardHornVariant.ONLY_LIKES_YOU_AS_A_FRIEND) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(SpectrumRenderLayers.GlowInTheDarkRenderLayer.get(horns.getTexture()));
			var color = lizard.getColor().getColorInt();
            this.getContextModel().render(matrices, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV, color);
        }
    }
    
}
