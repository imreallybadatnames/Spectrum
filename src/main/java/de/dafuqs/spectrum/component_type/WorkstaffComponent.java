package de.dafuqs.spectrum.component_type;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;

public record WorkstaffComponent(boolean canTill, boolean canShoot, int fortuneLevel) {
	
	public static final WorkstaffComponent DEFAULT = new WorkstaffComponent(true, false, 4);
	
	public static final Codec<WorkstaffComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.BOOL.fieldOf("can_till").forGetter(c -> c.canTill),
			Codec.BOOL.fieldOf("can_shoot").forGetter(c -> c.canShoot),
			Codec.INT.fieldOf("fortune_level").forGetter(c -> c.fortuneLevel)
	).apply(i, WorkstaffComponent::new));
	
	public static final PacketCodec<ByteBuf, WorkstaffComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.BOOL,
			c -> c.canTill,
			PacketCodecs.BOOL,
			c -> c.canShoot,
			PacketCodecs.VAR_INT,
			c -> c.fortuneLevel,
			WorkstaffComponent::new
	);
	
}
