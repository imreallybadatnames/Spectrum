package de.dafuqs.spectrum.blocks.redstone;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class BlockLightDetectorBlock extends DetectorBlock {

	public static final MapCodec<BlockLightDetectorBlock> CODEC = createCodec(BlockLightDetectorBlock::new);

	public BlockLightDetectorBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends BlockLightDetectorBlock> getCodec() {
		//TODO: Make the codec
		return CODEC;
	}
	
	@Override
	protected void updateState(BlockState state, World world, BlockPos pos) {
		int power = world.getLightLevel(pos);
		
		power = state.get(INVERTED) ? 15 - power : power;
		if (state.get(POWER) != power) {
			world.setBlockState(pos, state.with(POWER, power), 3);
		}
	}
	
	@Override
	int getUpdateFrequencyTicks() {
		return 20;
	}
	
}
