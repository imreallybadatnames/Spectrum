package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.item.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.effect.*;
import net.minecraft.item.*;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.predicate.item.*;
import net.minecraft.registry.entry.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class PotionWorkshopBrewingCriterion extends AbstractCriterion<PotionWorkshopBrewingCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("potion_workshop_brewing");
	
	@SuppressWarnings("deprecation")
	public void trigger(ServerPlayerEntity player, ItemStack itemStack, int brewedCount) {
		this.trigger(player, conditions -> {
			List<StatusEffectInstance> effects;
			if (itemStack.getItem() instanceof InkPoweredPotionFillable inkPoweredPotionFillable) {
				effects = inkPoweredPotionFillable.getVanillaEffects(itemStack);
			} else {
				PotionContentsComponent potionComponent = itemStack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
				effects = potionComponent.customEffects();
			}
			
			int highestAmplifier = 0;
			int longestDuration = 0;
			for (StatusEffectInstance instance : effects) {
				if (instance.getAmplifier() > highestAmplifier) {
					highestAmplifier = instance.getAmplifier();
				}
				if (instance.getDuration() > longestDuration) {
					longestDuration = instance.getDuration();
				}
			}
			
			List<StatusEffect> uniqueEffects = new ArrayList<>();
			for (StatusEffectInstance instance : effects) {
				if (!uniqueEffects.contains(instance.getEffectType().value())) {
					uniqueEffects.add(instance.getEffectType().value());
				}
			}
			
			return conditions.matches(itemStack, effects, brewedCount, highestAmplifier, longestDuration, effects.size(), uniqueEffects.size());
		});
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
		Optional<LootContextPredicate> player,
		ItemPredicate itemPredicate,
		EntityEffectPredicate statusEffectsPredicate,
		NumberRange.IntRange brewedCountRange,
		NumberRange.IntRange maxAmplifierRange,
		NumberRange.IntRange maxDurationRange,
		NumberRange.IntRange effectCountRange,
		NumberRange.IntRange uniqueEffectCountRange
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
			ItemPredicate.CODEC.fieldOf("item").forGetter(Conditions::itemPredicate),
			EntityEffectPredicate.CODEC.fieldOf("effects").forGetter(Conditions::statusEffectsPredicate),
			NumberRange.IntRange.CODEC.fieldOf("brewed_count").forGetter(Conditions::brewedCountRange),
			NumberRange.IntRange.CODEC.fieldOf("highest_amplifier").forGetter(Conditions::maxAmplifierRange),
			NumberRange.IntRange.CODEC.fieldOf("longest_duration").forGetter(Conditions::maxDurationRange),
			NumberRange.IntRange.CODEC.fieldOf("effect_count").forGetter(Conditions::effectCountRange),
			NumberRange.IntRange.CODEC.fieldOf("unique_effect_count").forGetter(Conditions::uniqueEffectCountRange)
			).apply(instance, Conditions::new));
		
		
		public boolean matches(ItemStack stack, List<StatusEffectInstance> effects, int brewedCount, int maxAmplifier, int maxDuration, int effectCount, int uniqueEffectCount) {
			if (this.brewedCountRange.test(brewedCount) &&
				this.maxAmplifierRange.test(maxAmplifier) &&
				this.maxDurationRange.test(maxDuration) &&
				this.effectCountRange.test(effectCount) &&
				this.uniqueEffectCountRange.test(uniqueEffectCount) &&
				this.itemPredicate.test(stack)) {
				Map<RegistryEntry<StatusEffect>, StatusEffectInstance> effectMap = new HashMap<>();
				for (StatusEffectInstance instance : effects) {
					if (!effectMap.containsKey(instance.getEffectType())) {
						effectMap.put(instance.getEffectType(), instance);
					}
				}
				
				return this.statusEffectsPredicate.test(effectMap);
			}
			
			return false;
		}
	}
	
}
