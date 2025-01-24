package de.dafuqs.spectrum.api.item;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public interface FermentedItem {
	
	static boolean isPreviewStack(ItemStack stack) {
		return stack.contains(SpectrumDataComponentTypes.IS_PREVIEW_ITEM);
	}
	
	static void setPreviewStack(ItemStack stack) {
		stack.set(SpectrumDataComponentTypes.IS_PREVIEW_ITEM, Unit.INSTANCE);
	}
	
}
