package de.dafuqs.spectrum.injectors;

public interface EntityStatusEffectS2CPacketInjector {
	
	default boolean spectrum$isIncurable() {
		return false;
	}
	
}
