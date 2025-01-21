package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import net.minecraft.enchantment.*;
import net.minecraft.registry.*;

@SuppressWarnings("unused")
public class SpectrumEnchantments {
	
	public static final RegistryKey<Enchantment> CLOAKED_BIG_CATCH = of("cloaked/big_catch");
	public static final RegistryKey<Enchantment> CLOAKED_CLOVERS_FAVOR = of("cloaked/clovers_favor");
	public static final RegistryKey<Enchantment> CLOAKED_DISARMING = of("cloaked/disarming");
	public static final RegistryKey<Enchantment> CLOAKED_EXUBERANCE = of("cloaked/exuberance");
	public static final RegistryKey<Enchantment> CLOAKED_FIRST_STRIKE = of("cloaked/first_strike");
	public static final RegistryKey<Enchantment> CLOAKED_FOUNDRY = of("cloaked/foundry");
	public static final RegistryKey<Enchantment> CLOAKED_IMPROVED_CRITICAL = of("cloaked/improved_critical");
	public static final RegistryKey<Enchantment> CLOAKED_INDESTRUCTIBLE = of("cloaked/indestructible");
	public static final RegistryKey<Enchantment> CLOAKED_INERTIA = of("cloaked/inertia");
	public static final RegistryKey<Enchantment> CLOAKED_INEXORABLE = of("cloaked/inexorable");
	public static final RegistryKey<Enchantment> CLOAKED_INVENTORY_INSERTION = of("cloaked/inventory_insertion");
	public static final RegistryKey<Enchantment> CLOAKED_PEST_CONTROL = of("cloaked/pest_control");
	public static final RegistryKey<Enchantment> CLOAKED_RAZING = of("cloaked/razing");
	public static final RegistryKey<Enchantment> CLOAKED_RESONANCE = of("cloaked/resonance");
	public static final RegistryKey<Enchantment> CLOAKED_SERENDIPITY_REEL = of("cloaked/serendipity_reel");
	public static final RegistryKey<Enchantment> CLOAKED_SNIPING = of("cloaked/sniping");
	public static final RegistryKey<Enchantment> CLOAKED_STEADFAST = of("cloaked/steadfast");
	public static final RegistryKey<Enchantment> CLOAKED_TIGHT_GRIP = of("cloaked/tight_grip");
	public static final RegistryKey<Enchantment> CLOAKED_TREASURE_HUNTER = of("cloaked/treasure_hunter");
	public static final RegistryKey<Enchantment> CLOAKED_VOIDING = of("cloaked/voiding");
	
	public static final RegistryKey<Enchantment> BIG_CATCH = of("big_catch"); // Increase the chance to reel in entities instead of fishing loot
	public static final RegistryKey<Enchantment> CLOVERS_FAVOR = of("clovers_favor"); // Increases drop chance of <1 loot drops
	public static final RegistryKey<Enchantment> DISARMING = of("disarming"); // Drops mob equipment on hit (and players, but way less often)
	public static final RegistryKey<Enchantment> EXUBERANCE = of("exuberance"); // Drops more XP on kill
	public static final RegistryKey<Enchantment> FIRST_STRIKE = of("first_strike"); // Increased damage if enemy has full health
	public static final RegistryKey<Enchantment> FOUNDRY = of("foundry"); // applies smelting recipe before dropping items after mining
	public static final RegistryKey<Enchantment> IMPROVED_CRITICAL = of("improved_critical"); // Increased damage when landing a critical hit
	public static final RegistryKey<Enchantment> INDESTRUCTIBLE = of("indestructible"); // Make tools not use up durability
	public static final RegistryKey<Enchantment> INERTIA = of("inertia"); // Decreases mining speed, but increases with each mined block of the same type
	public static final RegistryKey<Enchantment> INEXORABLE = of("inexorable"); // prevents mining & movement slowdowns
	public static final RegistryKey<Enchantment> INVENTORY_INSERTION = of("inventory_insertion"); // don't drop items into the world, add to inv instead
	public static final RegistryKey<Enchantment> PEST_CONTROL = of("pest_control"); // Kills silverfish when mining infested blocks
	public static final RegistryKey<Enchantment> RAZING = of("razing"); // increased mining speed for very hard blocks
	public static final RegistryKey<Enchantment> RESONANCE = of("resonance"); // Silk Touch, just for different blocks
	public static final RegistryKey<Enchantment> SERENDIPITY_REEL = of("serendipity_reel"); // Increase luck when fishing
	public static final RegistryKey<Enchantment> SNIPING = of("sniping"); // Increases projectile speed => increased damage + range
	public static final RegistryKey<Enchantment> STEADFAST = of("steadfast"); // ItemStacks with this enchantment are not destroyed by cactus, fire, lava, ...
	public static final RegistryKey<Enchantment> TIGHT_GRIP = of("tight_grip"); // Increases attack speed
	public static final RegistryKey<Enchantment> TREASURE_HUNTER = of("treasure_hunter"); // Drops mob heads
	public static final RegistryKey<Enchantment> VOIDING = of("voiding"); // Voids all items mined
	
	private static RegistryKey<Enchantment> of(String id) {
		return RegistryKey.of(RegistryKeys.ENCHANTMENT, SpectrumCommon.locate(id));
	}
	
}
