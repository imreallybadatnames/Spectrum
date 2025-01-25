package de.dafuqs.spectrum.components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.energy.color.*;
import io.netty.buffer.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.network.codec.*;
import net.minecraft.text.*;

import java.util.*;
import java.util.function.*;

public record OptionalInkColorComponent(Optional<InkColor> color) implements TooltipAppender {
	
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
	
	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> accept, TooltipType type) {
		color.map(InkColor::getColoredInkName).ifPresent(accept);
	}
	
	@Override
	public boolean equals(Object o) {
		return this == o || o instanceof OptionalInkColorComponent(Optional<InkColor> oColor)
				&& oColor.equals(color);
	}
	
}
