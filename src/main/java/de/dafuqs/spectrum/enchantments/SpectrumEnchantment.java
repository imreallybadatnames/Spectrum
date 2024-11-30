package de.dafuqs.spectrum.enchantments;

import de.dafuqs.revelationary.api.advancements.*;
import de.dafuqs.spectrum.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

public abstract class SpectrumEnchantment extends Enchantment {
	
	@Environment(EnvType.CLIENT)
	@Override
    public Text getName(int level) {
		Text text = super.getName(level);
		if (canEntityUse(MinecraftClient.getInstance().player)) {
			return text;
		}
		
		if (SpectrumCommon.CONFIG.NameForUnrevealedEnchantments.isBlank() && text instanceof MutableText mutableText) {
			return mutableText.formatted(Formatting.byCode('k'));
		} else {
			return Text.literal(SpectrumCommon.CONFIG.NameForUnrevealedEnchantments).setStyle(text.getStyle());
		}
	}
	
	public Identifier getUnlockAdvancementIdentifier() {
		return unlockAdvancementIdentifier;
	}
	
}
