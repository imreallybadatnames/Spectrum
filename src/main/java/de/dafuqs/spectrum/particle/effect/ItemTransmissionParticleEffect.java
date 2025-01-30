package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.particle.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.particle.*;
import net.minecraft.world.event.*;

public class ItemTransmissionParticleEffect extends SimpleTransmissionParticleEffect {
	
	public static final MapCodec<ItemTransmissionParticleEffect> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			PositionSource.CODEC.fieldOf("destination").forGetter((effect) -> effect.destination),
			Codec.INT.fieldOf("arrival_in_ticks").forGetter((vibrationParticleEffect) -> vibrationParticleEffect.arrivalInTicks)
	).apply(instance, ItemTransmissionParticleEffect::new));
	
	public static PacketCodec<RegistryByteBuf, ItemTransmissionParticleEffect> PACKET_CODEC = PacketCodec.tuple(
			PositionSource.PACKET_CODEC, ItemTransmissionParticleEffect::getDestination,
			PacketCodecs.INTEGER, ItemTransmissionParticleEffect::getArrivalInTicks,
			ItemTransmissionParticleEffect::new
	);
	
	public ItemTransmissionParticleEffect(PositionSource positionSource, Integer arrivalInTicks) {
		super(positionSource, arrivalInTicks);
	}
	
	@Override
	public ParticleType<ItemTransmissionParticleEffect> getType() {
		return SpectrumParticleTypes.ITEM_TRANSMISSION;
	}
	
}
