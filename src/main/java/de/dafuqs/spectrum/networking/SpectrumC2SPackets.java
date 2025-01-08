package de.dafuqs.spectrum.networking;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.networking.c2s_payloads.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.packet.*;

public class SpectrumC2SPackets {

	public static <T extends CustomPayload> CustomPayload.Id<T> makeId(String id) {
		return new CustomPayload.Id<>(SpectrumCommon.locate(id));
	}

	public static void register() {
		PayloadTypeRegistry.playC2S().register(AddLoreBedrockAnvilPayload.ID, AddLoreBedrockAnvilPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(BindEnderSpliceToPlayerPayload.ID, BindEnderSpliceToPlayerPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(ChangeCompactingChestSettingsPayload.ID, ChangeCompactingChestSettingsPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(GuidebookConfirmationButtonPressedPayload.ID, GuidebookConfirmationButtonPressedPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(GuidebookHintBoughtPayload.ID, GuidebookHintBoughtPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(InkColorSelectedPayload.ID, InkColorSelectedPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(ParticleSpawnerConfigurationC2SPayload.ID, ParticleSpawnerConfigurationC2SPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(RenameItemInBedrockAnvilPayload.ID, RenameItemInBedrockAnvilPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(SetShadowSlotPayload.ID, SetShadowSlotPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(WorkstaffToggleSelectedPayload.ID, WorkstaffToggleSelectedPayload.CODEC);
	}

}
