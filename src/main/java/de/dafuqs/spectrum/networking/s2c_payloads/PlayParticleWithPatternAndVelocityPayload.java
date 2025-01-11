package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.particle.*;
import net.minecraft.registry.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record PlayParticleWithPatternAndVelocityPayload(BlockPos pos,
														ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PlayParticleWithPatternAndVelocityPayload> ID = SpectrumC2SPackets.makeId("play_particle_with_pattern_and_velocity");
	public static final PacketCodec<PacketByteBuf, PlayParticleWithPatternAndVelocityPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PlayParticleWithPatternAndVelocityPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PlayParticleWithPatternAndVelocityPayload::configuration,
			PlayParticleWithPatternAndVelocityPayload::new
	);
	
	/**
	 * Play particles matching a spawn pattern
	 *
	 * @param world          the world
	 * @param position       the pos of the particles
	 * @param particleEffect The particle effect to play
	 */
	public static void playParticleWithPatternAndVelocity(@Nullable PlayerEntity notThisPlayerEntity, ServerWorld world, @NotNull Vec3d position, @NotNull ParticleEffect particleEffect, @NotNull VectorPattern pattern, double velocity) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeDouble(position.x);
		buf.writeDouble(position.y);
		buf.writeDouble(position.z);
		buf.writeIdentifier(Registries.PARTICLE_TYPE.getId(particleEffect.getType()));
		buf.writeInt(pattern.ordinal());
		buf.writeDouble(velocity);
		
		// Iterate over all players tracking a position in the world and send the packet to each player
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, BlockPos.ofFloored(position))) {
			if (!player.equals(notThisPlayerEntity)) {
				ServerPlayNetworking.send(player, SpectrumS2CPackets.PLAY_PARTICLE_PACKET_WITH_PATTERN_AND_VELOCITY_ID, buf);
			}
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayParticleWithPatternAndVelocityPayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			Vec3d position = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
			ParticleType<?> particleType = Registries.PARTICLE_TYPE.get(buf.readIdentifier());
			VectorPattern pattern = VectorPattern.values()[buf.readInt()];
			double velocity = buf.readDouble();
			if (particleType instanceof ParticleEffect particleEffect) {
				client.execute(() -> {
					// Everything in this lambda is running on the render thread
					ParticleHelper.playParticleWithPatternAndVelocityClient(client.world, position, particleEffect, pattern, velocity);
				});
			}
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
