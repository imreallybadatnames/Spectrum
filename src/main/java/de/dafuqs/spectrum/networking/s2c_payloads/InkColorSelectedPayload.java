package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.screen.*;
import net.minecraft.server.network.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

import java.util.*;

public record InkColorSelectedPayload(BlockPos pos,
									  ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<InkColorSelectedPayload> ID = SpectrumC2SPackets.makeId("ink_color_selected");
	public static final PacketCodec<PacketByteBuf, InkColorSelectedPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			InkColorSelectedPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			InkColorSelectedPayload::configuration,
			InkColorSelectedPayload::new
	);
	
	public static void sendInkColorSelected(@Nullable InkColor color, ServerPlayerEntity player) {
		PacketByteBuf packetByteBuf = PacketByteBufs.create();
		if (color == null) {
			packetByteBuf.writeBoolean(false);
		} else {
			packetByteBuf.writeBoolean(true);
			packetByteBuf.writeIdentifier(color.getID());
		}
		ServerPlayNetworking.send(player, SpectrumS2CPackets.INK_COLOR_SELECTED, packetByteBuf);
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.PlayPayloadHandler<InkColorSelectedPayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			ScreenHandler screenHandler = client.player.currentScreenHandler;
			if (screenHandler instanceof InkColorSelectedPacketReceiver inkColorSelectedPacketReceiver) {
				boolean isSelection = buf.readBoolean();
				
				InkColor color = null;
				if (isSelection) {
					Optional<InkColor> optionalInkColor = InkColor.ofId(buf.readIdentifier());
					if (optionalInkColor.isPresent()) {
						color = optionalInkColor.get();
					}
				}
				inkColorSelectedPacketReceiver.onInkColorSelectedPacket(color);
			}
		};
	}
	
}
