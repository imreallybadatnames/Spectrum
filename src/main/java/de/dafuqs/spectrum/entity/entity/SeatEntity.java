package de.dafuqs.spectrum.entity.entity;

import de.dafuqs.spectrum.entity.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.data.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SeatEntity extends Entity {

    private static final TrackedData<Integer> EMPTY_TICKS = DataTracker.registerData(SeatEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Optional<BlockState>> CUSHION = DataTracker.registerData(SeatEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_STATE);
    private double offset = 0;

    public SeatEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public SeatEntity(World world, double offset) {
        super(SpectrumEntityTypes.SEAT, world);
        this.offset = offset;
    }

    @Override
    public void tick() {
        super.tick();

        var block = this.getWorld().getBlockState(getBlockPos()).getBlock();
        var cushion = getCushion();

        if (cushion.isEmpty()) {
            setRemoved(RemovalReason.DISCARDED);
            return;
        }

        var state = cushion.get();

        if (!state.isOf(block)) {
            var iter = BlockPos.iterateOutwards(getBlockPos(), 1, 1, 1);
            var fail = true;

            for (BlockPos pos : iter) {
                var check = this.getWorld().getBlockState(pos).getBlock();
                if (state.isOf(check)) {
                    updatePosition(pos.getX() + 0.5, pos.getY() + offset, pos.getZ() + 0.5);
                    fail = false;
                    break;
                }
            }

            if (fail)
                incrementEmptyTicks();
        }

        if (getFirstPassenger() == null)
            incrementEmptyTicks();
        else if (state.isOf(block)){
            setEmptyTicks(0);
        }

        if (getEmptyTicks() > 10) {
            setRemoved(RemovalReason.DISCARDED);
        }
    }

    public Optional<BlockState> getCushion() {
        return dataTracker.get(CUSHION);
    }

    public void setCushion(@NotNull BlockState state) {
        dataTracker.set(CUSHION, Optional.of(state));
    }

    public void setEmptyTicks(int ticks) {
        dataTracker.set(EMPTY_TICKS, ticks);
    }

    public int getEmptyTicks() {
        return dataTracker.get(EMPTY_TICKS);
    }

    public void incrementEmptyTicks() {
        setEmptyTicks(getEmptyTicks() + 1);
    }
    
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(EMPTY_TICKS, 0);
        builder.add(CUSHION, Optional.empty());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        setEmptyTicks(nbt.getInt("emptyTicks"));
	
		var state = NbtHelper.toBlockState(this.getWorld().createCommandRegistryWrapper(RegistryKeys.BLOCK), nbt.getCompound("BlockState"));
        dataTracker.set(CUSHION, Optional.ofNullable(state.isAir() ? null : state));

        offset = nbt.getDouble("offset");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("emptyTicks", getEmptyTicks());
        nbt.put("BlockState", NbtHelper.fromBlockState(dataTracker.get(CUSHION).orElse(Blocks.AIR.getDefaultState())));
        nbt.putDouble("offset", offset);
    }
	
    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public void pushAwayFrom(Entity entity) {}

    @Override
    public void move(MovementType movementType, Vec3d movement) {
        if (movementType != MovementType.PISTON)
            return;

        super.move(movementType, movement);
    }

    @Nullable
    private Vec3d locateSafeDismountingPos(Vec3d offset, LivingEntity passenger) {
        double x = this.getX() + offset.x;
        double y = this.getBoundingBox().minY + 0.5;
        double z = this.getZ() + offset.z;
        BlockPos.Mutable testPos = new BlockPos.Mutable();
		
		for (EntityPose pose : passenger.getPoses()) {
			testPos.set(x, y, z);
			double maxHeight = this.getBoundingBox().maxY + 0.75;
			
			while (true) {
				double height = this.getWorld().getDismountHeight(testPos);
				if ((double) testPos.getY() + height > maxHeight) {
					break;
				}
				
				if (Dismounting.canDismountInBlock(height)) {
					Box boundingBox = passenger.getBoundingBox(pose);
					Vec3d pos = new Vec3d(x, (double) testPos.getY() + height, z);
					if (Dismounting.canPlaceEntityAt(this.getWorld(), passenger, boundingBox.offset(pos))) {
						passenger.setPose(pose);
						return pos;
					}
				}
				
				testPos.move(Direction.UP);
				if (testPos.getY() >= maxHeight) {
					break;
				}
			}
		}

        return null;
    }

    @Override
    public void requestTeleport(double destX, double destY, double destZ) {}

    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Vec3d vec3d = getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), this.getYaw() + (passenger.getMainArm() == Arm.RIGHT ? 90.0F : -90.0F));
        Vec3d vec3d2 = this.locateSafeDismountingPos(vec3d, passenger);
        if (vec3d2 != null) {
            return vec3d2;
        } else {
            Vec3d vec3d3 = getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), this.getYaw() + (passenger.getMainArm() == Arm.LEFT ? 90.0F : -90.0F));
            Vec3d vec3d4 = this.locateSafeDismountingPos(vec3d3, passenger);
            return vec3d4 != null ? vec3d4 : this.getPos();
        }
    }
}
