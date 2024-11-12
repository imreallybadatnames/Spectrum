package de.dafuqs.spectrum.recipe.fluid_converting.dynamic;

import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.recipe.fluid_converting.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.*;

public class MeatToRottenFleshRecipe extends DragonrotConvertingRecipe {
	
	public static final RecipeSerializer<MeatToRottenFleshRecipe> SERIALIZER = new EmptyRecipeSerializer<>(MeatToRottenFleshRecipe::new);
	
	public MeatToRottenFleshRecipe(Identifier identifier) {
		super(identifier, "", false, UNLOCK_IDENTIFIER, getMeatsIngredient(), Items.ROTTEN_FLESH.getDefaultStack());
	}
	
	private static Ingredient getMeatsIngredient() {
		return Ingredient.ofStacks(Registries.ITEM.getOrCreateEntryList(ItemTags.MEAT)
				.stream()
				.filter(item -> item.value() == Items.ROTTEN_FLESH)
				.map(ItemStack::new));
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}
	
}
