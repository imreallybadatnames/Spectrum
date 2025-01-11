package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.effect.*;
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

public record TypedTransmissionPayload(BlockPos pos,
									   ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<TypedTransmissionPayload> ID = SpectrumC2SPackets.makeId("typed_transmission");
	public static final PacketCodec<PacketByteBuf, TypedTransmissionPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			TypedTransmissionPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			TypedTransmissionPayload::configuration,
			TypedTransmissionPayload::new
	);
	
	public static void playTransmissionParticle(ServerWorld world, @NotNull TypedTransmission transmission) {
		BlockPos blockPos = BlockPos.ofFloored(transmission.getOrigin());
		
		PacketByteBuf buf = PacketByteBufs.create();
		TypedTransmission.writeToBuf(buf, transmission);
		
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, blockPos)) {
			ServerPlayNetworking.send(player, SpectrumS2CPackets.TYPED_TRANSMISSION, buf);
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<TypedTransmissionPayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			TypedTransmission transmission = TypedTransmission.readFromBuf(buf);
			
			client.execute(() -> {
				// Everything in this lambda is running on the render thread
				switch (transmission.getVariant()) {
					case BLOCK_POS ->
							client.world.addImportantParticle(new BlockPosEventTransmissionParticleEffect(transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().getX(), transmission.getOrigin().getY(), transmission.getOrigin().getZ(), 0.0D, 0.0D, 0.0D);
					case ITEM ->
							client.world.addImportantParticle(new ItemTransmissionParticleEffect(transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().getX(), transmission.getOrigin().getY(), transmission.getOrigin().getZ(), 0.0D, 0.0D, 0.0D);
					case EXPERIENCE ->
							client.world.addImportantParticle(new ExperienceTransmissionParticleEffect(transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().getX(), transmission.getOrigin().getY(), transmission.getOrigin().getZ(), 0.0D, 0.0D, 0.0D);
					case HUMMINGSTONE ->
							client.world.addImportantParticle(new HummingstoneTransmissionParticleEffect(transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().getX(), transmission.getOrigin().getY(), transmission.getOrigin().getZ(), 0.0D, 0.0D, 0.0D);
					case REDSTONE ->
							client.world.addImportantParticle(new WirelessRedstoneTransmissionParticleEffect(transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().getX(), transmission.getOrigin().getY(), transmission.getOrigin().getZ(), 0.0D, 0.0D, 0.0D);
				}
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
