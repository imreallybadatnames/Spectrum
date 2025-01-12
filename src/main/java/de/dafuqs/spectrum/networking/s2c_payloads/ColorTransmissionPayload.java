package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.effect.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record ColorTransmissionPayload(BlockPos pos, ColoredTransmission transmission) implements CustomPayload {
	
	public static final Id<ColorTransmissionPayload> ID = SpectrumC2SPackets.makeId("color_transmission");
	public static final PacketCodec<PacketByteBuf, ColorTransmissionPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC, ColorTransmissionPayload::pos,
			ColoredTransmission.PACKET_CODEC, ColorTransmissionPayload::transmission,
			ColorTransmissionPayload::new
	);
	
	public static void playColorTransmissionParticle(ServerWorld world, @NotNull ColoredTransmission transmission) {
		BlockPos pos = BlockPos.ofFloored(transmission.getOrigin());
		
		PacketByteBuf buf = PacketByteBufs.create();
		ColoredTransmission.writeToBuf(buf, transmission);
		
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, pos)) {
			ServerPlayNetworking.send(player, new ColorTransmissionPayload(pos, transmission));
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<ColorTransmissionPayload> getPayloadHandler() {
		return (payload, context) -> {
			MinecraftClient client = context.client();
			client.execute(() -> {
				ColoredTransmission transmission = payload.transmission;
				client.world.addImportantParticle(new ColoredTransmissionParticleEffect(transmission.getDestination(), transmission.getArrivalInTicks(), transmission.getDyeColor()), true, transmission.getOrigin().getX(), transmission.getOrigin().getY(), transmission.getOrigin().getZ(), 0.0D, 0.0D, 0.0D);
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
