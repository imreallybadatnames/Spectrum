package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.pedestal.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record PlayPedestalStartCraftingParticlePayload(BlockPos pedestalPos) implements CustomPayload {
	
	public static final Id<PlayPedestalStartCraftingParticlePayload> ID = SpectrumC2SPackets.makeId("play_pedestal_start_crafting_particle");
	public static final PacketCodec<PacketByteBuf, PlayPedestalStartCraftingParticlePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC, PlayPedestalStartCraftingParticlePayload::pedestalPos,
			PlayPedestalStartCraftingParticlePayload::new
	);
	
	public static void spawnPedestalStartCraftingParticles(PedestalBlockEntity pedestalBlockEntity) {
		for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) pedestalBlockEntity.getWorld(), pedestalBlockEntity.getPos())) {
			ServerPlayNetworking.send(player, new PlayPedestalStartCraftingParticlePayload(pedestalBlockEntity.getPos()));
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayPedestalStartCraftingParticlePayload> getPayloadHandler() {
		return (payload, context) -> {
			MinecraftClient client = context.client();
			client.execute(() -> {
				PedestalBlockEntity.spawnCraftingStartParticles(client.world, payload.pedestalPos);
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
