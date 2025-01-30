package de.dafuqs.spectrum.components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import io.netty.buffer.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.network.codec.*;
import net.minecraft.text.*;

import java.util.function.*;

public record CustomPotionDataComponent(boolean unidentifiable, int additionalDrinkDuration) implements TooltipAppender {
	
	public static final CustomPotionDataComponent DEFAULT = new CustomPotionDataComponent(false, 0);
	
	public static final Codec<CustomPotionDataComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.BOOL.fieldOf("unidentifiable").forGetter(c -> c.unidentifiable),
			Codec.INT.fieldOf("additional_drink_duration").forGetter(c -> c.additionalDrinkDuration)
	).apply(i, CustomPotionDataComponent::new));
	
	public static final PacketCodec<ByteBuf, CustomPotionDataComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.BOOL, c -> c.unidentifiable,
			PacketCodecs.INTEGER, c -> c.additionalDrinkDuration,
			CustomPotionDataComponent::new
	);
	
	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
		int additionalDrinkDuration = this.additionalDrinkDuration();
		if (additionalDrinkDuration > 0) {
			tooltip.accept(Text.translatable("item.spectrum.potion.slower_to_drink"));
		} else if (additionalDrinkDuration < 0) {
			tooltip.accept(Text.translatable("item.spectrum.potion.faster_to_drink"));
		}
	}
	
}
