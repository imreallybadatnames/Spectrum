package de.dafuqs.spectrum.items.trinkets;

import de.dafuqs.spectrum.*;
import net.fabricmc.api.*;
import net.minecraft.client.item.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class TotemPendantItem extends SpectrumTrinketItem {
	
	public TotemPendantItem(Settings settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/totem_pendant"));
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.totem_pendant.tooltip").formatted(Formatting.GRAY));
	}
	
	
}
