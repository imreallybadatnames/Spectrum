package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.passive.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(MooshroomEntity.class)
public interface MooshroomEntityAccessor {
	
	@Accessor("stewEffect")
	SuspiciousStewEffectsComponent getStewEffects();
	
	@Accessor("stewEffect")
	void setStewEffects(SuspiciousStewEffectsComponent stewEffects);
	
}
