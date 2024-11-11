package de.dafuqs.spectrum.blocks.decoration;

import com.google.common.collect.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

import java.util.*;

public class ColoredPressurePlateBlock extends PressurePlateBlock {
	
	private static final Map<DyeColor, ColoredPressurePlateBlock> BLOCKS = Maps.newEnumMap(DyeColor.class);
	protected final DyeColor color;
	
	public ColoredPressurePlateBlock(Settings settings, DyeColor color) {
		super(SpectrumBlockSetTypes.COLORED_WOOD, settings);
		this.color = color;
		BLOCKS.put(color, this);
	}

//	@Override
//	public MapCodec<? extends ColoredPressurePlateBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
	public static ColoredPressurePlateBlock byColor(DyeColor color) {
		return BLOCKS.get(color);
	}
	
}
