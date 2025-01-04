package de.dafuqs.spectrum.enchantments;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;

public class InexorableEnchantment {
    
    public static void checkAndRemoveSlowdownModifiers(LivingEntity entity) {
		var armorInexorable = isArmorActive(entity);
		var toolInexorable = EnchantmentHelper.getLevel(SpectrumEnchantments.INEXORABLE, entity.getStackInHand(entity.getActiveHand())) > 0;

		var armorAttributes = Registries.ATTRIBUTE.getEntryList(SpectrumAttributeTags.INEXORABLE_ARMOR_EFFECTIVE);
		var toolAttributes = Registries.ATTRIBUTE.getEntryList(SpectrumAttributeTags.INEXORABLE_HANDHELD_EFFECTIVE);

		if (armorInexorable && armorAttributes.isPresent()) {
			for (RegistryEntry<EntityAttribute> attributeRegistryEntry : armorAttributes.get()) {

				var attributeInstance = entity.getAttributeInstance(attributeRegistryEntry.value());

				if (attributeInstance == null)
					continue;

				var badMods = attributeInstance.getModifiers()
                        .stream()
						.filter(modifier -> modifier.value() < 0)
                        .toList();
				
				badMods.forEach(modifier -> attributeInstance.removeModifier(modifier.id()));
            }
        }
        
        if (toolInexorable && toolAttributes.isPresent()) {
            for (RegistryEntry<EntityAttribute> attributeRegistryEntry : toolAttributes.get()) {
                
                var attributeInstance = entity.getAttributeInstance(attributeRegistryEntry.value());
                
                if (attributeInstance == null)
                    continue;
                
                var badMods = attributeInstance.getModifiers()
                        .stream()
						.filter(modifier -> modifier.value() < 0)
                        .toList();
				
				badMods.forEach(modifier -> attributeInstance.removeModifier(modifier.id()));
            }
        }
    }
    
    public static boolean isArmorActive(LivingEntity entity) {
        return EnchantmentHelper.getLevel(SpectrumEnchantments.INEXORABLE, entity.getEquippedStack(EquipmentSlot.CHEST)) > 0;
    }
}
