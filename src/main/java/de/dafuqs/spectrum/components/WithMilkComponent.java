package de.dafuqs.spectrum.components;

import com.mojang.serialization.*;
import io.netty.buffer.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.network.codec.*;
import net.minecraft.text.*;

import java.util.function.*;

public record WithMilkComponent() implements TooltipAppender {
	
	public static final Codec<WithMilkComponent> CODEC = Codec.unit(WithMilkComponent::new);
	public static final PacketCodec<ByteBuf, WithMilkComponent> PACKET_CODEC = PacketCodec.unit(new WithMilkComponent());
	
	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
		tooltip.accept(Text.translatable("item.spectrum.restoration_tea.tooltip_milk"));
	}
	
}
