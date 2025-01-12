package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.effect.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.client.world.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record TypedTransmissionPayload(TypedTransmission transmission) implements CustomPayload {
	
	public static final Id<TypedTransmissionPayload> ID = SpectrumC2SPackets.makeId("typed_transmission");
	public static final PacketCodec<PacketByteBuf, TypedTransmissionPayload> CODEC = PacketCodec.tuple(
			TypedTransmission.PACKET_CODEC, TypedTransmissionPayload::transmission,
			TypedTransmissionPayload::new
	);
	
	public static void playTransmissionParticle(ServerWorld world, @NotNull TypedTransmission transmission) {
		BlockPos pos = BlockPos.ofFloored(transmission.getOrigin());
		
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, pos)) {
			ServerPlayNetworking.send(player, new TypedTransmissionPayload(transmission));
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<TypedTransmissionPayload> getPayloadHandler() {
		return (payload, context) -> {
			MinecraftClient client = context.client();
			TypedTransmission transmission = payload.transmission();
			
			client.execute(() -> {
				ClientWorld world = client.world;
				switch (transmission.getVariant()) {
					case BLOCK_POS ->
							world.addImportantParticle(new BlockPosEventTransmissionParticleEffect(transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().getX(), transmission.getOrigin().getY(), transmission.getOrigin().getZ(), 0.0D, 0.0D, 0.0D);
					case ITEM ->
							world.addImportantParticle(new ItemTransmissionParticleEffect(transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().getX(), transmission.getOrigin().getY(), transmission.getOrigin().getZ(), 0.0D, 0.0D, 0.0D);
					case EXPERIENCE ->
							world.addImportantParticle(new ExperienceTransmissionParticleEffect(transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().getX(), transmission.getOrigin().getY(), transmission.getOrigin().getZ(), 0.0D, 0.0D, 0.0D);
					case HUMMINGSTONE ->
							world.addImportantParticle(new HummingstoneTransmissionParticleEffect(transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().getX(), transmission.getOrigin().getY(), transmission.getOrigin().getZ(), 0.0D, 0.0D, 0.0D);
					case REDSTONE ->
							world.addImportantParticle(new WirelessRedstoneTransmissionParticleEffect(transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().getX(), transmission.getOrigin().getY(), transmission.getOrigin().getZ(), 0.0D, 0.0D, 0.0D);
				}
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
