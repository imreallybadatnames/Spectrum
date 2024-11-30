package de.dafuqs.spectrum.enchantments;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class IndestructibleEnchantment extends SpectrumEnchantment {
	
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		return super.isAcceptableItem(stack) && !stack.isIn(SpectrumItemTags.INDESTRUCTIBLE_BLACKLISTED);
	}
	
}
