package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.registries.*;
import de.dafuqs.spectrum.sound.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.sound.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record PlayAscensionAppliedEffectsPayload(BlockPos pos,
												 ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PlayAscensionAppliedEffectsPayload> ID = SpectrumC2SPackets.makeId("play_ascension_applied_effects");
	public static final PacketCodec<PacketByteBuf, PlayAscensionAppliedEffectsPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PlayAscensionAppliedEffectsPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PlayAscensionAppliedEffectsPayload::configuration,
			PlayAscensionAppliedEffectsPayload::new
	);
	
	public static void playAscensionAppliedEffects(ServerPlayerEntity player) {
		ServerPlayNetworking.send(player, SpectrumS2CPackets.PLAY_ASCENSION_APPLIED_EFFECTS, PacketByteBufs.create());
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayAscensionAppliedEffectsPayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			// Everything in this lambda is running on the render thread
			client.world.playSound(null, client.player.getBlockPos(), SpectrumSoundEvents.FADING_PLACED, SoundCategory.PLAYERS, 1.0F, 1.0F);
			client.getSoundManager().play(new DivinitySoundInstance());
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
