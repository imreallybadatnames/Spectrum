package de.dafuqs.spectrum.components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;

public record PipeBombComponent(long timestamp, boolean isArmed) {
	
	public static final PipeBombComponent DEFAULT = new PipeBombComponent(0, false);
	
	public static final Codec<PipeBombComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.LONG.fieldOf("timestamp").forGetter(c -> c.timestamp),
			Codec.BOOL.fieldOf("is_armed").forGetter(c -> c.isArmed)
	).apply(i, PipeBombComponent::new));
	
	public static final PacketCodec<ByteBuf, PipeBombComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_LONG, c -> c.timestamp,
			PacketCodecs.BOOL, c -> c.isArmed,
			PipeBombComponent::new
	);
	
	@Override
	public boolean equals(Object o) {
		return this == o || o instanceof PipeBombComponent(long oTimestamp, boolean oIsArmed)
				&& oTimestamp == timestamp
				&& oIsArmed == isArmed;
	}
	
}
