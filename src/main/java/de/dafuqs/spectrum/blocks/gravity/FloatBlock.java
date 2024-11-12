package de.dafuqs.spectrum.blocks.gravity;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.entity.entity.*;
import net.minecraft.block.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;

public class FloatBlock extends FallingBlock {
	
	private final float gravityMod;
	
	public FloatBlock(Settings settings, float gravityMod) {
		super(settings);
		this.gravityMod = gravityMod;
	}

	@Override
	public MapCodec<? extends FloatBlock> getCodec() {
		//TODO: Make the codec
		return null;
	}
	
	public float getGravityMod() {
		return gravityMod;
	}
	
	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos blockPos, BlockState oldState, boolean notify) {
		world.scheduleBlockTick(blockPos, this, this.getFallDelay());
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState facingState, WorldAccess world, BlockPos blockPos, BlockPos facingPos) {
		world.scheduleBlockTick(blockPos, this, this.getFallDelay());
		return super.getStateForNeighborUpdate(state, direction, facingState, world, blockPos, facingPos);
	}
	
	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.checkForLaunch(world, pos);
	}
	
	private void checkForLaunch(World world, BlockPos pos) {
		if (!world.isClient) {
			if (gravityMod == 0) {
				launch(world, pos);
				return;
			}

			BlockPos collisionBlockPos;
			if (gravityMod > 0) {
				collisionBlockPos = pos.up();
			} else {
				collisionBlockPos = pos.down();
			}
			
			if (world.isAir(collisionBlockPos) || canFallThrough(world.getBlockState(collisionBlockPos))) {
				launch(world, pos);
			}
		}
	}

	private static void launch(World world, BlockPos pos) {
		FloatBlockEntity blockEntity = new FloatBlockEntity(world, pos, world.getBlockState(pos));
		world.spawnEntity(blockEntity);
	}

}
