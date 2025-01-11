package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.api.item.*;
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

public record BlackHoleChestStatusUpdatePayload(BlockPos pos,
												ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<BlackHoleChestStatusUpdatePayload> ID = SpectrumC2SPackets.makeId("black_hole_chest_status_update");
	public static final PacketCodec<PacketByteBuf, BlackHoleChestStatusUpdatePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			BlackHoleChestStatusUpdatePayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			BlackHoleChestStatusUpdatePayload::configuration,
			BlackHoleChestStatusUpdatePayload::new
	);
	
	public static void sendBlackHoleChestUpdate(BlackHoleChestBlockEntity chest) {
		var xpStack = chest.getStack(BlackHoleChestBlockEntity.EXPERIENCE_STORAGE_PROVIDER_ITEM_SLOT);
		
		
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeBlockPos(chest.getPos());
		buf.writeBoolean(chest.isFullServer());
		buf.writeBoolean(chest.canStoreExperience());
		if (xpStack.getItem() instanceof ExperienceStorageItem experienceStorageItem) {
			buf.writeLong(ExperienceStorageItem.getStoredExperience(xpStack));
			buf.writeLong(experienceStorageItem.getMaxStoredExperience(xpStack));
		} else {
			buf.writeLong(0);
			buf.writeLong(0);
		}
		
		for (ServerPlayerEntity player : PlayerLookup.tracking(chest)) {
			ServerPlayNetworking.send(player, SpectrumS2CPackets.BLACK_HOLE_CHEST_STATUS_UPDATE, buf);
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<BlackHoleChestStatusUpdatePayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			var pos = buf.readBlockPos();
			var isFull = buf.readBoolean();
			var canStoreXP = buf.readBoolean();
			var xp = buf.readLong();
			var max = buf.readLong();
			
			client.execute(() -> {
				var entity = client.world.getBlockEntity(pos, SpectrumBlockEntities.BLACK_HOLE_CHEST);
				
				entity.ifPresent(chest -> {
					chest.setFull(isFull);
					chest.setHasXPStorage(canStoreXP);
					chest.setXPData(xp, max);
				});
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
