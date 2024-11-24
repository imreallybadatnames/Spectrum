package de.dafuqs.spectrum.api.item;

import net.minecraft.enchantment.*;

/**
 * Items with that Interface will be able to be enchanted with the given enchants
 * Using this interface there is no need to override every vanilla enchantment to override `supported_items`
 * Note that for an item to be enchanted, it still needs to have `isEnchantable() == true` and `getEnchantability() > 0`
 */
public interface ExtendedEnchantable {
	
	boolean acceptsEnchantment(Enchantment enchantment);
}
