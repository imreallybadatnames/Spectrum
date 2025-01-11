package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.blocks.pastel_network.network.*;
import de.dafuqs.spectrum.helpers.ColorHelper;
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

import java.util.*;

public record PastelTransmissionPayload(BlockPos pos,
										ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PastelTransmissionPayload> ID = SpectrumC2SPackets.makeId("pastel_transmission");
	public static final PacketCodec<PacketByteBuf, PastelTransmissionPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PastelTransmissionPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PastelTransmissionPayload::configuration,
			PastelTransmissionPayload::new
	);
	
	public static void sendPastelTransmissionParticle(ServerPastelNetwork network, int travelTime, @NotNull PastelTransmission transmission) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeUuid(network.getUUID());
		buf.writeInt(travelTime);
		PastelTransmission.write(buf, transmission);
		
		for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) network.getWorld(), transmission.getStartPos())) {
			ServerPlayNetworking.send(player, SpectrumS2CPackets.PASTEL_TRANSMISSION, buf);
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PastelTransmissionPayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			UUID networkUUID = buf.readUuid();
			int travelTime = buf.readInt();
			PastelTransmission transmission = PastelTransmission.fromPacket(buf);
			BlockPos spawnPos = transmission.getStartPos();
			int color = ColorHelper.getRandomColor(networkUUID.hashCode());
			
			client.execute(() -> {
				// Everything in this lambda is running on the render thread
				client.world.addParticle(new PastelTransmissionParticleEffect(transmission.getNodePositions(), transmission.getVariant().toStack(), travelTime, color), spawnPos.getX() + 0.5, spawnPos.getY() + 0.5, spawnPos.getZ() + 0.5, 0, 0, 0);
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
