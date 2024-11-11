package de.dafuqs.spectrum.blocks.deeper_down.flora;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class WeepingGalaSprigBlock extends SaplingBlock {

	public WeepingGalaSprigBlock(SaplingGenerator generator, AbstractBlock.Settings settings) {
		super(generator, settings);
	}

	@Override
	public MapCodec<? extends WeepingGalaSprigBlock> getCodec() {
		//TODO: Make the codec
		return null;
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isIn(SpectrumBlockTags.ASH) || floor.isOf(SpectrumBlocks.ASHEN_BLACKSLAG) || super.canPlantOnTop(floor, world, pos);
	}
	
}
