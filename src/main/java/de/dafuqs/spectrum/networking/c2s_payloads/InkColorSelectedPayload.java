package de.dafuqs.spectrum.networking.c2s_payloads;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.registry.entry.*;
import net.minecraft.screen.*;
import net.minecraft.server.network.*;

import java.util.*;

public record InkColorSelectedPayload(Optional<RegistryEntry<InkColor>> color) implements CustomPayload {
	
	public static final Id<InkColorSelectedPayload> ID = SpectrumC2SPackets.makeId("ink_color_select");
	public static final PacketCodec<RegistryByteBuf, InkColorSelectedPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.optional(PacketCodecs.registryEntry(SpectrumRegistries.INK_COLORS_KEY)), InkColorSelectedPayload::color,
			InkColorSelectedPayload::new
	);
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
	public static ServerPlayNetworking.PlayPayloadHandler<InkColorSelectedPayload> getPayloadHandler() {
		return (payload, context) -> {
			ServerPlayerEntity player = context.player();
			ScreenHandler screenHandler = player.currentScreenHandler;
			if (screenHandler instanceof InkColorSelectedPacketReceiver inkColorSelectedPacketReceiver) {
				
				Optional<RegistryEntry<InkColor>> color = payload.color();
				boolean isSelection = color.isPresent();
				
				// send the newly selected color to all players that have the same gui open
				// this is minus the player that selected that entry (since they have that info already)
				inkColorSelectedPacketReceiver.onInkColorSelectedPacket(color);
				for (ServerPlayerEntity serverPlayer : context.server().getPlayerManager().getPlayerList()) {
					if (serverPlayer.currentScreenHandler instanceof InkColorSelectedPacketReceiver receiver && receiver.getBlockEntity() != null && receiver.getBlockEntity() == inkColorSelectedPacketReceiver.getBlockEntity()) {
						SpectrumS2CPacketSender.sendInkColorSelected(color, serverPlayer);
					}
				}
			}
		};
	}
	
}
