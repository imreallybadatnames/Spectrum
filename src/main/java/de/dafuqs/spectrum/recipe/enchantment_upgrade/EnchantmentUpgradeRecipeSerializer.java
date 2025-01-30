package de.dafuqs.spectrum.recipe.enchantment_upgrade;

import de.dafuqs.spectrum.api.recipe.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;

public class EnchantmentUpgradeRecipeSerializer extends EndecRecipeSerializer<EnchantmentUpgradeRecipe> implements GatedRecipeSerializer<EnchantmentUpgradeRecipe> {
	
	public static final StructEndec<EnchantmentUpgradeRecipe> ENDEC = StructEndecBuilder.of(
		Endec.STRING.optionalFieldOf("group", recipe -> recipe.group, ""),
		Endec.BOOLEAN.optionalFieldOf("secret", recipe -> recipe.secret, false),
		MinecraftEndecs.IDENTIFIER.fieldOf("required_advancement", recipe -> recipe.requiredAdvancementIdentifier),
		CodecUtils.toEndec(Enchantment.ENTRY_CODEC).fieldOf("enchantment", recipe -> recipe.enchantmentEntry),
		EnchantUpgradeLevelEntry.ENDEC.listOf().fieldOf("levels", EnchantmentUpgradeRecipe::getDefaultLevelEntry),
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
