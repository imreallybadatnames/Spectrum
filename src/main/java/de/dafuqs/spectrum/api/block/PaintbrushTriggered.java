package de.dafuqs.spectrum.api.block;

import de.dafuqs.spectrum.items.magic_items.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public interface PaintbrushTriggered {
	
	/**
	 * Use as first entry of onUse() for a block
	 */
	default ItemActionResult checkAndDoPaintbrushTrigger(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (player.getStackInHand(hand).getItem() instanceof PaintbrushItem) {
			ItemActionResult actionResult = onPaintBrushTrigger(state, world, pos, player, hand, hit);
			if (actionResult.isAccepted()) {
				world.playSound(null, pos, SpectrumSoundEvents.PAINTBRUSH_TRIGGER, SoundCategory.PLAYERS, 1.0F, 1.0F);
			} else {
				world.playSound(null, pos, SpectrumSoundEvents.USE_FAIL, SoundCategory.PLAYERS, 1.0F, 1.0F);
			}
			return actionResult;
		}
		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}
	
	/**
	 * Do custom logic here
	 * The Pedestal uses it to start crafting, for example
	 */
	ItemActionResult onPaintBrushTrigger(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit);
	
}
