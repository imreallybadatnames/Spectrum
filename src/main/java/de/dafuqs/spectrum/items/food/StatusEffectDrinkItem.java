package de.dafuqs.spectrum.items.food;

import net.minecraft.advancement.criterion.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.server.network.*;
import net.minecraft.stat.*;
import net.minecraft.world.*;
import net.minecraft.world.event.*;

public class StatusEffectDrinkItem extends DrinkItem {
	
	public StatusEffectDrinkItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
		if (playerEntity instanceof ServerPlayerEntity) {
			Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity) playerEntity, stack);
		}
		
		if (!world.isClient) {
			PotionContentsComponent potionContentsComponent = stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
			potionContentsComponent.forEachEffect((effect) -> {
				if ((effect.getEffectType().value()).isInstant()) {
					(effect.getEffectType().value()).applyInstantEffect(playerEntity, playerEntity, user, effect.getAmplifier(), 1.0);
				} else {
					user.addStatusEffect(effect);
				}
			});
		}
		
		if (playerEntity != null) {
			playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}
		
		user.emitGameEvent(GameEvent.DRINK);
		return super.finishUsing(stack, world, user);
	}
	
}
