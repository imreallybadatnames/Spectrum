package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.chests.*;
import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

import java.util.*;

public record FabricationChestStatusUpdatePayload(BlockPos pos,
												  ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<FabricationChestStatusUpdatePayload> ID = SpectrumC2SPackets.makeId("fabrication_chest_status_update");
	public static final PacketCodec<PacketByteBuf, FabricationChestStatusUpdatePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			FabricationChestStatusUpdatePayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			FabricationChestStatusUpdatePayload::configuration,
			FabricationChestStatusUpdatePayload::new
	);
	
	public static void sendFabricationChestStatusUpdate(FabricationChestBlockEntity chest) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeBlockPos(chest.getPos());
		buf.writeBoolean(chest.isFullServer());
		buf.writeBoolean(chest.hasValidRecipes());
		buf.writeInt(chest.getRecipeOutputs().size());
		for (ItemStack recipeOutput : chest.getRecipeOutputs()) {
			buf.writeItemStack(recipeOutput);
		}
		
		for (ServerPlayerEntity player : PlayerLookup.tracking(chest)) {
			ServerPlayNetworking.send(player, SpectrumS2CPackets.FABRICATION_CHEST_STATUS_UPDATE, buf);
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<FabricationChestStatusUpdatePayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			var pos = buf.readBlockPos();
			var isFull = buf.readBoolean();
			var hasValidRecipes = buf.readBoolean();
			var outputCount = buf.readInt();
			final var cachedOutputs = new ArrayList<ItemStack>(4);
			for (int i = 0; i < outputCount; i++) {
				cachedOutputs.add(buf.readItemStack());
			}
			
			client.execute(() -> {
				var entity = client.world.getBlockEntity(pos, SpectrumBlockEntities.FABRICATION_CHEST);
				
				if (entity.isEmpty())
					return;
				
				entity.get().updateState(isFull, hasValidRecipes, cachedOutputs);
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
