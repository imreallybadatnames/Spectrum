package de.dafuqs.spectrum.recipe.cinderhearth;

import de.dafuqs.matchbooks.recipe.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;

import java.util.*;

public class CinderhearthRecipe extends GatedStackSpectrumRecipe<SingleStackRecipeInput> {
	
	public static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/blocks/cinderhearth");

	protected final IngredientStack ingredient;
	protected final int time;
	protected final float experience;
	protected final List<Pair<ItemStack, Float>> resultsWithChance;

	public CinderhearthRecipe(String group, boolean secret, Identifier requiredAdvancementIdentifier, IngredientStack ingredient, int time, float experience, List<Pair<ItemStack, Float>> resultsWithChance) {
		super(group, secret, requiredAdvancementIdentifier);
		
		this.ingredient = ingredient;
		this.time = time;
		this.experience = experience;
		this.resultsWithChance = resultsWithChance;
		
		registerInToastManager(getType(), this);
	}
	
	@Override
	public boolean matches(SingleStackRecipeInput input, World world) {
		return ingredient.test(input.getStackInSlot(0));
	}
	
	@Override
	@Deprecated
	public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup registryLookup) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean fits(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registryLookup) {
		return resultsWithChance.get(0).getLeft();
	}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(SpectrumBlocks.CINDERHEARTH);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeTypes.CINDERHEARTH_RECIPE_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return SpectrumRecipeTypes.CINDERHEARTH;
	}
	
	@Override
	public Identifier getRecipeTypeUnlockIdentifier() {
		return UNLOCK_IDENTIFIER;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return SpectrumRecipeTypes.CINDERHEARTH_ID;
	}

	@Override
	public List<IngredientStack> getIngredientStacks() {
		return List.of(ingredient);
	}

	public float getExperience() {
		return experience;
	}
	
	public int getCraftingTime() {
		return time;
	}
	
	public List<ItemStack> getRolledOutputs(Random random, float yieldMod) {
		List<ItemStack> output = new ArrayList<>();
		for (Pair<ItemStack, Float> possibleOutput : resultsWithChance) {
			float chance = possibleOutput.getRight();
			if (chance >= 1.0 || random.nextFloat() < chance * yieldMod) {
				ItemStack currentOutputStack = possibleOutput.getLeft();
				if (yieldMod > 1) {
					int totalCount = Support.getIntFromDecimalWithChance(currentOutputStack.getCount() * yieldMod, random);
					while (totalCount > 0) { // if the rolled count exceeds the max stack size we need to split them (unstackable items, counts > 64, ...)
						int count = Math.min(totalCount, currentOutputStack.getMaxCount());
						ItemStack outputStack = currentOutputStack.copy();
						outputStack.setCount(count);
						output.add(outputStack);
						totalCount -= count;
					}
				} else {
					output.add(currentOutputStack.copy());
				}
			}
		}
		return output;
	}
	
	public List<ItemStack> getPossibleOutputs() {
		List<ItemStack> outputs = new ArrayList<>();
		for (Pair<ItemStack, Float> pair : resultsWithChance) {
			outputs.add(pair.getLeft());
		}
		return outputs;
	}
	
	public List<Pair<ItemStack, Float>> getResultsWithChance() {
		return resultsWithChance;
	}

}
