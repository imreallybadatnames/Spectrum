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

public record ColorTransmissionPayload(BlockPos pos,
									   ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<ColorTransmissionPayload> ID = SpectrumC2SPackets.makeId("color_transmission");
	public static final PacketCodec<PacketByteBuf, ColorTransmissionPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			ColorTransmissionPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			ColorTransmissionPayload::configuration,
			ColorTransmissionPayload::new
	);
	
	public static void playColorTransmissionParticle(ServerWorld world, @NotNull ColoredTransmission transfer) {
		BlockPos blockPos = BlockPos.ofFloored(transfer.getOrigin());
		
		PacketByteBuf buf = PacketByteBufs.create();
		ColoredTransmission.writeToBuf(buf, transfer);
		
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, blockPos)) {
			ServerPlayNetworking.send(player, SpectrumS2CPackets.COLOR_TRANSMISSION, buf);
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<ColorTransmissionPayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			ColoredTransmission transmission = ColoredTransmission.readFromBuf(buf);
			client.execute(() -> {
				// Everything in this lambda is running on the render thread
				client.world.addImportantParticle(new ColoredTransmissionParticleEffect(transmission.getDestination(), transmission.getArrivalInTicks(), transmission.getDyeColor()), true, transmission.getOrigin().getX(), transmission.getOrigin().getY(), transmission.getOrigin().getZ(), 0.0D, 0.0D, 0.0D);
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
