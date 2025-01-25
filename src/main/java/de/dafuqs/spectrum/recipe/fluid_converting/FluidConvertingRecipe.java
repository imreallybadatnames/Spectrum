package de.dafuqs.spectrum.recipe.fluid_converting;

import de.dafuqs.spectrum.recipe.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public abstract class FluidConvertingRecipe extends GatedSpectrumRecipe<RecipeInput> {
	
	protected final Ingredient input;
	protected final ItemStack output;
	
	public FluidConvertingRecipe(String group, boolean secret, Identifier requiredAdvancementIdentifier, @NotNull Ingredient input, ItemStack output) {
		super(group, secret, requiredAdvancementIdentifier);
		this.input = input;
		this.output = output;
	}
	
	@Override
	public boolean matches(@NotNull RecipeInput inv, World world) {
		return this.input.test(inv.getStackInSlot(0));
	}
	
	@Override
	public ItemStack craft(RecipeInput inv, RegistryWrapper.WrapperLookup drm) {
		return output.copy();
	}
	
	@Override
	public boolean fits(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registryManager) {
		return output;
	}
	
	@Override
	public DefaultedList<Ingredient> getIngredients() {
		DefaultedList<Ingredient> defaultedList = DefaultedList.of();
		defaultedList.add(this.input);
		return defaultedList;
	}
	
}
