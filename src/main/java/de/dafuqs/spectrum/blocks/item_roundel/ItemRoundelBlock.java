package de.dafuqs.spectrum.blocks.item_roundel;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.blocks.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

public class ItemRoundelBlock extends InWorldInteractionBlock {

	public static final MapCodec<ItemRoundelBlock> CODEC = createCodec(ItemRoundelBlock::new);

	protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 14.0D, 12.0D);

	public ItemRoundelBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends ItemRoundelBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ItemRoundelBlockEntity(pos, state);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}
	
	@Override
	public ItemActionResult onUseWithItem(ItemStack handStack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) {
			return ItemActionResult.SUCCESS;
		} else {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof ItemRoundelBlockEntity itemRoundelBlockEntity) {
				if (player.isSneaking() || handStack.isEmpty()) {
					retrieveLastStack(world, pos, player, hand, handStack, itemRoundelBlockEntity);
				} else {
					inputHandStack(world, player, hand, handStack, itemRoundelBlockEntity);
				}
			}
			return ItemActionResult.CONSUME;
		}
	}
	
}
