package de.dafuqs.spectrum.compat.gofish;

import net.fabricmc.loader.api.*;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class GoFishCompat {
	
	public static final Identifier DEFAULT_CRATES_LOOT_TABLE_ID = Identifier.of("gofish", "gameplay/fishing/crates");
	public static final Identifier NETHER_CRATES_LOOT_TABLE_ID = Identifier.of("gofish", "gameplay/fishing/nether/crates");
	public static final Identifier END_CRATES_LOOT_TABLE_ID = Identifier.of("gofish", "gameplay/fishing/end/crates");
	
	public static final Identifier NETHER_FISH_LOOT_TABLE_ID = Identifier.of("gofish", "gameplay/fishing/nether/fish");
	public static final Identifier END_FISH_LOOT_TABLE_ID = Identifier.of("gofish", "gameplay/fishing/end/fish");
	
	public static final Identifier DEEPFRY_ENCHANTMENT_ID = Identifier.of("gofish", "deepfry");
	
	public static boolean isLoaded() {
		return FabricLoader.getInstance().isModLoaded("go-fish");
	}
	
	public static boolean hasDeepfry(World world, ItemStack itemStack) {
		if (!isLoaded()) {
			return false;
		}
		
		ItemEnchantmentsComponent enchantments = itemStack.getEnchantments();
		for (RegistryEntry<Enchantment> enchantment : enchantments.getEnchantments()) {
			if (isDeepfry(world, enchantment)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isDeepfry(World world, RegistryEntry<Enchantment> enchantment) {
		Identifier id = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getId(enchantment.value());
		return id != null && id.equals(GoFishCompat.DEEPFRY_ENCHANTMENT_ID);
	}
	
}
