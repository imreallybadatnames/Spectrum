package de.dafuqs.spectrum.compat.gofish;

import net.fabricmc.loader.api.*;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.loot.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.util.*;

public class GoFishCompat {
	
	public static final String MOD_ID = "go-fish";
	public static final String NAMESPACE = "gofish";
	
	public static final RegistryKey<LootTable> DEFAULT_CRATES_LOOT_TABLE_ID = lootTableKey("gameplay/fishing/crates");
	public static final RegistryKey<LootTable> NETHER_CRATES_LOOT_TABLE_ID = lootTableKey("gameplay/fishing/nether/crates");
	public static final RegistryKey<LootTable> END_CRATES_LOOT_TABLE_ID = lootTableKey("gameplay/fishing/end/crates");
	
	public static final RegistryKey<LootTable> NETHER_FISH_LOOT_TABLE_ID = lootTableKey("gameplay/fishing/nether/fish");
	public static final RegistryKey<LootTable> END_FISH_LOOT_TABLE_ID = lootTableKey("gameplay/fishing/end/fish");
	
	public static final RegistryKey<Enchantment> DEEPFRY_ENCHANTMENT_ID = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(NAMESPACE, "deepfry"));
	
	public static RegistryKey<LootTable> lootTableKey(String id) {
		return RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(NAMESPACE, id));
	}
	
	public static boolean isLoaded() {
		return FabricLoader.getInstance().isModLoaded(MOD_ID);
	}
	
	public static boolean hasDeepfry(ItemStack itemStack) {
		if (!isLoaded()) {
			return false;
		}
		
		ItemEnchantmentsComponent enchantments = itemStack.getEnchantments();
		for (RegistryEntry<Enchantment> enchantment : enchantments.getEnchantments()) {
			if (enchantment.matchesKey(DEEPFRY_ENCHANTMENT_ID)) {
				return true;
			}
		}
		return false;
	}
	
}
