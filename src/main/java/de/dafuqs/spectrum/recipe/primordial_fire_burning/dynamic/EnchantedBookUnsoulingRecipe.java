package de.dafuqs.spectrum.recipe.primordial_fire_burning.dynamic;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.recipe.primordial_fire_burning.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.world.*;

public class EnchantedBookUnsoulingRecipe extends PrimordialFireBurningRecipe {
	
	public EnchantedBookUnsoulingRecipe() {
		// FIXME - What to do about enchantments here?
		super("", false, UNLOCK_IDENTIFIER,
				Ingredient.ofStacks(SpectrumEnchantmentHelper.addOrUpgradeEnchantment(Items.ENCHANTED_BOOK.getDefaultStack(), Enchantments.SOUL_SPEED, 1, false, false).getRight()),
				SpectrumEnchantmentHelper.addOrUpgradeEnchantment(Items.ENCHANTED_BOOK.getDefaultStack(), Enchantments.SWIFT_SNEAK, 1, false, false).getRight());
	}
	
	@Override
	public boolean matches(RecipeInput inv, World world) {
		ItemStack stack = inv.getStackInSlot(0);
		RegistryEntry.Reference<Enchantment> soulSpeed = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(Enchantments.SOUL_SPEED).orElseThrow();
		return stack.getEnchantments().getEnchantments().contains(soulSpeed);
	}
	
	@Override
	public ItemStack craft(RecipeInput inv, RegistryWrapper.WrapperLookup drm) {
		ItemStack stack = inv.getStackInSlot(0);
		
		RegistryEntry.Reference<Enchantment> soulSpeed = drm.createRegistryLookup().getOptionalEntry(RegistryKeys.ENCHANTMENT, Enchantments.SOUL_SPEED).orElseThrow();
		int level = stack.getEnchantments().getLevel(soulSpeed);
		if(level > 0) {
			stack = SpectrumEnchantmentHelper.removeEnchantments(drm, stack, Enchantments.SOUL_SPEED).getLeft();
			stack = SpectrumEnchantmentHelper.addOrUpgradeEnchantment(drm, stack, Enchantments.SWIFT_SNEAK, level, false, false).getRight();
		}
		return stack;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.ENCHANTED_BOOK_UNSOULING;
	}
	
}
