package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.particle.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.*;
import net.minecraft.world.event.*;

public class ColoredTransmissionParticleEffect extends TransmissionParticleEffect {
	
	public static final MapCodec<ColoredTransmissionParticleEffect> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			PositionSource.CODEC.fieldOf("destination").forGetter((effect) -> effect.destination),
			Codec.INT.fieldOf("arrival_in_ticks").forGetter((effect) -> effect.arrivalInTicks),
			DyeColor.CODEC.fieldOf("dye_color").forGetter((effect) -> effect.dyeColor)
	).apply(i, ColoredTransmissionParticleEffect::new));
	
	public static final PacketCodec<RegistryByteBuf, ColoredTransmissionParticleEffect> PACKET_CODEC = PacketCodec.tuple(
			PositionSource.PACKET_CODEC, c -> c.destination,
			PacketCodecs.VAR_INT, c -> c.arrivalInTicks,
			DyeColor.PACKET_CODEC, c -> c.dyeColor,
			ColoredTransmissionParticleEffect::new
	);
	
	public final DyeColor dyeColor;
	
	public ColoredTransmissionParticleEffect(PositionSource positionSource, Integer arrivalInTicks, DyeColor dyeColor) {
		super(SpectrumParticleTypes.COLORED_TRANSMISSION, positionSource, arrivalInTicks);
		this.dyeColor = dyeColor;
	}
	
	public DyeColor getDyeColor() {
		return dyeColor;
	}
	
}
