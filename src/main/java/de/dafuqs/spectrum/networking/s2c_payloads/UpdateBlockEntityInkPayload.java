package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.world.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

import java.util.*;

public record UpdateBlockEntityInkPayload(BlockPos pos,
										  ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<UpdateBlockEntityInkPayload> ID = SpectrumC2SPackets.makeId("update_block_entity_ink");
	public static final PacketCodec<PacketByteBuf, UpdateBlockEntityInkPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			UpdateBlockEntityInkPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			UpdateBlockEntityInkPayload::configuration,
			UpdateBlockEntityInkPayload::new
	);
	
	@SuppressWarnings("deprecation")
	public static void updateBlockEntityInk(BlockPos pos, InkStorage inkStorage, ServerPlayerEntity player) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeBlockPos(pos);
		buf.writeLong(inkStorage.getCurrentTotal());
		
		Map<InkColor, Long> colors = inkStorage.getEnergy();
		buf.writeInt(colors.size());
		for (Map.Entry<InkColor, Long> color : colors.entrySet()) {
			buf.writeIdentifier(color.getKey().getID());
			buf.writeLong(color.getValue());
		}
		
		ServerPlayNetworking.send(player, SpectrumS2CPackets.UPDATE_BLOCK_ENTITY_INK, buf);
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<UpdateBlockEntityInkPayload> getPayloadHandler() {
		return (payload, context) -> {
			BlockPos blockPos = buf.readBlockPos();
			long colorTotal = buf.readLong();
			
			int colorEntries = buf.readInt();
			Map<InkColor, Long> colors = new HashMap<>();
			for (int i = 0; i < colorEntries; i++) {
				Optional<InkColor> optionalInkColor = InkColor.ofId(buf.readIdentifier());
				if (optionalInkColor.isPresent()) {
					colors.put(optionalInkColor.get(), buf.readLong());
				}
			}
			
			context.client().execute(() -> {
				ClientWorld world = context.client().world;
				BlockEntity blockEntity = world.getBlockEntity(blockPos);
				
				if (blockEntity instanceof InkStorageBlockEntity<?> inkStorageBlockEntity) {
					inkStorageBlockEntity.getEnergyStorage().setEnergy(colors, colorTotal);
				}
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
