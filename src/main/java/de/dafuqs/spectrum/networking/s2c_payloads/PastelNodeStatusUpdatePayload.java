package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.blocks.pastel_network.nodes.*;
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

import java.util.*;

public record PastelNodeStatusUpdatePayload(BlockPos pos,
											ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<PastelNodeStatusUpdatePayload> ID = SpectrumC2SPackets.makeId("pastel_node_status_update");
	public static final PacketCodec<PacketByteBuf, PastelNodeStatusUpdatePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			PastelNodeStatusUpdatePayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			PastelNodeStatusUpdatePayload::configuration,
			PastelNodeStatusUpdatePayload::new
	);
	
	public static void sendPastelNodeStatusUpdate(List<PastelNodeBlockEntity> nodes, boolean longSpin) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeBoolean(longSpin);
		buf.writeInt(nodes.size());
		for (PastelNodeBlockEntity node : nodes) {
			var world = node.getWorld();
			
			if (world == null)
				continue;
			
			var time = longSpin ? 24 + world.getRandom().nextInt(11) : 10 + world.getRandom().nextInt(11);
			buf.writeBlockPos(node.getPos());
			buf.writeInt(time);
		}
		
		for (ServerPlayerEntity player : PlayerLookup.tracking(nodes.get(0))) {
			ServerPlayNetworking.send(player, new PastelNodeStatusUpdatePayload());
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PastelNodeStatusUpdatePayload> getPayloadHandler() {
		return (payload, context) -> {
			var trigger = buf.readBoolean();
			var nodeCount = buf.readInt();
			var positions = new ArrayList<BlockPos>(nodeCount);
			var times = new ArrayList<Integer>(nodeCount);
			
			for (int n = 0; n < nodeCount; n++) {
				positions.add(buf.readBlockPos());
				times.add(buf.readInt());
			}
			
			context.client().execute(() -> {
				for (int index = 0; index < positions.size(); index++) {
					var entity = context.client().world.getBlockEntity(positions.get(index));
					
					if (!(entity instanceof PastelNodeBlockEntity node))
						continue;
					
					node.setSpinTicks(times.get(index));
					
					if (trigger && node.isTriggerTransfer())
						node.markTriggered();
				}
			});
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
