package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.entity.mob.*;
import net.minecraft.particle.*;
import net.minecraft.sound.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(SlimeEntity.class)
public interface SlimeEntityAccessor {
	
	@Invoker
	void invokeSetSize(int newSize, boolean heal);
	
	@Invoker
	ParticleEffect invokeGetParticles();
	
	@Invoker
	SoundEvent invokeGetSquishSound();
	
	@Invoker
	float invokeGetSoundVolume();
	
}