package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.registry.tag.*;

public class SpectrumStatusEffectTags {
	
	public static TagKey<StatusEffect> INCURABLE;
	public static TagKey<StatusEffect> NO_DURATION_EXTENSION;
	public static TagKey<StatusEffect> SOPORIFIC;
	public static TagKey<StatusEffect> NIGHT_ALCHEMY;

	public static void register() {
		INCURABLE = of("uncurable");
		NO_DURATION_EXTENSION = of("no_duration_extension");
		SOPORIFIC = of("soporific");
		NIGHT_ALCHEMY = of("night_alchemy");
	}
	
	private static TagKey<StatusEffect> of(String id) {
		return TagKey.of(RegistryKeys.STATUS_EFFECT, SpectrumCommon.locate(id));
	}
	
	public static boolean isIncurable(RegistryEntry<StatusEffect> effect) {
		return effect.isIn(SpectrumStatusEffectTags.INCURABLE);
	}
	
	public static boolean hasEffectWithTag(LivingEntity livingEntity, TagKey<StatusEffect> tag) {
		for (var statusEffect : livingEntity.getActiveStatusEffects().keySet()) {
			if (statusEffect.isIn(tag)) {
				return true;
			}
		}
		return false;
	}
	
}
