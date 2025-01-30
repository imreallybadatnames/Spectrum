package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.*;
import de.dafuqs.spectrum.recipe.anvil_crushing.*;
import me.shedaniel.rei.api.common.category.*;
import me.shedaniel.rei.api.common.display.basic.*;
import me.shedaniel.rei.api.common.util.*;
import net.minecraft.recipe.*;

import java.util.*;

public class AnvilCrushingDisplay extends GatedSpectrumDisplay {
	public final float experience;
	public final float crushedItemsPerPointOfDamage;
	
	public AnvilCrushingDisplay(RecipeEntry<AnvilCrushingRecipe> recipe) {
		super(recipe, recipe.value().getIngredients().stream().map(EntryIngredients::ofIngredient).toList(), Collections.singletonList(EntryIngredients.of(recipe.value().getResult(BasicDisplay.registryAccess()))));
		this.experience = recipe.value().getExperience();
		this.crushedItemsPerPointOfDamage = recipe.value().getCrushedItemsPerPointOfDamage();
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return SpectrumPlugins.ANVIL_CRUSHING;
	}
	
}