package de.dafuqs.spectrum.blocks.decoration;

import com.google.common.collect.*;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.util.*;

import java.util.*;

public class ColoredPlankBlock extends Block {

	public static final MapCodec<ColoredPlankBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			createSettingsCodec(),
			DyeColor.CODEC.fieldOf("color").forGetter(ColoredPlankBlock::getColor)
	).apply(instance, ColoredPlankBlock::new));

	private static final Map<DyeColor, ColoredPlankBlock> BLOCKS = Maps.newEnumMap(DyeColor.class);
	protected final DyeColor color;
	
	public ColoredPlankBlock(Settings settings, DyeColor color) {
		super(settings);
		this.color = color;
		BLOCKS.put(color, this);
	}

	@Override
	public MapCodec<? extends ColoredPlankBlock> getCodec() {
		return CODEC;
	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
	public static ColoredPlankBlock byColor(DyeColor color) {
		return BLOCKS.get(color);
	}
	
}
