package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.helpers.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.event.*;

public class TypedTransmission extends SimpleTransmission {
	
	public enum Variant implements StringIdentifiable {
		BLOCK_POS,
		ITEM,
		EXPERIENCE,
		REDSTONE,
		HUMMINGSTONE;
		
		@Override
		public String asString() {
			return name();
		}
	}
	
	public static final Codec<TypedTransmission> CODEC = RecordCodecBuilder.create(i -> i.group(
			Vec3d.CODEC.fieldOf("origin").forGetter(c -> c.origin),
			PositionSource.CODEC.fieldOf("destination").forGetter(c -> c.destination),
			Codec.INT.fieldOf("arrival_in_ticks").forGetter(c -> c.arrivalInTicks),
			StringIdentifiable.createCodec(Variant::values).fieldOf("variant").forGetter(c -> c.variant)
	).apply(i, TypedTransmission::new));
	
	public static final PacketCodec<RegistryByteBuf, TypedTransmission> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecHelper.VEC3D, c -> c.origin,
			PositionSource.PACKET_CODEC, c -> c.destination,
			PacketCodecs.INTEGER, c -> c.arrivalInTicks,
			PacketCodecHelper.enumOf(Variant::values), c -> c.variant,
			TypedTransmission::new
	);
	
	private final Variant variant;
	
	public TypedTransmission(Vec3d origin, PositionSource destination, int arrivalInTicks, Variant variant) {
		super(origin, destination, arrivalInTicks);
		this.variant = variant;
	}
	
	public Variant getVariant() {
		return this.variant;
	}
	
}
