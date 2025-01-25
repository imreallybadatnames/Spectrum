package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.registry.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(RegistryOps.CachedRegistryInfoGetter.class)
public interface CachedRegistryInfoGetterAccessor {
	
	@Accessor
	RegistryWrapper.WrapperLookup getRegistriesLookup();
	
}
