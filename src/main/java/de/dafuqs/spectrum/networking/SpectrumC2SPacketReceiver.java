package de.dafuqs.spectrum.networking;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.inventories.*;
import de.dafuqs.spectrum.inventories.slots.*;
import de.dafuqs.spectrum.items.magic_items.*;
import de.dafuqs.spectrum.items.tools.*;
import de.dafuqs.spectrum.networking.packet.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.fabricmc.fabric.api.transfer.v1.item.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.*;
import net.minecraft.server.network.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;

import java.util.*;

public class SpectrumC2SPacketReceiver {
	
	public static void registerC2SReceivers() {
		ServerPlayNetworking.registerGlobalReceiver(RenameItemInBedrockAnvilPayload.ID, RenameItemInBedrockAnvilPayload.getPayloadHandler());
		ServerPlayNetworking.registerGlobalReceiver(AddLoreBedrockAnvilPayload.ID, AddLoreBedrockAnvilPayload.getPayloadHandler());
		ServerPlayNetworking.registerGlobalReceiver(GuidebookConfirmationButtonPressedPayload.ID, GuidebookConfirmationButtonPressedPayload.getPayloadHandler());
		ServerPlayNetworking.registerGlobalReceiver(GuidebookHintBoughtPayload.ID, GuidebookHintBoughtPayload.getPayloadHandler());
		ServerPlayNetworking.registerGlobalReceiver(ChangeCompactingChestSettingsPacket.ID, ChangeCompactingChestSettingsPacket.getPayloadHandler());
		ServerPlayNetworking.registerGlobalReceiver(ParticleSpawnerConfigurationC2SPacket.ID, ParticleSpawnerConfigurationC2SPacket.getPayloadHandler());
		
		ServerPlayNetworking.registerGlobalReceiver(SpectrumC2SPackets.BIND_ENDER_SPLICE_TO_PLAYER, (server, player, handler, buf, responseSender) -> {
			int entityId = buf.readInt();
			Entity entity = player.getWorld().getEntityById(entityId);
			if (entity instanceof ServerPlayerEntity targetPlayerEntity
					&& player.distanceTo(targetPlayerEntity) < 8
					&& player.getMainHandStack().isOf(SpectrumItems.ENDER_SPLICE)) {
				
				EnderSpliceItem.setTeleportTargetPlayer(player.getMainHandStack(), targetPlayerEntity);
				
				player.playSound(SpectrumSoundEvents.ENDER_SPLICE_BOUND, SoundCategory.PLAYERS, 1.0F, 1.0F);
				targetPlayerEntity.playSound(SpectrumSoundEvents.ENDER_SPLICE_BOUND, SoundCategory.PLAYERS, 1.0F, 1.0F);
			}
		});
		
		ServerPlayNetworking.registerGlobalReceiver(SpectrumC2SPackets.INK_COLOR_SELECTED, (server, player, handler, buf, responseSender) -> {
			ScreenHandler screenHandler = player.currentScreenHandler;
			if (screenHandler instanceof InkColorSelectedPacketReceiver inkColorSelectedPacketReceiver) {
				boolean isSelection = buf.readBoolean();
				
				InkColor color = null;
				if (isSelection) {
					Identifier inkColor = buf.readIdentifier();
					Optional<InkColor> optionalColor = InkColor.ofId(inkColor);
					if (optionalColor.isPresent()) {
						color = optionalColor.get();
					}
				}
				
				// send the newly selected color to all players that have the same gui open
				// this is minus the player that selected that entry (since they have that info already)
				inkColorSelectedPacketReceiver.onInkColorSelectedPacket(color);
				for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList()) {
					if (serverPlayer.currentScreenHandler instanceof InkColorSelectedPacketReceiver receiver && receiver.getBlockEntity() != null && receiver.getBlockEntity() == inkColorSelectedPacketReceiver.getBlockEntity()) {
						SpectrumS2CPacketSender.sendInkColorSelected(color, serverPlayer);
					}
				}
			}
		});
		
		ServerPlayNetworking.registerGlobalReceiver(SpectrumC2SPackets.WORKSTAFF_TOGGLE_SELECTED, (server, player, handler, buf, responseSender) -> {
			ScreenHandler screenHandler = player.currentScreenHandler;
			if (screenHandler instanceof WorkstaffScreenHandler workstaffScreenHandler) {
                WorkstaffItem.GUIToggle toggle = WorkstaffItem.GUIToggle.values()[buf.readInt()];
				workstaffScreenHandler.onWorkstaffToggleSelectionPacket(toggle);
			}
		});

		ServerPlayNetworking.registerGlobalReceiver(SpectrumC2SPackets.SET_SHADOW_SLOT, (server, player, handler, buf, responseSender) -> {
			ScreenHandler screenHandler = player.currentScreenHandler;

			int syncId = buf.readInt();
			if (screenHandler == null || screenHandler.syncId != syncId) {
				buf.skipBytes(buf.readableBytes());
				return;
			}

			Slot slot = screenHandler.getSlot(buf.readInt());
			if (slot == null || !(slot instanceof ShadowSlot) || !(slot.inventory instanceof FilterConfigurable.FilterInventory filterInventory)) {
				buf.skipBytes(buf.readableBytes());
				return;
			}

            @SuppressWarnings("UnstableApiUsage")
			ItemStack shadowStack = ItemVariant.fromPacket(buf).toStack(buf.readInt());

			filterInventory.getClicker().clickShadowSlot(syncId, slot, shadowStack);
		});
		
	}
	
}
