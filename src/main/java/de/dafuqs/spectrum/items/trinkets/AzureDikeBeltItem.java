package de.dafuqs.spectrum.items.trinkets;

import net.fabricmc.api.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.*;

import java.util.*;

public class AzureDikeBeltItem extends AzureDikeTrinketItem {
	
	public AzureDikeBeltItem(Settings settings) {
		super(settings);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.azure_dike_belt.tooltip"));
	}
	
	@Override
	public int maxAzureDike(ItemStack stack) {
		return 8;
	}
	
	@Override
	public float rechargeDelayAfterDamageModifier(ItemStack stack) {
		return 2.5F;
	}
	
}
