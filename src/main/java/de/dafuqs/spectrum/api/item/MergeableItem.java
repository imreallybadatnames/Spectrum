package de.dafuqs.spectrum.api.item;

import de.dafuqs.spectrum.components.*;
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
		var otherSig = other.getOrDefault(SpectrumDataComponentTypes.PAIRED_ITEM, PairedItemComponent.DEFAULT).signature();
		return comp != null && comp.signature() == otherSig;
	}

	void playSound(ServerPlayerEntity player);
}
