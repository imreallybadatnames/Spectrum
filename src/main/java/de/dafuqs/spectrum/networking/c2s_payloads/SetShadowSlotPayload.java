package de.dafuqs.spectrum.networking.c2s_payloads;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.inventories.slots.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.*;
import net.minecraft.server.network.*;

public record SetShadowSlotPayload(int screenHandlerSyncId, int slotId,
								   ItemStack shadowStack) implements CustomPayload {
	
	public static final Id<SetShadowSlotPayload> ID = SpectrumC2SPackets.makeId("set_shadow_slot");
	public static final PacketCodec<RegistryByteBuf, SetShadowSlotPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.INTEGER, SetShadowSlotPayload::screenHandlerSyncId,
			PacketCodecs.INTEGER, SetShadowSlotPayload::slotId,
			ItemStack.PACKET_CODEC, SetShadowSlotPayload::shadowStack,
			SetShadowSlotPayload::new
	);
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
	public static ServerPlayNetworking.PlayPayloadHandler<SetShadowSlotPayload> getPayloadHandler() {
		return (payload, context) -> {
			ServerPlayerEntity player = context.player();
			ScreenHandler screenHandler = player.currentScreenHandler;
			
			if (screenHandler == null || screenHandler.syncId != payload.screenHandlerSyncId) {
				return;
			}
			
			Slot slot = screenHandler.getSlot(payload.slotId);
			if (slot == null || !(slot instanceof ShadowSlot) || !(slot.inventory instanceof FilterConfigurable.FilterInventory filterInventory)) {
				return;
			}
			
			filterInventory.getClicker().clickShadowSlot(screenHandler.syncId, slot, payload.shadowStack());
		};
	}
	
}
