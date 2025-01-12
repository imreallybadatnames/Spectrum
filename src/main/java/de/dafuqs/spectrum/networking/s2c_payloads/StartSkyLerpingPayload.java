package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.util.math.*;
import net.minecraft.world.dimension.*;
import org.jetbrains.annotations.*;

public record StartSkyLerpingPayload(BlockPos pos, ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<StartSkyLerpingPayload> ID = SpectrumC2SPackets.makeId("start_sky_lerping");
	public static final PacketCodec<PacketByteBuf, StartSkyLerpingPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			StartSkyLerpingPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			StartSkyLerpingPayload::configuration,
			StartSkyLerpingPayload::new
	);
	
	public static void startSkyLerping(@NotNull ServerWorld serverWorld, int additionalTime) {
		PacketByteBuf buf = PacketByteBufs.create();
		long timeOfDay = serverWorld.getTimeOfDay();
		buf.writeLong(timeOfDay);
		buf.writeLong(timeOfDay + additionalTime);
		
		for (ServerPlayerEntity player : serverWorld.getPlayers()) {
			ServerPlayNetworking.send(player, new StartSkyLerpingPayload());
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<StartSkyLerpingPayload> getPayloadHandler() {
		return (payload, context) -> {
			MinecraftClient client = context.client();
			DimensionType dimensionType = client.world.getDimension();
			long sourceTime = buf.readLong();
			long targetTime = buf.readLong();
			
			client.execute(() -> {
				client.world.
						SpectrumClient.skyLerper.trigger(dimensionType, sourceTime, client.getRenderTickCounter().getTickDelta(false), targetTime);
				if (client.world.isSkyVisible(client.player.getBlockPos())) {
					client.world.playSound(null, client.player.getBlockPos(), SpectrumSoundEvents.CELESTIAL_POCKET_WATCH_FLY_BY, SoundCategory.NEUTRAL, 0.15F, 1.0F);
				}
			});
			
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
