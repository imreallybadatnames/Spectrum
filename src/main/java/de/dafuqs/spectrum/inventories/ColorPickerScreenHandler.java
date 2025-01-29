package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.blocks.*;
import de.dafuqs.spectrum.blocks.energy.*;
import de.dafuqs.spectrum.inventories.slots.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.registry.entry.*;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public class ColorPickerScreenHandler extends ScreenHandler implements InkColorSelectedPacketReceiver {
	
	public static final int PLAYER_INVENTORY_START_X = 8;
	public static final int PLAYER_INVENTORY_START_Y = 84;
	
	protected final World world;
	public final ServerPlayerEntity player;
	private final PropertyDelegate propertyDelegate;
	protected ColorPickerBlockEntity blockEntity;
	
	@Override
	public void sendContentUpdates() {
		super.sendContentUpdates();
		
		if (this.player != null && this.blockEntity.getInkDirty()) {
			UpdateBlockEntityInkPayload.updateBlockEntityInk(blockEntity.getPos(), blockEntity.getEnergyStorage(), player);
		}
	}
	
	public ColorPickerScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new ArrayPropertyDelegate(4));
	}
	
	public ColorPickerScreenHandler(int syncId, PlayerInventory playerInventory, PropertyDelegate propertyDelegate) {
		super(SpectrumScreenHandlerTypes.COLOR_PICKER, syncId);
		
		this.player = playerInventory.player instanceof ServerPlayerEntity serverPlayerEntity ? serverPlayerEntity : null;
		this.world = playerInventory.player.getWorld();
		this.propertyDelegate = propertyDelegate;
		
		InkColor selectedColor = propertyDelegate.get(3) == -1 ? null : InkColor.ofDyeColor(DyeColor.byId(propertyDelegate.get(3)));
		
		BlockEntity blockEntity = playerInventory.player.getWorld().getBlockEntity(getBlockPos());
		if (blockEntity instanceof ColorPickerBlockEntity colorPickerBlockEntity) {
			this.blockEntity = colorPickerBlockEntity;
			this.blockEntity.setSelectedColor(selectedColor);
		} else {
			throw new IllegalArgumentException("GUI called with a position where no valid BlockEntity exists");
		}
		
		checkSize(colorPickerBlockEntity, ColorPickerBlockEntity.INVENTORY_SIZE);
		colorPickerBlockEntity.onOpen(playerInventory.player);
		
		// color picker slots
		this.addSlot(new ColorPickerInputSlot(colorPickerBlockEntity, 0, 26, 33));
		this.addSlot(new InkStorageSlot(colorPickerBlockEntity, 1, 133, 33));
		
		// player inventory
		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				this.addSlot(new Slot(playerInventory, k + j * 9 + 9, PLAYER_INVENTORY_START_X + k * 18, PLAYER_INVENTORY_START_Y + j * 18));
			}
		}
		
		// player hotbar
		for (int j = 0; j < 9; ++j) {
			this.addSlot(new Slot(playerInventory, j, PLAYER_INVENTORY_START_X + j * 18, PLAYER_INVENTORY_START_Y + 58));
		}
		
		if (this.player != null) {
			UpdateBlockEntityInkPayload.updateBlockEntityInk(blockEntity.getPos(), this.blockEntity.getEnergyStorage(), player);
		}
		
		addProperties(propertyDelegate);
	}
	
	@Override
	public ColorPickerBlockEntity getBlockEntity() {
		return this.blockEntity;
	}
	
	@Override
	public boolean canUse(PlayerEntity player) {
		return this.blockEntity.canPlayerUse(player);
	}
	
	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		this.blockEntity.onClose(player);
	}
	
	@Override
	public ItemStack quickMove(PlayerEntity player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();
			if (index < ColorPickerBlockEntity.INVENTORY_SIZE) {
				if (!this.insertItem(itemStack2, ColorPickerBlockEntity.INVENTORY_SIZE, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(itemStack2, 0, ColorPickerBlockEntity.INVENTORY_SIZE, false)) {
				return ItemStack.EMPTY;
			}
			
			if (itemStack2.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}
		
		return itemStack;
	}
	
	public BlockPos getBlockPos() {
		return BlockPosDelegate.getBlockPos(propertyDelegate);
	}
	
	@Override
	public void onInkColorSelectedPacket(@Nullable RegistryEntry<InkColor> inkColor) {
		this.blockEntity.setSelectedColor(inkColor == null ? null : inkColor.value());
	}
	
}
