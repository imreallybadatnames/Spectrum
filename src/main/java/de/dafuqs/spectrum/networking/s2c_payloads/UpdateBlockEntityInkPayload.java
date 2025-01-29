package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

import java.util.*;

public record UpdateBlockEntityInkPayload(BlockPos pos, Map<InkColor, Long> storage, long currentTotal) implements CustomPayload {
	
	public static final Id<UpdateBlockEntityInkPayload> ID = SpectrumC2SPackets.makeId("update_block_entity_ink");
	public static final PacketCodec<PacketByteBuf, UpdateBlockEntityInkPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC, UpdateBlockEntityInkPayload::pos,
			PacketCodecs.map(HashMap::new, InkColor.PACKET_CODEC, PacketCodecs.VAR_LONG), UpdateBlockEntityInkPayload::storage,
			PacketCodecs.VAR_LONG, UpdateBlockEntityInkPayload::currentTotal,
			UpdateBlockEntityInkPayload::new
	);
	
	@SuppressWarnings("deprecation")
	public static void updateBlockEntityInk(BlockPos pos, InkStorage inkStorage, ServerPlayerEntity player) {
		ServerPlayNetworking.send(player, new UpdateBlockEntityInkPayload(pos, inkStorage.getEnergy(), inkStorage.getCurrentTotal()));
	}
	
	@Environment(EnvType.CLIENT)
	@SuppressWarnings({"resource", "deprecation", "DataFlowIssue"})
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<UpdateBlockEntityInkPayload> getPayloadHandler() {
		return (payload, context) -> {
			MinecraftClient client = context.client();
			context.client().execute(() -> {
				BlockEntity blockEntity = client.world.getBlockEntity(payload.pos);
				if (blockEntity instanceof InkStorageBlockEntity<?> inkStorageBlockEntity) {
					inkStorageBlockEntity.getEnergyStorage().setEnergy(payload.storage, payload.currentTotal);
				}
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
