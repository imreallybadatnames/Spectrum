package de.dafuqs.spectrum.items.food;

import de.dafuqs.spectrum.registries.SpectrumItems;
import net.minecraft.block.*;
import net.minecraft.client.item.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class AliasedTooltipItem extends AliasedBlockItem {

	private final List<MutableText> tooltipTexts = new ArrayList<>();

	public AliasedTooltipItem(Block block, Settings settings, String tooltip) {
		super(block, settings);
		this.tooltipTexts.add(Text.translatable(tooltip));
	}

	public AliasedTooltipItem(Block block, Settings settings, String[] tooltips) {
		super(block, settings);
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

	@Override
	public int getMaxUseTime(ItemStack stack) {
		if (stack.isOf(SpectrumItems.NIGHTDEW_SPROUT)) {
			return 96;
		}
		return super.getMaxUseTime(stack);
	}
}
