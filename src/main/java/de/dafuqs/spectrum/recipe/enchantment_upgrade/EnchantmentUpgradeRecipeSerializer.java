package de.dafuqs.spectrum.recipe.enchantment_upgrade;

import de.dafuqs.spectrum.api.recipe.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;

import java.util.*;

public class EnchantmentUpgradeRecipeSerializer extends EndecRecipeSerializer<EnchantmentUpgradeRecipe> implements GatedRecipeSerializer<EnchantmentUpgradeRecipe> {
	
	// FIXME - Experimental. Will likely break as I don't believe the recipes are properly being registered
	// Maybe the recipe injection code (KubeJS compat) is easier?
	public static final StructEndec<EnchantmentUpgradeRecipe> ENDEC = StructEndecBuilder.of(
		Endec.STRING.optionalFieldOf("group", recipe -> recipe.group, ""),
		Endec.BOOLEAN.optionalFieldOf("secret", recipe -> recipe.secret, false),
		MinecraftEndecs.IDENTIFIER.fieldOf("required_advancement", recipe -> recipe.requiredAdvancementIdentifier),
		CodecUtils.toEndec(Enchantment.ENTRY_CODEC).fieldOf("enchantment", recipe -> recipe.enchantmentEntry),
		EnchantUpgradeLevelEntry.ENDEC.listOf().xmap(enchantUpgradeLevelEntries -> {
			List<EnchantUpgradeLevelEntry> entries = new ArrayList<>();
			for (EnchantUpgradeLevelEntry enchantmentUpgradeRecipe : enchantUpgradeLevelEntries) {
				entries.add(new EnchantUpgradeLevelEntry(
					enchantmentUpgradeRecipe.experience(),
					enchantmentUpgradeRecipe.requiredItem(),
					enchantmentUpgradeRecipe.count())
				);
			}
			return entries;
		}, o -> o).fieldOf("levels", EnchantmentUpgradeRecipe::getDefaultLevelEntry),
		EnchantmentUpgradeRecipe::createRecipes
	);
	
	public EnchantmentUpgradeRecipeSerializer() {
		super(ENDEC);
	}
	
	public record EnchantUpgradeLevelEntry(int experience, Item requiredItem, int count) {
		public static final Endec<EnchantUpgradeLevelEntry> ENDEC = StructEndecBuilder.of(
			Endec.INT.fieldOf("experience", EnchantUpgradeLevelEntry::experience),
			MinecraftEndecs.ofRegistry(Registries.ITEM).fieldOf("required_item", EnchantUpgradeLevelEntry::requiredItem),
			Endec.INT.fieldOf("count", EnchantUpgradeLevelEntry::count),
			EnchantUpgradeLevelEntry::new
		);
	}
}
