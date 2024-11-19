package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.*;
import de.dafuqs.spectrum.recipe.fluid_converting.*;
import me.shedaniel.rei.api.common.category.*;
import net.minecraft.recipe.*;
import net.minecraft.util.*;

public class DragonrotConvertingDisplay extends FluidConvertingDisplay {
	
	public DragonrotConvertingDisplay(RecipeEntry<DragonrotConvertingRecipe> recipe) {
		super(recipe);
	}
	
	@Override
	public Identifier getUnlockIdentifier() {
		return DragonrotConvertingRecipe.UNLOCK_IDENTIFIER;
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return SpectrumPlugins.DRAGONROT_CONVERTING;
	}
	
}