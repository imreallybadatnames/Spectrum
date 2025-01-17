package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.networking.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.math.*;
import net.minecraft.world.event.*;

public class TypedTransmission extends SimpleTransmission {
	
	public enum Variant {
		BLOCK_POS,
		ITEM,
		EXPERIENCE,
		REDSTONE,
		HUMMINGSTONE;
		
		public static final PacketCodec<ByteBuf, Variant> PACKET_CODEC = PacketCodecs.indexed(i -> Variant.values()[i], Variant::ordinal);
	}
	
	public static final Codec<TypedTransmission> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Vec3d.CODEC.fieldOf("origin").forGetter((itemTransfer) -> itemTransfer.origin),
			PositionSource.CODEC.fieldOf("destination").forGetter((itemTransfer) -> itemTransfer.destination),
			Codec.INT.fieldOf("arrival_in_ticks").forGetter((itemTransfer) -> itemTransfer.arrivalInTicks),
			Codec.INT.fieldOf("variant").forGetter((itemTransfer) -> itemTransfer.variant.ordinal())
	).apply(instance, TypedTransmission::new));
	
	public static final PacketCodec<RegistryByteBuf, TypedTransmission> PACKET_CODEC = PacketCodec.tuple(
			SpectrumPacketCodecs.VEC_3D, TypedTransmission::getOrigin,
			PositionSource.PACKET_CODEC, TypedTransmission::getDestination,
			PacketCodecs.INTEGER, TypedTransmission::getArrivalInTicks,
			Variant.PACKET_CODEC, TypedTransmission::getVariant,
			TypedTransmission::new
	);
	
	
	private final Variant variant;
	
	public TypedTransmission(Vec3d origin, PositionSource destination, int arrivalInTicks, Variant variant) {
		super(origin, destination, arrivalInTicks);
		this.variant = variant;
	}
	
	public TypedTransmission(Object origin, Object destination, Object arrivalInTicks, Object variant) {
		this((Vec3d) origin, (PositionSource) destination, (int) arrivalInTicks, (Variant) variant);
	}
	
	public Variant getVariant() {
		return this.variant;
	}
	
}
