package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import net.minecraft.potion.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;

public class SpectrumPotions {
	
	public static RegistryEntry<Potion> PIGMENT_POTION;
	
	private static RegistryEntry<Potion> register(String name, Potion potion) {
		return Registry.registerReference(Registries.POTION, SpectrumCommon.locate(name), potion);
	}
	
	public static void register() {
		PIGMENT_POTION = register("pigment_potion", new Potion());
	}
	
}
