package de.dafuqs.spectrum.recipe.anvil_crushing;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class AnvilCrusher {

	public static void crush(ItemEntity itemEntity, float damageAmount) {
		ItemStack thisItemStack = itemEntity.getStack();
		World world = itemEntity.getWorld();
		
		SingleStackRecipeInput inventory = new SingleStackRecipeInput(thisItemStack);
		
		Optional<RecipeEntry<AnvilCrushingRecipe>> optionalAnvilCrushingRecipe = world.getRecipeManager().getFirstMatch(SpectrumRecipeTypes.ANVIL_CRUSHING, inventory, world);
		if (optionalAnvilCrushingRecipe.isPresent()) {
			// Item can be crafted via anvil. Do anvil crafting
			AnvilCrushingRecipe recipe = optionalAnvilCrushingRecipe.get().value();
			
			int itemStackAmount = itemEntity.getStack().getCount();
			int crushingInputAmount = Math.min(itemStackAmount, (int) (recipe.getCrushedItemsPerPointOfDamage() * damageAmount));
			
			if (crushingInputAmount > 0) {
				Vec3d position = itemEntity.getPos();
				
				ItemStack crushingOutput = recipe.getResult(world.getRegistryManager()).copy();
				Vec3d pos = itemEntity.getPos();

				// Remove the input amount from the source stack
				// Or the source stack altogether if it would be empty
				int remainingItemStackAmount = itemStackAmount - crushingInputAmount;
				if (remainingItemStackAmount > 0) {
					thisItemStack.setCount(remainingItemStackAmount);
				} else {
					itemEntity.remove(Entity.RemovalReason.DISCARDED);
				}
				
				MultiblockCrafter.spawnItemStackAsEntitySplitViaMaxCount(world, pos, crushingOutput, crushingOutput.getCount() * crushingInputAmount, Vec3d.ZERO, false, null);
				
				// Spawn XP depending on how much is crafted, but at least 1
				float craftingXPFloat = recipe.getExperience() * crushingInputAmount;
				int craftingXP = Support.getIntFromDecimalWithChance(craftingXPFloat, world.random);
				
				if (craftingXP > 0) {
					ExperienceOrbEntity experienceOrbEntity = new ExperienceOrbEntity(world, position.x, position.y, position.z, craftingXP);
					world.spawnEntity(experienceOrbEntity);
				}
				
				// Play sound
				SoundEvent soundEvent = recipe.getSoundEvent();
				if (soundEvent != null) {
					float randomVolume = 1.0F + world.getRandom().nextFloat() * 0.2F;
					float randomPitch = 0.9F + world.getRandom().nextFloat() * 0.2F;
					world.playSound(null, position.x, position.y, position.z, soundEvent, SoundCategory.PLAYERS, randomVolume, randomPitch);
				}
				
				PlayParticleWithExactVelocityPayload.playParticleWithExactVelocity((ServerWorld) world, position, recipe.getParticleEffect(), recipe.getParticleCount(), Vec3d.ZERO);
			}
		}
	}
	
}
