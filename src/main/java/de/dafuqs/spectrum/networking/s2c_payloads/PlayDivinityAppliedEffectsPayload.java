package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.sound.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record PlayDivinityAppliedEffectsPayload(BlockPos pos,
												ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PlayDivinityAppliedEffectsPayload> ID = SpectrumC2SPackets.makeId("play_divinity_applied_effects");
	public static final PacketCodec<PacketByteBuf, PlayDivinityAppliedEffectsPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PlayDivinityAppliedEffectsPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PlayDivinityAppliedEffectsPayload::configuration,
			PlayDivinityAppliedEffectsPayload::new
	);
	
	public static void playDivinityAppliedEffects(ServerPlayerEntity player) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeInt(player.getId());
		ServerPlayNetworking.send(player, SpectrumS2CPackets.PLAY_DIVINITY_APPLIED_EFFECTS, buf);
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayDivinityAppliedEffectsPayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			// Everything in this lambda is running on the render thread
			PlayerEntity player = client.player;
			client.particleManager.addEmitter(player, SpectrumParticleTypes.DIVINITY, 30);
			client.gameRenderer.showFloatingItem(SpectrumItems.DIVINATION_HEART.getDefaultStack());
			client.world.playSound(null, player.getBlockPos(), SpectrumSoundEvents.FAILING_PLACED, SoundCategory.PLAYERS, 1.0F, 1.0F);
			
			ParticleHelper.playParticleWithPatternAndVelocityClient(player.getWorld(), player.getPos(), SpectrumParticleTypes.WHITE_CRAFTING, VectorPattern.SIXTEEN, 0.4);
			ParticleHelper.playParticleWithPatternAndVelocityClient(player.getWorld(), player.getPos(), SpectrumParticleTypes.RED_CRAFTING, VectorPattern.SIXTEEN, 0.4);
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
