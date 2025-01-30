package de.dafuqs.spectrum.entity.entity;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.mixin.accessors.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.data.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.sound.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public abstract class BidentBaseEntity extends TridentEntity {
	
	protected static final TrackedData<ItemStack> STACK = DataTracker.registerData(BidentBaseEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
	
	public BidentBaseEntity(EntityType<? extends TridentEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(STACK, Items.AIR.getDefaultStack());
	}
	
	@Override
	public void setStack(ItemStack stack) {
		setTrackedStack(stack.copy());
		super.setStack(stack);
		this.dataTracker.set(TridentEntityAccessor.spectrum$getLoyalty(), (byte) SpectrumEnchantmentHelper.getLevel(getWorld().getRegistryManager(), Enchantments.LOYALTY, stack));
		this.dataTracker.set(TridentEntityAccessor.spectrum$getEnchanted(), stack.hasGlint());
	}
	
	@Override
	protected SoundEvent getHitSound() {
		return SpectrumSoundEvents.BIDENT_HIT_GROUND;
	}
	
	public ItemStack getTrackedStack() {
		return this.dataTracker.get(STACK);
	}
	
	public void setTrackedStack(ItemStack stack) {
		dataTracker.set(STACK, stack);
	}
	
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(STACK, CodecHelper.fromNbt(ItemStack.CODEC, nbt.get("Trident"), ItemStack.EMPTY));
	}
	
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		CodecHelper.writeNbt(nbt, "Trident", ItemStack.CODEC, this.dataTracker.get(STACK));
	}
	
	@Override
	public Box calculateBoundingBox() {
		return super.calculateBoundingBox();
	}
}
