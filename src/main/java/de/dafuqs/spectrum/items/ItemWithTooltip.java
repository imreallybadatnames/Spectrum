package de.dafuqs.spectrum.items;

import net.minecraft.client.item.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.UnaryOperator;

public class ItemWithTooltip extends Item {

	private final List<MutableText> tooltipTexts = new ArrayList<>();
	
	public ItemWithTooltip(Settings settings, String tooltip) {
		super(settings);
		this.tooltipTexts.add(Text.translatable(tooltip));
	}
	
	public ItemWithTooltip(Settings settings, String[] tooltips) {
		super(settings);
		Arrays.stream(tooltips)
				.map(Text::translatable)
				.forEach(tooltipTexts::add);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		for (MutableText text : this.tooltipTexts) {
			tooltip.add(text.formatted(Formatting.GRAY));
		}
	}
}
