package de.dafuqs.spectrum.enchantments;

import com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem;
import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.compat.SpectrumIntegrationPacks;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import java.util.*;

public class ImprovedCriticalEnchantment extends SpectrumEnchantment {
	
	public static final UUID EXTRA_CRIT_DAMAGE_MULTIPLIER_ATTRIBUTE_UUID = UUID.fromString("e9bca8d4-9dcb-4e9e-8a7b-48b129c7ec5a");
	public static final String EXTRA_CRIT_DAMAGE_MULTIPLIER_ATTRIBUTE_NAME = "spectrum:improved_critical";
	
	public ImprovedCriticalEnchantment(Rarity weight, Identifier unlockAdvancementIdentifier, EquipmentSlot... slotTypes) {
		super(weight, EnchantmentTarget.WEAPON, slotTypes, unlockAdvancementIdentifier);
	}
	
	public static float getAddtionalCritDamageMultiplier(int improvedCriticalLevel) {
		return SpectrumCommon.CONFIG.ImprovedCriticalExtraDamageMultiplierPerLevel * improvedCriticalLevel;
	}
	
}

