package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.*;
import de.dafuqs.spectrum.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.registry.tag.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import java.util.*;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
	
	@ModifyReturnValue(method = "getApplicableItems()Lnet/minecraft/registry/entry/RegistryEntryList;", at = @At("RETURN"))
	public RegistryEntryList<Item> spectrum$getAcceptableItems(RegistryEntryList<Item> original) {
		Enchantment enchantment = (Enchantment) (Object) this;
		
		RegistryEntry<Enchantment> entry = RegistryEntry.of(enchantment);
		TagKey<Item> blacklistedEnchantableTag = TagKey.of(RegistryKeys.ITEM, SpectrumCommon.locate("enchantable/blacklisted/" + entry.getIdAsString().replace(':', '_')));
		List<RegistryEntry<Item>> modified = original.stream().filter(itemRegistryEntry -> itemRegistryEntry.isIn(blacklistedEnchantableTag)).toList();
		
		return RegistryEntryList.of(modified);
	}
	
	@ModifyReturnValue(method = "isSupportedItem(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"))
	public boolean spectrum$isSupportedItems(boolean original, ItemStack stack) {
		return spectrum$modifyIsSupported((Enchantment) (Object) this, stack, original);
	}
	
	@ModifyReturnValue(method = "isAcceptableItem(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"))
	public boolean spectrum$isAcceptableItem(boolean original, ItemStack stack) {
		return spectrum$modifyIsSupported((Enchantment) (Object) this, stack, original);
	}

	@Unique
	private static boolean spectrum$modifyIsSupported(Enchantment enchantment, ItemStack stack, boolean original) {
		if (original) {
			RegistryEntry<Enchantment> entry = RegistryEntry.of(enchantment);
			TagKey<Item> blacklistedEnchantableTag = TagKey.of(RegistryKeys.ITEM, SpectrumCommon.locate("enchantable/blacklisted/" + entry.getIdAsString().replace(':', '_')));
			if (stack.isIn(blacklistedEnchantableTag)) {
				original = false;
			}
		} else {
			RegistryEntry<Enchantment> entry = RegistryEntry.of(enchantment);
			TagKey<Item> extendedEnchantableTag = TagKey.of(RegistryKeys.ITEM, SpectrumCommon.locate("enchantable/extended/" + entry.getIdAsString().replace(':', '_')));
			if (stack.isIn(extendedEnchantableTag)) {
				original = true;
			}
		}
		
		return original;
	}
	
}
