package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.blocks.present.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

import java.util.*;

public record PlayPresentOpeningParticlesPayload(BlockPos pos,
												 ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PlayPresentOpeningParticlesPayload> ID = SpectrumC2SPackets.makeId("play_present_opening_particles");
	public static final PacketCodec<PacketByteBuf, PlayPresentOpeningParticlesPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PlayPresentOpeningParticlesPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PlayPresentOpeningParticlesPayload::configuration,
			PlayPresentOpeningParticlesPayload::new
	);
	
	public static void playPresentOpeningParticles(ServerWorld serverWorld, BlockPos pos, Map<DyeColor, Integer> colors) {
		PacketByteBuf packetByteBuf = PacketByteBufs.create();
		packetByteBuf.writeBlockPos(pos);
		packetByteBuf.writeInt(colors.size());
		for (Map.Entry<DyeColor, Integer> color : colors.entrySet()) {
			packetByteBuf.writeByte(color.getKey().getId());
			packetByteBuf.writeByte(color.getValue());
		}
		
		// Iterate over all players tracking a position in the world and send the packet to each player
		for (ServerPlayerEntity player : PlayerLookup.tracking(serverWorld, pos)) {
			ServerPlayNetworking.send(player, SpectrumS2CPackets.PLAY_PRESENT_OPENING_PARTICLES, packetByteBuf);
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayPresentOpeningParticlesPayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			BlockPos pos = buf.readBlockPos();
			int colorCount = buf.readInt();
			
			Map<DyeColor, Integer> colors = new HashMap<>();
			for (int i = 0; i < colorCount; i++) {
				DyeColor dyeColor = DyeColor.byId(buf.readByte());
				int amount = buf.readByte();
				colors.put(dyeColor, amount);
			}
			
			client.execute(() -> {
				// Everything in this lambda is running on the render thread
				PresentBlock.spawnParticles(client.world, pos, colors);
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
