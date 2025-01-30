package de.dafuqs.spectrum.items.food;

import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.nbt.*;
import net.minecraft.text.*;

import java.util.*;

public class TeaItem extends DrinkItem {
	
	public TeaItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		
		NbtCompound nbtCompound = stack.getNbt();
		if (nbtCompound != null && nbtCompound.contains("Milk")) {
			tooltip.add(Text.translatable("item.spectrum.restoration_tea.tooltip_milk"));
		}
	}
	
}
