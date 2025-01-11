package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.items.map.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.minecraft.client.render.*;
import net.minecraft.item.*;
import net.minecraft.item.map.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

public record SyncArtisansAtlasPayload(BlockPos pos,
									   ParticleSpawnerConfiguration configuration) implements CustomPayload {
	
	public static final Id<SyncArtisansAtlasPayload> ID = SpectrumC2SPackets.makeId("sync_artisans_atlas");
	public static final PacketCodec<PacketByteBuf, SyncArtisansAtlasPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC,
			SyncArtisansAtlasPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC,
			SyncArtisansAtlasPayload::configuration,
			SyncArtisansAtlasPayload::new
	);
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.PlayPayloadHandler<SyncArtisansAtlasPayload> getPayloadHandler() {
		return (client, handler, buf, responseSender) -> {
			String targetIdStr = buf.readString();
			Identifier targetId = targetIdStr.length() == 0 ? null : Identifier.of(targetIdStr);
			
			MapUpdateS2CPacket packet = new MapUpdateS2CPacket(buf);
			
			client.execute(() -> {
				NetworkThreadUtils.forceMainThread(packet, handler, client);
				MapRenderer mapRenderer = client.gameRenderer.getMapRenderer();
				int i = packet.getId();
				String string = FilledMapItem.getMapName(i);
				
				if (client.world != null) {
					MapState mapState = client.world.getMapState(string);
					
					if (mapState == null) {
						mapState = new ArtisansAtlasState(packet.getScale(), packet.isLocked(), client.world.getRegistryKey());
						client.world.putClientsideMapState(string, mapState);
					}
					
					if (mapState instanceof ArtisansAtlasState artisansAtlasState) {
						artisansAtlasState.setTargetId(targetId);
						packet.apply(mapState);
						mapRenderer.updateTexture(i, mapState);
					}
				}
			});
		};
	}
	
}
