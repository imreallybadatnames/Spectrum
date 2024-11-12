package de.dafuqs.spectrum.blocks.upgrade;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class UpgradeBlockItem extends BlockItem {
	
	private final String tooltipString;
	
	public UpgradeBlockItem(Block block, Settings settings, String tooltipString) {
		super(block, settings);
		this.tooltipString = tooltipString;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum." + this.tooltipString + ".tooltip").formatted(Formatting.GRAY));
	}
	
}
