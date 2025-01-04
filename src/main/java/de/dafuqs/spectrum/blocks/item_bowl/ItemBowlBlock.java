package de.dafuqs.spectrum.blocks.item_bowl;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.blocks.*;
import de.dafuqs.spectrum.blocks.enchanter.*;
import de.dafuqs.spectrum.blocks.spirit_instiller.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ItemBowlBlock extends InWorldInteractionBlock {

	public static final MapCodec<ItemBowlBlock> CODEC = createCodec(ItemBowlBlock::new);

	protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D);

	// Positions to check on place / destroy to upgrade those blocks upgrade counts
	private final List<Vec3i> possibleEnchanterOffsets = new ArrayList<>() {{
		add(new Vec3i(5, 0, 3));
		add(new Vec3i(-5, 0, -3));
		add(new Vec3i(-3, 0, 5));
		add(new Vec3i(-3, 0, -5));
		add(new Vec3i(3, 0, 5));
		add(new Vec3i(3, 0, -5));
		add(new Vec3i(5, 0, 3));
		add(new Vec3i(5, 0, -3));
	}};

	// Positions to check on place / destroy to upgrade those blocks upgrade counts
	private final List<Vec3i> possibleSpiritInstillerOffsets = new ArrayList<>() {{
		add(new Vec3i(0, -1, 2));
		add(new Vec3i(0, -1, -2));
		add(new Vec3i(2, -1, 0));
		add(new Vec3i(-2, -1, 0));
	}};

	public ItemBowlBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends ItemBowlBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ItemBowlBlockEntity(pos, state);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return world.isClient ? validateTicker(type, SpectrumBlockEntities.ITEM_BOWL, ItemBowlBlockEntity::clientTick) : null;
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}
	
	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		ItemScatterer.onStateReplaced(state, newState, world, pos);
		super.onStateReplaced(state, world, pos, newState, moved);
		updateConnectedMultiBlocks(world, pos);
	}
	
	/**
	 * When placed or removed the item bowl searches for a valid block entity and triggers it to update its current recipe
	 */
	private void updateConnectedMultiBlocks(@NotNull World world, @NotNull BlockPos pos) {
		for (Vec3i possibleUpgradeBlockOffset : possibleEnchanterOffsets) {
			BlockPos currentPos = pos.add(possibleUpgradeBlockOffset);
			BlockEntity blockEntity = world.getBlockEntity(currentPos);
			if (blockEntity instanceof EnchanterBlockEntity enchanterBlockEntity) {
				enchanterBlockEntity.inventoryChanged();
				break;
			}
		}
		
		for (Vec3i possibleUpgradeBlockOffset : possibleSpiritInstillerOffsets) {
			BlockPos currentPos = pos.add(possibleUpgradeBlockOffset);
			BlockEntity blockEntity = world.getBlockEntity(currentPos);
			if (blockEntity instanceof SpiritInstillerBlockEntity spiritInstillerBlockEntity) {
				spiritInstillerBlockEntity.inventoryChanged();
				break;
			}
		}
	}
	
	@Override
	public ItemActionResult onUseWithItem(ItemStack handStack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) {
			return ItemActionResult.SUCCESS;
		} else {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof ItemBowlBlockEntity itemBowlBlockEntity) {
				if (exchangeStack(world, pos, player, hand, handStack, itemBowlBlockEntity)) {
					updateConnectedMultiBlocks(world, pos);
				}
			}
			return ItemActionResult.CONSUME;
		}
	}
	
}
