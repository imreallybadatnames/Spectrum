package de.dafuqs.spectrum.blocks.deeper_down.flora;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class WeepingGalaSprigBlock extends SaplingBlock {

	public static final MapCodec<WeepingGalaSprigBlock> CODEC = createCodec(WeepingGalaSprigBlock::new);

	public WeepingGalaSprigBlock(AbstractBlock.Settings settings) {
		super(SpectrumSaplingGenerators.WEEPING_GALA_SAPLING_GENERATOR, settings);
	}

	@Override
	public MapCodec<? extends WeepingGalaSprigBlock> getCodec() {
		return CODEC;
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isIn(SpectrumBlockTags.ASH) || floor.isOf(SpectrumBlocks.ASHEN_BLACKSLAG) || super.canPlantOnTop(floor, world, pos);
	}
	
}
