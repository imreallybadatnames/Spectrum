package de.dafuqs.spectrum.compat.idwtialsimmoedm;

import io.wispforest.idwtialsimmoedm.api.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

@Environment(EnvType.CLIENT)
public class IdwtialsimmoedmCompat {
	public static void register() {
		GatherDescriptionCallback.ENCHANTMENT.register(ench -> {
			Entity player = MinecraftClient.getInstance().player;
			Text original = DefaultDescriptions.forEnchantmentRaw(ench);
			if (original == null) return null;
			// FIXME - Fix once enchantments are refactored
			if (ench instanceof SpectrumEnchantment spectrumEnchantment && !spectrumEnchantment.canEntityUse(player)) {
				return GatherDescriptionCallback.wrapDescription(original.copy().formatted(Formatting.OBFUSCATED));
			}
			return null;
		});
	}
}
