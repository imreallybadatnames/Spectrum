package de.dafuqs.spectrum.compat.idwtialsimmoedm;

import de.dafuqs.spectrum.helpers.*;
import io.wispforest.idwtialsimmoedm.api.*;
import net.fabricmc.api.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

@Environment(EnvType.CLIENT)
public class IdwtialsimmoedmCompat {
	public static void register() {
		GatherDescriptionCallback.ENCHANTMENT.register(ench -> {
			Text original = DefaultDescriptions.forEnchantmentRaw(ench);
			if (original == null) return null;
			
			// Cloaks should always be obfuscated, since they're fake
			if (SpectrumEnchantmentHelper.isCloaking(ench))
				return GatherDescriptionCallback.wrapDescription(original.copy().formatted(Formatting.OBFUSCATED));
			
			// Conditional enchants should be obfuscated if the player hasn't unlocked them
			// That said, we'd need to sync unlock data from the server since Enchantment
			// requires ServerWorld to test conditions. As such, it'll just be displayed for now.
			return null;
		});
	}
}
