package de.dafuqs.spectrum.blocks.decoration;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.state.*;
import net.minecraft.util.math.*;

public class ShinglesBlock extends HorizontalFacingBlock {

	public static final MapCodec<ShinglesBlock> CODEC = createCodec(ShinglesBlock::new);

	public ShinglesBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
	}

	@Override
	public MapCodec<? extends ShinglesBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
}
