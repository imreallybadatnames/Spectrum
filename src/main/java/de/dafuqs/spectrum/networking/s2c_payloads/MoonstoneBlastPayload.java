package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.spells.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record MoonstoneBlastPayload(BlockPos pos, ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<MoonstoneBlastPayload> ID = SpectrumC2SPackets.makeId("moonstone_blast");
	public static final PacketCodec<PacketByteBuf, MoonstoneBlastPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			MoonstoneBlastPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			MoonstoneBlastPayload::configuration,
			MoonstoneBlastPayload::new
	);
	
	public static void sendMoonstoneBlast(ServerWorld serverWorld, MoonstoneStrike moonstoneStrike) {
		// Iterate over all players tracking a position in the world and send the packet to each player
		for (ServerPlayerEntity player : PlayerLookup.tracking(serverWorld, BlockPos.ofFloored(moonstoneStrike.getX(), moonstoneStrike.getY(), moonstoneStrike.getZ()))) {
			Vec3d playerVelocity = moonstoneStrike.getAffectedPlayers().getOrDefault(player, Vec3d.ZERO);
			
			PacketByteBuf buf = PacketByteBufs.create();
			buf.writeDouble(moonstoneStrike.getX());
			buf.writeDouble(moonstoneStrike.getY());
			buf.writeDouble(moonstoneStrike.getZ());
			buf.writeFloat(moonstoneStrike.getPower());
			buf.writeFloat(moonstoneStrike.getKnockbackMod());
			buf.writeDouble(playerVelocity.x);
			buf.writeDouble(playerVelocity.y);
			buf.writeDouble(playerVelocity.z);
			
			ServerPlayNetworking.send(player, SpectrumS2CPackets.MOONSTONE_BLAST, buf);
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<MoonstoneBlastPayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			PlayerEntity player = client.player;
			
			double x = buf.readDouble();
			double y = buf.readDouble();
			double z = buf.readDouble();
			float power = buf.readFloat();
			float knockback = buf.readFloat();
			double playerVelocityX = buf.readDouble();
			double playerVelocityY = buf.readDouble();
			double playerVelocityZ = buf.readDouble();
			
			client.execute(() -> {
				MoonstoneStrike.create(client.world, null, null, x, y, z, power, knockback);
				player.setVelocity(player.getVelocity().add(playerVelocityX, playerVelocityY, playerVelocityZ));
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
