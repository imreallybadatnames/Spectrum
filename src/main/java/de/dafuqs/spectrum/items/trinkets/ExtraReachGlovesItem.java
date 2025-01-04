package de.dafuqs.spectrum.items.trinkets;

import com.google.common.collect.*;
import com.jamieswhiteshirt.reachentityattributes.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.energy.storage.*;
import dev.emi.trinkets.api.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.registry.entry.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class ExtraReachGlovesItem extends InkDrainTrinketItem {
	
	public ExtraReachGlovesItem(Settings settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/gloves_of_dawns_grasp"), InkColors.LIGHT_BLUE);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		tooltip.add(Text.translatable("item.spectrum.gloves_of_dawns_grasp.tooltip").formatted(Formatting.GRAY));
		super.appendTooltip(stack, context, tooltip, type);
	}
	
	@Override
	public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, Identifier slotIdentifier) {
		Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, slotIdentifier);
		
		FixedSingleInkStorage inkStorage = getEnergyStorage(stack);
		long storedInk = inkStorage.getEnergy(inkStorage.getStoredColor());
		double extraReach = getExtraReach(storedInk);
		if (extraReach != 0) {
			modifiers.put(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE, new EntityAttributeModifier(uuid, "spectrum:gloves_of_dawns_grasp", extraReach, EntityAttributeModifier.Operation.ADD_VALUE));
			modifiers.put(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(uuid, "spectrum:gloves_of_dawns_grasp", extraReach / 6, EntityAttributeModifier.Operation.ADD_VALUE));
		}
		
		return modifiers;
	}
	
	public double getExtraReach(long storedInk) {
		if (storedInk < 100) {
			return 0;
		} else {
			return 0.5 + roundHalf(Math.log(storedInk / 100.0f) / Math.log(64));
		}
	}
	
	public static double roundHalf(double number) {
		return ((int) (number * 2)) / 2D;
	}
	
}
