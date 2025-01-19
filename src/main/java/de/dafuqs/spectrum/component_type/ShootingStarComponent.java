package de.dafuqs.spectrum.component_type;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;

public record ShootingStarComponent(int remainingHits, boolean hardened) {
	
	public static final ShootingStarComponent DEFAULT = new ShootingStarComponent(5, false);
	
	public static final Codec<ShootingStarComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.INT.fieldOf("remaining_hits").forGetter(c -> c.remainingHits),
			Codec.BOOL.fieldOf("hardened").forGetter(c -> c.hardened)
	).apply(i, ShootingStarComponent::new));
	
	public static final PacketCodec<ByteBuf, ShootingStarComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.INTEGER, c -> c.remainingHits,
			PacketCodecs.BOOL, c -> c.hardened,
			ShootingStarComponent::new
	);
	
}
