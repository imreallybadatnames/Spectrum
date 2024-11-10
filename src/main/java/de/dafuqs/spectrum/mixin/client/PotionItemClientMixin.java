package de.dafuqs.spectrum.mixin.client;

import net.fabricmc.api.*;
import net.minecraft.client.item.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.*;
import net.minecraft.text.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Environment(EnvType.CLIENT)
@Mixin({PotionItem.class, LingeringPotionItem.class, TippedArrowItem.class})
public abstract class PotionItemClientMixin {
	
	@Inject(method = "appendTooltip(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/Item$TooltipContext;Ljava/util/List;Lnet/minecraft/item/tooltip/TooltipType;)V", at = @At("HEAD"), cancellable = true)
	private void spectrum$makePotionUnidentifiable(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo ci) {
		NbtCompound nbtCompound = stack.getNbt();
		if (nbtCompound != null && nbtCompound.contains("spectrum_unidentifiable", NbtElement.BYTE_TYPE) && nbtCompound.getBoolean("spectrum_unidentifiable")) {
			tooltip.add(Text.translatable("item.spectrum.potion.tooltip.unidentifiable"));
			ci.cancel();
		}
	}
	
}
