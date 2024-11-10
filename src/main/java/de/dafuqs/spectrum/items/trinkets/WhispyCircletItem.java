package de.dafuqs.spectrum.items.trinkets;

import com.google.common.collect.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.registries.*;
import dev.emi.trinkets.api.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.effect.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.*;
import net.minecraft.stat.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class WhispyCircletItem extends SpectrumTrinketItem {
	
	private final static int TRIGGER_EVERY_X_TICKS = 100;
	private final static int NEGATIVE_EFFECT_SHORTENING_TICKS = 200;
	
	public WhispyCircletItem(Settings settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/whispy_circlet"));
	}
	
	public static void removeSingleStatusEffect(@NotNull LivingEntity entity, StatusEffectCategory category) {
		Collection<StatusEffectInstance> currentEffects = entity.getStatusEffects();
		if (currentEffects.isEmpty()) {
			return;
		}

		List<StatusEffectInstance> negativeEffects = new ArrayList<>();
		for (StatusEffectInstance statusEffectInstance : currentEffects) {
			var effect = statusEffectInstance.getEffectType().value();
			if (effect.getCategory() == category && !SpectrumStatusEffectTags.isIn(SpectrumStatusEffectTags.SOPORIFIC, effect) && !SpectrumStatusEffectTags.isIncurable(effect)) {
				negativeEffects.add(statusEffectInstance);
			}
		}
		
		if (negativeEffects.isEmpty()) {
			return;
		}
		
		World world = entity.getWorld();
		int randomIndex = world.random.nextInt(negativeEffects.size());
		entity.removeStatusEffect(negativeEffects.get(randomIndex).getEffectType());
	}
	
	public static void removeNegativeStatusEffects(@NotNull LivingEntity entity) {
		Set<RegistryEntry<StatusEffect>> effectsToRemove = new HashSet<>();
		for (var instance : entity.getStatusEffects()) {
			if (affects(instance.getEffectType().value())) {
				effectsToRemove.add(instance.getEffectType());
			}
		}
		
		for (var effect : effectsToRemove) {
			entity.removeStatusEffect(effect);
		}
	}
	
	public static void shortenNegativeStatusEffects(@NotNull LivingEntity entity, int duration) {
		Collection<StatusEffectInstance> newEffects = new ArrayList<>();
		Collection<RegistryEntry<StatusEffect>> effectTypesToClear = new ArrayList<>();
		
		// remove them first, so hidden "stacked" effects are preserved
		for (StatusEffectInstance instance : entity.getStatusEffects()) {
			if (affects(instance.getEffectType().value())) {
				int newDurationTicks = instance.getDuration() - duration;
				if (newDurationTicks > 0) {
					newEffects.add(new StatusEffectInstance(instance.getEffectType(), newDurationTicks, instance.getAmplifier(), instance.isAmbient(), instance.shouldShowParticles(), instance.shouldShowIcon()));
				}
				if (!effectTypesToClear.contains(instance.getEffectType())) {
					effectTypesToClear.add(instance.getEffectType());
				}
			}
		}
		
		for (var effectTypeToClear : effectTypesToClear) {
			entity.removeStatusEffect(effectTypeToClear);
		}
		for (StatusEffectInstance newEffect : newEffects) {
			entity.addStatusEffect(newEffect);
		}
	}
	
	public static boolean affects(StatusEffect statusEffect) {
		return statusEffect.getCategory() == StatusEffectCategory.HARMFUL && !SpectrumStatusEffectTags.isIn(SpectrumStatusEffectTags.SOPORIFIC, statusEffect) && !SpectrumStatusEffectTags.isIncurable(statusEffect);
	}
	
	public static void preventPhantomSpawns(@NotNull ServerPlayerEntity serverPlayerEntity) {
		serverPlayerEntity.getStatHandler().setStat(serverPlayerEntity, Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST), 0);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.whispy_circlet.tooltip").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.whispy_circlet.tooltip2").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.whispy_circlet.tooltip3").formatted(Formatting.GRAY));
	}
	
	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		super.tick(stack, slot, entity);
		
		World world = entity.getWorld();
		if (!world.isClient) {
			long time = entity.getWorld().getTime();
			if (time % TRIGGER_EVERY_X_TICKS == 0) {
				shortenNegativeStatusEffects(entity, NEGATIVE_EFFECT_SHORTENING_TICKS);
			}
			if (time % 10000 == 0 && entity instanceof ServerPlayerEntity serverPlayerEntity) {
				preventPhantomSpawns(serverPlayerEntity);
			}
		}
	}
	
	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		Multimap<EntityAttribute, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, uuid);
		modifiers.put(SpectrumEntityAttributes.MENTAL_PRESENCE, new EntityAttributeModifier(uuid, "spectrum:neat_ring", 0.3, EntityAttributeModifier.Operation.ADD_VALUE));
		return modifiers;
	}

}
