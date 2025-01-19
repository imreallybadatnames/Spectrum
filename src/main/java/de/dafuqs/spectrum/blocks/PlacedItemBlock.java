package de.dafuqs.spectrum.blocks;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.loot.context.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public abstract class PlacedItemBlock extends BlockWithEntity {
	
	public PlacedItemBlock(Settings settings) {
		super(settings);
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new PlacedItemBlockEntity(pos, state);
	}
	
	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof PlacedItemBlockEntity placedItemBlockEntity) {
			ItemStack placedStack = stack.copy();
			placedStack.setCount(1);
			placedItemBlockEntity.setStack(placedStack);
			if (placer instanceof PlayerEntity playerPlacer) {
				placedItemBlockEntity.setOwner(playerPlacer);
			}
		}
	}

	@Override
	public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
		Optional<PlacedItemBlockEntity> blockEntity = world.getBlockEntity(pos, SpectrumBlockEntities.PLACED_ITEM);
		if (blockEntity.isPresent()) {
			return blockEntity.get().getStack().copy();
		}
		return ItemStack.EMPTY;
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
		BlockEntity blockEntity = builder.get(LootContextParameters.BLOCK_ENTITY);
		if (blockEntity instanceof PlacedItemBlockEntity placedItemBlockEntity) {
			return List.of(placedItemBlockEntity.getStack());
		} else {
			return super.getDroppedStacks(state, builder);
		}
	}

}
