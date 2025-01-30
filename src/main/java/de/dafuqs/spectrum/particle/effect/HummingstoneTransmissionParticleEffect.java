package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.particle.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.particle.*;
import net.minecraft.world.event.*;

public class HummingstoneTransmissionParticleEffect extends SimpleTransmissionParticleEffect {
	
	public static final MapCodec<HummingstoneTransmissionParticleEffect> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			PositionSource.CODEC.fieldOf("destination").forGetter((effect) -> effect.destination),
			Codec.INT.fieldOf("arrival_in_ticks").forGetter((vibrationParticleEffect) -> vibrationParticleEffect.arrivalInTicks)
	).apply(instance, HummingstoneTransmissionParticleEffect::new));
	
	public static PacketCodec<RegistryByteBuf, HummingstoneTransmissionParticleEffect> PACKET_CODEC = PacketCodec.tuple(
			PositionSource.PACKET_CODEC, HummingstoneTransmissionParticleEffect::getDestination,
			PacketCodecs.INTEGER, HummingstoneTransmissionParticleEffect::getArrivalInTicks,
			HummingstoneTransmissionParticleEffect::new
	);
	
	public HummingstoneTransmissionParticleEffect(PositionSource positionSource, Integer arrivalInTicks) {
		super(positionSource, arrivalInTicks);
	}
	
	@Override
	public ParticleType<HummingstoneTransmissionParticleEffect> getType() {
		return SpectrumParticleTypes.HUMMINGSTONE_TRANSMISSION;
	}
	
}
