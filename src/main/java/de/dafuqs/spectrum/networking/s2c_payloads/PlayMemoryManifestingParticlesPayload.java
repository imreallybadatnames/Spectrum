package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.memory.*;
import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.helpers.ColorHelper;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.particle.effect.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.*;
import org.joml.*;

public record PlayMemoryManifestingParticlesPayload(BlockPos pos,
													ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PlayMemoryManifestingParticlesPayload> ID = SpectrumC2SPackets.makeId("play_memory_manifesting_particles");
	public static final PacketCodec<PacketByteBuf, PlayMemoryManifestingParticlesPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PlayMemoryManifestingParticlesPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PlayMemoryManifestingParticlesPayload::configuration,
			PlayMemoryManifestingParticlesPayload::new
	);
	
	public static void playMemoryManifestingParticles(ServerWorld serverWorld, @NotNull BlockPos blockPos, EntityType<?> entityType, int amount) {
		Pair<Integer, Integer> eggColors = MemoryBlockEntity.getEggColorsForEntity(entityType);
		
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeBlockPos(blockPos);
		buf.writeInt(eggColors.getLeft());
		buf.writeInt(eggColors.getRight());
		buf.writeInt(amount);
		
		// Iterate over all players tracking a position in the world and send the packet to each player
		for (ServerPlayerEntity player : PlayerLookup.tracking(serverWorld, blockPos)) {
			ServerPlayNetworking.send(player, SpectrumS2CPackets.PLAY_MEMORY_MANIFESTING_PARTICLES, buf);
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayMemoryManifestingParticlesPayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			BlockPos position = buf.readBlockPos();
			int color1 = buf.readInt();
			int color2 = buf.readInt();
			int amount = buf.readInt();
			
			client.execute(() -> {
				Random random = client.world.random;
				
				Vector3f colorVec1 = de.dafuqs.spectrum.helpers.ColorHelper.colorIntToVec(color1);
				Vector3f colorVec2 = ColorHelper.colorIntToVec(color2);
				
				for (int i = 0; i < amount; i++) {
					int randomLifetime = 30 + random.nextInt(20);
					
					// color1
					client.world.addParticle(
							new DynamicParticleEffect(SpectrumParticleTypes.WHITE_CRAFTING, 0.5F, colorVec1, 1.0F, randomLifetime, false, true),
							position.getX() + 0.5, position.getY() + 0.5, position.getZ(),
							0.15 - random.nextFloat() * 0.3, random.nextFloat() * 0.15 + 0.1, 0.15 - random.nextFloat() * 0.3
					);
					
					// color2
					client.world.addParticle(
							new DynamicParticleEffect(SpectrumParticleTypes.WHITE_CRAFTING, 0.5F, colorVec2, 1.0F, randomLifetime, false, true),
							position.getX() + 0.5, position.getY(), position.getZ() + 0.5,
							0.15 - random.nextFloat() * 0.3, random.nextFloat() * 0.15 + 0.1, 0.15 - random.nextFloat() * 0.3
					);
				}
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
