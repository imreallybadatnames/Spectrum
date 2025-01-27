package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.blocks.*;
import de.dafuqs.spectrum.blocks.pedestal.*;
import de.dafuqs.spectrum.inventories.slots.*;
import de.dafuqs.spectrum.recipe.pedestal.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.*;
import net.minecraft.recipe.input.*;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public class PedestalScreenHandler extends AbstractRecipeScreenHandler<RecipeInput, Recipe<RecipeInput>> {
	
	protected final World world;
	private final Inventory inventory;
	private final PedestalBlockEntity blockEntity;
	private final PropertyDelegate propertyDelegate;
	private final RecipeBookCategory category;
	
	public PedestalScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(PedestalBlockEntity.INVENTORY_SIZE), new ArrayPropertyDelegate(PedestalBlockEntity.PROPERTY_DELEGATE_SIZE));
	}
	
	public PedestalScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
		this(SpectrumScreenHandlerTypes.PEDESTAL, RecipeBookCategory.CRAFTING, syncId, playerInventory, inventory, propertyDelegate);
	}
	
	protected PedestalScreenHandler(ScreenHandlerType<?> type, RecipeBookCategory recipeBookCategory, int i, @NotNull PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
		super(type, i);
		this.inventory = inventory;
		this.category = recipeBookCategory;
		this.propertyDelegate = propertyDelegate;
		this.world = playerInventory.player.getWorld();
		this.blockEntity = world.getBlockEntity(getBlockPos()) instanceof PedestalBlockEntity p ? p : null;
		
		checkSize(inventory, PedestalBlockEntity.INVENTORY_SIZE);
		checkDataCount(propertyDelegate, PedestalBlockEntity.PROPERTY_DELEGATE_SIZE);
		inventory.onOpen(playerInventory.player);
		
		// crafting slots
		for (int m = 0; m < 3; ++m) {
			for (int n = 0; n < 3; ++n) {
				addSlot(new Slot(inventory, n + m * 3, 30 + n * 18, 19 + m * 18));
			}
		}
		
		// gemstone powder slots
		switch (getPedestalRecipeTier()) {
			case BASIC, SIMPLE -> {
				this.addSlot(new StackFilterSlot(inventory, 9, 44 + 18, 77, SpectrumItems.TOPAZ_POWDER));
				this.addSlot(new StackFilterSlot(inventory, 10, 44 + 2 * 18, 77, SpectrumItems.AMETHYST_POWDER));
				this.addSlot(new StackFilterSlot(inventory, 11, 44 + 3 * 18, 77, SpectrumItems.CITRINE_POWDER));
				this.addSlot(new DisabledSlot(inventory, 12, -2000, 77));
				this.addSlot(new DisabledSlot(inventory, 13, -2000, 77));
			}
			case ADVANCED -> {
				this.addSlot(new StackFilterSlot(inventory, 9, 35 + 18, 77, SpectrumItems.TOPAZ_POWDER));
				this.addSlot(new StackFilterSlot(inventory, 10, 35 + 2 * 18, 77, SpectrumItems.AMETHYST_POWDER));
				this.addSlot(new StackFilterSlot(inventory, 11, 35 + 3 * 18, 77, SpectrumItems.CITRINE_POWDER));
				this.addSlot(new StackFilterSlot(inventory, 12, 35 + 4 * 18, 77, SpectrumItems.ONYX_POWDER));
				this.addSlot(new DisabledSlot(inventory, 13, -2000, 77));
			}
			case COMPLEX -> {
				this.addSlot(new StackFilterSlot(inventory, 9, 44, 77, SpectrumItems.TOPAZ_POWDER));
				this.addSlot(new StackFilterSlot(inventory, 10, 44 + 18, 77, SpectrumItems.AMETHYST_POWDER));
				this.addSlot(new StackFilterSlot(inventory, 11, 44 + 2 * 18, 77, SpectrumItems.CITRINE_POWDER));
				this.addSlot(new StackFilterSlot(inventory, 12, 44 + 3 * 18, 77, SpectrumItems.ONYX_POWDER));
				this.addSlot(new StackFilterSlot(inventory, 13, 44 + 4 * 18, 77, SpectrumItems.MOONSTONE_POWDER));
			}
		}
		
		// crafting tablet slot
		this.addSlot(new StackFilterSlot(inventory, PedestalBlockEntity.CRAFTING_TABLET_SLOT_ID, 93, 19, SpectrumItems.CRAFTING_TABLET));
		
		// preview slot
		this.addSlot(new PedestalPreviewSlot(inventory, 15, 127, 37));
		
		// player inventory
		int l;
		for (l = 0; l < 3; ++l) {
			for (int k = 0; k < 9; ++k) {
				this.addSlot(new Slot(playerInventory, k + l * 9 + 9, 8 + k * 18, 112 + l * 18));
			}
		}
		
		// player hotbar
		for (l = 0; l < 9; ++l) {
			this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 170));
		}
		
		this.addProperties(propertyDelegate);
	}
	
	@Override
	public void populateRecipeFinder(RecipeMatcher recipeMatcher) {
		if (this.inventory != null) {
			((RecipeInputProvider) this.inventory).provideRecipeInputs(recipeMatcher);
		}
	}
	
	@Override
	public void clearCraftingSlots() {
		for (int i = 0; i < 9; i++) {
			this.getSlot(i).setStack(ItemStack.EMPTY);
		}
	}
	
	@Override
	public boolean matches(RecipeEntry<Recipe<RecipeInput>> recipe) {
		return blockEntity != null && recipe.value().matches(blockEntity.recipeInput, world);
	}
	
	@Override
	public int getCraftingResultSlotIndex() {
		return 16;
	}
	
	@Override
	public int getCraftingWidth() {
		return 3;
	}
	
	@Override
	public int getCraftingHeight() {
		return 3;
	}
	
	@Override
	public int getCraftingSlotCount() {
		return 9;
	}
	
	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}
	
	@Environment(EnvType.CLIENT)
	public int getCraftingProgress() {
		int craftingTime = getCraftingTime();
		int craftingTimeTotal = getCraftingTimeTotal();
		return craftingTimeTotal != 0 && craftingTime != 0 ? craftingTime * 24 / craftingTimeTotal : 0;
	}
	
	public boolean isCrafting() {
		return getCraftingTime() > 0;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public RecipeBookCategory getCategory() {
		return this.category;
	}
	
	@Override
	public boolean canInsertIntoSlot(int index) {
		return index != 1;
	}
	
	// Shift-Clicking
	// 0-8: crafting slots
	// 9-13: powder slots
	// 14: crafting tablet
	// 15: preview slot
	// 16: hidden output slot
	@Override
	public ItemStack quickMove(PlayerEntity player, int index) {
		ItemStack clickedStackCopy = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		
		BlockEntity blockEntity = world.getBlockEntity(getBlockPos());
		if (blockEntity instanceof PedestalBlockEntity pedestalBlockEntity) {
			pedestalBlockEntity.setInventoryChanged();
		}
		
		if (slot.hasStack()) {
			ItemStack clickedStack = slot.getStack();
			clickedStackCopy = clickedStack.copy();
			
			if (index < 15) {
				// pedestal => player inv
				if (!this.insertItem(clickedStack, 16, 51, false)) {
					return ItemStack.EMPTY;
				}
			} else if (clickedStackCopy.isOf(SpectrumItems.TOPAZ_POWDER)) {
				if (!this.insertItem(clickedStack, 9, 10, false)) {
					return ItemStack.EMPTY;
				}
			} else if (clickedStackCopy.isOf(SpectrumItems.AMETHYST_POWDER)) {
				if (!this.insertItem(clickedStack, 10, 11, false)) {
					return ItemStack.EMPTY;
				}
			} else if (clickedStackCopy.isOf(SpectrumItems.CITRINE_POWDER)) {
				if (!this.insertItem(clickedStack, 11, 12, false)) {
					return ItemStack.EMPTY;
				}
			} else if (clickedStackCopy.isOf(SpectrumItems.ONYX_POWDER)) {
				if (!this.insertItem(clickedStack, 12, 13, false)) {
					return ItemStack.EMPTY;
				}
			} else if (clickedStackCopy.isOf(SpectrumItems.MOONSTONE_POWDER)) {
				if (!this.insertItem(clickedStack, 13, 14, false)) {
					return ItemStack.EMPTY;
				}
			} else if (clickedStackCopy.isOf(SpectrumItems.CRAFTING_TABLET)) {
				if (!this.insertItem(clickedStack, PedestalBlockEntity.CRAFTING_TABLET_SLOT_ID, PedestalBlockEntity.CRAFTING_TABLET_SLOT_ID + 1, false)) {
					return ItemStack.EMPTY;
				}
			}
			
			// crafting grid
			if (!this.insertItem(clickedStack, 0, 9, false)) {
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
	
	public BlockPos getBlockPos() {
		return BlockPosDelegate.getBlockPos(propertyDelegate);
	}
	
	public int getCraftingTime() {
		return propertyDelegate.get(3);
	}
	
	public int getCraftingTimeTotal() {
		return propertyDelegate.get(4);
	}
	
	public PedestalRecipeTier getPedestalRecipeTier() {
		return PedestalRecipeTier.values()[propertyDelegate.get(5)];
	}
	
	public PedestalRecipeTier getMaxPedestalRecipeTier() {
		return PedestalRecipeTier.values()[propertyDelegate.get(6)];
	}
	
	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		this.inventory.onClose(player);
	}
	
}
