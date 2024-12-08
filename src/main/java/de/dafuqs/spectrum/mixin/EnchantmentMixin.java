package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.helpers.SpectrumEnchantmentHelper;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

	@Inject(method = "getApplicableItems()Lnet/minecraft/registry/entry/RegistryEntryList;", at = @At("RETURN"), cancellable = true)
	public void spectrum$getAcceptableItems(CallbackInfoReturnable<RegistryEntryList<Item>> cir) {
		var enchantment = (Enchantment) (Object) this;
		var items = cir.getReturnValue();
		var blacklist = SpectrumEnchantmentHelper.getBlacklist(enchantment);
		var withoutBlacklisted = blacklist.stream().filter(b -> !items.contains(b)).toList();
		cir.setReturnValue(RegistryEntryList.of(withoutBlacklisted));
	}

	@Inject(method = "isSupportedItem(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"), cancellable = true)
	public void spectrum$isSupportedItems(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(spectrum$modifyIsSupported((Enchantment) (Object) this, stack, cir.getReturnValue()));
	}

	@Inject(method = "isAcceptableItem(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"), cancellable = true)
	public void spectrum$isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(spectrum$modifyIsSupported((Enchantment) (Object) this, stack, cir.getReturnValue()));
	}

	@Unique
	private static boolean spectrum$modifyIsSupported(Enchantment enchantment, ItemStack stack, boolean original) {
		var isExtendedEnchantable = stack.getItem() instanceof ExtendedEnchantable extendedEnchantable
				&& SpectrumCommon.getRegistryLookup()
				.flatMap(r -> r.getOptionalWrapper(RegistryKeys.ENCHANTMENT))
				.map(impl -> extendedEnchantable.acceptsEnchantment(impl, enchantment))
				.orElse(false);
		var isBlacklisted = stack.isIn(SpectrumEnchantmentHelper.getBlacklist(enchantment));
		return (original || isExtendedEnchantable) && !isBlacklisted;
	}
	
}
