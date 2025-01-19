package de.dafuqs.spectrum.networking;

import de.dafuqs.spectrum.networking.s2c_payloads.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;

public class SpectrumS2CPackets {

	public static void register() {
		PayloadTypeRegistry.playC2S().register(PlayParticleWithRandomOffsetAndVelocityPayload.ID, PlayParticleWithRandomOffsetAndVelocityPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PlayParticleWithExactVelocityPayload.ID, PlayParticleWithExactVelocityPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PlayParticleWithPatternAndVelocityPayload.ID, PlayParticleWithPatternAndVelocityPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PlayParticleAroundBlockSidesPayload.ID, PlayParticleAroundBlockSidesPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PlayPedestalCraftingFinishedParticlePayload.ID, PlayPedestalCraftingFinishedParticlePayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PlayPedestalUpgradedParticlePayload.ID, PlayPedestalUpgradedParticlePayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PlayPedestalStartCraftingParticlePayload.ID, PlayPedestalStartCraftingParticlePayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PlayFusionCraftingInProgressParticlePayload.ID, PlayFusionCraftingInProgressParticlePayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PlayFusionCraftingFinishedParticlePayload.ID, PlayFusionCraftingFinishedParticlePayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PlayShootingStarParticlesPayload.ID, PlayShootingStarParticlesPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PlayPresentOpeningParticlesPayload.ID, PlayPresentOpeningParticlesPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(TypedTransmissionPayload.ID, TypedTransmissionPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(ColorTransmissionPayload.ID, ColorTransmissionPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PastelTransmissionPayload.ID, PastelTransmissionPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PlayBlockBoundSoundInstancePayload.ID, PlayBlockBoundSoundInstancePayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PlayTakeOffBeltSoundInstancePayload.ID, PlayTakeOffBeltSoundInstancePayload.CODEC);
		PayloadTypeRegistry.playC2S().register(StartSkyLerpingPayload.ID, StartSkyLerpingPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PlayMemoryManifestingParticlesPayload.ID, PlayMemoryManifestingParticlesPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(UpdateBlockEntityInkPayload.ID, UpdateBlockEntityInkPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(InkColorSelectedPayload.ID, InkColorSelectedPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PlayAscensionAppliedEffectsPayload.ID, PlayAscensionAppliedEffectsPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PlayDivinityAppliedEffectsPayload.ID, PlayDivinityAppliedEffectsPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(MoonstoneBlastPayload.ID, MoonstoneBlastPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(SyncArtisansAtlasPayload.ID, SyncArtisansAtlasPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(SyncMentalPresencePayload.ID, SyncMentalPresencePayload.CODEC);
		PayloadTypeRegistry.playC2S().register(CompactingChestStatusUpdatePayload.ID, CompactingChestStatusUpdatePayload.CODEC);
		PayloadTypeRegistry.playC2S().register(BlackHoleChestStatusUpdatePayload.ID, BlackHoleChestStatusUpdatePayload.CODEC);
		PayloadTypeRegistry.playC2S().register(FabricationChestStatusUpdatePayload.ID, FabricationChestStatusUpdatePayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PastelNodeStatusUpdatePayload.ID, PastelNodeStatusUpdatePayload.CODEC);
	}
	
	@Environment(EnvType.CLIENT)
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
