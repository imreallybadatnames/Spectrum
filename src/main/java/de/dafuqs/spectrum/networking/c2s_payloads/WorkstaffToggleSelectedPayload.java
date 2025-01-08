package de.dafuqs.spectrum.networking.c2s_payloads;

import de.dafuqs.spectrum.inventories.*;
import de.dafuqs.spectrum.items.tools.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.screen.*;
import net.minecraft.server.network.*;

public record WorkstaffToggleSelectedPayload(int index) implements CustomPayload {
	
	public static final Id<WorkstaffToggleSelectedPayload> ID = SpectrumC2SPackets.makeId("workstaff_toggle_selected");
	public static final PacketCodec<RegistryByteBuf, WorkstaffToggleSelectedPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.INTEGER, WorkstaffToggleSelectedPayload::index,
			WorkstaffToggleSelectedPayload::new
	);
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
	public static ServerPlayNetworking.PlayPayloadHandler<WorkstaffToggleSelectedPayload> getPayloadHandler() {
		return (payload, context) -> {
			ServerPlayerEntity player = context.player();
			ScreenHandler screenHandler = player.currentScreenHandler;
			if (screenHandler instanceof WorkstaffScreenHandler workstaffScreenHandler) {
				WorkstaffItem.GUIToggle toggle = WorkstaffItem.GUIToggle.values()[payload.index];
				workstaffScreenHandler.onWorkstaffToggleSelectionPacket(toggle);
			}
		};
	}
	
}
