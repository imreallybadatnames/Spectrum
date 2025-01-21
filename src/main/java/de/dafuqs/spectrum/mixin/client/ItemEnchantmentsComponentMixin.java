package de.dafuqs.spectrum.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.registries.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.*;
import net.minecraft.registry.entry.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import java.util.function.*;

@Mixin(ItemEnchantmentsComponent.class)
public class ItemEnchantmentsComponentMixin {
	
	@Shadow
	@Final
	Object2IntOpenHashMap<RegistryEntry<Enchantment>> enchantments;
	
	@ModifyVariable(method = "appendTooltip(Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V", at = @At("STORE"))
	private RegistryEntry<Enchantment> spectrum$appendTooltip$hideRevealedEnchantmentCloak(RegistryEntry<Enchantment> entry) {
		var cloaks = entry.value().getEffect(SpectrumEnchantmentEffectComponentTypes.CLOAKED);
		if (cloaks.isEmpty()) return entry;
		for (var cloak : cloaks)
			if (!enchantments.containsKey(cloak.effect()))
				return entry;
		return null;
	}
	
	@WrapOperation(method = "appendTooltip(Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getName(Lnet/minecraft/registry/entry/RegistryEntry;I)Lnet/minecraft/text/Text;"))
	private Text spectrum$modifyCloakingEnchantmentName(RegistryEntry<Enchantment> entry, int level, Operation<Text> original) {
		var cloaks = entry.value().getEffect(SpectrumEnchantmentEffectComponentTypes.CLOAKED);
		
		// If we're not cloaking, return as normal
		if (cloaks.isEmpty())
			return original.call(entry, level);
		
		// If any cloaked enchant has yet to be revealed, obfuscate
		for (var cloak : cloaks) {
			if (!enchantments.containsKey(cloak.effect())) {
				var text = original.call(entry, level);
				if (SpectrumCommon.CONFIG.NameForUnrevealedEnchantments.isBlank() && text instanceof MutableText mutableText)
					return mutableText.formatted(Formatting.byCode('k'));
				else
					return Text.literal(SpectrumCommon.CONFIG.NameForUnrevealedEnchantments).setStyle(text.getStyle());
			}
		}
		
		// Everything is revealed so hide the name (handled in the mixin below)
		return null;
	}
	
	@WrapOperation(method = "appendTooltip(Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"))
	private void spectrum$ignoreNullEnchantmentName(Consumer<Text> instance, Object obj, Operation<Void> original) {
		if (obj != null) original.call(instance, obj);
	}
}