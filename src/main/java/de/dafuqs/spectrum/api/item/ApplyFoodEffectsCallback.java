package de.dafuqs.spectrum.api.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

public interface ApplyFoodEffectsCallback {
	
	/**
	 * Called when an item is consumed by an entity
	 *
	 * @param stack  the stack that is consumed
	 * @param entity the entity consuming the item
	 */
	void afterConsumption(World world, ItemStack stack, LivingEntity entity);
	
	static void applyFoodComponent(World world, LivingEntity entity, FoodComponent foodComponent) {
		if (entity instanceof PlayerEntity player) {
			player.getHungerManager().add(foodComponent.nutrition(), foodComponent.saturation());
		}
		
		for (var entry : foodComponent.effects()) {
			if (!world.isClient && world.random.nextFloat() < entry.probability()) {
				entity.addStatusEffect(new StatusEffectInstance(entry.effect()));
			}
		}
	}
	
}
