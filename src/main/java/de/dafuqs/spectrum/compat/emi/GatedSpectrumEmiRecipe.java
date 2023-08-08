package de.dafuqs.spectrum.compat.emi;

import de.dafuqs.spectrum.recipe.*;
import dev.emi.emi.api.recipe.*;
import dev.emi.emi.api.stack.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import java.util.*;

public abstract class GatedSpectrumEmiRecipe<T extends GatedRecipe> extends SpectrumEmiRecipe {
	public final T recipe;

	public GatedSpectrumEmiRecipe(EmiRecipeCategory category, Identifier unlockIdentifier, T recipe, int width, int height) {
		super(category, unlockIdentifier, recipe.getId(), width, height);
		this.recipe = recipe;
		if (!recipe.getOutput(DynamicRegistryManager.EMPTY).isEmpty()) {
			this.output = List.of(EmiStack.of(recipe.getOutput(DynamicRegistryManager.EMPTY)));
		}
	}
	
	@Override
	public boolean isUnlocked() {
		return hasAdvancement(recipe.getRequiredAdvancementIdentifier()) && super.isUnlocked();
	}
	
	@Override
	public boolean hideCraftable() {
		return recipe.isSecret() || super.hideCraftable();
	}
}
