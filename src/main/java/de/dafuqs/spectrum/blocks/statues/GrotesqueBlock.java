package de.dafuqs.spectrum.blocks.statues;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.state.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class GrotesqueBlock extends HorizontalFacingBlock {

	private final VoxelShape shape;
	protected final Text tooltipText;


	public GrotesqueBlock(Settings settings, double width, double height, String tooltipKey) {
		super(settings);
		tooltipText = Text.translatable(tooltipKey).formatted(Formatting.GRAY);
		var min = (16 - width) / 2;
		var max = width + min;
		shape = Block.createCuboidShape(min, 0, min, max, height, max);
	}

	@Override
	public MapCodec<? extends GrotesqueBlock> getCodec() {
		//TODO: Make the codec
		return null;
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(tooltipText);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return shape;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
