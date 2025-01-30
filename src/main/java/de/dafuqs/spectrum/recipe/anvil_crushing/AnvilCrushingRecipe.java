package de.dafuqs.spectrum.recipe.anvil_crushing;

import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.particle.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.world.*;

public class AnvilCrushingRecipe extends GatedSpectrumRecipe<SingleStackRecipeInput> {
	
	protected final Ingredient ingredient;
	protected final ItemStack result;
	protected final float crushedItemsPerPointOfDamage;
	protected final float experience;
	protected final Identifier particleEffectIdentifier;
	protected final int particleCount;
	protected final Identifier soundEvent;
	
	public AnvilCrushingRecipe(String group, boolean secret, Identifier requiredAdvancementIdentifier,
							   Ingredient ingredient, ItemStack result, float crushedItemsPerPointOfDamage,
							   float experience, Identifier particleEffectIdentifier, int particleCount, Identifier soundEventIdentifier) {
		
		super(group, secret, requiredAdvancementIdentifier);
		
		this.ingredient = ingredient;
		this.result = result;
		this.crushedItemsPerPointOfDamage = crushedItemsPerPointOfDamage;
		this.experience = experience;
		this.particleEffectIdentifier = particleEffectIdentifier;
		this.particleCount = particleCount;
		this.soundEvent = soundEventIdentifier;
		
		if (requiredAdvancementIdentifier != null) {
			registerInToastManager(getType(), this);
		}
	}
	
	@Override
	public boolean matches(SingleStackRecipeInput input, World world) {
		return ingredient.test(input.item());
	}
	
	@Override
	public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup registryLookup) {
		return result.copy();
	}
	
	@Override
	public boolean fits(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
		return result;
	}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(Blocks.ANVIL);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.ANVIL_CRUSHING_RECIPE_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return SpectrumRecipeTypes.ANVIL_CRUSHING;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "anvil_crushing";
	}
	
	@Override
	public DefaultedList<Ingredient> getIngredients() {
		DefaultedList<Ingredient> defaultedList = DefaultedList.of();
		defaultedList.add(this.ingredient);
		return defaultedList;
	}

	public float getCrushedItemsPerPointOfDamage() {
		return crushedItemsPerPointOfDamage;
	}

	public SoundEvent getSoundEvent() {
		return Registries.SOUND_EVENT.get(soundEvent);
	}

	public ParticleEffect getParticleEffect() {
		return (ParticleEffect) Registries.PARTICLE_TYPE.get(particleEffectIdentifier);
	}

	public int getParticleCount() {
		return particleCount;
	}

	public float getExperience() {
		return experience;
	}
	
}
