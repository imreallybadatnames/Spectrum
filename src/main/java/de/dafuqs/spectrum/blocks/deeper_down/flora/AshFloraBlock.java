package de.dafuqs.spectrum.blocks.deeper_down.flora;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.blocks.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class AshFloraBlock extends SpreadableFloraBlock {

	public static final MapCodec<AshFloraBlock> CODEC = createCodec(AshFloraBlock::new);

	public AshFloraBlock(Settings settings) {
		super(7, settings);
	}

//	@Override
//	public MapCodec<? extends AshFloraBlock> getCodec() {
//		//TODO: Make the codec
//		return CODEC;
//	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return (floor.isIn(SpectrumBlockTags.ASH) || floor.isOf(SpectrumBlocks.ASHEN_BLACKSLAG) || super.canPlantOnTop(floor, world, pos))
				&& floor.isSideSolidFullSquare(world, pos, Direction.UP);
	}
}
