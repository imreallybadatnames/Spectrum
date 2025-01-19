package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.api.interaction.*;
import de.dafuqs.spectrum.blocks.bottomless_bundle.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.collection.*;

public class SpectrumItemProviders {
	
	public static void register() {
		ItemProviderRegistry.register(Items.SHULKER_BOX, new ItemProvider() {
			@Override
			public int provideItems(PlayerEntity player, ItemStack stack, Item requestedItem, int amount) {
				NbtCompound nbt = stack.getSubNbt("BlockEntityTag");
				if (nbt == null) {
					return 0;
				}
				
				DefaultedList<ItemStack> inventory = DefaultedList.ofSize(ShulkerBoxBlockEntity.INVENTORY_SIZE, ItemStack.EMPTY);
				Inventories.readNbt(nbt, inventory);
				
				int removedCount = 0;
				for (ItemStack s : inventory) {
					if (s.isOf(requestedItem)) {
						int amountToRemove = Math.min(s.getCount(), amount - removedCount);
						s.decrement(amountToRemove);
						removedCount += amountToRemove;
						if (removedCount == amount) {
							break;
						}
					}
				}
				Inventories.writeNbt(nbt, inventory);
				
				return removedCount;
			}
			
			@Override
			public int getItemCount(PlayerEntity player, ItemStack stack, Item requestedItem) {
				NbtCompound nbt = stack.getSubNbt("BlockEntityTag");
				if (nbt == null) {
					return 0;
				}
				
				DefaultedList<ItemStack> inventory = DefaultedList.ofSize(ShulkerBoxBlockEntity.INVENTORY_SIZE, ItemStack.EMPTY);
				Inventories.readNbt(nbt, inventory);
				
				int count = 0;
				for (ItemStack s : inventory) {
					if (s.isOf(requestedItem)) {
						count += s.getCount();
					}
				}
				
				return count;
			}
		});
		
		ItemProviderRegistry.register(Items.BUNDLE, new ItemProvider() {
			@Override
			public int provideItems(PlayerEntity player, ItemStack stack, Item requestedItem, int amount) {
				NbtCompound nbtCompound = stack.getNbt();
				if (nbtCompound == null) {
					return 0;
				}
				
				int removedCount = 0;
				NbtList nbtList = nbtCompound.getList("Items", NbtElement.COMPOUND_TYPE);
				for (NbtElement e : nbtList) {
					ItemStack s = ItemStack.fromNbt((NbtCompound) e);
					if (s.isOf(requestedItem)) {
						int amountToRemove = Math.min(s.getCount(), amount - removedCount);
						((NbtCompound) e).putByte("Count", (byte) (s.getCount() - amountToRemove));
						removedCount += amountToRemove;
						if (removedCount == amount) {
							break;
						}
					}
				}
				
				return removedCount;
			}
			
			@Override
			public int getItemCount(PlayerEntity player, ItemStack stack, Item requestedItem) {
				NbtCompound nbtCompound = stack.getNbt();
				if (nbtCompound == null) {
					return 0;
				}
				
				int total = 0;
				NbtList nbtList = nbtCompound.getList("Items", NbtElement.COMPOUND_TYPE);
				for (NbtElement e : nbtList) {
					ItemStack s = ItemStack.fromNbt((NbtCompound) e);
					if (s.isOf(requestedItem)) {
						total += s.getCount();
					}
				}
				
				return total;
			}
		});
		
		ItemProviderRegistry.register(SpectrumBlocks.BOTTOMLESS_BUNDLE, new ItemProvider() {
			@Override
			public int provideItems(PlayerEntity player, ItemStack stack, Item requestedItem, int amount) {
				var builder = BottomlessBundleItem.BottomlessStack.Builder.of(player.getWorld(), stack);
				var removed = builder.remove(amount);
				if (removed == null || !removed.isOf(requestedItem))
					return 0;
				stack.set(SpectrumDataComponentTypes.BOTTOMLESS_STACK, builder.build());
				return removed.getCount();
			}
			
			@Override
			public int getItemCount(PlayerEntity player, ItemStack stack, Item requestedItem) {
				var bottomlessStack = stack.get(SpectrumDataComponentTypes.BOTTOMLESS_STACK);
				if (bottomlessStack == null || !bottomlessStack.template().isOf(requestedItem))
					return 0;
				return bottomlessStack.count();
			}
		});
		
		// BAG_OF_HOLDING only works server side
		// the client does not know about the content of the ender chest, unless opened
		ItemProviderRegistry.register(SpectrumItems.BAG_OF_HOLDING, new ItemProvider() {
			@Override
			public int provideItems(PlayerEntity player, ItemStack stack, Item requestedItem, int amount) {
				if (player == null) {
					return 0;
				}
				
				Inventory inv = player.getEnderChestInventory();
				int removedCount = 0;
				for (int i = 0; i < inv.size(); i++) {
					ItemStack s = inv.getStack(i);
					if (s.isOf(requestedItem)) {
						int amountToRemove = Math.min(s.getCount(), amount - removedCount);
						s.decrement(amountToRemove);
						removedCount += amountToRemove;
						if (removedCount == amount) {
							break;
						}
					}
				}
				
				return removedCount;
			}
			
			@Override
			public int getItemCount(PlayerEntity player, ItemStack stack, Item requestedItem) {
				if (player == null) {
					return 0;
				}
				return player.getEnderChestInventory().count(requestedItem);
			}
		});
	}
	
}
