package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.blocks.*;
import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.screen.*;
import net.minecraft.util.math.*;

public class ParticleSpawnerScreenHandler extends ScreenHandler {
	
	protected final PlayerEntity player;
	private final PropertyDelegate propertyDelegate;
	protected ParticleSpawnerBlockEntity particleSpawnerBlockEntity;
	
	public ParticleSpawnerScreenHandler(int syncId, PlayerInventory inventory) {
		this(syncId, inventory, new ArrayPropertyDelegate(3));
	}
	
	public ParticleSpawnerScreenHandler(int syncId, PlayerInventory playerInventory, PropertyDelegate propertyDelegate) {
		super(SpectrumScreenHandlerTypes.PARTICLE_SPAWNER, syncId);
		
		this.player = playerInventory.player;
		this.propertyDelegate = propertyDelegate;
		this.particleSpawnerBlockEntity = player.getWorld().getBlockEntity(getBlockPos(), SpectrumBlockEntities.PARTICLE_SPAWNER).orElse(null);
		
		addProperties(propertyDelegate);
	}
	
	public ParticleSpawnerBlockEntity getBlockEntity() {
		return this.particleSpawnerBlockEntity;
	}
	
	@Override
	public ItemStack quickMove(PlayerEntity player, int index) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean canUse(PlayerEntity player) {
		return this.particleSpawnerBlockEntity != null && !this.particleSpawnerBlockEntity.isRemoved();
	}
	
	public BlockPos getBlockPos() {
		return BlockPosDelegate.getBlockPos(propertyDelegate);
	}
	
}
