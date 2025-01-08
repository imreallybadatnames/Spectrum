package de.dafuqs.spectrum.networking;

import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.fabricmc.fabric.api.transfer.v1.item.*;
import net.minecraft.item.*;
import net.minecraft.network.*;

@Environment(EnvType.CLIENT)
public class SpectrumC2SPacketSender {

	@SuppressWarnings("UnstableApiUsage")
    public static void sendShadowSlot(int syncId, int id, ItemStack shadowStack) {
		PacketByteBuf packetByteBuf = PacketByteBufs.create();
		packetByteBuf.writeInt(syncId);
		packetByteBuf.writeInt(id);
		ItemVariant.of(shadowStack).toPacket(packetByteBuf);
		packetByteBuf.writeInt(shadowStack.getCount());
		ClientPlayNetworking.send(SpectrumC2SPackets.SET_SHADOW_SLOT, packetByteBuf);
	}
	
}
