package de.dafuqs.spectrum.helpers.enchantments;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.player.*;

public class ExuberanceHelper {
	
	public static float getExuberanceMod(PlayerEntity breakingPlayer) {
		if (breakingPlayer != null) {
			var drm = breakingPlayer.getWorld().getRegistryManager();
			int exuberanceLevel = SpectrumEnchantmentHelper.getEquipmentLevel(drm, SpectrumEnchantments.EXUBERANCE, breakingPlayer);
			return getExuberanceMod(exuberanceLevel);
		} else {
			return 1.0F;
		}
	}
	
	public static float getExuberanceMod(int level) {
		return 1.0F + level * SpectrumCommon.CONFIG.ExuberanceBonusExperiencePercentPerLevel;
	}
	
}