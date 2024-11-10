package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.*;
import net.minecraft.text.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Mixin(PotionItem.class)
public abstract class PotionItemMixin {

	@ModifyReturnValue(method = "getMaxUseTime(Lnet/minecraft/item/ItemStack;)I", at = @At("RETURN"))
	private int spectrum$modifyDrinkTime(int drinkTime, ItemStack stack) {
		NbtCompound compound = stack.getNbt();
		if (compound != null && compound.contains("SpectrumAdditionalDrinkDuration", NbtElement.NUMBER_TYPE)) {
			int additionalDrinkDuration = compound.getInt("SpectrumAdditionalDrinkDuration");
			drinkTime += Math.max(4, drinkTime + additionalDrinkDuration);
		}
		return drinkTime;
	}

	@Inject(method = "appendTooltip(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/Item$TooltipContext;Ljava/util/List;Lnet/minecraft/item/tooltip/TooltipType;)V", at = @At("TAIL"))
	public void spectrum$appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo ci) {
		NbtCompound compound = stack.getNbt();
		if (compound != null && compound.contains("SpectrumAdditionalDrinkDuration", NbtElement.NUMBER_TYPE)) {
			int additionalDrinkDuration = compound.getInt("SpectrumAdditionalDrinkDuration");
			if (additionalDrinkDuration > 0) {
				tooltip.add(Text.translatable("item.spectrum.potion.slower_to_drink"));
			} else if (additionalDrinkDuration < 0) {
				tooltip.add(Text.translatable("item.spectrum.potion.faster_to_drink"));
			}
		}
	}

}
