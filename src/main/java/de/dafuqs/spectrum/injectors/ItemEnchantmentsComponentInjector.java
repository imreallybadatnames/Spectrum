package de.dafuqs.spectrum.injectors;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.enchantment.*;
import net.minecraft.registry.entry.*;

import java.util.*;

public interface ItemEnchantmentsComponentInjector {
	
	Set<RegistryEntry<Enchantment>> getEnchantments();
	
	default CloakState spectrum$cloakState(RegistryEntry<Enchantment> ench) {
		// If this enchantment isn't in this component, what are you even doing
		var enchantments = getEnchantments();
		if (!enchantments.contains(ench))
			return CloakState.HIDDEN;
		
		// If this enchantment is non-cloaking, it's always revealed
		var cloaks = ench.value().getEffect(SpectrumEnchantmentEffectComponentTypes.CLOAKED);
		if (cloaks.isEmpty()) return CloakState.REVEALED;
		
		// If any enchantment is not unlocked, we show that something's left by obfuscating the cloak
		for (var cloak : cloaks)
			if (!enchantments.contains(cloak.effect()))
				return CloakState.OBFUSCATED;
		
		// All cloaked enchantments are unlocked, so we pretend this one isn't here
		return CloakState.HIDDEN;
	}
	
	enum CloakState {
		HIDDEN,
		OBFUSCATED,
		REVEALED
	}
	
}

