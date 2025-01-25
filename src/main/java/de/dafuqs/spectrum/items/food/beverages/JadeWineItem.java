package de.dafuqs.spectrum.items.food.beverages;

import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class JadeWineItem extends BeverageItem {
	
	public JadeWineItem(Settings settings) {
		super(settings.component(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT).component(SpectrumDataComponentTypes.JADE_WINE, JadeWineComponent.DEFAULT));
	}
	
	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		if (FermentedItem.isPreviewStack(stack)) {
			String translationKey = getTranslationKey();
			tooltip.add(Text.translatable(translationKey + ".tooltip.preview").formatted(Formatting.GRAY));
			tooltip.add(Text.translatable(translationKey + ".tooltip.preview2").formatted(Formatting.GRAY));
			tooltip.add(Text.translatable("item.spectrum.tooltip.could_use_some_sweetener").formatted(Formatting.GRAY));
		}
	}
	
}
