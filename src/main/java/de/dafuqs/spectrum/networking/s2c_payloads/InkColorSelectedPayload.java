package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.registry.entry.*;
import net.minecraft.screen.*;
import net.minecraft.server.network.*;

import java.util.*;

public record InkColorSelectedPayload(Optional<RegistryEntry<InkColor>> color) implements CustomPayload {
	
	public static final Id<InkColorSelectedPayload> ID = SpectrumC2SPackets.makeId("ink_color_selected");
	public static final PacketCodec<RegistryByteBuf, InkColorSelectedPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.optional(PacketCodecs.registryEntry(SpectrumRegistries.INK_COLORS_KEY)), InkColorSelectedPayload::color,
			InkColorSelectedPayload::new
	);
	
	public static void sendInkColorSelected(Optional<RegistryEntry<InkColor>> color, ServerPlayerEntity player) {
		ServerPlayNetworking.send(player, new InkColorSelectedPayload(color));
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.PlayPayloadHandler<InkColorSelectedPayload> getPayloadHandler() {
		return (payload, context) -> {
			ScreenHandler screenHandler = context.player().currentScreenHandler;
			if (screenHandler instanceof InkColorSelectedPacketReceiver inkColorSelectedPacketReceiver) {
				inkColorSelectedPacketReceiver.onInkColorSelectedPacket(payload.color);
			}
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
}
