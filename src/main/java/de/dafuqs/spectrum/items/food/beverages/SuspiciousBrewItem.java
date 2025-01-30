package de.dafuqs.spectrum.items.food.beverages;

import de.dafuqs.spectrum.api.item.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class SuspiciousBrewItem extends BeverageItem {
	
	//TODO should this use the SuspiciousStewContents component instead?
	
	public SuspiciousBrewItem(Settings settings) {
		super(settings.component(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT));
	}
	
	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		if (FermentedItem.isPreviewStack(stack)) {
			tooltip.add(Text.translatable("item.spectrum.suspicious_brew.tooltip.preview").formatted(Formatting.GRAY));
			tooltip.add(Text.translatable("item.spectrum.suspicious_brew.tooltip.preview2").formatted(Formatting.GRAY));
		}
	}
	
}
