package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.compat.REI.*;
import me.shedaniel.rei.api.common.util.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.text.*;

import java.util.*;

public abstract class GatedItemInformationDisplay extends GatedSpectrumDisplay {
	
	protected final Item item;
	protected final Text description;
	
	public GatedItemInformationDisplay(RecipeEntry<? extends DescriptiveGatedRecipe<?>> recipe) {
		super(recipe, Collections.singletonList(EntryIngredients.of(recipe.value().getItem())), Collections.emptyList());
		this.item = recipe.value().getItem();
		this.description = recipe.value().getDescription();
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public Text getDescription() {
		return this.description;
	}
	
}