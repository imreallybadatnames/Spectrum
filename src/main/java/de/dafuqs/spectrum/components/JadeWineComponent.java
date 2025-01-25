package de.dafuqs.spectrum.components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import io.netty.buffer.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.network.codec.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.function.*;

public record JadeWineComponent(float bloominess, boolean sweetened) implements TooltipAppender {
	
	public static final JadeWineComponent DEFAULT = new JadeWineComponent(0, false);
	
	public static final Codec<JadeWineComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.FLOAT.optionalFieldOf("bloominess", 0f).forGetter(JadeWineComponent::bloominess),
			Codec.BOOL.optionalFieldOf("sweetened", false).forGetter(JadeWineComponent::sweetened)
	).apply(i, JadeWineComponent::new));
	
	public static final PacketCodec<ByteBuf, JadeWineComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.FLOAT, JadeWineComponent::bloominess,
			PacketCodecs.BOOL, JadeWineComponent::sweetened,
			JadeWineComponent::new
	);
	
	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
		if (sweetened)
			tooltip.accept(Text.translatable("item.spectrum.jade_wine.tooltip.bloominess_sweetened", bloominess).formatted(Formatting.GRAY));
		else
			tooltip.accept(Text.translatable("item.spectrum.jade_wine.tooltip.bloominess", bloominess).formatted(Formatting.GRAY));
	}
	
	@Override
	public boolean equals(Object o) {
		return this == o || o instanceof JadeWineComponent(float oBloominess, boolean oSweetened)
				&& oBloominess == bloominess
				&& oSweetened == sweetened;
	}
	
}
