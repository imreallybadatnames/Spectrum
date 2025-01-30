package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.particle.*;
import net.minecraft.registry.*;
import net.minecraft.util.math.*;
import net.minecraft.world.event.*;

import java.util.*;

public class TransmissionParticleEffect implements ParticleEffect {
	
	public static final MapCodec<TransmissionParticleEffect> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			Registries.PARTICLE_TYPE.getCodec().fieldOf("particle_type").forGetter(c -> c.particleType),
			PositionSource.CODEC.fieldOf("destination").forGetter(c -> c.destination),
			Codec.INT.fieldOf("arrival_in_ticks").forGetter(c -> c.arrivalInTicks)
	).apply(i, TransmissionParticleEffect::new));
	
	public static final PacketCodec<RegistryByteBuf, TransmissionParticleEffect> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.registryValue(RegistryKeys.PARTICLE_TYPE), c -> c.particleType,
			PositionSource.PACKET_CODEC, c -> c.destination,
			PacketCodecs.VAR_INT, c -> c.arrivalInTicks,
			TransmissionParticleEffect::new
	);
	
	protected final ParticleType<?> particleType;
	protected final PositionSource destination;
	protected final int arrivalInTicks;
	
	public TransmissionParticleEffect(ParticleType<?> particleType, PositionSource positionSource, int arrivalInTicks) {
		this.particleType = particleType;
		this.destination = positionSource;
		this.arrivalInTicks = arrivalInTicks;
	}
	
	public PositionSource getDestination() {
		return this.destination;
	}
	
	public int getArrivalInTicks() {
		return this.arrivalInTicks;
	}
	
	@Override
	public ParticleType<?> getType() {
		return particleType;
	}
	
	@Override
	public String toString() {
		Optional<Vec3d> pos = this.destination.getPos(null);
		if (pos.isPresent()) {
			double d = pos.get().getX();
			double e = pos.get().getY();
			double f = pos.get().getZ();
			return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %d", Registries.PARTICLE_TYPE.getId(this.getType()), d, e, f, this.arrivalInTicks);
		}
		return String.format(Locale.ROOT, "%s <no destination> %d", Registries.PARTICLE_TYPE.getId(this.getType()), this.arrivalInTicks);
	}
}
