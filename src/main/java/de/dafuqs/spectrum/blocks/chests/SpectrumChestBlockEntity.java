package de.dafuqs.spectrum.blocks.chests;

import de.dafuqs.spectrum.inventories.*;
import net.fabricmc.api.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.*;
import net.minecraft.screen.*;
import net.minecraft.sound.*;
import net.minecraft.util.collection.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

@EnvironmentInterfaces({@EnvironmentInterface(
		value = EnvType.CLIENT,
		itf = LidOpenable.class
)})
public abstract class SpectrumChestBlockEntity extends LootableContainerBlockEntity implements LidOpenable {
	
	public final ViewerCountManager stateManager;
	protected final ChestLidAnimator lidAnimator;
	protected DefaultedList<ItemStack> inventory;
	
	protected SpectrumChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
		this.inventory = DefaultedList.ofSize(size(), ItemStack.EMPTY);
		this.lidAnimator = new ChestLidAnimator();
		
		this.stateManager = new ViewerCountManager() {
			@Override
			protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
				playSound(world, pos, getOpenSound());
				onOpen();
			}
			
			@Override
			protected void onContainerClose(World world, BlockPos pos, BlockState state) {
				playSound(world, pos, getCloseSound());
				onClose();
			}
			
			@Override
			protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
				onInvOpenOrClose(world, pos, state, oldViewerCount, newViewerCount);
			}
			
			@Override
			protected boolean isPlayerViewing(PlayerEntity player) {
				ScreenHandler screenHandler = player.currentScreenHandler;
				
				Inventory inventory = null;
				if (screenHandler instanceof GenericContainerScreenHandler) {
					inventory = ((GenericContainerScreenHandler) screenHandler).getInventory();
				} else if (screenHandler instanceof FabricationChestScreenHandler fabricationChestScreenHandler) {
					inventory = fabricationChestScreenHandler.getInventory();
				} else if (screenHandler instanceof BlackHoleChestScreenHandler blackHoleChestScreenHandler) {
					inventory = blackHoleChestScreenHandler.getInventory();
				} else if (screenHandler instanceof CompactingChestScreenHandler compactingChestScreenHandler) {
					inventory = compactingChestScreenHandler.getInventory();
				}
				
				return inventory == SpectrumChestBlockEntity.this;
			}
		};
	}
	
	private static void playSound(World world, BlockPos pos, SoundEvent soundEvent) {
		world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, soundEvent, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
	}
	
	@SuppressWarnings("unused")
    public static void clientTick(World world, BlockPos pos, BlockState state, SpectrumChestBlockEntity blockEntity) {
		blockEntity.lidAnimator.step();
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public float getAnimationProgress(float tickDelta) {
		return this.lidAnimator.getProgress(tickDelta);
	}
	
	public void onOpen() {
	
	}
	
	public void onClose() {
	
	}
	
	@Override
	public boolean onSyncedBlockEvent(int type, int data) {
		if (type == 1) {
			this.lidAnimator.setOpen(data > 0);
			return true;
		} else {
			return super.onSyncedBlockEvent(type, data);
		}
	}
	
	protected void onInvOpenOrClose(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
		Block block = state.getBlock();
		world.addSyncedBlockEvent(pos, block, 1, newViewerCount);
	}
	
	@Override
	public void onOpen(PlayerEntity player) {
		if (!player.isSpectator()) {
			this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
		}
		
	}
	
	@Override
	public void onClose(PlayerEntity player) {
		if (!player.isSpectator()) {
			this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
		}
	}
	
	@Override
	public void setStack(int slot, ItemStack stack) {
		super.setStack(slot, stack);
	}
	
	@Override
	protected DefaultedList<ItemStack> getHeldStacks() {
		return this.inventory;
	}
	
	@Override
	protected void setHeldStacks(DefaultedList<ItemStack> list) {
		this.inventory = list;
	}
	
	public void onScheduledTick() {
		this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
	}
	
	@Override
	public void readNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(tag, registryLookup);
		this.readLootTable(tag);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		Inventories.readNbt(tag, this.inventory, registryLookup);
	}
	
	@Override
	public void writeNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(tag, registryLookup);
		this.writeLootTable(tag);
		if (!this.inventory.isEmpty()) {
			Inventories.writeNbt(tag, this.inventory, registryLookup);
		}
	}
	
	public SoundEvent getOpenSound() {
		return SoundEvents.BLOCK_CHEST_OPEN;
	}
	
	public SoundEvent getCloseSound() {
		return SoundEvents.BLOCK_CHEST_CLOSE;
	}
	
}
