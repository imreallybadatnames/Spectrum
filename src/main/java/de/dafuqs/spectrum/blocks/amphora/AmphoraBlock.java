package de.dafuqs.spectrum.blocks.amphora;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.*;
import net.minecraft.stat.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public class AmphoraBlock extends BlockWithEntity {

	public static final MapCodec<AmphoraBlock> CODEC = createCodec(AmphoraBlock::new);
	public static final DirectionProperty FACING = Properties.FACING;
	public static final BooleanProperty OPEN = Properties.OPEN;

	public AmphoraBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(OPEN, false));
	}

	@Override
	public MapCodec<? extends BlockWithEntity> getCodec() {
		return CODEC;
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		} else {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof AmphoraBlockEntity amphoraBlockEntity) {
				player.openHandledScreen(amphoraBlockEntity);
				player.incrementStat(Stats.OPEN_BARREL);
				PiglinBrain.onGuardedBlockInteracted(player, true);
			}
			
			return ActionResult.CONSUME;
		}
	}

	@Override
	protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		ItemScatterer.onStateReplaced(state, newState, world, pos);
		super.onStateReplaced(state, world, pos, newState, moved);
	}
	
	@Override
	@Nullable
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new AmphoraBlockEntity(pos, state);
	}
	
	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof AmphoraBlockEntity amphoraBlockEntity) {
			amphoraBlockEntity.tick();
		}
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, OPEN);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
	}

}
