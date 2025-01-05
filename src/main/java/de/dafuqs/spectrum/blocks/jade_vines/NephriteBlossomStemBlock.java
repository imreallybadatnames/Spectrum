package de.dafuqs.spectrum.blocks.jade_vines;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.tag.convention.v2.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.state.*;
import net.minecraft.state.property.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;
import net.minecraft.world.event.*;
import org.jetbrains.annotations.*;

public class NephriteBlossomStemBlock extends PlantBlock {

	public static final MapCodec<NephriteBlossomStemBlock> CODEC = createCodec(NephriteBlossomStemBlock::new);

	public static final EnumProperty<StemComponent> STEM_PART = StemComponent.PROPERTY;

	public NephriteBlossomStemBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(STEM_PART, StemComponent.BASE));
	}

	@Override
	public MapCodec<? extends NephriteBlossomStemBlock> getCodec() {
		return CODEC;
	}
	
	public static BlockState getStemVariant(boolean top) {
		return SpectrumBlocks.NEPHRITE_BLOSSOM_STEM.getDefaultState().with(STEM_PART, top ? StemComponent.STEMALT : StemComponent.STEM);
	}
	
	@Override
	public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
		return SpectrumBlocks.NEPHRITE_BLOSSOM_BULB.asItem().getDefaultStack();
	}
	
	@Override
	public ItemActionResult onUseWithItem(ItemStack handStack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (handStack.isIn(ConventionalItemTags.SHEAR_TOOLS) && state.get(STEM_PART) == StemComponent.BASE) {
			BlockState newState = state.with(STEM_PART, StemComponent.STEM);
			world.setBlockState(pos, newState);
			player.playSoundToPlayer(SoundEvents.ENTITY_MOOSHROOM_SHEAR, SoundCategory.BLOCKS, 1, 0.9F + player.getRandom().nextFloat() * 0.2F);
			handStack.damage(1, player, LivingEntity.getSlotForHand(hand));
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(newState));
    
            return ItemActionResult.success(world.isClient());
        }

        return super.onUseWithItem(handStack, state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        var world = ctx.getWorld();
        var pos = ctx.getBlockPos();
        var floor = world.getBlockState(pos.down());

        var state = super.getPlacementState(ctx);

        if (state == null)
            return null;

        if (floor.isOf(this) ) {
            if (floor.get(STEM_PART) != StemComponent.STEMALT) {
                state = state.with(STEM_PART, StemComponent.STEMALT);
            }
            else if (floor.get(STEM_PART) != StemComponent.STEM) {
                state = state.with(STEM_PART, StemComponent.STEM);
            }
        }

        return state;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(this) || super.canPlantOnTop(floor, world, pos);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (!state.canPlaceAt(world, pos)) {
			world.scheduleBlockTick(pos, this, 1);
		}

		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

    @Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.scheduledTick(state, world, pos, random);
		if (!state.canPlaceAt(world, pos)) {
			world.breakBlock(pos, true);
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(STEM_PART);
	}

}
