package de.dafuqs.spectrum.items.trinkets;

import com.google.common.collect.*;
import de.dafuqs.additionalentityattributes.*;
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

public class ExtraMiningSpeedRingItem extends InkDrainTrinketItem {
	
	public ExtraMiningSpeedRingItem(Settings settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/ring_of_pursuit"), InkColors.MAGENTA);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		tooltip.add(Text.translatable("item.spectrum.ring_of_pursuit.tooltip").formatted(Formatting.GRAY));
		super.appendTooltip(stack, context, tooltip, type);
	}
	
	@Override
	public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, Identifier slotIdentifier) {
		Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, slotIdentifier);
		
		FixedSingleInkStorage inkStorage = getEnergyStorage(stack);
		long storedInk = inkStorage.getEnergy(inkStorage.getStoredColor());
		double miningSpeedMod = getExtraMiningSpeed(storedInk);
		if (miningSpeedMod != 0) {
			modifiers.put(AdditionalEntityAttributes.DIG_SPEED, new EntityAttributeModifier(uuid, "spectrum:ring_of_pursuit", miningSpeedMod, EntityAttributeModifier.Operation.ADD_VALUE));
		}
		
		return modifiers;
	}
	
	public double getExtraMiningSpeed(long storedInk) {
		if (storedInk < 100) {
			return 0;
		} else {
			return 1 + (int) (Math.log(storedInk / 100.0f) / Math.log(8));
		}
	}
	
}
