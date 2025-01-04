package de.dafuqs.spectrum.items.trinkets;

import com.google.common.collect.*;
import de.dafuqs.additionalentityattributes.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.energy.storage.*;
import de.dafuqs.spectrum.registries.*;
import dev.emi.trinkets.api.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.registry.entry.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class LaurelsOfSerenityItem extends InkDrainTrinketItem {
    
    public LaurelsOfSerenityItem(Settings settings) {
        super(settings, SpectrumCommon.locate("unlocks/trinkets/laurels_of_serenity"), InkColors.PURPLE);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.spectrum.laurels_of_serenity.tooltip").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, type);
    }
    
    @Override
    public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, Identifier slotIdentifier) {
        Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, slotIdentifier);
        
        FixedSingleInkStorage inkStorage = getEnergyStorage(stack);
        long storedInk = inkStorage.getEnergy(inkStorage.getStoredColor());
        double detectionRangeMod = getDetectionRangeMultiplier(storedInk);
        if (detectionRangeMod != 0) {
            // For some weird reason, Pug, who PRd the attribute to Additional Entity Attributes
            // made negative values be the 'good' variant (aka reducing the distance mobs need to be in to detect an entity)
            // so it shows up red in tooltips. Hmmmm
            modifiers.put(AdditionalEntityAttributes.MOB_DETECTION_RANGE, new EntityAttributeModifier(uuid, "spectrum:laurels_of_serenity_detection_range", -detectionRangeMod, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
        double sleepResistanceMod = getInducedSleepResistanceMod(storedInk);
        if (sleepResistanceMod != 0) {
            modifiers.put(SpectrumEntityAttributes.MENTAL_PRESENCE, new EntityAttributeModifier(uuid, "spectrum:laurels_of_serenity_sleep", sleepResistanceMod, EntityAttributeModifier.Operation.ADD_VALUE));
        }
        
        return modifiers;
    }
    
    public float getDetectionRangeMultiplier(long storedInk) {
        if (storedInk < 100) {
            return 0;
        } else {
            return 0.15F * (int) (Math.log(storedInk / 100.0f) / Math.log(8)); //TODO: reduce once the ink trinket cap is set back to 1.6 billion
        }
    }
    
    public float getInducedSleepResistanceMod(long storedInk) {
        if (storedInk < 100) {
            return 0;
        } else {
            return 0.15F * (int) (Math.log(storedInk / 100.0f) / Math.log(8)); //TODO: reduce once the ink trinket cap is set back to 1.6 billion
        }
    }

}
