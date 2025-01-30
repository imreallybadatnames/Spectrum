package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.particle.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.particle.*;
import net.minecraft.world.event.*;

public class ExperienceTransmissionParticleEffect extends SimpleTransmissionParticleEffect {
	
	public static final MapCodec<ExperienceTransmissionParticleEffect> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			PositionSource.CODEC.fieldOf("destination").forGetter((effect) -> effect.destination),
			Codec.INT.fieldOf("arrival_in_ticks").forGetter((vibrationParticleEffect) -> vibrationParticleEffect.arrivalInTicks)
	).apply(instance, ExperienceTransmissionParticleEffect::new));
	
	public static PacketCodec<RegistryByteBuf, ExperienceTransmissionParticleEffect> PACKET_CODEC = PacketCodec.tuple(
			PositionSource.PACKET_CODEC, ExperienceTransmissionParticleEffect::getDestination,
			PacketCodecs.INTEGER, ExperienceTransmissionParticleEffect::getArrivalInTicks,
			ExperienceTransmissionParticleEffect::new
	);
	
	public ExperienceTransmissionParticleEffect(PositionSource positionSource, Integer arrivalInTicks) {
		super(positionSource, arrivalInTicks);
	}
	
	@Override
	public ParticleType<ExperienceTransmissionParticleEffect> getType() {
		return SpectrumParticleTypes.EXPERIENCE_TRANSMISSION;
	}
	
}
