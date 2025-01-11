package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.particle.*;
import net.minecraft.registry.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import org.jetbrains.annotations.*;

public record PlayParticleWithRandomOffsetAndVelocityPayload(BlockPos pos,
															 ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PlayParticleWithRandomOffsetAndVelocityPayload> ID = SpectrumC2SPackets.makeId("play_particle_with_random_offset_and_velocity");
	public static final PacketCodec<PacketByteBuf, PlayParticleWithRandomOffsetAndVelocityPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PlayParticleWithRandomOffsetAndVelocityPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PlayParticleWithRandomOffsetAndVelocityPayload::configuration,
			PlayParticleWithRandomOffsetAndVelocityPayload::new
	);
	
	/**
	 * Play particle effect
	 *
	 * @param world          the world
	 * @param position       the pos of the particles
	 * @param particleEffect The particle effect to play
	 */
	public static void playParticleWithRandomOffsetAndVelocity(ServerWorld world, Vec3d position, @NotNull ParticleEffect particleEffect, int amount, Vec3d randomOffset, Vec3d randomVelocity) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeDouble(position.x);
		buf.writeDouble(position.y);
		buf.writeDouble(position.z);
		buf.writeIdentifier(Registries.PARTICLE_TYPE.getId(particleEffect.getType()));
		buf.writeInt(amount);
		buf.writeDouble(randomOffset.x);
		buf.writeDouble(randomOffset.y);
		buf.writeDouble(randomOffset.z);
		buf.writeDouble(randomVelocity.x);
		buf.writeDouble(randomVelocity.y);
		buf.writeDouble(randomVelocity.z);
		
		// Iterate over all players tracking a position in the world and send the packet to each player
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, BlockPos.ofFloored(position))) {
			ServerPlayNetworking.send(player, SpectrumS2CPackets.PLAY_PARTICLE_WITH_RANDOM_OFFSET_AND_VELOCITY, buf);
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayParticleWithRandomOffsetAndVelocityPayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			Vec3d position = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
			ParticleType<?> particleType = Registries.PARTICLE_TYPE.get(buf.readIdentifier());
			int amount = buf.readInt();
			Vec3d randomOffset = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
			Vec3d randomVelocity = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
			if (particleType instanceof ParticleEffect particleEffect) {
				client.execute(() -> {
					// Everything in this lambda is running on the render thread
					
					Random random = client.world.random;
					
					for (int i = 0; i < amount; i++) {
						double randomOffsetX = randomOffset.x - random.nextDouble() * randomOffset.x * 2;
						double randomOffsetY = randomOffset.y - random.nextDouble() * randomOffset.y * 2;
						double randomOffsetZ = randomOffset.z - random.nextDouble() * randomOffset.z * 2;
						double randomVelocityX = randomVelocity.x - random.nextDouble() * randomVelocity.x * 2;
						double randomVelocityY = randomVelocity.y - random.nextDouble() * randomVelocity.y * 2;
						double randomVelocityZ = randomVelocity.z - random.nextDouble() * randomVelocity.z * 2;
						
						client.world.addParticle(particleEffect,
								position.getX() + randomOffsetX, position.getY() + randomOffsetY, position.getZ() + randomOffsetZ,
								randomVelocityX, randomVelocityY, randomVelocityZ);
					}
				});
			}
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
}
