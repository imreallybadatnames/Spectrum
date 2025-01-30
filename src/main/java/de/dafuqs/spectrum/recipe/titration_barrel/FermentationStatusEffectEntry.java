package de.dafuqs.spectrum.recipe.titration_barrel;

import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.entity.effect.*;
import net.minecraft.registry.*;

import java.util.*;

public record FermentationStatusEffectEntry(
	StatusEffect statusEffect,
	int baseDuration,
	List<StatusEffectPotencyEntry> potencyEntries
) {
	
	public static final StructEndec<FermentationStatusEffectEntry> FERMENTATION_ENTRY_ENDEC = StructEndecBuilder.of(
		MinecraftEndecs.ofRegistry(Registries.STATUS_EFFECT).fieldOf("status_effect", FermentationStatusEffectEntry::statusEffect),
		Endec.INT.fieldOf("base_duration", FermentationStatusEffectEntry::baseDuration),
		StatusEffectPotencyEntry.POTENCY_ENDEC.listOf().fieldOf("potency_entries", FermentationStatusEffectEntry::potencyEntries),
		FermentationStatusEffectEntry::new
	);
	
	public record StatusEffectPotencyEntry(int minAlcPercent, int minThickness, int potency) {
		
		public static final StructEndec<StatusEffectPotencyEntry> POTENCY_ENDEC = StructEndecBuilder.of(
			Endec.INT.fieldOf("min_alc", StatusEffectPotencyEntry::minAlcPercent),
			Endec.INT.fieldOf("min_thickness", StatusEffectPotencyEntry::minThickness),
			Endec.INT.fieldOf("potency", StatusEffectPotencyEntry::potency),
			StatusEffectPotencyEntry::new
		);
		
	}
	
}
