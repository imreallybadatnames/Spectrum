package de.dafuqs.spectrum.helpers;

import com.google.common.collect.*;
import com.mojang.datafixers.util.Pair;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.item.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class TooltipHelper {
	
	public static void addFoodComponentEffectTooltip(ItemStack stack, List<Text> tooltip, float updateTickRate) {
		FoodComponent foodComponent = stack.get(DataComponentTypes.FOOD);
		if (foodComponent != null) {
			buildEffectTooltipWithChance(tooltip, foodComponent.effects(), stack.getUseAction() == UseAction.DRINK ? Text.translatable("spectrum.food.whenDrunk") : Text.translatable("spectrum.food.whenEaten"), updateTickRate);
		}
	}
	
	public static void buildEffectTooltipWithChance(List<Text> tooltip, List<FoodComponent.StatusEffectEntry> entries, MutableText attributeModifierText, float updateTickRate) {
		if (entries.isEmpty()) {
			return;
		}
		
		List<Pair<EntityAttribute, EntityAttributeModifier>> modifiersList = Lists.newArrayList();
		for (var entry : entries) {
			var statusEffectInstance = entry.effect();
			var chance = entry.probability();
			
			var translatableText = Text.translatable(statusEffectInstance.getTranslationKey());
			var statusEffect = statusEffectInstance.getEffectType();
			statusEffect.value().forEachAttributeModifier(statusEffectInstance.getAmplifier(), (attribute, modifier) ->
				modifiersList.add(new Pair<>(attribute.value(), modifier)));
			
			if (statusEffectInstance.getAmplifier() > 0) {
				translatableText = Text.translatable("potion.withAmplifier", translatableText, Text.translatable("potion.potency." + statusEffectInstance.getAmplifier()));
			}
			if (statusEffectInstance.getDuration() > 20) {
				translatableText = Text.translatable("potion.withDuration", translatableText, StringHelper.formatTicks(statusEffectInstance.getDuration(), updateTickRate));
			}
			if (chance < 1.0F) {
				translatableText = Text.translatable("spectrum.food.withChance", translatableText, Math.round(chance * 100));
			}
			
			tooltip.add(translatableText.formatted(statusEffect.value().getCategory().getFormatting()));
		}
		
		if (!modifiersList.isEmpty()) {
			tooltip.add(Text.empty());
			tooltip.add(attributeModifierText.formatted(Formatting.DARK_PURPLE));
			
			for (var pair : modifiersList) {
				var modifier = pair.getSecond();
				double d = modifier.value();
				double e;
				if (modifier.operation() != EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE && modifier.operation() != EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
					e = modifier.value();
				} else {
					e = modifier.value() * 100.0D;
				}
				
				if (d > 0.0D) {
					tooltip.add((Text.translatable("attribute.modifier.plus." + modifier.operation().getId(), AttributeModifiersComponent.DECIMAL_FORMAT.format(e), Text.translatable((pair.getFirst()).getTranslationKey()))).formatted(Formatting.BLUE));
				} else if (d < 0.0D) {
					e *= -1.0D;
					tooltip.add((Text.translatable("attribute.modifier.take." + modifier.operation().getId(), AttributeModifiersComponent.DECIMAL_FORMAT.format(e), Text.translatable((pair.getFirst()).getTranslationKey()))).formatted(Formatting.RED));
				}
			}
		}
	}
	
}
