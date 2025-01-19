package de.dafuqs.spectrum.component_type;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.energy.color.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.text.*;

import java.util.*;

public record OptionalInkColorComponent(Optional<InkColor> color) {
	
	public static final OptionalInkColorComponent DEFAULT = new OptionalInkColorComponent(Optional.empty());
	
	public static final Codec<OptionalInkColorComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			InkColor.CODEC.optionalFieldOf("ink_color").forGetter(c -> c.color)
	).apply(i, OptionalInkColorComponent::new));
	
	public static final PacketCodec<ByteBuf, OptionalInkColorComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.optional(InkColor.PACKET_CODEC), optionalInkColorComponent -> optionalInkColorComponent.color,
			OptionalInkColorComponent::new
	);
	
	public void addTooltip(List<Text> tooltip) {
		if (this.color.isPresent()) {
			tooltip.add(color.get().getColoredInkName());
		}
	}
	
}
