package de.dafuqs.spectrum.blocks.decoration;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;

public class DecayingLightBlock extends WandLightBlock {

	public static final MapCodec<DecayingLightBlock> CODEC = createCodec(DecayingLightBlock::new);

	public DecayingLightBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends DecayingLightBlock> getCodec() {
		return CODEC;
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.randomTick(state, world, pos, random);
		int light = state.get(LightBlock.LEVEL_15);
		if (light < 2) {
			if (state.get(WATERLOGGED)) {
				world.setBlockState(pos, Blocks.WATER.getDefaultState(), 3);
			} else {
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
			}
		} else {
			world.setBlockState(pos, state.with(LightBlock.LEVEL_15, light - 1), 3);
		}
	}
	
}
