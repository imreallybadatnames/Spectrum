package de.dafuqs.spectrum.components;

import com.mojang.serialization.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;

public record PairedItemComponent(long signature) {
	
	public static final Codec<PairedItemComponent> CODEC = Codec.LONG.xmap(PairedItemComponent::new, PairedItemComponent::signature);
	
	public static final PacketCodec<ByteBuf, PairedItemComponent> PACKET_CODEC = PacketCodecs.VAR_LONG.xmap(PairedItemComponent::new, PairedItemComponent::signature);
	
	@Override
	public boolean equals(Object o) {
		return this == o || o instanceof PairedItemComponent(long oSignature)
				&& oSignature == signature;
	}
	
}
