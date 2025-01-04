package de.dafuqs.spectrum.items.food;

import de.dafuqs.spectrum.items.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class DrinkItem extends CustomEatTimeItem {
	
	public DrinkItem(Settings settings) {
		super(settings, 40);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		ItemStack itemStack = super.finishUsing(stack, world, user);
		
		if (user instanceof PlayerEntity player) {
			if (!player.getAbilities().creativeMode) {
				if (stack.isEmpty()) {
					return new ItemStack(Items.GLASS_BOTTLE);
				}
				player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
			}
		}
		
		return itemStack;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}
	
	public SoundEvent getEatSound() {
		return SoundEvents.ENTITY_GENERIC_DRINK;
	}
	
}
