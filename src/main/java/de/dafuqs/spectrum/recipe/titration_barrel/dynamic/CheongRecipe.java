package de.dafuqs.spectrum.recipe.titration_barrel.dynamic;

import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.recipe.IngredientStack;
import de.dafuqs.spectrum.recipe.titration_barrel.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.fluid.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;

import java.util.*;

public class CheongRecipe extends TitrationBarrelRecipe {

	public static final RecipeSerializer<CheongRecipe> SERIALIZER = new EmptyRecipeSerializer<>(CheongRecipe::new);
	public static final Item TAPPING_ITEM = Items.GLASS_BOTTLE;
	public static final int MIN_FERMENTATION_TIME_HOURS = 4;
	public static final ItemStack OUTPUT_STACK = getDefaultStackWithCount(SpectrumItems.CHEONG, 4);
	public static final ItemStack OUTPUT_STACK_MERMAIDS = getDefaultStackWithCount(SpectrumItems.MERMAIDS_JAM, 4);

	public static final List<IngredientStack> INGREDIENT_STACKS = new ArrayList<>() {{
		add(new IngredientStack(Ingredient.fromTag(SpectrumItemTags.FRUITS), null, 8));
		add(new IngredientStack(Ingredient.ofItems(Items.SUGAR), null, 16));
	}};

	public CheongRecipe() {
		super("", false, null, INGREDIENT_STACKS, FluidIngredient.of(Fluids.WATER),
				OUTPUT_STACK, TAPPING_ITEM, MIN_FERMENTATION_TIME_HOURS, null);
	}

	@Override
	public ItemStack tap(Inventory inventory, long secondsFermented, float downfall) {
		ItemStack result = inventory.containsAny(Collections.singleton(SpectrumItems.MERMAIDS_GEM))
				? OUTPUT_STACK_MERMAIDS.copy()
				: OUTPUT_STACK.copy();
		result.setCount(1);
		return result;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}
}
