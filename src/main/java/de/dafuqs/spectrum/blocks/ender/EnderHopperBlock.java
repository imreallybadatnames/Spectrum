package de.dafuqs.spectrum.blocks.ender;

import de.dafuqs.spectrum.enums.*;
import de.dafuqs.spectrum.inventories.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.screen.*;
import net.minecraft.server.network.*;
import net.minecraft.stat.*;
import net.minecraft.state.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.function.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import static net.minecraft.block.HopperBlock.*;

public class EnderHopperBlock extends BlockWithEntity {
	
	private final VoxelShape TOP_SHAPE = Block.createCuboidShape(0.0D, 10.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private final VoxelShape MIDDLE_SHAPE = Block.createCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 10.0D, 12.0D);
	private final VoxelShape OUTSIDE_SHAPE = VoxelShapes.union(MIDDLE_SHAPE, TOP_SHAPE);
	private final VoxelShape DEFAULT_SHAPE = VoxelShapes.combineAndSimplify(OUTSIDE_SHAPE, Hopper.INSIDE_SHAPE, BooleanBiFunction.ONLY_FIRST);
	private final VoxelShape DOWN_SHAPE = VoxelShapes.union(DEFAULT_SHAPE, Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D));
	private final VoxelShape DOWN_RAYCAST_SHAPE = Hopper.INSIDE_SHAPE;
	
	public EnderHopperBlock(Settings settings) {
		super(settings);
	}
	
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new EnderHopperBlockEntity(pos, state);
	}
	
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (placer instanceof ServerPlayerEntity) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof EnderHopperBlockEntity) {
				((EnderHopperBlockEntity) blockEntity).setOwner((ServerPlayerEntity) placer);
				blockEntity.markDirty();
			}
		}
	}
	
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return DOWN_SHAPE;
	}
	
	public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
		return DOWN_RAYCAST_SHAPE;
	}
	
	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		if (world.isClient) {
			return null;
		} else {
			return checkType(type, SpectrumBlockEntities.ENDER_HOPPER, EnderHopperBlockEntity::serverTick);
		}
	}
	
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		this.updateEnabled(world, pos, state);
	}
	
	private void updateEnabled(World world, BlockPos pos, BlockState state) {
		boolean bl = !world.isReceivingRedstonePower(pos);
		if (bl != state.get(ENABLED)) {
			world.setBlockState(pos, state.with(ENABLED, bl), 4);
		}
	}
	
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (!state.isOf(newState.getBlock())) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof HopperBlockEntity hopperBlockEntity) {
				ItemScatterer.spawn(world, pos, hopperBlockEntity);
				world.updateComparators(pos, this);
			}
			
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}
	
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof EnderHopperBlockEntity) {
			EnderHopperBlockEntity.onEntityCollided(world, pos, state, entity, (EnderHopperBlockEntity) blockEntity);
		}
	}
	
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}
	
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(ENABLED);
	}
	
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		} else {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof EnderHopperBlockEntity) {
				EnderHopperBlockEntity enderHopperBlockEntity = (EnderHopperBlockEntity) blockEntity;
				
				if (!enderHopperBlockEntity.hasOwner()) {
					enderHopperBlockEntity.setOwner(player);
				}
				
				if (enderHopperBlockEntity.isOwner(player)) {
					EnderChestInventory enderChestInventory = player.getEnderChestInventory();
					
					player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> {
						return GenericSpectrumContainerScreenHandler.createGeneric9x3(i, playerInventory, enderChestInventory, ProgressionStage.EARLYGAME);
					}, enderHopperBlockEntity.getContainerName()));
					player.incrementStat(Stats.OPEN_ENDERCHEST);
					PiglinBrain.onGuardedBlockInteracted(player, true);
				} else {
					SpectrumS2CPacketSender.sendHudMessage((ServerPlayerEntity) player, Text.translatable("block.spectrum.ender_hopper_with_owner", enderHopperBlockEntity.getOwnerName()), false);
				}
				
				
			}
			return ActionResult.CONSUME;
		}
	}
	
}