package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.networking.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.util.math.*;

public record ParticleSpawnerConfigurationS2CPayload(BlockPos pos,
                                                     ParticleSpawnerConfiguration configuration) implements CustomPayload {
    
    public static final Id<ParticleSpawnerConfigurationS2CPayload> ID = SpectrumC2SPackets.makeId("change_particle_spawner_settings_client");
    public static final PacketCodec<PacketByteBuf, ParticleSpawnerConfigurationS2CPayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC,
            ParticleSpawnerConfigurationS2CPayload::pos,
            ParticleSpawnerConfiguration.PACKET_CODEC,
            ParticleSpawnerConfigurationS2CPayload::configuration,
            ParticleSpawnerConfigurationS2CPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
