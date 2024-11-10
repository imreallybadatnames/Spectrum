package de.dafuqs.spectrum.api.recipe;

import net.minecraft.item.*;
import net.minecraft.recipe.input.*;
import net.minecraft.text.*;

public interface DescriptiveGatedRecipe<C extends RecipeInput> extends GatedRecipe<C> {
	
	Text getDescription();
	
	Item getItem();
	
}
