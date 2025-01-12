package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.blocks.shooting_star.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record PlayShootingStarParticlesPayload(BlockPos pos,
											   ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PlayShootingStarParticlesPayload> ID = SpectrumC2SPackets.makeId("play_shooting_star_particles");
	public static final PacketCodec<PacketByteBuf, PlayShootingStarParticlesPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PlayShootingStarParticlesPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PlayShootingStarParticlesPayload::configuration,
			PlayShootingStarParticlesPayload::new
	);
	
	public static void sendPlayShootingStarParticles(@NotNull ShootingStarEntity shootingStarEntity) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeDouble(shootingStarEntity.getPos().getX());
		buf.writeDouble(shootingStarEntity.getPos().getY());
		buf.writeDouble(shootingStarEntity.getPos().getZ());
		buf.writeInt(shootingStarEntity.getShootingStarType().ordinal());
		for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) shootingStarEntity.getWorld(), shootingStarEntity.getBlockPos())) {
			ServerPlayNetworking.send(player, new PlayShootingStarParticlesPayload());
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayShootingStarParticlesPayload> getPayloadHandler() {
		return (payload, context) -> {
			double x = buf.readDouble();
			double y = buf.readDouble();
			double z = buf.readDouble();
			ShootingStar.Type shootingStarType = ShootingStar.Type.getType(buf.readInt());
			
			context.client().execute(() -> {
				context.client().world.
						ShootingStarEntity.playHitParticles(client.world, x, y, z, shootingStarType, 25);
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
