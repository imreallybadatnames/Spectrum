package de.dafuqs.spectrum.blocks.geology;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.blocks.conditional.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.mixin.accessors.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.particle.*;
import net.fabricmc.api.*;
import net.minecraft.block.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.intprovider.*;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;

public class ShimmerstoneOreBlock extends CloakedOreBlock {
	
	public static final MapCodec<ShimmerstoneOreBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			IntProvider.createValidatingCodec(0, 10).fieldOf("experience").forGetter(b -> ((ExperienceDroppingBlockAccessor) b).getExperienceDropped()),
			createSettingsCodec(),
			Identifier.CODEC.fieldOf("advancement").forGetter(CloakedOreBlock::getCloakAdvancementIdentifier),
			BlockState.CODEC.fieldOf("cloak").forGetter(b -> b.getBlockStateCloaks().get(b.getDefaultState()))
	).apply(instance, ShimmerstoneOreBlock::new));
	
	public ShimmerstoneOreBlock(IntProvider experienceDropped, Settings settings, Identifier cloakAdvancementIdentifier, BlockState cloakBlockState) {
		super(experienceDropped, settings, cloakAdvancementIdentifier, cloakBlockState);
	}
	
	@Override
	public MapCodec<? extends ShimmerstoneOreBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		var random = world.getRandom();
		if (!world.isClient() && !entity.bypassesSteppingEffects() && random.nextInt(3) == 0) {
			PlayParticleAroundBlockSidesPayload.playParticleAroundBlockSides((ServerWorld) world, 1, pos, new Vec3d(0, 0.05, 0), SpectrumParticleTypes.SHIMMERSTONE_SPARKLE, this::isVisibleTo, Direction.UP);
			if (random.nextInt(3) == 0) {
				PlayParticleAroundBlockSidesPayload.playParticleAroundBlockSides((ServerWorld) world, 1, pos, new Vec3d(0, 0.025, 0), SpectrumParticleTypes.SHIMMERSTONE_SPARKLE, this::isVisibleTo, Direction.values());
				
			}
		}
		super.onSteppedOn(world, pos, state, entity);
	}
	
	@Override
	public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (!world.isClient()) {
			var random = world.getRandom();
			if (random.nextBoolean()) {
				var amount = (int) Math.ceil(MathHelper.clamp(fallDistance / 2, 1, 10));
				PlayParticleAroundBlockSidesPayload.playParticleAroundBlockSides((ServerWorld) world, amount, pos, new Vec3d(0, 0.05 + amount / 30.0, 0), SpectrumParticleTypes.SHIMMERSTONE_SPARKLE, this::isVisibleTo, Direction.UP);
			}
		}
		super.onLandedUpon(world, state, pos, entity, fallDistance);
	}
	
	@Environment(EnvType.CLIENT)
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		super.randomDisplayTick(state, world, pos, random);
		
		if (isVisibleTo(MinecraftClient.getInstance().player)) {
			ParticleHelper.playParticleAroundBlockSides(world, SpectrumParticleTypes.SHIMMERSTONE_SPARKLE, pos, Direction.values(), 1, new Vec3d(0, 0.025, 0));
		}
	}
	
	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!world.isClient()) {
			PlayParticleAroundBlockSidesPayload.playParticleAroundBlockSides((ServerWorld) world, 3, pos, new Vec3d(0, 0.05, 0), SpectrumParticleTypes.SHIMMERSTONE_SPARKLE, serverPlayer -> isVisibleTo(serverPlayer), Direction.values());
		}
		return super.onBreak(world, pos, state, player);
	}
	
	@Override
	public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
		super.onBlockBreakStart(state, world, pos, player);
		if (!world.isClient()) {
			PlayParticleAroundBlockSidesPayload.playParticleAroundBlockSides((ServerWorld) world, 1, pos, new Vec3d(0, 0.01, 0), SpectrumParticleTypes.SHIMMERSTONE_SPARKLE, serverPlayer -> isVisibleTo(serverPlayer), Direction.values());
		}
	}
}
