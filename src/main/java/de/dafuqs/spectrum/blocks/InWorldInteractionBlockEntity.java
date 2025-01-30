package de.dafuqs.spectrum.blocks;

import de.dafuqs.spectrum.api.block.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.loot.*;
import net.minecraft.nbt.*;
import net.minecraft.network.listener.*;
import net.minecraft.network.packet.*;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.registry.*;
import net.minecraft.server.world.*;
import net.minecraft.util.collection.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public abstract class InWorldInteractionBlockEntity extends BlockEntity implements LootableInventory, ImplementedInventory {
	
	private final int inventorySize;
	protected DefaultedList<ItemStack> items;
	@Nullable protected RegistryKey<LootTable> lootTable;
	protected long lootTableSeed;
	
	public InWorldInteractionBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize) {
		super(type, pos, state);
		this.inventorySize = inventorySize;
		this.items = DefaultedList.ofSize(inventorySize, ItemStack.EMPTY);
	}
	
	// interaction methods
	public void updateInClientWorld() {
		if (world instanceof ServerWorld serverWorld)
			serverWorld.getChunkManager().markForUpdate(pos);
	}
	
	// Called when the chunk is first loaded to initialize this be
	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		NbtCompound nbtCompound = new NbtCompound();
		this.writeNbt(nbtCompound, registryLookup);
		return nbtCompound;
	}
	
	@Override
	public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		this.items = DefaultedList.ofSize(inventorySize, ItemStack.EMPTY);
		if (!this.readLootTable(nbt)) {
			Inventories.readNbt(nbt, items, registryLookup);
		}
	}
	
	@Override
	public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		if (!this.writeLootTable(nbt)) {
			Inventories.writeNbt(nbt, items, registryLookup);
		}
	}

	@Override
	public void generateLoot(@Nullable PlayerEntity player) {
		if (lootTableSeed == 0 && world != null)
			lootTableSeed = world.getRandom().nextLong();
		LootableInventory.super.generateLoot(player);
		this.markDirty();
	}
	
	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}
	
	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}
	
	@Override
	public void inventoryChanged() {
		this.markDirty();
		if (world != null && !world.isClient) {
			updateInClientWorld();
		}
	}
	
	@Override
	public @Nullable RegistryKey<LootTable> getLootTable() {
		return lootTable;
	}
	
	@Override
	public void setLootTable(@Nullable RegistryKey<LootTable> lootTable) {
		this.lootTable = lootTable;
	}
	
	@Override
	public long getLootTableSeed() {
		return lootTableSeed;
	}
	
	@Override
	public void setLootTableSeed(long lootTableSeed) {
		this.lootTableSeed = lootTableSeed;
	}
	
	@Override
	protected void readComponents(BlockEntity.ComponentsAccess components) {
		super.readComponents(components);
		ContainerLootComponent containerLootComponent = components.get(DataComponentTypes.CONTAINER_LOOT);
		if (containerLootComponent != null) {
			this.lootTable = containerLootComponent.lootTable();
			this.lootTableSeed = containerLootComponent.seed();
		}
	}
	
	@Override
	protected void addComponents(ComponentMap.Builder componentMapBuilder) {
		super.addComponents(componentMapBuilder);
		if (this.lootTable != null) {
			componentMapBuilder.add(DataComponentTypes.CONTAINER_LOOT, new ContainerLootComponent(this.lootTable, this.lootTableSeed));
		}
	}
	
}
