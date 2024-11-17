package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.blocks.chests.*;
import de.dafuqs.spectrum.inventories.slots.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.*;
import net.minecraft.world.*;

public class FabricationChestScreenHandler extends ScreenHandler {
	
	protected final World world;
	private final Inventory inventory;
	
	public FabricationChestScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(SpectrumScreenHandlerTypes.FABRICATION_CHEST, syncId, playerInventory);
	}
	
	protected FabricationChestScreenHandler(ScreenHandlerType<?> type, int i, PlayerInventory playerInventory) {
		this(type, i, playerInventory, new SimpleInventory(FabricationChestBlockEntity.INVENTORY_SIZE));
	}
	
	public FabricationChestScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		this(SpectrumScreenHandlerTypes.FABRICATION_CHEST, syncId, playerInventory, inventory);
	}
	
	protected FabricationChestScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {
		super(type, syncId);
		this.inventory = inventory;
		this.world = playerInventory.player.getWorld();
		
		checkSize(inventory, FabricationChestBlockEntity.INVENTORY_SIZE);
		inventory.onOpen(playerInventory.player);
		
		// chest inventory
		int l;
		for (l = 0; l < 3; ++l) {
			for (int k = 0; k < 9; ++k) {
				this.addSlot(new Slot(inventory, l * 9 + k, 8 + k * 18, 67 + l * 18));
			}
		}
		
		// crafting tablet slots
		for (int j = 0; j < 4; j++) {
			int slotId = FabricationChestBlockEntity.RECIPE_SLOTS[j];
			this.addSlot(new StackFilterSlot(inventory, slotId, 26 + j * 36, 18, SpectrumItems.CRAFTING_TABLET));
		}
		
		// crafting result slots
		for (int j = 0; j < 4; j++) {
			int slotId = FabricationChestBlockEntity.RESULT_SLOTS[j];
			this.addSlot(new ExtractOnlySlot(inventory, slotId, 26 + j * 36, 42));
		}
		
		// player inventory
		for (l = 0; l < 3; ++l) {
			for (int k = 0; k < 9; ++k) {
				this.addSlot(new Slot(playerInventory, k + l * 9 + 9, 8 + k * 18, 138 + l * 18));
			}
		}
		
		// player hotbar
		for (l = 0; l < 9; ++l) {
			this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 196));
		}
	}
	
	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}
	
	@Override
	public ItemStack quickMove(PlayerEntity player, int index) {
		ItemStack clickedStackCopy = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		
		if (slot.hasStack()) {
			ItemStack clickedStack = slot.getStack();
			clickedStackCopy = clickedStack.copy();
			
			if (index < FabricationChestBlockEntity.INVENTORY_SIZE) {
				// => player inv
				if (!this.insertItem(clickedStack, 35, 71, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index > FabricationChestBlockEntity.INVENTORY_SIZE && clickedStackCopy.isOf(SpectrumItems.CRAFTING_TABLET)) {
				if (!this.insertItem(clickedStack, FabricationChestBlockEntity.RECIPE_SLOTS[0], FabricationChestBlockEntity.RECIPE_SLOTS[FabricationChestBlockEntity.RECIPE_SLOTS.length - 1] + 1, false)) {
					return ItemStack.EMPTY;
				}
			}
			
			// chest => inventory
			if (!this.insertItem(clickedStack, 0, FabricationChestBlockEntity.CHEST_SLOTS.length - 1, false)) {
				return ItemStack.EMPTY;
			}
			
			if (clickedStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
			
			if (clickedStack.getCount() == clickedStackCopy.getCount()) {
				return ItemStack.EMPTY;
			}
			
			slot.onTakeItem(player, clickedStack);
		}
		
		
		return clickedStackCopy;
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		this.inventory.onClose(player);
	}
	
}
