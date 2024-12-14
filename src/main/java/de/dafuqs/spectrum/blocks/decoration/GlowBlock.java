package de.dafuqs.spectrum.blocks.decoration;

import com.google.common.collect.*;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class GlowBlock extends Block {

	public static final MapCodec<GlowBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			createSettingsCodec(),
			DyeColor.CODEC.fieldOf("color").forGetter(GlowBlock::getColor)
	).apply(i, GlowBlock::new));

	private static final Map<DyeColor, GlowBlock> GLOWBLOCKS = Maps.newEnumMap(DyeColor.class);
	protected final DyeColor color;
	
	public GlowBlock(Settings settings, DyeColor color) {
		super(settings);
		this.color = color;
		GLOWBLOCKS.put(color, this);
	}

	@Override
	public MapCodec<? extends GlowBlock> getCodec() {
		return CODEC;
	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
	public static GlowBlock byColor(DyeColor color) {
		return GLOWBLOCKS.get(color);
	}
	
	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 1.0F;
	}
	
}
