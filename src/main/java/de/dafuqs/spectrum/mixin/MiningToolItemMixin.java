package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.*;
import de.dafuqs.spectrum.component_type.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.progression.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.registry.tag.*;
import net.minecraft.server.network.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(MiningToolItem.class)
public abstract class MiningToolItemMixin {
	
	@Shadow
	@Final
	private TagKey<Block> effectiveBlocks;
	
	@Inject(at = @At("HEAD"), method = "postMine(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/LivingEntity;)Z")
	public void countInertiaBlocks(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
		if (stack != null) { // thank you, gobber
			long inertiaAmount = 0;
			
			if (SpectrumEnchantmentHelper.hasEnchantment(miner.getWorld().getRegistryManager(), SpectrumEnchantments.INERTIA, stack)) {
				var inertia = stack.getOrDefault(SpectrumDataComponentTypes.INERTIA, InertiaComponent.DEFAULT);
				inertiaAmount = state.isOf(inertia.lastMined()) ? inertia.count() + 1 : 1;
				stack.set(SpectrumDataComponentTypes.INERTIA, new InertiaComponent(state.getBlock(), inertiaAmount));
			}
			
			if (miner instanceof ServerPlayerEntity serverPlayerEntity)
				SpectrumAdvancementCriteria.INERTIA_USED.trigger(serverPlayerEntity, state, (int) inertiaAmount);
		}
	}
	
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
