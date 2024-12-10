package de.dafuqs.spectrum.blocks.deeper_down.groundcover;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.registry.tag.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.light.*;
import org.jetbrains.annotations.*;

public class SpectrumSpreadableBlock extends SnowyBlock {

	protected final @Nullable Block blockAbleToSpreadTo;
	private final BlockState deadState;
	
	public SpectrumSpreadableBlock(Settings settings, @Nullable Block blockAbleToSpreadTo, BlockState deadState) {
		super(settings);
		this.blockAbleToSpreadTo = blockAbleToSpreadTo;
		this.deadState = deadState;
	}

	@Override
	public MapCodec<? extends SpectrumSpreadableBlock> getCodec() {
		//TODO: Make the codec
		return null;
	}

	private static boolean canSpread(BlockState state, WorldView world, BlockPos pos) {
		BlockPos blockPos = pos.up();
		return canSurvive(state, world, pos) && !world.getFluidState(blockPos).isIn(FluidTags.WATER);
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!canSurvive(state, world, pos)) {
			world.setBlockState(pos, deadState);
		} else {
			if (world.getLightLevel(pos.up()) >= 9) {
				BlockState blockState = this.getDefaultState();
				
				for (int i = 0; i < 4; ++i) {
					BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
					if (blockAbleToSpreadTo != null && world.getBlockState(blockPos).isOf(blockAbleToSpreadTo) && canSpread(blockState, world, blockPos)) {
						world.setBlockState(blockPos, blockState.with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)));
					}
				}
			}
		}
	}
	
	private static boolean canSurvive(BlockState state, WorldView world, BlockPos pos) {
		BlockPos blockPos = pos.up();
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.isOf(Blocks.SNOW) && blockState.get(SnowBlock.LAYERS) == 1) {
			return true;
		} else if (blockState.getFluidState().getLevel() == 8) {
			return false;
		} else {
			int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(world, blockPos));
			return i < world.getMaxLightLevel();
		}
	}
	
}