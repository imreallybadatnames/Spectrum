package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.client.world.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.particle.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public record PlayPedestalCraftingFinishedParticlePayload(BlockPos pos, ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PlayPedestalCraftingFinishedParticlePayload> ID = SpectrumC2SPackets.makeId("play_pedestal_crafting_finished_particle");
	public static final PacketCodec<PacketByteBuf, PlayPedestalCraftingFinishedParticlePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PlayPedestalCraftingFinishedParticlePayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PlayPedestalCraftingFinishedParticlePayload::configuration,
			PlayPedestalCraftingFinishedParticlePayload::new
	);
	
	/**
	 * @param world     the world
	 * @param blockPos  the blockpos of the pedestal
	 * @param itemStack the itemstack that was crafted
	 */
	public static void sendPlayPedestalCraftingFinishedParticle(World world, BlockPos blockPos, ItemStack itemStack) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeBlockPos(blockPos);
		buf.writeItemStack(itemStack);
		for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, blockPos)) {
			ServerPlayNetworking.send(player, new PlayPedestalCraftingFinishedParticlePayload());
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayPedestalCraftingFinishedParticlePayload> getPayloadHandler() {
		return (payload, context) -> {
			MinecraftClient client = context.client();
			BlockPos position = buf.readBlockPos(); // the block pos of the pedestal
			ItemStack itemStack = buf.readItemStack(); // the item stack that was crafted
			client.execute(() -> {
				ClientWorld world = client.world;
				Random random = world.random;
				
				for (int i = 0; i < 10; i++) {
					client.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack), position.getX() + 0.5, position.getY() + 1, position.getZ() + 0.5, 0.15 - random.nextFloat() * 0.3, random.nextFloat() * 0.15 + 0.1, 0.15 - random.nextFloat() * 0.3);
				}
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
