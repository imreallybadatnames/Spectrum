package de.dafuqs.spectrum.networking;

import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.items.tools.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.fabricmc.fabric.api.transfer.v1.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import org.jetbrains.annotations.*;

@Environment(EnvType.CLIENT)
public class SpectrumC2SPacketSender {
	
	public static void sendBindEnderSpliceToPlayer(PlayerEntity playerEntity) {
		PacketByteBuf packetByteBuf = PacketByteBufs.create();
		packetByteBuf.writeInt(playerEntity.getId());
		ClientPlayNetworking.send(SpectrumC2SPackets.BIND_ENDER_SPLICE_TO_PLAYER, packetByteBuf);
	}
	
	public static void sendInkColorSelectedInGUI(@Nullable InkColor color) {
		PacketByteBuf packetByteBuf = PacketByteBufs.create();
		if (color == null) {
			packetByteBuf.writeBoolean(false);
		} else {
			packetByteBuf.writeBoolean(true);
			packetByteBuf.writeIdentifier(color.getID());
		}
		ClientPlayNetworking.send(SpectrumC2SPackets.INK_COLOR_SELECTED, packetByteBuf);
	}

    public static void sendWorkstaffToggle(WorkstaffItem.GUIToggle toggle) {
        PacketByteBuf packetByteBuf = PacketByteBufs.create();
        packetByteBuf.writeInt(toggle.ordinal());
        ClientPlayNetworking.send(SpectrumC2SPackets.WORKSTAFF_TOGGLE_SELECTED, packetByteBuf);
    }

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
