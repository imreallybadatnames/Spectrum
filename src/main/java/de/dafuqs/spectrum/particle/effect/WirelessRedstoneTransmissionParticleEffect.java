package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.particle.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.particle.*;
import net.minecraft.world.event.*;

public class WirelessRedstoneTransmissionParticleEffect extends SimpleTransmissionParticleEffect {
	
	public static final MapCodec<WirelessRedstoneTransmissionParticleEffect> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			PositionSource.CODEC.fieldOf("destination").forGetter((effect) -> effect.destination),
			Codec.INT.fieldOf("arrival_in_ticks").forGetter((vibrationParticleEffect) -> vibrationParticleEffect.arrivalInTicks)
	).apply(instance, WirelessRedstoneTransmissionParticleEffect::new));
	
	public static PacketCodec<RegistryByteBuf, WirelessRedstoneTransmissionParticleEffect> PACKET_CODEC = PacketCodec.tuple(
			PositionSource.PACKET_CODEC, WirelessRedstoneTransmissionParticleEffect::getDestination,
			PacketCodecs.INTEGER, WirelessRedstoneTransmissionParticleEffect::getArrivalInTicks,
			WirelessRedstoneTransmissionParticleEffect::new
	);
	
	public WirelessRedstoneTransmissionParticleEffect(PositionSource positionSource, Integer arrivalInTicks) {
		super(positionSource, arrivalInTicks);
	}
	
	@Override
	public ParticleType<WirelessRedstoneTransmissionParticleEffect> getType() {
		return SpectrumParticleTypes.WIRELESS_REDSTONE_TRANSMISSION;
	}
	
}
