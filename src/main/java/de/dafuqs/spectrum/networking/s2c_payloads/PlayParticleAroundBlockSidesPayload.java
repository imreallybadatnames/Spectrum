package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.helpers.*;
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
import org.jetbrains.annotations.*;

import java.util.function.*;

public record PlayParticleAroundBlockSidesPayload(BlockPos pos,
												  ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PlayParticleAroundBlockSidesPayload> ID = SpectrumC2SPackets.makeId("play_particle_around_block_sides");
	public static final PacketCodec<PacketByteBuf, PlayParticleAroundBlockSidesPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PlayParticleAroundBlockSidesPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PlayParticleAroundBlockSidesPayload::configuration,
			PlayParticleAroundBlockSidesPayload::new
	);
	
	public static void playParticleAroundBlockSides(ServerWorld world, int quantity, @NotNull Vec3d position, @NotNull Vec3d velocity, @NotNull ParticleEffect particleEffect, Predicate<ServerPlayerEntity> sendCheck, @NotNull Direction... sides) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeInt(quantity);
		buf.writeDouble(position.x);
		buf.writeDouble(position.y);
		buf.writeDouble(position.z);
		buf.writeDouble(velocity.x);
		buf.writeDouble(velocity.y);
		buf.writeDouble(velocity.z);
		buf.writeIdentifier(Registries.PARTICLE_TYPE.getId(particleEffect.getType()));
		buf.writeInt(sides.length);
		for (Direction side : sides) {
			buf.writeInt(side.ordinal());
		}
		
		// Iterate over all players tracking a position in the world and send the packet to each player
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, BlockPos.ofFloored(position))) {
			if (!sendCheck.test(player))
				continue;
			
			ServerPlayNetworking.send(player, SpectrumS2CPackets.PLAY_PARTICLE_AROUND_BLOCK_SIDES, buf);
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.PlayPayloadHandler<PlayParticleAroundBlockSidesPayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			int quantity = buf.readInt();
			Vec3d position = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
			Vec3d velocity = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
			ParticleType<?> particleType = Registries.PARTICLE_TYPE.get(buf.readIdentifier());
			var sideCount = buf.readInt();
			var sides = new Direction[sideCount];
			for (int i = 0; i < sideCount; i++) {
				sides[i] = Direction.values()[buf.readInt()];
			}
			
			if (particleType instanceof ParticleEffect particleEffect && client.world != null) {
				client.execute(() -> {
					ParticleHelper.playParticleAroundBlockSides(client.world, particleEffect, position, sides, quantity, velocity);
				});
			}
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
