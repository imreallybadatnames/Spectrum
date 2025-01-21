package de.dafuqs.spectrum.helpers.enchantments;

import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class FoundryHelper {
	
	@Nullable
	public static ItemStack getSmeltedItemStack(ItemStack inputItemStack, World world) {
		var drm = world.getRegistryManager();
		var input = new SingleStackRecipeInput(inputItemStack);
		return world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, input, world)
				.map(recipe -> {
					var recipeOutputStack = recipe.value().getResult(drm).copy();
					recipeOutputStack.setCount(recipeOutputStack.getCount() * inputItemStack.getCount());
					return recipeOutputStack;
				})
				.orElse(null);
	}
	
	@NotNull
	public static List<ItemStack> applyFoundry(World world, List<ItemStack> originalStacks) {
		List<ItemStack> returnItemStacks = new ArrayList<>();
		
		for (ItemStack is : originalStacks) {
			ItemStack smeltedStack = FoundryHelper.getSmeltedItemStack(is, world);
			if (smeltedStack == null) {
				returnItemStacks.add(is);
			} else {
				while (!smeltedStack.isEmpty()) {
					int currentAmount = Math.min(smeltedStack.getCount(), smeltedStack.getItem().getMaxCount());
					ItemStack currentStack = smeltedStack.copyWithCount(currentAmount);
					returnItemStacks.add(currentStack);
					smeltedStack.setCount(smeltedStack.getCount() - currentAmount);
				}
			}
		}
		return returnItemStacks;
	}
	
}