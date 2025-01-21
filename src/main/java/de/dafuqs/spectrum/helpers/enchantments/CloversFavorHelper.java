package de.dafuqs.spectrum.helpers.enchantments;

import de.dafuqs.additionalentityattributes.*;
import net.minecraft.entity.*;
import org.jetbrains.annotations.*;

public class CloversFavorHelper {
	
	// TODO: give this enchantment AdditionalEntityAttributes.BONUS_RARE_LOOT_ROLLS 1 for level 1, 4 for level 2, ...
	public static float rollChance(float baseChance, @Nullable Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			float rareLootLevel = (float) livingEntity.getAttributeValue(AdditionalEntityAttributes.BONUS_RARE_LOOT_ROLLS);
			if (rareLootLevel > 0) {
				return baseChance * rareLootLevel;
			}
		}
		return 0;
	}
	
}
