package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.particle.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;

import java.util.*;
import java.util.function.*;

// TODO: playParticleAroundBlockSides() is a mess
public record PlayParticleAroundBlockSidesPayload(BlockPos pos, int quantity, Vec3d velocity, ParticleEffect particle, List<Direction> sides) implements CustomPayload {
	
	public static final Id<PlayParticleAroundBlockSidesPayload> ID = SpectrumC2SPackets.makeId("play_particle_around_block_sides");
	public static final PacketCodec<RegistryByteBuf, PlayParticleAroundBlockSidesPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC, PlayParticleAroundBlockSidesPayload::pos,
			PacketCodecs.INTEGER, PlayParticleAroundBlockSidesPayload::quantity,
			SpectrumPacketCodecs.VEC_3D, PlayParticleAroundBlockSidesPayload::velocity,
			ParticleTypes.PACKET_CODEC, PlayParticleAroundBlockSidesPayload::particle,
			Direction.PACKET_CODEC, PlayParticleAroundBlockSidesPayload::directions,
			PlayParticleAroundBlockSidesPayload::new
	);
	
	public static void playParticleAroundBlockSides(ServerWorld world, int quantity, BlockPos pos, Vec3d velocity, ParticleEffect particleEffect, Predicate<ServerPlayerEntity> sendCheck, Direction... sides) {
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, pos)) {
			if (!sendCheck.test(player))
				continue;
			
			ServerPlayNetworking.send(player, new PlayParticleAroundBlockSidesPayload(pos, quantity, velocity, particleEffect, sides));
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.PlayPayloadHandler<PlayParticleAroundBlockSidesPayload> getPayloadHandler() {
		return (payload, context) -> {
			MinecraftClient client = context.client();
			client.execute(() -> {
				ParticleHelper.playParticleAroundBlockSides(client.world, payload.particle, payload.pos, payload.sides, payload.quantity, payload.velocity);
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
