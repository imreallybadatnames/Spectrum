package de.dafuqs.spectrum.blocks.ender;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.loot.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.*;
import net.minecraft.screen.*;
import net.minecraft.text.*;
import net.minecraft.util.collection.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.*;

import java.util.*;

public class EnderDropperBlockEntity extends DispenserBlockEntity implements PlayerOwnedWithName {
	
	private UUID ownerUUID;
	private String ownerName;
	
	public EnderDropperBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(SpectrumBlockEntities.ENDER_DROPPER, blockPos, blockState);
	}
	
	@Override
	protected Text getContainerName() {
		if (hasOwner()) {
			return Text.translatable("block.spectrum.ender_dropper.owner", this.ownerName);
		} else {
			return Text.translatable("block.spectrum.ender_dropper");
		}
	}
	
	@Override
	public int chooseNonEmptySlot(Random random) {
		return getInventory().map(inventory -> {
			int selectedIndex = -1;
			int chance = 1;
			
			for (int i = 0; i < inventory.size(); i++) {
				if (!(inventory.getStack(i)).isEmpty() && random.nextInt(chance++) == 0) {
					selectedIndex = i;
				}
			}
			
			return selectedIndex;
		}).orElse(-1);
	}
	
	@Override
	public ItemStack addToFirstFreeSlot(ItemStack stack) {
		getInventory().ifPresent(inventory -> {
			int i = this.getMaxCount(stack);
			
			for (int j = 0; j < inventory.size(); j++) {
				ItemStack itemStack = inventory.getStack(j);
				if (itemStack.isEmpty() || ItemStack.areItemsAndComponentsEqual(stack, itemStack)) {
					int k = Math.min(stack.getCount(), i - itemStack.getCount());
					if (k > 0) {
						if (itemStack.isEmpty()) {
							this.setStack(j, stack.split(k));
						} else {
							stack.decrement(k);
							itemStack.increment(k);
						}
					}
					
					if (stack.isEmpty()) {
						break;
					}
				}
			}
		});
		return stack;
	}
	
	@Override
	public ItemStack getStack(int slot) {
		return getInventory().map(i -> i.getStack(slot)).orElse(ItemStack.EMPTY);
	}
	
	@Override
	public void setStack(int slot, ItemStack itemStack) {
		getInventory().ifPresent(i -> i.setStack(slot, itemStack));
	}
	
	@Override
	public ItemStack removeStack(int slot) {
		return getInventory().map(i -> i.removeStack(slot)).orElse(ItemStack.EMPTY);
	}
	
	@Override
	public ItemStack removeStack(int slot, int amount) {
		return getInventory().map(i -> i.removeStack(slot, amount)).orElse(ItemStack.EMPTY);
	}
	
	@Override
	public boolean isEmpty() {
		return getInventory().map(SimpleInventory::isEmpty).orElse(true);
	}
	
	@Override
	public boolean canTransferTo(Inventory hopperInventory, int slot, ItemStack stack) {
		return false;
	}
	
	private Optional<EnderChestInventory> getInventory() {
		var player = getOwnerIfOnline();
		if (player != null)
			return Optional.of(player.getEnderChestInventory());
		return Optional.empty();
	}
	
	@Override
	public UUID getOwnerUUID() {
		return this.ownerUUID;
	}
	
	@Override
	public String getOwnerName() {
		return this.ownerName;
	}
	
	@Override
	public void setOwner(PlayerEntity playerEntity) {
		this.ownerUUID = playerEntity.getUuid();
		this.ownerName = playerEntity.getName().getString();
		markDirty();
	}
	
	@Override
	public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		this.ownerUUID = PlayerOwned.readOwnerUUID(nbt);
		this.ownerName = PlayerOwned.readOwnerName(nbt);
	}
	
	@Override
	public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		PlayerOwned.writeOwnerUUID(nbt, this.ownerUUID);
		PlayerOwned.writeOwnerName(nbt, this.ownerName);
	}
	
	@Override
	protected DefaultedList<ItemStack> getHeldStacks() {
		return getInventory().map(SimpleInventory::getHeldStacks).orElse(DefaultedList.of());
	}
	
	@Override
	protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
		getInventory().ifPresent(inv -> {
			for (int i = 0; i < inventory.size(); i++)
				inv.setStack(i, inventory.get(i));
		});
	}
	
	@Override
	public int size() {
		return getInventory().map(SimpleInventory::size).orElse(0);
	}
	
	@Nullable
	public RegistryKey<LootTable> getLootTable() {
		return null;
	}
	
	@Override @Nullable
	public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return null;
	}
	
	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return null;
	}
	
}
