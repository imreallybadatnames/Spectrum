package de.dafuqs.spectrum.api.item;

import de.dafuqs.spectrum.SpectrumCommon;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.*;

public interface Preenchanted {

	void addDefaultEnchantments(RegistryWrapper.Impl<Enchantment> impl, ItemEnchantmentsComponent.Builder builder);

	default ItemEnchantmentsComponent getDefaultEnchantments() {
		var builder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);
		SpectrumCommon.getRegistryLookup()
				.flatMap(r -> r.getOptionalWrapper(RegistryKeys.ENCHANTMENT))
				.ifPresent(impl -> addDefaultEnchantments(impl, builder));
		return builder.build();
	}

	default @NotNull ItemStack getDefaultEnchantedStack(Item item) {
		var stack = new ItemStack(item);
		SpectrumCommon.getRegistryLookup().ifPresent(
				r -> stack.set(DataComponentTypes.ENCHANTMENTS, getDefaultEnchantments()));
		return stack;
	}
	
	/**
	 * Checks a stack if it only has enchantments that are lower or equal its DefaultEnchantments,
	 * meaning enchantments had been added on top of the original ones.
	 */
	default boolean onlyHasPreEnchantments(ItemStack stack) {
		var currentEnchants = stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
		var defaultEnchants = getDefaultEnchantments();
		return currentEnchants.equals(defaultEnchants);
	}
	
}
