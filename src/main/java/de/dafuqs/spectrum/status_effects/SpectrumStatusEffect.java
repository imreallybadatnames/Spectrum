package de.dafuqs.spectrum.status_effects;

import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import org.jetbrains.annotations.*;

public class SpectrumStatusEffect extends StatusEffect {
	
	public SpectrumStatusEffect(StatusEffectCategory category, int color) {
		super(category, color);
	}
	
	@Override
	public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
	}
	
}
