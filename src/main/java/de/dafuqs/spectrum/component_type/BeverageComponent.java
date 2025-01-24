package de.dafuqs.spectrum.component_type;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import io.netty.buffer.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.network.codec.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.function.*;

public record BeverageComponent(long daysAged, int alcoholPercent, float thickness) implements TooltipAppender {
	
	public static final BeverageComponent DEFAULT = new BeverageComponent(0, 0, 0);
	
	public static final Codec<BeverageComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.LONG.optionalFieldOf("days_aged", 0L).forGetter(BeverageComponent::daysAged),
			Codec.INT.optionalFieldOf("alcohol_percent", 0).forGetter(BeverageComponent::alcoholPercent),
			Codec.FLOAT.optionalFieldOf("thickness", 0f).forGetter(BeverageComponent::thickness)
	).apply(i, BeverageComponent::new));
	
	public static final PacketCodec<ByteBuf, BeverageComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_LONG, BeverageComponent::daysAged,
			PacketCodecs.VAR_INT, BeverageComponent::alcoholPercent,
			PacketCodecs.FLOAT, BeverageComponent::thickness,
			BeverageComponent::new
	);
	
	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
		if (daysAged > 365) {
			long ageInDays = daysAged % 365;
			long ageInYears = Math.floorDiv(daysAged, 365);
			if (ageInDays == 0)
				tooltip.accept(Text.translatable("item.spectrum.infused_beverage.tooltip.age_years", ageInYears, alcoholPercent).formatted(Formatting.GRAY));
			else
				tooltip.accept(Text.translatable("item.spectrum.infused_beverage.tooltip.age_composite", ageInYears, ageInDays, alcoholPercent).formatted(Formatting.GRAY));
		} else {
			tooltip.accept(Text.translatable("item.spectrum.infused_beverage.tooltip.age", daysAged, alcoholPercent).formatted(Formatting.GRAY));
		}
	}
}
