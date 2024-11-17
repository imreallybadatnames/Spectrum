package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class SpectrumLootTables {
	
	// Shooting Stars
	public static final RegistryKey<LootTable> SHOOTING_STAR_BOUNCE = keyOf("entity/shooting_star/shooting_star_bounce");
	public static final RegistryKey<LootTable> COLORFUL_SHOOTING_STAR = keyOf("entity/shooting_star/colorful_shooting_star");
	public static final RegistryKey<LootTable> FIERY_SHOOTING_STAR = keyOf("entity/shooting_star/fiery_shooting_star");
	public static final RegistryKey<LootTable> GEMSTONE_SHOOTING_STAR = keyOf("entity/shooting_star/gemstone_shooting_star");
	public static final RegistryKey<LootTable> GLISTERING_SHOOTING_STAR = keyOf("entity/shooting_star/glistering_shooting_star");
	public static final RegistryKey<LootTable> PRISTINE_SHOOTING_STAR = keyOf("entity/shooting_star/pristine_shooting_star");
	
	// Fishing
	public static final RegistryKey<LootTable> UNIVERSAL_FISHING = keyOf("gameplay/universal_fishing");
	
	public static final RegistryKey<LootTable> LAVA_FISHING = keyOf("gameplay/fishing/lava/fishing");
	public static final RegistryKey<LootTable> END_FISHING = keyOf("gameplay/fishing/end/fishing");
	public static final RegistryKey<LootTable> DEEPER_DOWN_FISHING = keyOf("gameplay/fishing/deeper_down/fishing");
	public static final RegistryKey<LootTable> GOO_FISHING = keyOf("gameplay/fishing/goo/fishing");
	public static final RegistryKey<LootTable> LIQUID_CRYSTAL_FISHING = keyOf("gameplay/fishing/liquid_crystal/fishing");
	public static final RegistryKey<LootTable> MIDNIGHT_SOLUTION_FISHING = keyOf("gameplay/fishing/midnight_solution/fishing");
	
	// Entities
	public static final RegistryKey<LootTable> KINDLING_CLIPPING = keyOf("gameplay/kindling_clipping");
	public static final RegistryKey<LootTable> EGG_LAYING_WOOLY_PIG_SHEARING = keyOf("entities/egg_laying_wooly_pig_shearing");
	
	// Blocks
	public static final RegistryKey<LootTable> WEEPING_GALA_SPRIG_RESIN = keyOf("gameplay/weeping_gala_sprig_resin");
	public static final RegistryKey<LootTable> NIGHTDEW_VINE_RARE_DROP = keyOf("gameplay/nightdew_vine_rare_drop");
	
	public static final RegistryKey<LootTable> SAWBLADE_HOLLY_HARVESTING = keyOf("gameplay/sawblade_holly_harvesting");
	public static final RegistryKey<LootTable> SAWBLADE_HOLLY_SHEARING = keyOf("gameplay/sawblade_holly_shearing");
	
	public static final RegistryKey<LootTable> JADE_VINE_HARVESTING_PETALS = keyOf("gameplay/jade_vine_petal_harvesting");
	public static final RegistryKey<LootTable> JADE_VINE_HARVESTING_NECTAR = keyOf("gameplay/jade_vine_nectar_harvesting");
	
	public static final RegistryKey<LootTable> SLATE_NOXCAP_STRIPPING = keyOf("gameplay/stripping/slate_noxcap_stripping");
	public static final RegistryKey<LootTable> EBONY_NOXCAP_STRIPPING = keyOf("gameplay/stripping/ebony_noxcap_stripping");
	public static final RegistryKey<LootTable> IVORY_NOXCAP_STRIPPING = keyOf("gameplay/stripping/ivory_noxcap_stripping");
	public static final RegistryKey<LootTable> CHESTNUT_NOXCAP_STRIPPING = keyOf("gameplay/stripping/chestnut_noxcap_stripping");

	public static RegistryKey<LootTable> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.LOOT_TABLE, SpectrumCommon.locate(id));
	}

}
