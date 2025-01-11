package de.dafuqs.spectrum.blocks;

import com.google.common.collect.*;
import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.state.*;
import net.minecraft.state.property.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

public class DeeperDownPortalBlock extends Block {

	public static final MapCodec<DeeperDownPortalBlock> CODEC = createCodec(DeeperDownPortalBlock::new);

	private final static Identifier CREATE_PORTAL_ADVANCEMENT_IDENTIFIER = SpectrumCommon.locate("midgame/open_deeper_down_portal");
	private final static String CREATE_PORTAL_ADVANCEMENT_CRITERION = "opened_deeper_down_portal";

	public static final BooleanProperty FACING_UP = Properties.UP;

	protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4D, 16.0D);
	protected static final VoxelShape SHAPE_UP = Block.createCuboidShape(0.0D, 4D, 0.0D, 16.0D, 16.0D, 16.0D);

	public DeeperDownPortalBlock(Settings settings) {
		super(settings);
		this.setDefaultState((this.stateManager.getDefaultState()).with(FACING_UP, false));
	}

	@Override
	public MapCodec<? extends DeeperDownPortalBlock> getCodec() {
		return CODEC;
	}

	@Override
	public boolean hasSidedTransparency(BlockState state) {
		return true;
	}

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		super.onBlockAdded(state, world, pos, oldState, notify);

		if (!world.isClient) { // that should be a given, but in modded you never know
			PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity((ServerWorld) world, Vec3d.ofCenter(pos), SpectrumParticleTypes.VOID_FOG, 30, new Vec3d(0.5, 0.0, 0.5), Vec3d.ZERO);
			if (!hasNeighboringPortals(world, pos)) {
				world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SpectrumSoundEvents.DEEPER_DOWN_PORTAL_OPEN, SoundCategory.BLOCKS, 0.75F, 0.75F);

				for (PlayerEntity nearbyPlayer : world.getEntitiesByType(EntityType.PLAYER, Box.of(Vec3d.ofCenter(pos), 16D, 16D, 16D), LivingEntity::isAlive)) {
					Support.grantAdvancementCriterion((ServerPlayerEntity) nearbyPlayer, CREATE_PORTAL_ADVANCEMENT_IDENTIFIER, CREATE_PORTAL_ADVANCEMENT_CRITERION);
				}
			}
		}
	}

	@Override
	public ItemActionResult onUseWithItem(ItemStack handStack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (handStack.isOf(SpectrumItems.BEDROCK_DUST)) {
			if (world.isClient) {
				return ItemActionResult.SUCCESS;
			} else {
				BlockState placedState = Blocks.BEDROCK.getDefaultState();
				world.setBlockState(pos, placedState);
				world.playSound(null, pos, placedState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
				return ItemActionResult.CONSUME;
			}
		}

		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	private boolean hasNeighboringPortals(World world, BlockPos pos) {
		for (Direction direction : Direction.Type.HORIZONTAL) {
			if (world.getBlockState(pos.offset(direction)).isOf(this)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(FACING_UP) ? SHAPE_UP : SHAPE;
	}

	@Override
	public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canBucketPlace(BlockState state, Fluid fluid) {
		return false;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING_UP);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (world instanceof ServerWorld serverWorld && entity.canUsePortals(false) && !entity.hasPortalCooldown()) {
			
			entity.resetPortalCooldown();
			RegistryKey<World> currentWorldKey = world.getRegistryKey();
			
			if (currentWorldKey == World.NETHER) {
				// teleport between top/bottom of the nether
				boolean facingUp = state.get(FACING_UP); // true of on top of nether
				
				if (facingUp) {
					BlockPos portalPos = new BlockPos(pos.getX(), world.getBottomY(), pos.getZ());
					if (!world.getBlockState(portalPos).isOf(SpectrumBlocks.DEEPER_DOWN_PORTAL)) {
						world.setBlockState(portalPos, SpectrumBlocks.DEEPER_DOWN_PORTAL.getDefaultState().with(FACING_UP, false));
					}
					
					if (entity instanceof PlayerEntity) {
						makeRoomAround(world, portalPos, 4, 2, true, BlockTags.BASE_STONE_NETHER);
					}
					
					BlockPos targetPos = portalPos.up(2);
					entity.teleportTo(new TeleportTarget(serverWorld, Vec3d.ofCenter(targetPos), Vec3d.ZERO, entity.getYaw(), entity.getPitch(), TeleportTarget.NO_OP));
					teleportToSafePosition(serverWorld, entity, targetPos, 3);
				} else {
					BlockPos portalPos = new BlockPos(pos.getX(), world.getBottomY() + world.getDimension().logicalHeight() - 1, pos.getZ());
					if (!world.getBlockState(portalPos).isOf(SpectrumBlocks.DEEPER_DOWN_PORTAL)) {
						world.setBlockState(portalPos, SpectrumBlocks.DEEPER_DOWN_PORTAL.getDefaultState().with(FACING_UP, true));
					}
					
					if (entity instanceof PlayerEntity) {
						makeRoomAround(world, portalPos, 4, 2, false, BlockTags.BASE_STONE_NETHER);
					}
					
					BlockPos targetPos = portalPos.down(3);
					entity.teleportTo(new TeleportTarget(serverWorld, Vec3d.ofCenter(targetPos), Vec3d.ZERO, entity.getYaw(), entity.getPitch(), TeleportTarget.NO_OP));
					teleportToSafePosition(serverWorld, entity, targetPos.down(), 5);
				}
				
				return;
			}
			
			if (currentWorldKey == World.OVERWORLD) {
				// => teleport to DD
				ServerWorld targetWorld = serverWorld.getServer().getWorld(SpectrumDimensions.DIMENSION_KEY);
				if (targetWorld != null) {
					BlockPos portalPos = new BlockPos(pos.getX(), targetWorld.getTopY() - 1, pos.getZ());
					if (!targetWorld.getBlockState(portalPos).isOf(SpectrumBlocks.DEEPER_DOWN_PORTAL)) {
						targetWorld.setBlockState(portalPos, SpectrumBlocks.DEEPER_DOWN_PORTAL.getDefaultState().with(FACING_UP, true));
					}
					
					if (entity instanceof PlayerEntity) {
						makeRoomAround(targetWorld, portalPos, 4, 2, false, SpectrumBlockTags.BASE_STONE_DEEPER_DOWN);
					}
					
					BlockPos targetPos = portalPos.down(3);
					entity.teleportTo(new TeleportTarget(targetWorld, Vec3d.ofCenter(targetPos), Vec3d.ZERO, entity.getYaw(), entity.getPitch(),
							TeleportTarget.SEND_TRAVEL_THROUGH_PORTAL_PACKET.then(TeleportTarget.ADD_PORTAL_CHUNK_TICKET)));
					teleportToSafePosition(targetWorld, entity, targetPos.down(), 5);
					
					return;
				}
			}
			
			// => teleport to Overworld
			ServerWorld targetWorld = serverWorld.getServer().getWorld(World.OVERWORLD);
			if (targetWorld != null) {
				BlockPos portalPos = new BlockPos(pos.getX(), targetWorld.getBottomY(), pos.getZ());
				if (!targetWorld.getBlockState(portalPos).isOf(SpectrumBlocks.DEEPER_DOWN_PORTAL)) {
					targetWorld.setBlockState(portalPos, SpectrumBlocks.DEEPER_DOWN_PORTAL.getDefaultState().with(FACING_UP, false));
				}
				makeRoomAround(targetWorld, portalPos, 4, 2, true, BlockTags.BASE_STONE_OVERWORLD);
				
				BlockPos targetPos = portalPos.up(2);
				entity.teleportTo(new TeleportTarget(targetWorld, Vec3d.ofCenter(targetPos), Vec3d.ZERO, entity.getYaw(), entity.getPitch(),
						TeleportTarget.SEND_TRAVEL_THROUGH_PORTAL_PACKET.then(TeleportTarget.ADD_PORTAL_CHUNK_TICKET)));
				teleportToSafePosition(targetWorld, entity, targetPos, 3);
			}
		}
	}
	
	public void makeRoomAround(World world, BlockPos blockPos, int height, int maxWidth, boolean pointingUp, TagKey<Block> tagToClear) {
		BlockState state = world.getBlockState(blockPos);
		if (state.getCollisionShape(world, blockPos).isEmpty() && state.getCollisionShape(world, blockPos.up()).isEmpty()) {
			return;
		}
		
		for (BlockPos pos : iterateVerticalCone(blockPos, height, maxWidth, pointingUp)) {
			if (world.getBlockEntity(pos) != null) {
				continue;
			}

			state = world.getBlockState(pos);
			if (state.isOf(Blocks.BEDROCK) || state.isIn(tagToClear)) {
				world.breakBlock(pos, true, null);
			}
			
		}
	}
	
	public static Iterable<BlockPos> iterateVerticalCone(BlockPos center, int height, int maxWidth, boolean pointingUp) {
		int x = center.getX();
		int y = center.getY();
		int z = center.getZ();
		
		return () -> new AbstractIterator<>() {
			int xOffset = 0;
			int yOffset = 0;
			int zOffset = 0;
			int currentMaxWidth = 0;
			
			private final BlockPos.Mutable pos = new BlockPos.Mutable();
			
			protected BlockPos computeNext() {
				if (yOffset > height) {
					return this.endOfData();
				}
				
				this.pos.set(x + xOffset, pointingUp ? y + yOffset : y - yOffset, z + zOffset);
				
				zOffset++;
				if (zOffset > currentMaxWidth) {
					zOffset = -currentMaxWidth;
					xOffset++;
					if (xOffset > currentMaxWidth) {
						xOffset = -currentMaxWidth;
						yOffset++;
						currentMaxWidth = Math.min(yOffset, maxWidth);
					}
				}
				
				return pos;
			}
		};
	}

	public void teleportToSafePosition(World world, Entity entity, BlockPos targetPos, int maxRadius) {
		for (BlockPos bp : BlockPos.iterateOutwards(targetPos, maxRadius, maxRadius, maxRadius)) {
			entity.setPosition(Vec3d.ofBottomCenter(bp));
			if (world.getBlockState(bp.down()).getCollisionShape(world, bp.down()) == VoxelShapes.fullCube()
					&& world.isSpaceEmpty(entity)
					&& entity.getY() < (double) world.getTopY()
					&& entity.getY() > (double) world.getBottomY()) {

				entity.requestTeleport(bp.getX() + 0.5, bp.getY() + 0.5, bp.getZ() + 0.5);
				return;
			}
		}

		world.removeBlock(targetPos.up(1), false);
		world.removeBlock(targetPos, false);
		world.setBlockState(targetPos.down(1), Blocks.COBBLED_DEEPSLATE.getDefaultState());
		entity.requestTeleport(targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (!state.get(DeeperDownPortalBlock.FACING_UP) || random.nextInt(8) == 0) {
			spawnVoidFogParticle(world, pos, random);
		}
	}

	private static void spawnVoidFogParticle(World world, BlockPos pos, Random random) {
		double d = (double) pos.getX() + random.nextDouble();
		double e = (double) pos.getY() + 0.3D;
		double f = (double) pos.getZ() + random.nextDouble();
		world.addParticle(SpectrumParticleTypes.VOID_FOG, d, e, f, 0.0D, 0.1D, 0.0D);
	}

}
