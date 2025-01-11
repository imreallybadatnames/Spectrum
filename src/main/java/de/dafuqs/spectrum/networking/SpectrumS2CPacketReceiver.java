package de.dafuqs.spectrum.networking;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import net.fabricmc.api.*;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.minecraft.particle.*;
import net.minecraft.registry.*;
import net.minecraft.util.math.*;

@SuppressWarnings("resource")
@Environment(EnvType.CLIENT)
public class SpectrumS2CPacketReceiver {
	
	@SuppressWarnings("deprecation")
	public static void registerS2CReceivers() {
		ClientPlayNetworking.registerGlobalReceiver(PlayParticleWithRandomOffsetAndVelocityPayload.ID, PlayParticleWithRandomOffsetAndVelocityPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayParticleWithExactVelocityPayload.ID, PlayParticleWithExactVelocityPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayParticleWithPatternAndVelocityPayload.ID, PlayParticleWithPatternAndVelocityPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayParticleAroundBlockSidesPayload.ID, PlayParticleAroundBlockSidesPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(StartSkyLerpingPayload.ID, StartSkyLerpingPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayPedestalCraftingFinishedParticlePayload.ID, PlayPedestalCraftingFinishedParticlePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayShootingStarParticlesPayload.ID, PlayShootingStarParticlesPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayFusionCraftingInProgressParticlePayload.ID, PlayFusionCraftingInProgressParticlePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayFusionCraftingFinishedParticlePayload.ID, PlayFusionCraftingFinishedParticlePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayMemoryManifestingParticlesPayload.ID, PlayMemoryManifestingParticlesPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayPedestalUpgradedParticlePayload.ID, PlayPedestalUpgradedParticlePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayPedestalStartCraftingParticlePayload.ID, PlayPedestalStartCraftingParticlePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(ParticleSpawnerConfigurationS2CPayload.ID, ParticleSpawnerConfigurationS2CPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PastelTransmissionPayload.ID, PastelTransmissionPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(TypedTransmissionPayload.ID, TypedTransmissionPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(ColorTransmissionPayload.ID, ColorTransmissionPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayBlockBoundSoundInstancePayload.ID, PlayBlockBoundSoundInstancePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayTakeOffBeltSoundInstancePayload.ID, PlayTakeOffBeltSoundInstancePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(UpdateBlockEntityInkPayload.ID, UpdateBlockEntityInkPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(InkColorSelectedPayload.ID, InkColorSelectedPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayPresentOpeningParticlesPayload.ID, PlayPresentOpeningParticlesPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayAscensionAppliedEffectsPayload.ID, PlayAscensionAppliedEffectsPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayDivinityAppliedEffectsPayload.ID, PlayDivinityAppliedEffectsPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(MoonstoneBlastPayload.ID, MoonstoneBlastPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(SyncArtisansAtlasPayload.ID, SyncArtisansAtlasPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(SyncMentalPresencePayload.ID, SyncMentalPresencePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(CompactingChestStatusUpdatePayload.ID, CompactingChestStatusUpdatePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(FabricationChestStatusUpdatePayload.ID, FabricationChestStatusUpdatePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(BlackHoleChestStatusUpdatePayload.ID, BlackHoleChestStatusUpdatePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PastelNodeStatusUpdatePayload.ID, PastelNodeStatusUpdatePayload.getPayloadHandler());
    }
	
}
