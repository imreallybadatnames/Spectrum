package de.dafuqs.spectrum.blocks.decoration;

import com.google.common.collect.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

import java.util.*;

public class ColoredFenceGateBlock extends FenceGateBlock {
	
	private static final Map<DyeColor, ColoredFenceGateBlock> BLOCKS = Maps.newEnumMap(DyeColor.class);
	protected final DyeColor color;
	
	public ColoredFenceGateBlock(Settings settings, DyeColor color) {
		super(SpectrumWoodTypes.COLORED_WOOD, settings);
		this.color = color;
		BLOCKS.put(color, this);
	}

//	@Override
//	public MapCodec<? extends ColoredFenceGateBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
	public static ColoredFenceGateBlock byColor(DyeColor color) {
		return BLOCKS.get(color);
	}
	
}
