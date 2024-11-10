package de.dafuqs.spectrum.items.food;

import net.minecraft.client.item.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.*;
import net.minecraft.world.*;

import java.util.*;

public class FreigeistItem extends DrinkItem {
	
	public FreigeistItem(Settings settings) {
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.freigeist.tooltip"));
	}
	
}
