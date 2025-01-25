package de.dafuqs.spectrum.recipe;

import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.helpers.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.recipe.input.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

public abstract class GatedSpectrumRecipe<C extends RecipeInput> implements GatedRecipe<C> {
	
	public final String group;
	public final boolean secret;
	public final Identifier requiredAdvancementIdentifier;
	
	protected GatedSpectrumRecipe(String group, boolean secret, Identifier requiredAdvancementIdentifier) {
		this.group = group;
		this.secret = secret;
		this.requiredAdvancementIdentifier = requiredAdvancementIdentifier;
	}
	
	@Override
	public String getGroup() {
		return this.group;
	}
	
	@Override
	public boolean isSecret() {
		return this.secret;
	}
	
	/**
	 * The advancement the player has to have for the recipe be craftable
	 *
	 * @return The advancement identifier. A null value means the player is always able to craft this recipe
	 */
	@Nullable
	@Override
	public Identifier getRequiredAdvancementIdentifier() {
		return this.requiredAdvancementIdentifier;
	}
	
	@Override
	public Identifier getRecipeTypeUnlockIdentifier() {
		return null;
	}
	
	@Override
	public boolean isIgnoredInRecipeBook() {
		return true;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof GatedSpectrumRecipe<?> gatedSpectrumRecipe) {
			return gatedSpectrumRecipe.getId().equals(this.getId());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.getId().toString();
	}
	
	protected static ItemStack getDefaultStackWithCount(Item item, int count) {
		ItemStack stack = item.getDefaultStack();
		stack.setCount(count);
		return stack;
	}
	
	protected static ItemStack copyNbt(ItemStack sourceStack, ItemStack output) {
		// this overrides all nbt data, that are not nested compounds (like lists)...
		NbtCompound sourceNbt = sourceStack.getNbt();
		if (sourceNbt != null) {
			ItemStack modifiedOutput = output.copy();
			NbtCompound modifiedNbt = sourceNbt.copy();
			modifiedNbt.copyFrom(sourceNbt);
			modifiedNbt.remove(ItemStack.DAMAGE_KEY);
			modifiedOutput.setNbt(modifiedNbt);
			// ...therefore, we need to restore all previous enchantments that the original item had and are still applicable to the new item
			output = SpectrumEnchantmentHelper.clearAndCombineEnchantments(modifiedOutput, false, false, output, sourceStack);
		}
		return output;
	}
	
}
