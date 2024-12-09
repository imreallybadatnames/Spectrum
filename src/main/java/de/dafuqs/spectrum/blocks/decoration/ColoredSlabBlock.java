package de.dafuqs.spectrum.blocks.decoration;

import com.google.common.collect.*;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.util.*;

import java.util.*;

public class ColoredSlabBlock extends SlabBlock {

	public static final MapCodec<ColoredSlabBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			createSettingsCodec(),
			DyeColor.CODEC.fieldOf("color").forGetter(ColoredSlabBlock::getColor)
	).apply(instance, ColoredSlabBlock::new));

	private static final Map<DyeColor, ColoredSlabBlock> BLOCKS = Maps.newEnumMap(DyeColor.class);
	protected final DyeColor color;
	
	public ColoredSlabBlock(Settings settings, DyeColor color) {
		super(settings);
		this.color = color;
		BLOCKS.put(color, this);
	}

	@Override
	public MapCodec<? extends ColoredSlabBlock> getCodec() {
		return CODEC;
	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
	public static ColoredSlabBlock byColor(DyeColor color) {
		return BLOCKS.get(color);
	}
	
}
