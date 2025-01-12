package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.client.world.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.particle.*;
import net.minecraft.registry.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record PlayParticleWithExactVelocityPayload(BlockPos pos, ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PlayParticleWithExactVelocityPayload> ID = SpectrumC2SPackets.makeId("play_particle_with_exact_velocity");
	public static final PacketCodec<PacketByteBuf, PlayParticleWithExactVelocityPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PlayParticleWithExactVelocityPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PlayParticleWithExactVelocityPayload::configuration,
			PlayParticleWithExactVelocityPayload::new
	);
	
	/**
	 * Play particle effect
	 *
	 * @param world          the world
	 * @param position       the pos of the particles
	 * @param particleEffect The particle effect to play
	 */
	public static void playParticles(ServerWorld world, BlockPos position, ParticleEffect particleEffect, int amount) {
		playParticleWithExactVelocity(world, Vec3d.ofCenter(position), particleEffect, amount, Vec3d.ZERO);
	}
	
	/**
	 * Play particle effect
	 *
	 * @param world          the world
	 * @param position       the pos of the particles
	 * @param particleEffect The particle effect to play
	 */
	public static void playParticleWithExactVelocity(ServerWorld world, @NotNull Vec3d position, @NotNull ParticleEffect particleEffect, int amount, @NotNull Vec3d velocity) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeDouble(position.x);
		buf.writeDouble(position.y);
		buf.writeDouble(position.z);
		buf.writeIdentifier(Registries.PARTICLE_TYPE.getId(particleEffect.getType()));
		buf.writeInt(amount);
		buf.writeDouble(velocity.x);
		buf.writeDouble(velocity.y);
		buf.writeDouble(velocity.z);
		
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, BlockPos.ofFloored(position))) {
			ServerPlayNetworking.send(player, new PlayParticleWithExactVelocityPayload());
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayParticleWithExactVelocityPayload> getPayloadHandler() {
		return (payload, context) -> {
			MinecraftClient client = context.client();
			double posX = buf.readDouble();
			double posY = buf.readDouble();
			double posZ = buf.readDouble();
			ParticleType<?> particleType = Registries.PARTICLE_TYPE.get(buf.readIdentifier());
			int amount = buf.readInt();
			double velocityX = buf.readDouble();
			double velocityY = buf.readDouble();
			double velocityZ = buf.readDouble();
			if (particleType instanceof ParticleEffect particleEffect) {
				client.execute(() -> {
					ClientWorld world = client.world;
					
					for (int i = 0; i < amount; i++) {
						world.addParticle(particleEffect, posX, posY, posZ, velocityX, velocityY, velocityZ);
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
