package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.*;
import de.dafuqs.spectrum.recipe.fluid_converting.*;
import me.shedaniel.rei.api.common.category.*;
import net.minecraft.recipe.*;
import net.minecraft.util.*;

public class GooConvertingDisplay extends FluidConvertingDisplay {
	
	public GooConvertingDisplay(RecipeEntry<FluidConvertingRecipe> recipe) {
		super(recipe);
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return SpectrumPlugins.GOO_CONVERTING;
	}
	
	@Override
	public Identifier getUnlockIdentifier() {
		return GooConvertingRecipe.UNLOCK_IDENTIFIER;
	}
	
}