package de.dafuqs.spectrum.blocks.redstone;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.*;

import java.util.*;

public class BlockBreakerBlockEntity extends BlockEntity implements PlayerOwned {
	
	private UUID ownerUUID;
	
	public BlockBreakerBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.BLOCK_BREAKER, pos, state);
	}
	
	@Override
	public UUID getOwnerUUID() {
		return this.ownerUUID;
	}
	
	@Override
	public void setOwner(PlayerEntity playerEntity) {
		this.ownerUUID = playerEntity.getUuid();
		this.markDirty();
	}
	
	@Override
	public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		
		PlayerOwned.writeOwnerUUID(nbt, this.ownerUUID);
	}
	
	@Override
	public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		
		this.ownerUUID = PlayerOwned.readOwnerUUID(nbt);
	}
	
}
