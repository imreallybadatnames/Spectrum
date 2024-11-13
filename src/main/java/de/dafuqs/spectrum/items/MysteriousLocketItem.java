package de.dafuqs.spectrum.items;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class MysteriousLocketItem extends Item {
	
	public MysteriousLocketItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (!world.isClient) {
			ItemStack handStack = user.getStackInHand(hand);
			if (handStack.contains(SpectrumDataComponentTypes.SOCKETED)) {
				handStack.decrement(1);
				user.getInventory().offerOrDrop(SpectrumItems.MYSTERIOUS_COMPASS.getDefaultStack());
				world.playSound(null, user.getX(), user.getY(), user.getZ(), SpectrumSoundEvents.UNLOCK, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			}
		}
		return super.use(world, user, hand);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.mysterious_locket.tooltip").formatted(Formatting.GRAY));
		if (stack.contains(SpectrumDataComponentTypes.SOCKETED)) {
			tooltip.add(Text.translatable("item.spectrum.mysterious_locket.tooltip_socketed").formatted(Formatting.GRAY));
		} else {
			tooltip.add(Text.translatable("item.spectrum.mysterious_locket.tooltip_empty").formatted(Formatting.GRAY));
		}
	}
	
}
