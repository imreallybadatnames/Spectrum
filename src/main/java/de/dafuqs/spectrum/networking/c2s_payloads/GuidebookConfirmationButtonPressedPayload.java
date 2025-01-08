package de.dafuqs.spectrum.networking.c2s_payloads;

import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.progression.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.sound.*;

public record GuidebookConfirmationButtonPressedPayload(String confirmationString) implements CustomPayload {
	
	public static final Id<GuidebookConfirmationButtonPressedPayload> ID = SpectrumC2SPackets.makeId("confirmation_button_pressed");
	public static final PacketCodec<PacketByteBuf, GuidebookConfirmationButtonPressedPayload> CODEC = PacketCodec.tuple(PacketCodecs.STRING, GuidebookConfirmationButtonPressedPayload::confirmationString, GuidebookConfirmationButtonPressedPayload::new);
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
	public static ServerPlayNetworking.PlayPayloadHandler<GuidebookConfirmationButtonPressedPayload> getPayloadHandler() {
		return (payload, context) -> {
			ServerPlayerEntity player = context.player();
			SpectrumAdvancementCriteria.CONFIRMATION_BUTTON_PRESSED.trigger(player, payload.confirmationString);
			player.getWorld().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0F, 1.0F);
		};
		
	}
	
}
