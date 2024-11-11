package de.dafuqs.spectrum.blocks.decoration;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class RadiantGlassBlock extends TransparentBlock {

	public static final MapCodec<RadiantGlassBlock> CODEC = createCodec(RadiantGlassBlock::new);

	public RadiantGlassBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends RadiantGlassBlock> getCodec() {
		return CODEC;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
		if (stateFrom.isOf(this) || stateFrom.isOf(SpectrumBlocks.RADIANT_SEMI_PERMEABLE_GLASS)) {
			return true;
		}
		return super.isSideInvisible(state, stateFrom, direction);
	}
	
	@Override
	public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}
	
}
