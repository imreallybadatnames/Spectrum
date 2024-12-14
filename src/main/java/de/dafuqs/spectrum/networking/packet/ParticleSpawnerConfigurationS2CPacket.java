package de.dafuqs.spectrum.networking.packet;

import de.dafuqs.spectrum.blocks.particle_spawner.ParticleSpawnerConfiguration;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record ParticleSpawnerConfigurationS2CPacket(BlockPos pos, ParticleSpawnerConfiguration configuration) implements CustomPayload {

    public static final Id<ParticleSpawnerConfigurationS2CPacket> ID = SpectrumC2SPackets.makeId("change_particle_spawner_settings_client");
    public static final PacketCodec<PacketByteBuf, ParticleSpawnerConfigurationS2CPacket> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC,
            ParticleSpawnerConfigurationS2CPacket::pos,
            ParticleSpawnerConfiguration.PACKET_CODEC,
            ParticleSpawnerConfigurationS2CPacket::configuration,
            ParticleSpawnerConfigurationS2CPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
