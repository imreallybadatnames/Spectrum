package de.dafuqs.spectrum.helpers;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.effect.*;
import net.minecraft.util.*;

public class StatusEffectHelper {
	
	private static final Identifier INCURABLE_EFFECT_BACKGROUNDS = SpectrumCommon.locate("textures/gui/incurable_effect_backgrounds.png");
	private static final Identifier NIGHT_EFFECT_BACKGROUNDS = SpectrumCommon.locate("textures/gui/night_alchemy_effect_backgrounds.png");
	private static final Identifier DIVINITY_EFFECT_BACKGROUNDS = SpectrumCommon.locate("textures/gui/divinity_effect_backgrounds.png");
	
	public static Identifier getTexture(Identifier texture, StatusEffectInstance effect) {
		var type = effect.getEffectType();
		
		if (type == SpectrumStatusEffects.DIVINITY)
			return DIVINITY_EFFECT_BACKGROUNDS;
		
		if (isIncurable(effect) && type != SpectrumStatusEffects.ETERNAL_SLUMBER && type != SpectrumStatusEffects.FATAL_SLUMBER) {
			return INCURABLE_EFFECT_BACKGROUNDS;
		}
		
		if (type.isIn(SpectrumStatusEffectTags.NIGHT_ALCHEMY))
			return NIGHT_EFFECT_BACKGROUNDS;
		
		return texture;
	}
	
	//TODO this needs a better name. What even is this.
	public static boolean isIncurable(StatusEffectInstance instance) {
		var type = instance.getEffectType();
		if (type == SpectrumStatusEffects.ETERNAL_SLUMBER || type == SpectrumStatusEffects.FATAL_SLUMBER)
			return false;
		
		return instance.spectrum$isIncurable();
	}
}
