package de.dafuqs.spectrum.blocks.decoration;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.*;

public class SpectrumLineFacingBlock extends SpectrumFacingBlock {

	public static final MapCodec<SpectrumLineFacingBlock> CODEC = createCodec(SpectrumLineFacingBlock::new);

	public SpectrumLineFacingBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends SpectrumLineFacingBlock> getCodec() {
		return CODEC;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection());
	}
	
}
