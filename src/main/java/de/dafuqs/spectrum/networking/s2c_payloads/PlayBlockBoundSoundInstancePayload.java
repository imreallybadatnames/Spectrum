package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.sound.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.block.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.registry.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record PlayBlockBoundSoundInstancePayload(BlockPos pos,
												 ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PlayBlockBoundSoundInstancePayload> ID = SpectrumC2SPackets.makeId("play_block_bound_sound_instance");
	public static final PacketCodec<PacketByteBuf, PlayBlockBoundSoundInstancePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PlayBlockBoundSoundInstancePayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PlayBlockBoundSoundInstancePayload::configuration,
			PlayBlockBoundSoundInstancePayload::new
	);
	
	public static void sendPlayBlockBoundSoundInstance(SoundEvent soundEvent, @NotNull ServerWorld world, BlockPos blockPos, int maxDurationTicks) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeIdentifier(Registries.SOUND_EVENT.getId(soundEvent));
		buf.writeIdentifier(Registries.BLOCK.getId(world.getBlockState(blockPos).getBlock()));
		buf.writeBlockPos(blockPos);
		buf.writeInt(maxDurationTicks);
		
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, blockPos)) {
			ServerPlayNetworking.send(player, new PlayBlockBoundSoundInstancePayload());
		}
	}
	
	public static void sendCancelBlockBoundSoundInstance(@NotNull ServerWorld world, BlockPos blockPos) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeIdentifier(Identifier.of("stop"));
		buf.writeIdentifier(Registries.BLOCK.getId(world.getBlockState(blockPos).getBlock()));
		buf.writeBlockPos(blockPos);
		buf.writeInt(1);
		
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, blockPos)) {
			ServerPlayNetworking.send(player, new PlayBlockBoundSoundInstancePayload());
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayBlockBoundSoundInstancePayload> getPayloadHandler() {
		return (payload, context) -> {
			if (SpectrumCommon.CONFIG.BlockSoundVolume > 0) {
				Identifier soundEffectIdentifier = buf.readIdentifier();
				Identifier blockIdentifier = buf.readIdentifier();
				BlockPos blockPos = buf.readBlockPos();
				int maxDurationTicks = buf.readInt();
				
				context.client().execute(() -> {
					if (soundEffectIdentifier.getPath().equals("stop")) {
						CraftingBlockSoundInstance.stopPlayingOnPos(blockPos);
					} else {
						SoundEvent soundEvent = Registries.SOUND_EVENT.get(soundEffectIdentifier);
						Block block = Registries.BLOCK.get(blockIdentifier);
						
						CraftingBlockSoundInstance.startSoundInstance(soundEvent, blockPos, block, maxDurationTicks);
					}
				});
			}
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
