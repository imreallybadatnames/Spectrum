package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.enchantments.*;
import net.minecraft.enchantment.*;
import net.minecraft.registry.*;

@SuppressWarnings("unused")
public class SpectrumEnchantments {
	
	public static final RegistryKey<Enchantment> BIG_CATCH = new BigCatchEnchantment(Enchantment.Rarity.RARE); // Increase the chance to reel in entities instead of fishing loot
	public static final RegistryKey<Enchantment> CLOVERS_FAVOR = new CloversFavorEnchantment(Enchantment.Rarity.RARE); // Increases drop chance of <1 loot drops
	public static final RegistryKey<Enchantment> DISARMING = new DisarmingEnchantment(Enchantment.Rarity.VERY_RARE); // Drops mob equipment on hit (and players, but way less often)
	public static final RegistryKey<Enchantment> EXUBERANCE = new ExuberanceEnchantment(Enchantment.Rarity.UNCOMMON); // Drops more XP on kill
	public static final RegistryKey<Enchantment> FIRST_STRIKE = new FirstStrikeEnchantment(Enchantment.Rarity.RARE); // Increased damage if enemy has full health
	public static final RegistryKey<Enchantment> CLOAKED_FOUNDRY = of("cloaked/foundry");
	public static final RegistryKey<Enchantment> IMPROVED_CRITICAL = new ImprovedCriticalEnchantment(Enchantment.Rarity.RARE); // Increased damage when landing a critical hit
	public static final RegistryKey<Enchantment> INDESTRUCTIBLE = new IndestructibleEnchantment(Enchantment.Rarity.RARE); // Make tools not use up durability
	public static final RegistryKey<Enchantment> INERTIA = new InertiaEnchantment(Enchantment.Rarity.VERY_RARE); // Decreases mining speed, but increases with each mined block of the same type
	public static final RegistryKey<Enchantment> INEXORABLE = new InexorableEnchantment(Enchantment.Rarity.VERY_RARE); // prevents mining & movement slowdowns
	public static final RegistryKey<Enchantment> INVENTORY_INSERTION = new InventoryInsertionEnchantment(Enchantment.Rarity.RARE); // don't drop items into the world, add to inv instead
	public static final RegistryKey<Enchantment> CLOAKED_PEST_CONTROL = of("cloaked/pest_control");
	public static final RegistryKey<Enchantment> CLOAKED_RAZING = of("cloaked/razing");
	public static final RegistryKey<Enchantment> CLOAKED_RESONANCE = of("cloaked/resonance");
	public static final RegistryKey<Enchantment> SERENDIPITY_REEL = new SerendipityReelEnchantment(Enchantment.Rarity.RARE); // Increase luck when fishing
	public static final RegistryKey<Enchantment> SNIPING = new SnipingEnchantment(Enchantment.Rarity.VERY_RARE); // Increases projectile speed => increased damage + range
	public static final RegistryKey<Enchantment> STEADFAST = new SteadfastEnchantment(Enchantment.Rarity.COMMON); // ItemStacks with this enchantment are not destroyed by cactus, fire, lava, ...
	public static final RegistryKey<Enchantment> CLOAKED_TIGHT_GRIP = of("cloaked/tight_grip");
	public static final RegistryKey<Enchantment> CLOAKED_TREASURE_HUNTER = of("cloaked/treasure_hunter");
	public static final RegistryKey<Enchantment> CLOAKED_VOIDING = of("cloaked/voiding");

	public static final RegistryKey<Enchantment> FOUNDRY = of("foundry"); // applies smelting recipe before dropping items after mining
	public static final RegistryKey<Enchantment> PEST_CONTROL = of("pest_control"); // Kills silverfish when mining infested blocks
	public static final RegistryKey<Enchantment> RAZING = of("razing"); // increased mining speed for very hard blocks
	public static final RegistryKey<Enchantment> RESONANCE = of("resonance"); // Silk Touch, just for different blocks
	public static final RegistryKey<Enchantment> TIGHT_GRIP = of("tight_grip"); // Increases attack speed
	public static final RegistryKey<Enchantment> TREASURE_HUNTER = of("treasure_hunter"); // Drops mob heads
	public static final RegistryKey<Enchantment> VOIDING = of("voiding"); // Voids all items mined

	private static RegistryKey<Enchantment> of(String id) {
		return RegistryKey.of(RegistryKeys.ENCHANTMENT, SpectrumCommon.locate(id));
	}

}
