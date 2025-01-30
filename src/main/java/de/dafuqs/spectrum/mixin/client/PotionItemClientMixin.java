package de.dafuqs.spectrum.mixin.client;

import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.text.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Environment(EnvType.CLIENT)
@Mixin({PotionItem.class, LingeringPotionItem.class, TippedArrowItem.class})
public abstract class PotionItemClientMixin {
	
	@Inject(method = "appendTooltip(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/Item$TooltipContext;Ljava/util/List;Lnet/minecraft/item/tooltip/TooltipType;)V", at = @At("HEAD"), cancellable = true)
	private void spectrum$makePotionUnidentifiable(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo ci) {
		if (stack.contains(SpectrumDataComponentTypes.UNIDENTIFIABLE)) {
			tooltip.add(Text.translatable("item.spectrum.potion.tooltip.unidentifiable"));
			ci.cancel();
		}
	}
	
}
