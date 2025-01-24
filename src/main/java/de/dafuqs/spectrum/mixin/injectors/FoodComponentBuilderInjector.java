package de.dafuqs.spectrum.mixin.injectors;

import net.minecraft.component.type.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@SuppressWarnings("unused")
public interface FoodComponentBuilderInjector {
	
	default FoodComponent.Builder setEatSeconds(float eatSeconds) {
		((_Accessor) this).spectrum$setEatSeconds(eatSeconds);
		return (FoodComponent.Builder) this;
	}
	
	@Mixin(FoodComponent.Builder.class)
	interface _Accessor {
		@Accessor("eatSeconds")
		void spectrum$setEatSeconds(float eatSeconds);
	}
	
}
