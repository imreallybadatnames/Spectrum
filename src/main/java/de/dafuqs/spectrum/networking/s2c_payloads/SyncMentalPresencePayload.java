package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.cca.*;
import de.dafuqs.spectrum.deeper_down.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record SyncMentalPresencePayload(BlockPos pos,
										ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<SyncMentalPresencePayload> ID = SpectrumC2SPackets.makeId("sync_mental_presence");
	public static final PacketCodec<PacketByteBuf, SyncMentalPresencePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			SyncMentalPresencePayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			SyncMentalPresencePayload::configuration,
			SyncMentalPresencePayload::new
	);
	
	public static void sendMentalPresenceSync(ServerPlayerEntity player, double value) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeDouble(value);
		ServerPlayNetworking.send(player, SpectrumS2CPackets.SYNC_MENTAL_PRESENCE, buf);
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<SyncMentalPresencePayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			double value = buf.readDouble();
			
			client.execute(() -> {
				if (client.player != null) {
					MiscPlayerDataComponent.get(client.player).setLastSyncedSleepPotency(value);
					DarknessEffects.markForEffectUpdate();
				}
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
