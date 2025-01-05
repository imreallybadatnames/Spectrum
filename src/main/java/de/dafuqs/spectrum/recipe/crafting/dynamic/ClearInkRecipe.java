package de.dafuqs.spectrum.recipe.crafting.dynamic;

import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.world.*;

public class ClearInkRecipe extends SingleItemCraftingRecipe {
	
	@Override
	public boolean matches(World world, ItemStack stack) {
		return stack.getItem() instanceof InkStorageItem;
	}
	
	@Override
	public ItemStack craft(ItemStack stack) {
		if (stack.getItem() instanceof InkStorageItem<?> inkStorageItem) {
			stack = stack.copy();
			stack.setCount(1);
			inkStorageItem.clearEnergyStorage(stack);
		}
		return stack;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.CLEAR_INK_SERIALIZER;
	}
	
}
