package de.dafuqs.spectrum.injectors;

import net.minecraft.component.type.*;
import org.apache.commons.lang3.*;

public interface FoodComponentBuilderInjector {
	
	default FoodComponent.Builder spectrum$setEatSeconds(float eatSeconds) {
		throw new NotImplementedException();
	}
	
}
