package de.dafuqs.spectrum.blocks.decoration;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.entity.entity.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

public class CushionBlock extends Block {

    public static final MapCodec<CushionBlock> CODEC = createCodec(CushionBlock::new);

    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 7, 16);
    public static final double SITTING_OFFSET = 5 / 16.0;

    public CushionBlock(Settings settings) {
        super(settings);
    }

	@Override
	public MapCodec<? extends CushionBlock> getCodec() {
		return CODEC;
	}

    public void onEntityLand(BlockView world, Entity entity) {
        if (entity.bypassesLandingEffects()) {
            super.onEntityLand(world, entity);
        } else {
            this.bounce(entity);
        }
    }

    private void bounce(Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < 0.0) {
            entity.setVelocity(vec3d.x, -vec3d.y, vec3d.z);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        var seat = getOrCreateSeatEntity(world, pos, state);

        if (seat.getFirstPassenger() == null) {
            player.startRiding(seat, true);
            return ActionResult.success(world.isClient());
        }

        return super.onUse(state, world, pos, player, hit);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);

        var seat = getOrCreateSeatEntity(world, pos, state);
        seat.remove(Entity.RemovalReason.DISCARDED);
        return state;
    }
	
	@Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);

        if (!moved && !state.isOf(newState.getBlock())) {
            var seat = getOrCreateSeatEntity(world, pos, newState);
            seat.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    public SeatEntity getOrCreateSeatEntity(World world, BlockPos pos, BlockState state) {
        var seats = world.getEntitiesByClass(SeatEntity.class, new Box(pos), seatEntity -> true);
        SeatEntity seat;

        if (seats.isEmpty()) {
            seat = new SeatEntity(world, SITTING_OFFSET);
            var seatPos = Vec3d.of(pos).add(0.5, SITTING_OFFSET, 0.5);
            seat.setPosition(seatPos);
            seat.setCushion(state);
            world.spawnEntity(seat);
        }
        else {
            seat = seats.getFirst();
        }

        return seat;
    }
}
