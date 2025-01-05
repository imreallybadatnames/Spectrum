package de.dafuqs.spectrum.entity.entity;

import net.minecraft.block.piston.*;
import net.minecraft.entity.*;
import net.minecraft.entity.data.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.listener.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class LivingMarkerEntity extends LivingEntity {
	
	public LivingMarkerEntity(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override
	public void tick() {
	
	}
	
	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
	
	}
	
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
	
	}
	
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
	
	}
	
	@Override
	protected boolean canAddPassenger(Entity passenger) {
		return false;
	}
	
	@Override
	protected boolean couldAcceptPassenger() {
		return false;
	}
	
	@Override
	public PistonBehavior getPistonBehavior() {
		return PistonBehavior.IGNORE;
	}
	
	public boolean canAvoidTraps() {
		return true;
	}
	
	@Override
	public Iterable<ItemStack> getArmorItems() {
		return new ArrayList<>();
	}
	
	@Override
	public ItemStack getEquippedStack(EquipmentSlot slot) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public void equipStack(EquipmentSlot slot, ItemStack stack) {
	
	}
	
	@Override
	public Arm getMainArm() {
		return Arm.LEFT;
	}
	
	@Override
	public Packet<ClientPlayPacketListener> createSpawnPacket(EntityTrackerEntry entityTrackerEntry) {
		throw new IllegalStateException("Living Markers should never be sent");
	}
	
	@Override
	protected void addPassenger(Entity passenger) {
		throw new IllegalStateException("Living Marker: should never addPassenger without checking couldAcceptPassenger()");
	}
	
}
