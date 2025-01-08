package de.dafuqs.spectrum.networking.c2s_payloads;

import de.dafuqs.spectrum.items.magic_items.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;

public record BindEnderSpliceToPlayerPayload(int entityId) implements CustomPayload {
	
	public static final Id<BindEnderSpliceToPlayerPayload> ID = SpectrumC2SPackets.makeId("bind_ender_splice_to_player");
	public static final PacketCodec<PacketByteBuf, BindEnderSpliceToPlayerPayload> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, BindEnderSpliceToPlayerPayload::entityId, BindEnderSpliceToPlayerPayload::new);
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
	public static ServerPlayNetworking.PlayPayloadHandler<BindEnderSpliceToPlayerPayload> getPayloadHandler() {
		return (payload, context) -> {
			ServerPlayerEntity player = context.player();
			Entity entity = player.getWorld().getEntityById(payload.entityId());
			if (entity instanceof ServerPlayerEntity targetPlayerEntity
					&& player.distanceTo(targetPlayerEntity) < 8
					&& player.getMainHandStack().isOf(SpectrumItems.ENDER_SPLICE)) {
				
				EnderSpliceItem.setTeleportTargetPlayer(player.getMainHandStack(), targetPlayerEntity);
				
				player.playSound(SpectrumSoundEvents.ENDER_SPLICE_BOUND, 1.0F, 1.0F);
				targetPlayerEntity.playSound(SpectrumSoundEvents.ENDER_SPLICE_BOUND, 1.0F, 1.0F);
			}
		};
	}
	
}
