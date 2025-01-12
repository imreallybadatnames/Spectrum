package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.chests.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record CompactingChestStatusUpdatePayload(BlockPos pos, boolean hasToCraft) implements CustomPayload {
	
	public static final Id<CompactingChestStatusUpdatePayload> ID = SpectrumC2SPackets.makeId("compacting_chest_status_update");
	public static final PacketCodec<PacketByteBuf, CompactingChestStatusUpdatePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC, CompactingChestStatusUpdatePayload::pos,
			PacketCodecs.BOOL, CompactingChestStatusUpdatePayload::hasToCraft,
			CompactingChestStatusUpdatePayload::new
	);
	
	public static void sendCompactingChestStatusUpdate(CompactingChestBlockEntity chest) {
		for (ServerPlayerEntity player : PlayerLookup.tracking(chest)) {
			ServerPlayNetworking.send(player, new CompactingChestStatusUpdatePayload(chest.getPos(), chest.hasToCraft()));
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<CompactingChestStatusUpdatePayload> getPayloadHandler() {
		return (payload, context) -> {
			var pos = payload.pos;
			var hasToCraft = payload.hasToCraft;
			
			context.client().execute(() -> {
				var entity = context.client().world.getBlockEntity(pos, SpectrumBlockEntities.COMPACTING_CHEST);
				entity.ifPresent(compactingChestBlockEntity -> compactingChestBlockEntity.shouldCraft(hasToCraft));
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
