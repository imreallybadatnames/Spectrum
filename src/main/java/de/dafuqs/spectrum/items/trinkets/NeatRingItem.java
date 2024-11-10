package de.dafuqs.spectrum.items.trinkets;

import de.dafuqs.spectrum.*;
import net.fabricmc.api.*;
import net.minecraft.client.item.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class NeatRingItem extends SpectrumTrinketItem {


	public NeatRingItem(Settings settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/neat_ring"));
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.neat_ring.tooltip"));
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return true;
	}
	
}
