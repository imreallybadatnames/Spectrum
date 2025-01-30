package de.dafuqs.spectrum.items.food.beverages;

import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.items.food.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.text.*;

import java.util.*;

public class BeverageItem extends StatusEffectDrinkItem implements FermentedItem {
	
	public BeverageItem(Settings settings) {
		super(settings.component(SpectrumDataComponentTypes.BEVERAGE, BeverageComponent.DEFAULT));
	}
	
	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		
		var infused = stack.get(SpectrumDataComponentTypes.INFUSED_BEVERAGE);
		if (infused != null) infused.appendTooltip(context, tooltip::add, type);
		
		var beverage = stack.get(SpectrumDataComponentTypes.BEVERAGE);
		if (beverage != null) beverage.appendTooltip(context, tooltip::add, type);
		
		var jade = stack.get(SpectrumDataComponentTypes.JADE_WINE);
		if (jade != null) jade.appendTooltip(context, tooltip::add, type);
		
		var effects = stack.get(DataComponentTypes.POTION_CONTENTS);
		if (effects != null) effects.buildTooltip(tooltip::add, 1.0f, context.getUpdateTickRate());
	}
	
}
