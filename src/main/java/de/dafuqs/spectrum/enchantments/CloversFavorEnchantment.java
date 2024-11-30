package de.dafuqs.spectrum.enchantments;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.*;

public class CloversFavorEnchantment {

	public static float rollChance(float baseChance, Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			int rareLootLevel = SpectrumEnchantmentHelper.getLevel(entity.getWorld().getRegistryManager(), SpectrumEnchantments.CLOVERS_FAVOR, livingEntity.getMainHandStack());
			if (rareLootLevel > 0) {
				return baseChance * (float) rareLootLevel * rareLootLevel;
			}
		}
		return 0;
	}

}
