package de.dafuqs.spectrum.blocks.rock_candy;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.blocks.*;
import de.dafuqs.spectrum.helpers.ColorHelper;
import de.dafuqs.spectrum.particle.effect.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.state.*;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.*;
import net.minecraft.text.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SugarStickBlock extends Block implements RockCandy {
	
	protected final static Map<RockCandyVariant, Block> SUGAR_STICK_BLOCKS = new EnumMap<>(RockCandyVariant.class);
	
	protected final RockCandyVariant rockCandyVariant;
	
	public static final int ITEM_SEARCH_RANGE = 5;
	public static final int REQUIRED_ITEM_COUNT_PER_STAGE = 4;
	
	public static final EnumProperty<FluidLogging.State> LOGGED = FluidLogging.NONE_AND_CRYSTAL;
	public static final IntProperty AGE = Properties.AGE_2;
	
	protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0D, 3.0D, 5.0D, 11.0D, 16.0D, 11.0D);
	
	public SugarStickBlock(Settings settings, RockCandyVariant rockCandyVariant) {
		super(settings);
		this.rockCandyVariant = rockCandyVariant;
		SUGAR_STICK_BLOCKS.put(this.rockCandyVariant, this);
		this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0).with(LOGGED, FluidLogging.State.NOT_LOGGED));
	}

    @Override
    public MapCodec<? extends SugarStickBlock> getCodec() {
        //TODO: Make the codec
        return null;
    }
	
	@Override
	public RockCandyVariant getVariant() {
		return this.rockCandyVariant;
	}
	
	@Override
	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		if (fluidState.getFluid() == SpectrumFluids.LIQUID_CRYSTAL) {
			return super.getPlacementState(ctx).with(LOGGED, FluidLogging.State.LIQUID_CRYSTAL);
		} else {
			return super.getPlacementState(ctx);
		}
	}
	
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(LOGGED).isOf(SpectrumFluids.LIQUID_CRYSTAL) ? SpectrumFluids.LIQUID_CRYSTAL.getStill(false) : super.getFluidState(state);
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(AGE, LOGGED);
	}
	
	@Override
	public boolean hasRandomTicks(BlockState state) {
		return state.get(LOGGED).isOf(SpectrumFluids.LIQUID_CRYSTAL) && state.get(AGE) < Properties.AGE_2_MAX;
	}
	
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		super.randomDisplayTick(state, world, pos, random);
		if (state.get(LOGGED).isOf(Fluids.EMPTY)) {
			int age = state.get(AGE);
			
			if (age == 2 || (age == 1 ? random.nextBoolean() : random.nextFloat() < 0.25)) {
				world.addParticle(new DynamicParticleEffect(0.1F, ColorHelper.getRGBVec(rockCandyVariant.getDyeColor()), 0.5F, 120, true, true),
						pos.getX() + 0.25 + random.nextFloat() * 0.5,
						pos.getY() + 0.25 + random.nextFloat() * 0.5,
						pos.getZ() + 0.25 + random.nextFloat() * 0.5,
						0.08 - random.nextFloat() * 0.16,
						0.04 - random.nextFloat() * 0.16,
						0.08 - random.nextFloat() * 0.16);
			}
			
		}
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.randomTick(state, world, pos, random);
		
		if (state.get(LOGGED).isOf(SpectrumFluids.LIQUID_CRYSTAL)) {
			int age = state.get(AGE);
			if (age < Properties.AGE_2_MAX) {
				List<ItemEntity> itemEntities = world.getNonSpectatingEntities(ItemEntity.class, Box.of(Vec3d.ofCenter(pos), ITEM_SEARCH_RANGE, ITEM_SEARCH_RANGE, ITEM_SEARCH_RANGE));
				Collections.shuffle(itemEntities);
				for (ItemEntity itemEntity : itemEntities) {
					// is the item also submerged?
					// lazy, but mostly accurate and performant way to check if it's the same liquid pool
					if (!itemEntity.isSubmergedIn(SpectrumFluidTags.LIQUID_CRYSTAL)) {
						continue;
					}
					
					ItemStack stack = itemEntity.getStack();
					if (stack.getCount() >= REQUIRED_ITEM_COUNT_PER_STAGE) {
						@Nullable RockCandyVariant itemVariant = RockCandyVariant.getFor(stack);
						if (itemVariant != null) {
							BlockState newState;
							if (rockCandyVariant != RockCandyVariant.SUGAR) {
								newState = state;
							} else {
								newState = SUGAR_STICK_BLOCKS.get(itemVariant).getDefaultState();
							}
							
							stack.decrement(REQUIRED_ITEM_COUNT_PER_STAGE);
							world.setBlockState(pos, newState.with(AGE, age + 1).with(LOGGED, state.get(LOGGED)));
							world.playSound(null, pos, newState.getSoundGroup().getHitSound(), SoundCategory.BLOCKS, 0.5F, 1.0F);
							break;
						}
					}
				}
			}
		}
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}
	
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		Direction direction = Direction.UP;
		return Block.sideCoversSmallSquare(world, pos.offset(direction), direction.getOpposite());
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return direction == Direction.UP && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	
	@Override
	public boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}

	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		
		BlockStateComponent stateComponent = stack.get(DataComponentTypes.BLOCK_STATE);
		if (stateComponent != null) {
			Integer age = stateComponent.getValue(SugarStickBlock.AGE);
			switch (age) {
				case 1 -> {
					tooltip.add(Text.translatable("block.spectrum.sugar_stick.tooltip.medium"));
				}
				case 2 -> {
					tooltip.add(Text.translatable("block.spectrum.sugar_stick.tooltip.large"));
				}
				case null, default -> {
				}
			}
		}
	}
	
}
