package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;

public class SpectrumEntityAttributes {
	
	/**
	 * How vulnerable the entity is to sleep effects. The sleep effects use this value as a multiplier
	 * <1 means it is more resistant than the default, getting weaker effects
	 * >1 means it is more vulnerable
	 */
	public static final RegistryEntry<EntityAttribute> MENTAL_PRESENCE = register("mental_presence", new ClampedEntityAttribute("attribute.name.spectrum.mental_presence", 1.0, 0, 1024));
	
	
	private static RegistryEntry<EntityAttribute> register(String name, EntityAttribute attribute) {
		return Registry.registerReference(Registries.ATTRIBUTE, SpectrumCommon.locate(name), attribute);
	}
	
	public static void register() {
	
	}
	
}
