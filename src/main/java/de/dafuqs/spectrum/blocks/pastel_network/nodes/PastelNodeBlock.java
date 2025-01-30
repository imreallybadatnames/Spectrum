package de.dafuqs.spectrum.blocks.pastel_network.nodes;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.revelationary.api.advancements.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.blocks.decoration.*;
import de.dafuqs.spectrum.blocks.pastel_network.network.*;
import de.dafuqs.spectrum.progression.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.state.*;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PastelNodeBlock extends SpectrumFacingBlock implements BlockEntityProvider, ColorableBlock {

	public static final MapCodec<PastelNodeBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			createSettingsCodec(),
			StringIdentifiable.createCodec(PastelNodeType::values).fieldOf("node_type").forGetter(b -> b.pastelNodeType)
	).apply(i, PastelNodeBlock::new));

	public static final BooleanProperty LIT = Properties.LIT;
	public static final BooleanProperty EMITTING = Properties.POWERED;

	public static final Map<Direction, VoxelShape> SHAPES = new HashMap<>() {{
		put(Direction.UP, Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D));
		put(Direction.DOWN, Block.createCuboidShape(5.0D, 12.0D, 5.0D, 11.0D, 16.0D, 11.0D));
		put(Direction.NORTH, Block.createCuboidShape(5.0D, 5.0D, 12.0D, 11.0D, 11.0D, 16.0D));
		put(Direction.SOUTH, Block.createCuboidShape(5.0D, 5.0D, 0.0D, 11.0D, 11.0D, 4.0D));
		put(Direction.EAST, Block.createCuboidShape(0.0D, 5.0D, 5.0D, 4.0D, 11.0D, 11.0D));
		put(Direction.WEST, Block.createCuboidShape(12.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D));
	}};

	protected final PastelNodeType pastelNodeType;

	public PastelNodeBlock(Settings settings, PastelNodeType pastelNodeType) {
		super(settings.luminance(s -> s.get(LIT) ? 13 : 0));
		this.pastelNodeType = pastelNodeType;
		setDefaultState(getDefaultState().with(LIT, false).with(EMITTING, false));
	}

	@Override
	public MapCodec<? extends PastelNodeBlock> getCodec() {
		return CODEC;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return SpectrumCommon.CONFIG.MinimalNodes ? BlockRenderType.ENTITYBLOCK_ANIMATED : BlockRenderType.MODEL;
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		Direction targetDirection = state.get(FACING).getOpposite();
		BlockPos targetPos = pos.offset(targetDirection);
		return world.getBlockState(targetPos).isSolid();
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (!newState.isOf(state.getBlock())) {
			PastelNodeBlockEntity blockEntity = getBlockEntity(world, pos);
			if (blockEntity != null) {
				blockEntity.onBroken();
				blockEntity.getOuterRing().ifPresent(r -> dropStack(world, pos, r.upgradeItem.getDefaultStack()));
				blockEntity.getInnerRing().ifPresent(r -> dropStack(world, pos, r.upgradeItem.getDefaultStack()));
				blockEntity.getRedstoneRing().ifPresent(r -> dropStack(world, pos, r.upgradeItem.getDefaultStack()));
			}
		}
		super.onStateReplaced(state, world, pos, newState, moved);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return ((w, p, s, b) -> PastelNodeBlockEntity.tick(w, p, s, (PastelNodeBlockEntity) b));
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);
	}

	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.addAll(this.pastelNodeType.getTooltips());
		tooltip.add(Text.translatable("block.spectrum.pastel_network_nodes.tooltip.range", PastelNodeBlockEntity.RANGE).formatted(Formatting.GRAY));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(LIT, EMITTING);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : state;
	}

	@Override
	public ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		@Nullable PastelNodeBlockEntity blockEntity = getBlockEntity(world, pos);
		if (blockEntity == null) {
			return ItemActionResult.FAIL;
		}

		if (player.isSneaking() && stack.isEmpty()) {
			if (AdvancementHelper.hasAdvancement(player, SpectrumAdvancements.PASTEL_NODE_UPGRADING)) {
				var removed = blockEntity.tryRemoveUpgrade();
				if (!removed.isEmpty()) {
					if (!player.getAbilities().creativeMode)
						player.getInventory().offerOrDrop(removed);
					
					blockEntity.updateUpgrades();
					return ItemActionResult.success(world.isClient());
				}
			}
			return ItemActionResult.FAIL;
		} else if (stack.isOf(SpectrumItems.PAINTBRUSH)) {
			if (player.isCreative() && player.isSneaking()) {
				return sendDebugMessage(world, player, blockEntity);
			}
			return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		} else if (AdvancementHelper.hasAdvancement(player, SpectrumAdvancements.PASTEL_NODE_UPGRADING) && stack.isIn(SpectrumItemTags.PASTEL_NODE_UPGRADES) && blockEntity.tryInteractRings(stack, pastelNodeType)) {
			if (!world.isClient())
				SpectrumAdvancementCriteria.PASTEL_NODE_UPGRADING.trigger((ServerPlayerEntity) player, stack);

			if (!player.getAbilities().creativeMode)
				stack.decrement(1);

			world.playSoundAtBlockCenter(pos, SpectrumSoundEvents.MEDIUM_CRYSTAL_RING, SoundCategory.BLOCKS, 0.25F, 0.9F + world.getRandom().nextFloat() * 0.2F, true);
			blockEntity.updateUpgrades();
			return ItemActionResult.success(world.isClient());
		} else if (this.pastelNodeType.usesFilters()) {
			if (world.isClient) {
				return ItemActionResult.SUCCESS;
			} else {
				player.openHandledScreen(blockEntity);
				return ItemActionResult.CONSUME;
			}
		}
		return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
	}

	@Override
	public boolean emitsRedstonePower(BlockState state) {
		return true;
	}

	@Override
	public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return state.get(EMITTING) ? 15 : 0;
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		world.setBlockState(pos, state.with(EMITTING, false));
	}

	@NotNull
	private static ItemActionResult sendDebugMessage(World world, PlayerEntity player, PastelNodeBlockEntity blockEntity) {
		if (world.isClient) {
			if (blockEntity != null) {
				PastelNetwork network = blockEntity.parentNetwork;
				player.sendMessage(Text.translatable("block.spectrum.pastel_network_nodes.connection_debug"));
				if (network == null) {
					player.sendMessage(Text.literal("C: No connected network :("));
				} else {
					player.sendMessage(Text.literal("C: " + network.getUUID().toString()));
					player.sendMessage(Text.literal("C: " + network.getNodeDebugText()));
				}
			}
			return ItemActionResult.SUCCESS;
		} else {
			if (blockEntity != null) {
				PastelNetwork network = blockEntity.parentNetwork;
				if (network == null) {
					player.sendMessage(Text.literal("S: No connected network :("));
				} else {
					player.sendMessage(Text.literal("S: " + network.getUUID().toString()));
					player.sendMessage(Text.literal("S: " + network.getNodeDebugText()));
				}
			}
			return ItemActionResult.CONSUME;
		}
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPES.get(state.get(FACING));
	}

	public @Nullable PastelNodeBlockEntity getBlockEntity(WorldAccess world, BlockPos blockPos) {
		BlockEntity blockEntity = world.getBlockEntity(blockPos);
		if (blockEntity instanceof PastelNodeBlockEntity pastelNodeBlockEntity) {
			return pastelNodeBlockEntity;
		}
		return null;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new PastelNodeBlockEntity(pos, state);
	}
	
	@Override
	public boolean color(World world, BlockPos pos, DyeColor color) {
		@Nullable PastelNodeBlockEntity blockEntity = getBlockEntity(world, pos);
		if (blockEntity == null) {
			return false;
		}
		return blockEntity.setColor(color);
	}
	
	@Override
	public DyeColor getColor(World world, BlockPos pos) {
		@Nullable PastelNodeBlockEntity blockEntity = getBlockEntity(world, pos);
		if (blockEntity == null) {
			return DyeColor.RED;
		}
		return blockEntity.getColor().orElse(DyeColor.GRAY);
	}
	
}
