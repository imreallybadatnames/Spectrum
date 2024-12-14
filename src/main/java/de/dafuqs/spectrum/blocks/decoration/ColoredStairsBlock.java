package de.dafuqs.spectrum.blocks.decoration;

import com.google.common.collect.*;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.util.*;

import java.util.*;

public class ColoredStairsBlock extends StairsBlock {

	public static final MapCodec<ColoredStairsBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			BlockState.CODEC.fieldOf("base_state").forGetter(b -> b.baseBlockState),
			createSettingsCodec(),
			DyeColor.CODEC.fieldOf("color").forGetter(ColoredStairsBlock::getColor)
	).apply(instance, ColoredStairsBlock::new));

	private static final Map<DyeColor, ColoredStairsBlock> BLOCKS = Maps.newEnumMap(DyeColor.class);
	protected final DyeColor color;
	
	public ColoredStairsBlock(BlockState baseBlockState, Settings settings, DyeColor color) {
		super(baseBlockState, settings);
		this.color = color;
		BLOCKS.put(color, this);
	}

	@Override
	public MapCodec<? extends ColoredStairsBlock> getCodec() {
		return CODEC;
	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
	public static ColoredStairsBlock byColor(DyeColor color) {
		return BLOCKS.get(color);
	}
	
}
