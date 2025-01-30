package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.*;
import de.dafuqs.spectrum.recipe.primordial_fire_burning.*;
import me.shedaniel.rei.api.common.category.*;
import me.shedaniel.rei.api.common.display.basic.*;
import me.shedaniel.rei.api.common.util.*;
import net.minecraft.recipe.*;

import java.util.*;

public class PrimordialFireBurningDisplay extends GatedSpectrumDisplay {
	
	public PrimordialFireBurningDisplay(RecipeEntry<PrimordialFireBurningRecipe> recipe) {
		super(recipe, recipe.value().getIngredients().stream().map(EntryIngredients::ofIngredient).toList(), Collections.singletonList(EntryIngredients.of(recipe.value().getResult(BasicDisplay.registryAccess()))));
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return SpectrumPlugins.PRIMORDIAL_FIRE_BURNING;
	}
	
}