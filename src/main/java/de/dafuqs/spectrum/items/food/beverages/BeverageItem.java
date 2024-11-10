package de.dafuqs.spectrum.items.food.beverages;

import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.items.food.*;
import de.dafuqs.spectrum.items.food.beverages.properties.*;
import net.minecraft.client.item.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.*;
import net.minecraft.world.*;

import java.util.*;

public abstract class BeverageItem extends StatusEffectDrinkItem implements FermentedItem {
	
	public BeverageItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public abstract BeverageProperties getBeverageProperties(ItemStack stack);

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		getBeverageProperties(stack).addTooltip(stack, tooltip);
	}
	
}
