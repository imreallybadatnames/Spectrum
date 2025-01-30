package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.networking.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.event.*;

public class ColoredTransmission extends SimpleTransmission {
	
	public static final Codec<ColoredTransmission> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Vec3d.CODEC.fieldOf("origin").forGetter(c -> c.origin),
			PositionSource.CODEC.fieldOf("destination").forGetter(c -> c.destination),
			Codec.INT.fieldOf("arrival_in_ticks").forGetter(c -> c.arrivalInTicks),
			DyeColor.CODEC.fieldOf("dye_color").forGetter(c -> c.dyeColor)
	).apply(instance, ColoredTransmission::new));
	
	public static final PacketCodec<RegistryByteBuf, ColoredTransmission> PACKET_CODEC = PacketCodec.tuple(
			SpectrumPacketCodecs.VEC_3D, c -> c.origin,
			PositionSource.PACKET_CODEC, c -> c.destination,
			PacketCodecs.INTEGER, c -> c.arrivalInTicks,
			DyeColor.PACKET_CODEC, c -> c.dyeColor,
			ColoredTransmission::new
	);
	
	protected final DyeColor dyeColor;
	
	public ColoredTransmission(Vec3d origin, PositionSource destination, int arrivalInTicks, DyeColor dyeColor) {
		super(origin, destination, arrivalInTicks);
		this.dyeColor = dyeColor;
	}
	
	public DyeColor getDyeColor() {
		return this.dyeColor;
	}
	
}
