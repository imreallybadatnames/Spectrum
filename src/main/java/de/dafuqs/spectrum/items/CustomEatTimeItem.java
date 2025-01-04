package de.dafuqs.spectrum.items;

import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class CustomEatTimeItem extends Item {
	
	private final int eatTime;
	
	public CustomEatTimeItem(Settings settings, int eatTime) {
		super(settings);
		this.eatTime = eatTime;
	}
	
	@Override
	public int getMaxUseTime(ItemStack stack, LivingEntity user) {
		FoodComponent foodComponent = stack.get(DataComponentTypes.FOOD);
		return foodComponent != null ? foodComponent.getEatTicks() : eatTime;
	}
}
