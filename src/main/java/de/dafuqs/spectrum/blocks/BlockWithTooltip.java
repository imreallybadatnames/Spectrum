package de.dafuqs.spectrum.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.*;

import java.util.*;

public class BlockWithTooltip extends Block {
	
	protected final Text tooltipText;
	
	public BlockWithTooltip(Settings settings, Text tooltipText) {
		super(settings);
		this.tooltipText = tooltipText;
	}

	@Override
	public MapCodec<? extends BlockWithTooltip> getCodec() {
		//TODO: Make the codec
		return null;
	}

	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(tooltipText);
	}
}
