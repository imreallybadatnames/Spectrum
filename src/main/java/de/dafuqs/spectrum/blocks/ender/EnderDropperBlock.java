package de.dafuqs.spectrum.blocks.ender;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.inventories.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.transfer.v1.item.*;
import net.fabricmc.fabric.api.transfer.v1.storage.*;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.screen.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public class EnderDropperBlock extends DispenserBlock {

	public static final MapCodec<EnderDropperBlock> CODEC = createCodec(EnderDropperBlock::new);

	private static final DispenserBehavior BEHAVIOR = new ItemDispenserBehavior();

	public EnderDropperBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends EnderDropperBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	protected DispenserBehavior getBehaviorForItem(World world, ItemStack stack) {
		return BEHAVIOR;
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new EnderDropperBlockEntity(pos, state);
	}
	
	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (placer instanceof ServerPlayerEntity serverPlayer) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof EnderDropperBlockEntity dropperEntity) {
				dropperEntity.setOwner(serverPlayer);
				blockEntity.markDirty();
			}
		}
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		} else {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof EnderDropperBlockEntity enderDropperBlockEntity) {
				
				if (!enderDropperBlockEntity.hasOwner()) {
					enderDropperBlockEntity.setOwner(player);
				}
				
				if (enderDropperBlockEntity.isOwner(player)) {
					EnderChestInventory enderChestInventory = player.getEnderChestInventory();
					
					player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> GenericSpectrumContainerScreenHandler.createGeneric9x3(i, playerInventory, enderChestInventory, ScreenBackgroundVariant.EARLYGAME), enderDropperBlockEntity.getContainerName()));
					
					PiglinBrain.onGuardedBlockInteracted(player, true);
				} else {
					player.sendMessage(Text.translatable("block.spectrum.ender_dropper_with_owner", enderDropperBlockEntity.getOwnerName()), true);
				}
			}
			return ActionResult.CONSUME;
		}
	}
	
	@Override
	protected void dispense(ServerWorld world, BlockState state, BlockPos pos) {
		EnderDropperBlockEntity enderDropperBlockEntity = world.getBlockEntity(pos, SpectrumBlockEntities.ENDER_DROPPER).orElse(null);
		if (enderDropperBlockEntity == null) {
			return;
		}

		BlockPointer blockPointer = new BlockPointer(world, pos, state, enderDropperBlockEntity);
		int i = enderDropperBlockEntity.chooseNonEmptySlot(world.random);
		if (i < 0) {
			world.syncWorldEvent(WorldEvents.DISPENSER_FAILS, pos, 0); // no items in inv
		} else {
			ItemStack itemStack = enderDropperBlockEntity.getStack(i);
			if (!itemStack.isEmpty()) {
				Direction direction = world.getBlockState(pos).get(FACING);
				if (world.getBlockState(pos.offset(direction)).isAir()) {
					ItemStack itemStack3 = BEHAVIOR.dispense(blockPointer, itemStack);
					enderDropperBlockEntity.setStack(i, itemStack3);
				} else {
					Storage<ItemVariant> target = ItemStorage.SIDED.find(world, pos.offset(direction), direction.getOpposite());
					if (target != null) {
						// getting inv will always work since .chooseNonEmptySlot() and others would fail otherwise
						//noinspection DataFlowIssue
						Inventory inv = enderDropperBlockEntity.getOwnerIfOnline().getEnderChestInventory();
						long moved = StorageUtil.move(
								InventoryStorage.of(inv, direction).getSlot(i),
								target,
								iv -> true,
								1,
								null
						);
						// return without triggering fail event if successfully moved
						if (moved == 1) return;
					}
					world.syncWorldEvent(WorldEvents.DISPENSER_FAILS, pos, 0); // no room to dispense to
				}
			}
		}
	}

}