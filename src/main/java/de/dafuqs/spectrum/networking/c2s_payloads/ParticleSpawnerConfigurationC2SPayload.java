package de.dafuqs.spectrum.networking.c2s_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.inventories.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;

public record ParticleSpawnerConfigurationC2SPayload(
        ParticleSpawnerConfiguration configuration) implements CustomPayload {
    
    public static final CustomPayload.Id<ParticleSpawnerConfigurationC2SPayload> ID = SpectrumC2SPackets.makeId("change_particle_spawner_settings");
    public static final PacketCodec<PacketByteBuf, ParticleSpawnerConfigurationC2SPayload> CODEC = PacketCodec.tuple(
            ParticleSpawnerConfiguration.PACKET_CODEC,
            ParticleSpawnerConfigurationC2SPayload::configuration,
            ParticleSpawnerConfigurationC2SPayload::new
    );
    
    public static ServerPlayNetworking.PlayPayloadHandler<ParticleSpawnerConfigurationC2SPayload> getPayloadHandler() {
        return (packet, context) -> {
            // receive the client packet...
            if (context.player().currentScreenHandler instanceof ParticleSpawnerScreenHandler particleSpawnerScreenHandler) {
                ParticleSpawnerBlockEntity blockEntity = particleSpawnerScreenHandler.getBlockEntity();
                if (blockEntity != null) {
                    // ...apply the new settings...
                    blockEntity.applySettings(packet.configuration());
                    
                    // ...and distribute it to all clients again
                    // Iterate over all players tracking a position in the world and send the packet to each player
                    for (ServerPlayerEntity serverPlayerEntity : PlayerLookup.tracking((ServerWorld) blockEntity.getWorld(), blockEntity.getPos())) {
                        ServerPlayNetworking.send(serverPlayerEntity, new ParticleSpawnerConfigurationS2CPayload(blockEntity.getPos(), blockEntity.getConfiguration()));
                    }
                }
            }
        };
    }
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
