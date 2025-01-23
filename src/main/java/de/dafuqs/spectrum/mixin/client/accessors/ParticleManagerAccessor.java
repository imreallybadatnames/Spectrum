package de.dafuqs.spectrum.mixin.client.accessors;

import net.minecraft.client.particle.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

import java.util.*;

@Mixin(ParticleManager.class)
public interface ParticleManagerAccessor {
	
	@Accessor
	Map<Identifier, SpriteProvider> getSpriteAwareFactories();
	
}
