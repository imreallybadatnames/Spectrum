package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.chests.*;
import de.dafuqs.spectrum.blocks.particle_spawner.*;
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

public record CompactingChestStatusUpdatePayload(BlockPos pos,
												 ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<CompactingChestStatusUpdatePayload> ID = SpectrumC2SPackets.makeId("compacting_chest_status_update");
	public static final PacketCodec<PacketByteBuf, CompactingChestStatusUpdatePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			CompactingChestStatusUpdatePayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			CompactingChestStatusUpdatePayload::configuration,
			CompactingChestStatusUpdatePayload::new
	);
	
	public static void sendCompactingChestStatusUpdate(CompactingChestBlockEntity chest) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeBlockPos(chest.getPos());
		buf.writeBoolean(chest.hasToCraft());
		
		for (ServerPlayerEntity player : PlayerLookup.tracking(chest)) {
			ServerPlayNetworking.send(player, SpectrumS2CPackets.COMPACTING_CHEST_STATUS_UPDATE, buf);
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<CompactingChestStatusUpdatePayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			var pos = buf.readBlockPos();
			var hasToCraft = buf.readBoolean();
			
			client.execute(() -> {
				var entity = client.world.getBlockEntity(pos, SpectrumBlockEntities.COMPACTING_CHEST);
				
				if (entity.isEmpty())
					return;
				
				entity.get().shouldCraft(hasToCraft);
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
