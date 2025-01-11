package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.blocks.pedestal.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record PlayPedestalStartCraftingParticlePayload(BlockPos pos,
													   ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PlayPedestalStartCraftingParticlePayload> ID = SpectrumC2SPackets.makeId("play_pedestal_start_crafting_particle");
	public static final PacketCodec<PacketByteBuf, PlayPedestalStartCraftingParticlePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PlayPedestalStartCraftingParticlePayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PlayPedestalStartCraftingParticlePayload::configuration,
			PlayPedestalStartCraftingParticlePayload::new
	);
	
	public static void spawnPedestalStartCraftingParticles(PedestalBlockEntity pedestalBlockEntity) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeBlockPos(pedestalBlockEntity.getPos());
		// Iterate over all players tracking a position in the world and send the packet to each player
		for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) pedestalBlockEntity.getWorld(), pedestalBlockEntity.getPos())) {
			ServerPlayNetworking.send(player, SpectrumS2CPackets.PLAY_PEDESTAL_START_CRAFTING_PARTICLE_PACKET_ID, buf);
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayPedestalStartCraftingParticlePayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			BlockPos position = buf.readBlockPos(); // the block pos of the pedestal
			
			client.execute(() -> {
				// Everything in this lambda is running on the render thread
				PedestalBlockEntity.spawnCraftingStartParticles(client.world, position);
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
