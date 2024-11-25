package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.api.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
	
	@Inject(method = "isAcceptableItem(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"), cancellable = true)
	public void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		var accepted = cir.getReturnValue();
		
		if (!accepted) {
			var enchantment = (Enchantment) (Object) this;
			if (stack.getItem() instanceof ExtendedEnchantable extendedEnchantable && extendedEnchantable.acceptsEnchantment(enchantment)) {
				cir.setReturnValue(true);
			}
		}
	}
	
}
