package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.component.type.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(FoodComponentAccessor.class)
public interface FoodComponentAccessor {
	
	@Accessor("eatSeconds")
	FoodComponent.Builder setEatSeconds(float eatSeconds);
	
}
