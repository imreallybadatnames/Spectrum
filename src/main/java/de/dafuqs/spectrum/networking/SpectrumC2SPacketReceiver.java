package de.dafuqs.spectrum.networking;

import de.dafuqs.spectrum.networking.c2s_payloads.*;
import net.fabricmc.fabric.api.networking.v1.*;

public class SpectrumC2SPacketReceiver {
	
	public static void registerC2SReceivers() {
		ServerPlayNetworking.registerGlobalReceiver(RenameItemInBedrockAnvilPayload.ID, RenameItemInBedrockAnvilPayload.getPayloadHandler());
		ServerPlayNetworking.registerGlobalReceiver(AddLoreBedrockAnvilPayload.ID, AddLoreBedrockAnvilPayload.getPayloadHandler());
		ServerPlayNetworking.registerGlobalReceiver(GuidebookConfirmationButtonPressedPayload.ID, GuidebookConfirmationButtonPressedPayload.getPayloadHandler());
		ServerPlayNetworking.registerGlobalReceiver(GuidebookHintBoughtPayload.ID, GuidebookHintBoughtPayload.getPayloadHandler());
		ServerPlayNetworking.registerGlobalReceiver(ChangeCompactingChestSettingsPayload.ID, ChangeCompactingChestSettingsPayload.getPayloadHandler());
		ServerPlayNetworking.registerGlobalReceiver(ParticleSpawnerConfigurationC2SPayload.ID, ParticleSpawnerConfigurationC2SPayload.getPayloadHandler());
		ServerPlayNetworking.registerGlobalReceiver(BindEnderSpliceToPlayerPayload.ID, BindEnderSpliceToPlayerPayload.getPayloadHandler());
		ServerPlayNetworking.registerGlobalReceiver(InkColorSelectedPayload.ID, InkColorSelectedPayload.getPayloadHandler());
		ServerPlayNetworking.registerGlobalReceiver(WorkstaffToggleSelectedPayload.ID, WorkstaffToggleSelectedPayload.getPayloadHandler());
		ServerPlayNetworking.registerGlobalReceiver(SetShadowSlotPayload.ID, SetShadowSlotPayload.getPayloadHandler());
	}
	
}
