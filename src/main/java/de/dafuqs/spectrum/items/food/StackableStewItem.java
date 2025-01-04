package de.dafuqs.spectrum.items.food;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

public class StackableStewItem extends Item {
	
	public StackableStewItem(Item.Settings settings) {
		super(settings);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		ItemStack returnStack = super.finishUsing(stack, world, user);
		
		PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
		if (playerEntity == null || !playerEntity.getAbilities().creativeMode) {
			if (returnStack.isEmpty()) {
				return new ItemStack(Items.BOWL);
			}
			
			if (playerEntity != null) {
				playerEntity.getInventory().insertStack(new ItemStack(Items.BOWL));
			}
		}
		
		return returnStack;
	}
	
}
