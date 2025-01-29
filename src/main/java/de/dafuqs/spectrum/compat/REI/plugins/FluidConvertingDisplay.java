package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.revelationary.api.advancements.*;
import de.dafuqs.spectrum.compat.REI.*;
import de.dafuqs.spectrum.recipe.fluid_converting.*;
import me.shedaniel.rei.api.common.display.basic.*;
import me.shedaniel.rei.api.common.entry.*;
import net.minecraft.client.*;
import net.minecraft.recipe.*;
import net.minecraft.util.*;

public abstract class FluidConvertingDisplay extends GatedSpectrumDisplay {
	
	public <T extends FluidConvertingRecipe> FluidConvertingDisplay(RecipeEntry<T> recipe) {
		super(recipe, recipe.value().getIngredients().getFirst(), recipe.value().getResult(BasicDisplay.registryAccess()));
	}
	
	public final EntryIngredient getIn() {
		return getInputEntries().getFirst();
	}
	
	public final EntryIngredient getOut() {
		return getOutputEntries().getFirst();
	}
	
	@Override
    public boolean isUnlocked() {
		MinecraftClient client = MinecraftClient.getInstance();
		return AdvancementHelper.hasAdvancement(client.player, getUnlockIdentifier()) && super.isUnlocked();
	}
	
	public abstract Identifier getUnlockIdentifier();
	
}
