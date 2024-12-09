package de.dafuqs.spectrum.blocks.decoration;

import com.google.common.collect.*;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.util.*;

import java.util.*;

public class PigmentBlock extends Block {

	public static final MapCodec<PigmentBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			createSettingsCodec(),
			DyeColor.CODEC.fieldOf("color").forGetter(PigmentBlock::getColor)
	).apply(i, PigmentBlock::new));

	private static final Map<DyeColor, PigmentBlock> BLOCKS = Maps.newEnumMap(DyeColor.class);
	protected final DyeColor color;
	
	public PigmentBlock(Settings settings, DyeColor color) {
		super(settings);
		this.color = color;
		BLOCKS.put(color, this);
	}

	@Override
	public MapCodec<? extends PigmentBlock> getCodec() {
		return CODEC;
	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
	public static PigmentBlock byColor(DyeColor color) {
		return BLOCKS.get(color);
	}
	
}
