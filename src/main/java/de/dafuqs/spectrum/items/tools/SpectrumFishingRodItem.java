package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.entity.*;
import de.dafuqs.spectrum.compat.gofish.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.registry.tag.*;
import net.minecraft.sound.*;
import net.minecraft.stat.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.event.*;

import java.util.*;

public abstract class SpectrumFishingRodItem extends FishingRodItem {
	
	public SpectrumFishingRodItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		
		PlayerEntityAccessor playerEntityAccessor = ((PlayerEntityAccessor) user);
		if (playerEntityAccessor.getSpectrumBobber() != null) {
			if (!world.isClient) {
				int damage = playerEntityAccessor.getSpectrumBobber().use(itemStack);
				itemStack.damage(damage, user, LivingEntity.getSlotForHand(hand));
			}
			
			world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
			user.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
		} else {
			world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
			if (!world.isClient) {
				var drm = world.getRegistryManager();
				int luckOfTheSeaLevel = SpectrumEnchantmentHelper.getLevel(drm, Enchantments.LUCK_OF_THE_SEA, itemStack);
				int lureLevel = SpectrumEnchantmentHelper.getLevel(drm, Enchantments.LURE, itemStack);
				int exuberanceLevel = SpectrumEnchantmentHelper.getLevel(drm, SpectrumEnchantments.EXUBERANCE, itemStack);
				int bigCatchLevel = SpectrumEnchantmentHelper.getLevel(drm, SpectrumEnchantments.BIG_CATCH, itemStack);
				int serendipityReelLevel = SpectrumEnchantmentHelper.getLevel(drm, SpectrumEnchantments.SERENDIPITY_REEL, itemStack);
				boolean inventoryInsertion = SpectrumEnchantmentHelper.getLevel(drm, SpectrumEnchantments.INVENTORY_INSERTION, itemStack) > 0;
				boolean shouldSmeltDrops = shouldSmeltDrops(itemStack, user);
				spawnBobber(user, world, luckOfTheSeaLevel, lureLevel, exuberanceLevel, bigCatchLevel, serendipityReelLevel, inventoryInsertion, shouldSmeltDrops);
			}
			
			user.incrementStat(Stats.USED.getOrCreateStat(this));
			user.emitGameEvent(GameEvent.ITEM_INTERACT_START);
		}
		
		return TypedActionResult.success(itemStack, world.isClient());
	}
	
	public abstract void spawnBobber(PlayerEntity user, World world, int luckOfTheSeaLevel, int lureLevel, int exuberanceLevel, int bigCatchLevel, int serendipityReelLevel, boolean inventoryInsertion, boolean shouldSmeltDrops);
	
	public boolean canFishIn(FluidState fluidState) {
		return fluidState.isIn(FluidTags.WATER);
	}
	
	public boolean shouldSmeltDrops(ItemStack itemStack, PlayerEntity user) {
		return EnchantmentHelper.hasAnyEnchantmentsIn(itemStack, SpectrumEnchantmentTags.SMELTS_MORE_LOOT) || GoFishCompat.hasDeepfry(user.getWorld(), itemStack);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.spectrum_fishing_rods.tooltip").formatted(Formatting.GRAY));
	}
	
}
