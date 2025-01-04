package de.dafuqs.spectrum.blocks.energy;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.screen.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.event.listener.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CrystalApothecaryBlock extends BlockWithEntity {

	public static final MapCodec<CrystalApothecaryBlock> CODEC = createCodec(CrystalApothecaryBlock::new);

	public CrystalApothecaryBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends CrystalApothecaryBlock> getCodec() {
		return CODEC;
	}
	
	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new CrystalApothecaryBlockEntity(pos, state);
	}

	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("block.spectrum.crystal_apothecary.tooltip").formatted(Formatting.GRAY));
	}
	
	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof CrystalApothecaryBlockEntity crystalApothecaryBlockEntity) {
			if (placer instanceof ServerPlayerEntity serverPlayerEntity) {
				crystalApothecaryBlockEntity.setOwner(serverPlayerEntity);
			}
			crystalApothecaryBlockEntity.harvestExistingClusters();
		}
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		} else {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof CrystalApothecaryBlockEntity crystalApothecaryBlockEntity) {
				player.openHandledScreen(crystalApothecaryBlockEntity);
			}
			return ActionResult.CONSUME;
		}
	}
	
	@Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }
	
	@Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }
	
	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		ItemScatterer.onStateReplaced(state, newState, world, pos);
		super.onStateReplaced(state, world, pos, newState, moved);
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}
	
	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return validateTicker(type, SpectrumBlockEntities.CRYSTAL_APOTHECARY, CrystalApothecaryBlockEntity::tick);
	}
	
	@Override
	@Nullable
	public <T extends BlockEntity> GameEventListener getGameEventListener(ServerWorld world, T blockEntity) {
		return blockEntity instanceof CrystalApothecaryBlockEntity crystalApothecaryBlockEntity ? crystalApothecaryBlockEntity.getEventListener() : null;
	}
	
}
