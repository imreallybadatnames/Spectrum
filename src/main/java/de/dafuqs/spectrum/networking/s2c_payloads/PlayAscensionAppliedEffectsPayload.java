package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.registries.*;
import de.dafuqs.spectrum.sound.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.sound.*;
import org.jetbrains.annotations.*;

public record PlayAscensionAppliedEffectsPayload() implements CustomPayload {
	
	public static final Id<PlayAscensionAppliedEffectsPayload> ID = SpectrumC2SPackets.makeId("play_ascension_applied_effects");
	public static final PacketCodec<PacketByteBuf, PlayAscensionAppliedEffectsPayload> CODEC = PacketCodec.tuple(PlayAscensionAppliedEffectsPayload::new);
	
	public static void playAscensionAppliedEffects(ServerPlayerEntity player) {
		ServerPlayNetworking.send(player, new PlayAscensionAppliedEffectsPayload());
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayAscensionAppliedEffectsPayload> getPayloadHandler() {
		return (payload, context) -> {
			MinecraftClient client = context.client();
			
			client.world.playSound(null, client.player.getBlockPos(), SpectrumSoundEvents.FADING_PLACED, SoundCategory.PLAYERS, 1.0F, 1.0F);
			client.getSoundManager().play(new DivinitySoundInstance());
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
