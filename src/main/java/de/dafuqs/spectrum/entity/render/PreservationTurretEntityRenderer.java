package de.dafuqs.spectrum.entity.render;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.entity.models.*;
import de.dafuqs.spectrum.registries.client.*;
import net.fabricmc.api.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.util.math.*;
import net.minecraft.util.*;

@Environment(EnvType.CLIENT)
public class PreservationTurretEntityRenderer extends MobEntityRenderer<PreservationTurretEntity, PreservationTurretEntityModel<PreservationTurretEntity>> {
	
	public static final Identifier TEXTURE = SpectrumCommon.locate("textures/entity/preservation_turret/preservation_turret.png");
	
	public PreservationTurretEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new PreservationTurretEntityModel<>(context.getPart(SpectrumModelLayers.PRESERVATION_TURRET)), 0.0F);
	}
	
	@Override
	public Identifier getTexture(PreservationTurretEntity turretEntity) {
		return TEXTURE;
	}
	
	@Override
	protected void setupTransforms(PreservationTurretEntity turretEntity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {
		super.setupTransforms(turretEntity, matrices, animationProgress, bodyYaw + 180.0F, tickDelta, scale);
		matrices.translate(0.0, 0.5, 0.0);
		matrices.multiply(turretEntity.getAttachedFace().getOpposite().getRotationQuaternion());
		matrices.translate(0.0, -0.5, 0.0);
	}
	
}