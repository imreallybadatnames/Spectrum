package de.dafuqs.spectrum.networking;

import de.dafuqs.spectrum.networking.s2c_payloads.*;
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

}
