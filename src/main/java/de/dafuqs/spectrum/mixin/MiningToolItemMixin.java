package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.*;
import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.tag.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(MiningToolItem.class)
public abstract class MiningToolItemMixin {
	
	@Shadow
	@Final
	private TagKey<Block> effectiveBlocks;
	
	@ModifyReturnValue(method = "getMiningSpeedMultiplier(Lnet/minecraft/item/ItemStack;Lnet/minecraft/block/BlockState;)F", at = @At("RETURN"))
	public float applyMiningSpeedMultipliers(float original, ItemStack stack, BlockState state) {
		if (stack == null)
			return original; // thank you, gobber
		
		// RAZING GAMING
		int razingLevel = SpectrumEnchantmentHelper.getLevel(lookup, SpectrumEnchantments.RAZING, stack);
		if (razingLevel > 0 && state.isIn(this.effectiveBlocks)) {
			float hardness = state.getBlock().getHardness();
			original = (float) Math.max(1 + hardness, Math.pow(2, 1 + razingLevel / 8F));
		}
		
		// INERTIA GAMING
		// inertia mining speed calculation logic is capped at 5 levels.
		// Higher and the formula would do weird stuff
		int inertiaLevel = SpectrumEnchantmentHelper.getLevel(lookup, SpectrumEnchantments.INERTIA, stack);
		inertiaLevel = Math.min(4, inertiaLevel);
		if (inertiaLevel > 0) {
			var inertia = stack.getOrDefault(SpectrumDataComponentTypes.INERTIA, InertiaComponent.DEFAULT);
			if (state.isOf(inertia.lastMined())) {
				var additionalSpeedPercent = 2.0 * Math.log(inertia.count()) / Math.log((6 - inertiaLevel) * (6 - inertiaLevel) + 1);
				original *= 0.5F + (float) additionalSpeedPercent;
			} else {
				original /= 4;
			}
		}
		
		return original;
	}
	
}
