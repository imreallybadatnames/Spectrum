package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import net.minecraft.enchantment.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.registry.tag.*;

import java.util.*;

public class SpectrumEnchantmentTags {

	public static final TagKey<Enchantment> DELETES_OVERFLOW = of("deletes_overflow");
	public static final TagKey<Enchantment> DELETES_OVERFLOW_IN_INVENTORY = of("deletes_overflow_in_inventory");
	public static final TagKey<Enchantment> DIMENSIONAL_TELEPORT = of("dimensional_teleport");
	public static final TagKey<Enchantment> NO_BLOCK_DROPS = of("no_block_drops");
	public static final TagKey<Enchantment> PREVENTS_INCANDESCENT_EXPLOSION = of("prevents_incandescent_explosion");
	public static final TagKey<Enchantment> RESONANT_BLOCK_DROPS = of("resonant_block_drops");
	public static final TagKey<Enchantment> SPECTRUM_ENCHANTMENT = of("enchantments");
	
	private static TagKey<Enchantment> of(String id) {
		return TagKey.of(RegistryKeys.ENCHANTMENT, SpectrumCommon.locate(id));
	}

}
