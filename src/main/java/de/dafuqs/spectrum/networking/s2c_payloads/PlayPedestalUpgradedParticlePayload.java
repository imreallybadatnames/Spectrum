package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.recipe.pedestal.*;
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
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public record PlayPedestalUpgradedParticlePayload(BlockPos pos, ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PlayPedestalUpgradedParticlePayload> ID = SpectrumC2SPackets.makeId("play_pedestal_upgraded_particle");
	public static final PacketCodec<PacketByteBuf, PlayPedestalUpgradedParticlePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PlayPedestalUpgradedParticlePayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PlayPedestalUpgradedParticlePayload::configuration,
			PlayPedestalUpgradedParticlePayload::new
	);
	
	public static void spawnPedestalUpgradeParticles(World world, BlockPos blockPos, @NotNull PedestalVariant newPedestalVariant) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeBlockPos(blockPos);
		buf.writeInt(newPedestalVariant.getRecipeTier().ordinal());
		for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, blockPos)) {
			ServerPlayNetworking.send(player, new PlayPedestalUpgradedParticlePayload());
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayPedestalUpgradedParticlePayload> getPayloadHandler() {
		return (payload, context) -> {
			MinecraftClient client = context.client();
			BlockPos position = buf.readBlockPos(); // the block pos of the pedestal
			PedestalRecipeTier tier = PedestalRecipeTier.values()[buf.readInt()]; // the item stack that was crafted
			client.execute(() -> {
				client.world.
						PedestalBlock.spawnUpgradeParticleEffectsForTier(position, tier);
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
