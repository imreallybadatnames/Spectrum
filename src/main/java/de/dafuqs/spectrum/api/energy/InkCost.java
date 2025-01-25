package de.dafuqs.spectrum.api.energy;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.energy.color.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;

public record InkCost(InkColor color, long cost) {
	
	public static final Codec<InkCost> CODEC = RecordCodecBuilder.create(i -> i.group(
			InkColor.CODEC.fieldOf("color").forGetter(InkCost::color),
			Codec.LONG.fieldOf("cost").forGetter(InkCost::cost)
	).apply(i, InkCost::new));
	
	public static final PacketCodec<ByteBuf, InkCost> PACKET_CODEC = PacketCodec.tuple(
			InkColor.PACKET_CODEC, InkCost::color,
			PacketCodecs.VAR_LONG, InkCost::cost,
			InkCost::new
	);
	
}