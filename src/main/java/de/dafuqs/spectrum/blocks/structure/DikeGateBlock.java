package de.dafuqs.spectrum.blocks.structure;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.cca.azure_dike.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

import java.util.*;

public class DikeGateBlock extends TransparentBlock {

	public static final MapCodec<DikeGateBlock> CODEC = createCodec(DikeGateBlock::new);

	public DikeGateBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends DikeGateBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	@Deprecated
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (context instanceof EntityShapeContext entityShapeContext) {
			Entity entity = entityShapeContext.getEntity();
			if (entity instanceof LivingEntity livingEntity) {
				
				if (entity instanceof PlayerEntity player && player.isCreative()) {
					return VoxelShapes.empty();
				}
				
				var charges = AzureDikeProvider.getAzureDikeCharges(livingEntity);
				if (charges > 0) {
					return VoxelShapes.empty();
				}
			}
		}
		return VoxelShapes.fullCube();
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (context.isHolding(this.asItem())) {
			return VoxelShapes.fullCube();
		} else {
			return getCollisionShape(state, world, pos, context);
		}
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		punishEntityWithoutAzureDike(world, pos, player, false);
		return super.onUse(state, world, pos, player, hit);
	}
	
	@Override
	public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
		punishEntityWithoutAzureDike(world, pos, player, true);
		return super.calcBlockBreakingDelta(state, player, world, pos);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		punishEntityWithoutAzureDike(world, pos, entity, true);
		super.onEntityCollision(state, world, pos, entity);
	}
	
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		Iterator<Direction> directions = Util.copyShuffled(Direction.values(), random).iterator();
		for (int i = 0; i < 2; i++) {
			Direction direction = directions.next();
			BlockPos blockPos = pos.offset(direction);
			BlockState blockState = world.getBlockState(blockPos);
			if (!state.isOpaque() || !blockState.isSideSolidFullSquare(world, blockPos, direction.getOpposite())) {
				double d = direction.getOffsetX() == 0 ? random.nextDouble() : 0.5 + direction.getOffsetX() * 0.6;
				double e = direction.getOffsetY() == 0 ? random.nextDouble() : 0.5 + direction.getOffsetY() * 0.6;
				double f = direction.getOffsetZ() == 0 ? random.nextDouble() : 0.5 + direction.getOffsetZ() * 0.6;
				world.addParticle(SpectrumParticleTypes.AZURE_DIKE_RUNES, pos.getX() + d, pos.getY() + e, pos.getZ() + f, 0.0, 0.025, 0.0);
			}
		}
	}
	
	public void punishEntityWithoutAzureDike(BlockView world, BlockPos pos, Entity entity, boolean decreasedSounds) {
		if (world instanceof ServerWorld serverWorld && entity instanceof LivingEntity livingEntity) {
			int charges = (int) Math.ceil(AzureDikeProvider.getAzureDikeCharges(livingEntity));
			if (charges == 0) {
				entity.damage(SpectrumDamageTypes.dike(serverWorld), 1);
				PlayParticleWithExactVelocityPayload.playParticles(serverWorld, pos, SpectrumParticleTypes.AZURE_DIKE_RUNES, 10);
				if (entity instanceof ServerPlayerEntity serverPlayerEntity && (!decreasedSounds || ((ServerWorld) world).getTime() % 10 == 0)) {
					serverPlayerEntity.playSoundToPlayer(SpectrumSoundEvents.USE_FAIL, SoundCategory.PLAYERS, 0.75F, 1.0F);
				}
			}
		}
	}
	
}
