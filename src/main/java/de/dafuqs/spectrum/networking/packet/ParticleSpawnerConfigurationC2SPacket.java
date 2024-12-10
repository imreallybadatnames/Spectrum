package de.dafuqs.spectrum.networking.packet;

import de.dafuqs.spectrum.blocks.particle_spawner.ParticleSpawnerConfiguration;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record ParticleSpawnerConfigurationC2SPacket(ParticleSpawnerConfiguration configuration) implements CustomPayload {

    public static final CustomPayload.Id<ParticleSpawnerConfigurationC2SPacket> ID = SpectrumC2SPackets.makeId("change_particle_spawner_settings");
    public static final PacketCodec<PacketByteBuf, ParticleSpawnerConfigurationC2SPacket> CODEC = PacketCodec.tuple(
            ParticleSpawnerConfiguration.PACKET_CODEC,
            ParticleSpawnerConfigurationC2SPacket::configuration,
            ParticleSpawnerConfigurationC2SPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
