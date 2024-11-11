package de.dafuqs.spectrum.blocks.decoration;

import com.google.common.collect.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.util.*;

import java.util.*;

public class ColoredStairsBlock extends StairsBlock {
	
	private static final Map<DyeColor, ColoredStairsBlock> BLOCKS = Maps.newEnumMap(DyeColor.class);
	protected final DyeColor color;
	
	public ColoredStairsBlock(BlockState baseBlockState, Settings settings, DyeColor color) {
		super(baseBlockState, settings);
		this.color = color;
		BLOCKS.put(color, this);
	}

	@Override
	public MapCodec<? extends ColoredStairsBlock> getCodec() {
		//TODO: Make the codec
		return null;
	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
	public static ColoredStairsBlock byColor(DyeColor color) {
		return BLOCKS.get(color);
	}
	
}
