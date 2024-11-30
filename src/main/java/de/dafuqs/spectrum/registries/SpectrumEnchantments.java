package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.enchantments.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.registry.*;

@SuppressWarnings("unused")
public class SpectrumEnchantments {
	
	public static final RegistryKey<Enchantment> RESONANCE = of("cloaked/resonance"); // Silk Touch, just for different blocks
	public static final RegistryKey<Enchantment> VOIDING = of("cloaked/voiding"); // Voids all items mined
	public static final RegistryKey<Enchantment> PEST_CONTROL = of("cloaked/pest_control"); // Kills silverfish when mining infested blocks
	public static final SpectrumEnchantment FOUNDRY = new FoundryEnchantment(Enchantment.Rarity.RARE, SpectrumAdvancements.ENCHANTMENTS_FOUNDRY, EquipmentSlot.MAINHAND); // applies smelting recipe before dropping items after mining
	public static final SpectrumEnchantment INVENTORY_INSERTION = new InventoryInsertionEnchantment(Enchantment.Rarity.RARE, SpectrumAdvancements.ENCHANTMENTS_INVENTORY_INSERTION, EquipmentSlot.MAINHAND); // don't drop items into the world, add to inv instead
	public static final SpectrumEnchantment EXUBERANCE = new ExuberanceEnchantment(Enchantment.Rarity.UNCOMMON, SpectrumAdvancements.ENCHANTMENTS_EXUBERANCE, EquipmentSlot.MAINHAND); // Drops more XP on kill
	public static final SpectrumEnchantment TREASURE_HUNTER = new TreasureHunterEnchantment(Enchantment.Rarity.RARE, SpectrumAdvancements.ENCHANTMENTS_TREASURE_HUNTER, EquipmentSlot.MAINHAND); // Drops mob heads
	public static final SpectrumEnchantment DISARMING = new DisarmingEnchantment(Enchantment.Rarity.VERY_RARE, SpectrumAdvancements.ENCHANTMENTS_DISARMING, EquipmentSlot.MAINHAND); // Drops mob equipment on hit (and players, but way less often)
	public static final SpectrumEnchantment FIRST_STRIKE = new FirstStrikeEnchantment(Enchantment.Rarity.RARE, SpectrumAdvancements.ENCHANTMENTS_FIRST_STRIKE, EquipmentSlot.MAINHAND); // Increased damage if enemy has full health
	public static final SpectrumEnchantment IMPROVED_CRITICAL = new ImprovedCriticalEnchantment(Enchantment.Rarity.RARE, SpectrumAdvancements.ENCHANTMENTS_IMPROVED_CRITICAL, EquipmentSlot.MAINHAND); // Increased damage when landing a critical hit
	public static final SpectrumEnchantment INERTIA = new InertiaEnchantment(Enchantment.Rarity.VERY_RARE, SpectrumAdvancements.ENCHANTMENTS_INERTIA, EquipmentSlot.MAINHAND); // Decreases mining speed, but increases with each mined block of the same type
	public static final SpectrumEnchantment CLOVERS_FAVOR = new CloversFavorEnchantment(Enchantment.Rarity.RARE, SpectrumAdvancements.ENCHANTMENTS_CLOVERS_FAVOR, EquipmentSlot.MAINHAND); // Increases drop chance of <1 loot drops
	public static final SpectrumEnchantment SNIPING = new SnipingEnchantment(Enchantment.Rarity.VERY_RARE, SpectrumAdvancements.ENCHANTMENTS_SNIPING, EquipmentSlot.MAINHAND); // Increases projectile speed => increased damage + range
	public static final RegistryKey<Enchantment> TIGHT_GRIP = of("cloaked/tight_grip"); // Increases attack speed
	public static final SpectrumEnchantment STEADFAST = new SteadfastEnchantment(Enchantment.Rarity.COMMON, SpectrumAdvancements.ENCHANTMENTS_STEADFAST, EquipmentSlot.MAINHAND); // ItemStacks with this enchantment are not destroyed by cactus, fire, lava, ...
	public static final SpectrumEnchantment INDESTRUCTIBLE = new IndestructibleEnchantment(Enchantment.Rarity.RARE, SpectrumAdvancements.ENCHANTMENTS_INDESTRUCTIBLE, EquipmentSlot.MAINHAND); // Make tools not use up durability
	public static final SpectrumEnchantment BIG_CATCH = new BigCatchEnchantment(Enchantment.Rarity.RARE, SpectrumAdvancements.ENCHANTMENTS_BIG_CATCH, EquipmentSlot.MAINHAND); // Increase the chance to reel in entities instead of fishing loot
	public static final SpectrumEnchantment SERENDIPITY_REEL = new SerendipityReelEnchantment(Enchantment.Rarity.RARE, SpectrumAdvancements.ENCHANTMENTS_SERENDIPITY_REEL, EquipmentSlot.MAINHAND); // Increase luck when fishing
	public static final SpectrumEnchantment RAZING = new RazingEnchantment(Enchantment.Rarity.UNCOMMON, SpectrumAdvancements.ENCHANTMENTS_RAZING, EquipmentSlot.MAINHAND); // increased mining speed for very hard blocks
	public static final SpectrumEnchantment INEXORABLE = new InexorableEnchantment(Enchantment.Rarity.VERY_RARE, SpectrumAdvancements.ENCHANTMENTS_INEXORABLE, EquipmentSlot.MAINHAND, EquipmentSlot.CHEST, EquipmentSlot.OFFHAND); // prevents mining & movement slowdowns

	private static RegistryKey<Enchantment> of(String id) {
		return RegistryKey.of(RegistryKeys.ENCHANTMENT, SpectrumCommon.locate(id));
	}

}
