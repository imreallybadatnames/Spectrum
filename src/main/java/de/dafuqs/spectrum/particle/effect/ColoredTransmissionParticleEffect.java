package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.particle.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.particle.*;
import net.minecraft.util.*;
import net.minecraft.world.event.*;

public class ColoredTransmissionParticleEffect extends SimpleTransmissionParticleEffect {
	
	public static final MapCodec<ColoredTransmissionParticleEffect> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			PositionSource.CODEC.fieldOf("destination").forGetter((effect) -> effect.destination),
			Codec.INT.fieldOf("arrival_in_ticks").forGetter((vibrationParticleEffect) -> vibrationParticleEffect.arrivalInTicks),
			DyeColor.CODEC.fieldOf("color").forGetter((effect) -> effect.dyeColor)
	).apply(instance, ColoredTransmissionParticleEffect::new));
	
	public static PacketCodec<RegistryByteBuf, ColoredTransmissionParticleEffect> PACKET_CODEC = PacketCodec.tuple(
			PositionSource.PACKET_CODEC, ColoredTransmissionParticleEffect::getDestination,
			PacketCodecs.INTEGER, ColoredTransmissionParticleEffect::getArrivalInTicks,
			DyeColor.PACKET_CODEC, ColoredTransmissionParticleEffect::getDyeColor,
			ColoredTransmissionParticleEffect::new
	);
	
	public final DyeColor dyeColor;
	
	public ColoredTransmissionParticleEffect(PositionSource positionSource, Integer arrivalInTicks, DyeColor dyeColor) {
		super(positionSource, arrivalInTicks);
		this.dyeColor = dyeColor;
	}
	
	@Override
	public ParticleType<ColoredTransmissionParticleEffect> getType() {
		return SpectrumParticleTypes.COLORED_TRANSMISSION;
	}
	
	public DyeColor getDyeColor() {
		return dyeColor;
	}
	
}
