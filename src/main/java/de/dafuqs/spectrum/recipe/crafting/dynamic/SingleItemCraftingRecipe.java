package de.dafuqs.spectrum.recipe.crafting.dynamic;

import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.*;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.*;
import net.minecraft.world.*;

public abstract class SingleItemCraftingRecipe extends SpecialCraftingRecipe {
	
	public SingleItemCraftingRecipe() {
		super(CraftingRecipeCategory.MISC);
	}

	public SingleItemCraftingRecipe(CraftingRecipeCategory category) {
		super(category);
	}
	
	@Override
	public boolean matches(CraftingRecipeInput input, World world) {
		boolean matchingItemFound = false;
		
		for (int slot = 0; slot < input.getSize(); ++slot) {
			ItemStack itemStack = input.getStackInSlot(slot);
			if (itemStack.isEmpty()) {
				continue;
			}
			
			if (!matchingItemFound && matches(world, itemStack)) {
				matchingItemFound = true;
			} else {
				return false;
			}
		}
		
		return matchingItemFound;
	}
	
	@Override
	public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup registryLookup) {
		ItemStack stack;
		for (int slot = 0; slot < input.getSize(); ++slot) {
			stack = input.getStackInSlot(slot);
			if (!stack.isEmpty()) {
				return craft(stack.copy());
			}
		}
		
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean fits(int width, int height) {
		return width * height > 0;
	}
	
	public abstract boolean matches(World world, ItemStack stack);
	
	public abstract ItemStack craft(ItemStack stack);
	
}
