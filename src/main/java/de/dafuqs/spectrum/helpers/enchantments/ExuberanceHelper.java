package de.dafuqs.spectrum.helpers.enchantments;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;

public class ExuberanceHelper {
	
	public static float getExuberanceMod(PlayerEntity breakingPlayer) {
		if (breakingPlayer != null && EnchantmentHelper.getLevel(SpectrumEnchantments.EXUBERANCE, breakingPlayer.getMainHandStack()) > 0) {
			int exuberanceLevel = EnchantmentHelper.getEquipmentLevel(SpectrumEnchantments.EXUBERANCE, breakingPlayer);
			return getExuberanceMod(exuberanceLevel);
		} else {
			return 1.0F;
		}
	}
	
	public static float getExuberanceMod(int level) {
		return 1.0F + level * SpectrumCommon.CONFIG.ExuberanceBonusExperiencePercentPerLevel;
	}
	
}