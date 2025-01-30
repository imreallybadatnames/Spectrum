package de.dafuqs.spectrum.api.item;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.server.network.*;

public interface MergeableItem {
	
	ItemStack getResult(ServerPlayerEntity player, ItemStack firstHalf, ItemStack secondHalf);
	
	boolean canMerge(ServerPlayerEntity player, ItemStack parent, ItemStack other);
	
	default boolean verify(ItemStack parent, ItemStack other) {
		if (!parent.getEnchantments().equals(other.getEnchantments()))
			return false;
		
		var comp = parent.get(SpectrumDataComponentTypes.PAIRED_ITEM);
		var otherComp = other.get(SpectrumDataComponentTypes.PAIRED_ITEM);
		return comp != null && otherComp != null && comp.signature() == otherComp.signature();
	}

	void playSound(ServerPlayerEntity player);
}
