package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.component.type.*;
import net.minecraft.entity.passive.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(MooshroomEntity.class)
public interface MooshroomEntityAccessor {
	
	@Accessor
	SuspiciousStewEffectsComponent getStewEffects();
	
	@Accessor
	void setStewEffects(SuspiciousStewEffectsComponent stewEffects);
	
}
