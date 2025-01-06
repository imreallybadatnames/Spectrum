package de.dafuqs.spectrum.loot;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.blocks.mob_head.*;
import de.dafuqs.spectrum.compat.gofish.*;
import de.dafuqs.spectrum.entity.predicates.*;
import de.dafuqs.spectrum.loot.functions.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.loot.v3.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.*;
import net.minecraft.loot.*;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.*;
import net.minecraft.loot.entry.*;
import net.minecraft.loot.provider.number.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SpectrumLootPoolModifiers {
	
	private static final Map<RegistryKey<LootTable>, TreasureHunterDropDefinition> treasureHunterLootPools = new HashMap<>() {{
		// Additional vanilla head drops
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/creeper")), new TreasureHunterDropDefinition(Items.CREEPER_HEAD, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/skeleton")), new TreasureHunterDropDefinition(Items.SKELETON_SKULL, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/wither_skeleton")), new TreasureHunterDropDefinition(Items.WITHER_SKELETON_SKULL, 0.1F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/zombie")), new TreasureHunterDropDefinition(Items.ZOMBIE_HEAD, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/piglin")), new TreasureHunterDropDefinition(Items.PIGLIN_HEAD, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/piglin_brute")), new TreasureHunterDropDefinition(Items.PIGLIN_HEAD, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/ender_dragon")), new TreasureHunterDropDefinition(Items.DRAGON_HEAD, 0.35F)); // why not!
		
		// Spectrum head drops
		// ATTENTION: No specific enough loot tables exist for fox, axolotl, parrot and shulker variants.
		// Those are handled separately in setup()
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/sheep")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHEEP).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/bat")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.BAT).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/blaze")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.BLAZE).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/cat")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.CAT).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/cave_spider")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.CAVE_SPIDER).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/chicken")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.CHICKEN).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/cow")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.COW).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/donkey")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.DONKEY).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/drowned")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.DROWNED).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/elder_guardian")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.ELDER_GUARDIAN).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/enderman")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.ENDERMAN).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/endermite")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.ENDERMITE).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/evoker")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.EVOKER).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/ghast")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.GHAST).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/guardian")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.GUARDIAN).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/hoglin")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.HOGLIN).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/horse")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.HORSE).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/husk")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.HUSK).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/illusioner")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.ILLUSIONER).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/iron_golem")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.IRON_GOLEM).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/llama")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LLAMA).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/magma_cube")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.MAGMA_CUBE).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/mule")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.MULE).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/ocelot")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.OCELOT).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/panda")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.PANDA).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/phantom")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.PHANTOM).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/pig")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.PIG).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/polar_bear")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.POLAR_BEAR).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/pufferfish")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.PUFFERFISH).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/rabbit")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.RABBIT).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/ravager")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.RAVAGER).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/salmon")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SALMON).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/silverfish")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SILVERFISH).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/slime")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SLIME).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/snow_golem")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SNOW_GOLEM).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/spider")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SPIDER).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/squid")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SQUID).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/stray")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.STRAY).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/strider")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.STRIDER).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/trader_llama")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LLAMA).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/turtle")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.TURTLE).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/vex")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.VEX).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/villager")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.VILLAGER).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/vindicator")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.VINDICATOR).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/wandering_trader")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.WANDERING_TRADER).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/witch")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.WITCH).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/wither")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.WITHER).get().asItem(), 0.15F)); // he has 3 heads, after all!
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/wolf")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.WOLF).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/zoglin")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.ZOGLIN).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/zombie_villager")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.ZOMBIE_VILLAGER).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/zombified_piglin")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.ZOMBIFIED_PIGLIN).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/bee")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.BEE).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/tropical_fish")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.TROPICAL_FISH).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/goat")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.GOAT).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/glow_squid")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.GLOW_SQUID).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/warden")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.WARDEN).get().asItem(), 0.2F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/tadpole")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.TADPOLE).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/allay")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.ALLAY).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/camel")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.CAMEL).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/sniffer")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SNIFFER).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/skeleton_horse")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SKELETON_HORSE).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/zombie_horse")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.ZOMBIE_HORSE).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/dolphin")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.DOLPHIN).get().asItem(), 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/pillager")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.PILLAGER).get().asItem(), 0.02F));
		
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("spectrum:entities/egg_laying_wooly_pig")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.EGG_LAYING_WOOLY_PIG).get().asItem(), 0.1F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("spectrum:entities/kindling")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.KINDLING).get().asItem(), 0.1F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("spectrum:entities/preservation_turret")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.PRESERVATION_TURRET).get().asItem(), 0.1F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("spectrum:entities/monstrosity")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.MONSTROSITY).get().asItem(), 0.1F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("spectrum:entities/eraser")), new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.ERASER).get().asItem(), 0.1F));
	}};
	
	public static void setup() {
		LootTableEvents.MODIFY.register((key, builder, lootTableSource, wrapperLookup) -> {
			// Treasure hunter pools
			
			if (treasureHunterLootPools.containsKey(key)) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				TreasureHunterDropDefinition dropDefinition = treasureHunterLootPools.get(key);
				builder.pool(getLootPool(enchant, dropDefinition));
				// Some treasure hunter pools use custom loot conditions
				// because vanillas are too generic (fox/snow fox both use "fox" loot table)
			} else if (key.equals(LootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY) || key.equals(LootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY) ||
					key.equals(LootTables.TRAIL_RUINS_RARE_ARCHAEOLOGY) || key.equals(LootTables.DESERT_PYRAMID_ARCHAEOLOGY) || key.equals(LootTables.DESERT_WELL_ARCHAEOLOGY)) {
				builder.modifyPools(modifier -> modifier.with(ItemEntry.builder(SpectrumItems.NIGHTDEW_SPROUT).weight(2).quality(-1)));
			} else if (key.equals(LootTables.SNIFFER_DIGGING_GAMEPLAY)) {
				builder.modifyPools(modifier -> {
					modifier.with(ItemEntry.builder(SpectrumBlocks.WEEPING_GALA_SPRIG).weight(1));
					modifier.with(ItemEntry.builder(SpectrumItems.NIGHTDEW_SPROUT).weight(2));
				});
			} else if (key.equals(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("entities/fox")))) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				builder.pool(getFoxLootPool(enchant, FoxEntity.Type.RED, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.FOX).get().asItem(), 0.02F)));
				builder.pool(getFoxLootPool(enchant, FoxEntity.Type.SNOW, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.FOX_ARCTIC).get().asItem(), 0.02F)));
			} else if (key.equals(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("entities/mooshroom")))) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				builder.pool(getMooshroomLootPool(enchant, MooshroomEntity.Type.BROWN, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.MOOSHROOM_BROWN).get().asItem(), 0.02F)));
				builder.pool(getMooshroomLootPool(enchant, MooshroomEntity.Type.RED, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.MOOSHROOM_RED).get().asItem(), 0.02F)));
			} else if (key.equals(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("entities/shulker")))) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				builder.pool(getShulkerLootPool(enchant, null, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.BLACK, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_BLACK).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.BLUE, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_BLUE).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.BROWN, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_BROWN).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.CYAN, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_CYAN).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.GRAY, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_GRAY).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.GREEN, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_GREEN).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.LIGHT_BLUE, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_LIGHT_BLUE).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.LIGHT_GRAY, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_LIGHT_GRAY).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.LIME, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_LIME).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.MAGENTA, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_MAGENTA).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.ORANGE, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_ORANGE).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.PINK, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_PINK).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.PURPLE, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_PURPLE).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.RED, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_RED).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.WHITE, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_WHITE).get().asItem(), 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.YELLOW, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.SHULKER_YELLOW).get().asItem(), 0.05F)));
			} else if (key.equals(RegistryKey.of(RegistryKeys.LOOT_TABLE, SpectrumCommon.locate("entities/lizard")))) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				builder.pool(getLizardLootPool(enchant, InkColors.BLACK, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_BLACK).get().asItem(), 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.BLUE, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_BLUE).get().asItem(), 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.BROWN, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_BROWN).get().asItem(), 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.CYAN, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_CYAN).get().asItem(), 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.GRAY, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_GRAY).get().asItem(), 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.GREEN, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_GREEN).get().asItem(), 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.LIGHT_BLUE, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_LIGHT_BLUE).get().asItem(), 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.LIGHT_GRAY, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_LIGHT_GRAY).get().asItem(), 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.LIME, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_LIME).get().asItem(), 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.MAGENTA, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_MAGENTA).get().asItem(), 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.ORANGE, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_ORANGE).get().asItem(), 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.PINK, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_PINK).get().asItem(), 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.PURPLE, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_PURPLE).get().asItem(), 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.RED, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_RED).get().asItem(), 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.WHITE, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_WHITE).get().asItem(), 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.YELLOW, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.LIZARD_YELLOW).get().asItem(), 0.05F)));
			} else if (key.equals(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("entities/axolotl")))) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				builder.pool(getAxolotlLootPool(enchant, AxolotlEntity.Variant.BLUE, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.AXOLOTL_BLUE).get().asItem(), 0.02F)));
				builder.pool(getAxolotlLootPool(enchant, AxolotlEntity.Variant.CYAN, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.AXOLOTL_CYAN).get().asItem(), 0.02F)));
				builder.pool(getAxolotlLootPool(enchant, AxolotlEntity.Variant.GOLD, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.AXOLOTL_GOLD).get().asItem(), 0.02F)));
				builder.pool(getAxolotlLootPool(enchant, AxolotlEntity.Variant.LUCY, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.AXOLOTL_LEUCISTIC).get().asItem(), 0.02F)));
				builder.pool(getAxolotlLootPool(enchant, AxolotlEntity.Variant.WILD, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.AXOLOTL_WILD).get().asItem(), 0.02F)));
			} else if (key.equals(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("entities/parrot")))) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				builder.pool(getParrotLootPool(enchant, ParrotEntity.Variant.RED_BLUE, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.PARROT_RED).get().asItem(), 0.02F)));
				builder.pool(getParrotLootPool(enchant, ParrotEntity.Variant.BLUE, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.PARROT_BLUE).get().asItem(), 0.02F)));
				builder.pool(getParrotLootPool(enchant, ParrotEntity.Variant.GREEN, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.PARROT_GREEN).get().asItem(), 0.02F)));
				builder.pool(getParrotLootPool(enchant, ParrotEntity.Variant.YELLOW_BLUE, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.PARROT_CYAN).get().asItem(), 0.02F)));
				builder.pool(getParrotLootPool(enchant, ParrotEntity.Variant.GRAY, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.PARROT_GRAY).get().asItem(), 0.02F)));
			} else if (key.equals(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("entities/frog")))) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				builder.pool(getFrogLootPool(enchant, FrogVariant.TEMPERATE, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.FROG_TEMPERATE).get().asItem(), 0.02F)));
				builder.pool(getFrogLootPool(enchant, FrogVariant.COLD, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.FROG_COLD).get().asItem(), 0.02F)));
				builder.pool(getFrogLootPool(enchant, FrogVariant.WARM, new TreasureHunterDropDefinition(SpectrumSkullBlock.getBlock(SpectrumSkullType.FROG_WARM).get().asItem(), 0.02F)));
			} else if (GoFishCompat.isLoaded()) {
				//Go-Fish compat: fishing of crates & go-fish fishies
				if (key.equals(SpectrumLootTables.LAVA_FISHING)) {
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.NETHER_FISH_LOOT_TABLE_ID).weight(80).quality(-1).build()));
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.NETHER_CRATES_LOOT_TABLE_ID).weight(5).quality(2).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().typeSpecific(FishingHookPredicate.of(true)).build()))));
				} else if (key.equals(SpectrumLootTables.END_FISHING)) {
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.END_FISH_LOOT_TABLE_ID).weight(90).quality(-1).build()));
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.END_CRATES_LOOT_TABLE_ID).weight(5).quality(2).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().typeSpecific(FishingHookPredicate.of(true)).build()))));
				} else if (key.equals(SpectrumLootTables.DEEPER_DOWN_FISHING)) {
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.DEFAULT_CRATES_LOOT_TABLE_ID).weight(5).quality(2).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().typeSpecific(FishingHookPredicate.of(true)).build()))));
				} else if (key.equals(SpectrumLootTables.GOO_FISHING)) {
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.DEFAULT_CRATES_LOOT_TABLE_ID).weight(5).quality(2).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().typeSpecific(FishingHookPredicate.of(true)).build()))));
				} else if (key.equals(SpectrumLootTables.LIQUID_CRYSTAL_FISHING)) {
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.DEFAULT_CRATES_LOOT_TABLE_ID).weight(5).quality(2).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().typeSpecific(FishingHookPredicate.of(true)).build()))));
				} else if (key.equals(SpectrumLootTables.MIDNIGHT_SOLUTION_FISHING)) {
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.DEFAULT_CRATES_LOOT_TABLE_ID).weight(5).quality(2).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().typeSpecific(FishingHookPredicate.of(true)).build()))));
				}
			}
		});
	}
	
	private static RegistryEntry.Reference<Enchantment> getTreasureHunter(RegistryWrapper.WrapperLookup wrapperLookup) {
		RegistryEntry.Reference<Enchantment> enchant;
		RegistryWrapper.Impl<Enchantment> registryWrapper = wrapperLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
		enchant = registryWrapper.getOrThrow(SpectrumEnchantments.TREASURE_HUNTER);
		return enchant;
	}
	
	public static LootCondition.Builder Lootconditionbuilder(RegistryEntry.Reference<Enchantment> enchantment, float chance) {
		return () -> new RandomChanceWithEnchantedBonusLootCondition(chance, new EnchantmentLevelBasedValue.Linear(chance, chance), enchantment);
	}
	
	private static LootPool getLootPool(RegistryEntry.Reference<Enchantment> enchantment, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(Lootconditionbuilder(enchantment, dropDefinition.chancePerLevel).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private static LootPool getFoxLootPool(RegistryEntry.Reference<Enchantment> enchantment, FoxEntity.Type foxType, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(Lootconditionbuilder(enchantment, dropDefinition.chancePerLevel).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(EntitySubPredicateTypes.FOX.createPredicate(foxType)).build()).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private static LootPool getMooshroomLootPool(RegistryEntry.Reference<Enchantment> enchantment, MooshroomEntity.Type mooshroomType, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(Lootconditionbuilder(enchantment, dropDefinition.chancePerLevel).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(EntitySubPredicateTypes.MOOSHROOM.createPredicate(mooshroomType)).build()).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private static LootPool getShulkerLootPool(RegistryEntry.Reference<Enchantment> enchantment, @Nullable DyeColor dyeColor, TreasureHunterDropDefinition dropDefinition) {
		Optional<DyeColor> c = dyeColor == null ? Optional.empty() : Optional.of(dyeColor);

		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(Lootconditionbuilder(enchantment, dropDefinition.chancePerLevel).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(new ShulkerPredicate(Optional.ofNullable(dyeColor))).build()).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private static LootPool getLizardLootPool(RegistryEntry.Reference<Enchantment> enchantment, InkColor linkColor, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(Lootconditionbuilder(enchantment, dropDefinition.chancePerLevel).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(new LizardPredicate(Optional.of(linkColor), Optional.empty(), Optional.empty())).build()).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private static LootPool getAxolotlLootPool(RegistryEntry.Reference<Enchantment> enchantment, AxolotlEntity.Variant variant, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(Lootconditionbuilder(enchantment, dropDefinition.chancePerLevel).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(EntitySubPredicateTypes.AXOLOTL.createPredicate(variant)).build()).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private static LootPool getFrogLootPool(RegistryEntry.Reference<Enchantment> enchantment, RegistryKey<FrogVariant> variant, TreasureHunterDropDefinition dropDefinition) {
		RegistryEntry<FrogVariant> entry = Registries.FROG_VARIANT.entryOf(variant);
		
		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(Lootconditionbuilder(enchantment, dropDefinition.chancePerLevel).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(EntitySubPredicateTypes.FROG.createPredicate(RegistryEntryList.of(entry))).build()).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private static LootPool getParrotLootPool(RegistryEntry.Reference<Enchantment> enchantment, ParrotEntity.Variant variant, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(Lootconditionbuilder(enchantment, dropDefinition.chancePerLevel).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(EntitySubPredicateTypes.PARROT.createPredicate(variant)).build()).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private record TreasureHunterDropDefinition(Item drop, float chancePerLevel, Identifier advancementUnlockId) {
		
		public TreasureHunterDropDefinition(Item drop, float chancePerLevel) {
			this(drop, chancePerLevel, Registries.ITEM.getId(drop));
		}
		
	}
	
}
