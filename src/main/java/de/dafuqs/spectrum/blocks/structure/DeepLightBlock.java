package de.dafuqs.spectrum.blocks.structure;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.item.*;
import net.minecraft.state.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public class DeepLightBlock extends HorizontalFacingBlock implements BlockEntityProvider {

    public static final MapCodec<DeepLightBlock> CODEC = createCodec(DeepLightBlock::new);

	public DeepLightBlock(Settings settings) {
		super(settings);
	}

    @Override
    public MapCodec<? extends DeepLightBlock> getCodec() {
        return CODEC;
    }

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new DeepLightBlockEntity(pos, state);
	}
}
