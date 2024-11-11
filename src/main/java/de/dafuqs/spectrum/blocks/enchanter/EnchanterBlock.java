package de.dafuqs.spectrum.blocks.enchanter;

import com.klikli_dev.modonomicon.api.multiblock.*;
import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.blocks.*;
import de.dafuqs.spectrum.compat.modonomicon.*;
import de.dafuqs.spectrum.progression.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public class EnchanterBlock extends InWorldInteractionBlock {

	public static final MapCodec<EnchanterBlock> CODEC = createCodec(EnchanterBlock::new);

	public static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("midgame/build_enchanting_structure");

	public EnchanterBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends EnchanterBlock> getCodec() {
		return CODEC;
	}
	
	public static void clearCurrentlyRenderedMultiBlock(World world) {
		if (world.isClient) {
			ModonomiconHelper.clearRenderedMultiblock(SpectrumMultiblocks.get(SpectrumMultiblocks.ENCHANTER));
		}
	}
	
	public static boolean verifyStructure(World world, BlockPos blockPos, @Nullable ServerPlayerEntity serverPlayerEntity) {
		Multiblock multiblock = SpectrumMultiblocks.get(SpectrumMultiblocks.ENCHANTER);
		boolean valid = multiblock.validate(world, blockPos.down(3), BlockRotation.NONE);
		
		if (valid) {
			if (serverPlayerEntity != null) {
				SpectrumAdvancementCriteria.COMPLETED_MULTIBLOCK.trigger(serverPlayerEntity, multiblock);
			}
		} else {
			if (world.isClient) {
				ModonomiconHelper.renderMultiblock(SpectrumMultiblocks.get(SpectrumMultiblocks.ENCHANTER), SpectrumMultiblocks.ENCHANTER_TEXT, blockPos.down(4), BlockRotation.NONE);
			}
		}
		
		return valid;
	}
	
	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new EnchanterBlockEntity(pos, state);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return validateTicker(type, SpectrumBlockEntities.ENCHANTER, world.isClient ? EnchanterBlockEntity::clientTick : EnchanterBlockEntity::serverTick);
	}
	
	@Override
	public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
		if (world.isClient()) {
			clearCurrentlyRenderedMultiBlock((World) world);
		}
	}
	
	@Override
	public ItemActionResult onUseWithItem(ItemStack handStack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) {
			verifyStructure(world, pos, null);
			return ItemActionResult.SUCCESS;
		} else {
			if (verifyStructure(world, pos, (ServerPlayerEntity) player)) {
				
				// if the structure is valid the player can put / retrieve blocks into the shrine
				BlockEntity blockEntity = world.getBlockEntity(pos);
				if (blockEntity instanceof EnchanterBlockEntity enchanterBlockEntity) {

					if (player.isSneaking() || handStack.isEmpty()) {
						// sneaking or empty hand: remove items
						for (int i = 0; i < EnchanterBlockEntity.INVENTORY_SIZE; i++) {
							if (retrieveStack(world, pos, player, hand, handStack, enchanterBlockEntity, i)) {
								enchanterBlockEntity.setItemFacingDirection(player.getHorizontalFacing());
								enchanterBlockEntity.setOwner(player);
								enchanterBlockEntity.inventoryChanged();
								break;
							}
						}
						return ItemActionResult.CONSUME;
					} else {
						// hand is full and inventory is empty: add
						// hand is full and inventory already contains item: exchange them
						int inputInventorySlotIndex = handStack.getItem() instanceof ExperienceStorageItem ? enchanterBlockEntity.getStack(1).isEmpty() ? 1 : 0 : 0;
						if (exchangeStack(world, pos, player, hand, handStack, enchanterBlockEntity, inputInventorySlotIndex)) {
							enchanterBlockEntity.setItemFacingDirection(player.getHorizontalFacing());
							enchanterBlockEntity.setOwner(player);
							enchanterBlockEntity.inventoryChanged();
						}
					}
				}
			}
			return ItemActionResult.CONSUME;
		}
	}
	
}
