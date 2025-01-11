package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.fusion_shrine.*;
import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.block.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public record PlayFusionCraftingInProgressParticlePayload(BlockPos pos,
														  ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PlayFusionCraftingInProgressParticlePayload> ID = SpectrumC2SPackets.makeId("play_fusion_crafting_in_progress_particle");
	public static final PacketCodec<PacketByteBuf, PlayFusionCraftingInProgressParticlePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PlayFusionCraftingInProgressParticlePayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PlayFusionCraftingInProgressParticlePayload::configuration,
			PlayFusionCraftingInProgressParticlePayload::new
	);
	
	public static void sendPlayFusionCraftingInProgressParticles(World world, BlockPos blockPos) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeBlockPos(blockPos);
		
		// Iterate over all players tracking a position in the world and send the packet to each player
		for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, blockPos)) {
			ServerPlayNetworking.send(player, SpectrumS2CPackets.PLAY_FUSION_CRAFTING_IN_PROGRESS_PARTICLE_PACKET_ID, buf);
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayFusionCraftingInProgressParticlePayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			BlockPos position = buf.readBlockPos();
			client.execute(() -> {
				BlockEntity blockEntity = client.world.getBlockEntity(position);
				if (blockEntity instanceof FusionShrineBlockEntity fusionShrineBlockEntity) {
					fusionShrineBlockEntity.spawnCraftingParticles();
				}
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
