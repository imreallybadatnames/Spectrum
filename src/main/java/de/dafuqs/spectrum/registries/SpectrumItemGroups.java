package de.dafuqs.spectrum.registries;

import de.dafuqs.fractal.api.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.api.item_group.*;
import de.dafuqs.spectrum.blocks.memory.*;
import de.dafuqs.spectrum.blocks.mob_head.*;
import de.dafuqs.spectrum.compat.*;
import de.dafuqs.spectrum.compat.ae2.*;
import de.dafuqs.spectrum.compat.create.*;
import de.dafuqs.spectrum.compat.gobber.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.items.food.beverages.*;
import de.dafuqs.spectrum.recipe.titration_barrel.*;
import net.fabricmc.fabric.api.itemgroup.v1.*;
import net.minecraft.block.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.text.*;

@SuppressWarnings("unused")
public class SpectrumItemGroups {
	
	public static final ItemGroup MAIN = FabricItemGroup.builder()
			.icon(() -> new ItemStack(SpectrumBlocks.PEDESTAL_ALL_BASIC))
			.entries((displayContext, entries) -> {
				entries.add(SpectrumBlocks.PEDESTAL_ALL_BASIC, ItemGroup.StackVisibility.PARENT_TAB_ONLY);
				for (ItemSubGroup subGroup : SpectrumItemGroups.MAIN.fractal$getChildren()) {
					entries.addAll(subGroup.getSearchTabStacks(), ItemGroup.StackVisibility.SEARCH_TAB_ONLY);
				}
			})
			.noRenderedName()
			.displayName(Text.translatable("itemGroup.spectrum"))
			.build();
	
	public static void register() {
		Registry.register(Registries.ITEM_GROUP, ItemGroupIDs.MAIN_GROUP_ID, MAIN);
	}
	
	public static final ItemSubGroup EQUIPMENT = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_EQUIPMENT, Text.translatable("itemGroup.spectrum.equipment"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				addEquipmentEntry(SpectrumItems.GUIDEBOOK, entries);
				addEquipmentEntry(SpectrumItems.PAINTBRUSH, entries);
				addEquipmentEntry(SpectrumItems.BOTTLE_OF_FADING, entries);
				addEquipmentEntry(SpectrumItems.BOTTLE_OF_FAILING, entries);
				addEquipmentEntry(SpectrumItems.BOTTLE_OF_RUIN, entries);
				addEquipmentEntry(SpectrumItems.BOTTLE_OF_FORFEITURE, entries);
				addEquipmentEntry(SpectrumItems.BOTTLE_OF_DECAY_AWAY, entries);
				addEquipmentEntry(SpectrumItems.MULTITOOL, entries);
				addEquipmentEntry(SpectrumItems.TENDER_PICKAXE, entries);
				addEquipmentEntry(SpectrumItems.LUCKY_PICKAXE, entries);
				addEquipmentEntry(SpectrumItems.RAZOR_FALCHION, entries);
				addEquipmentEntry(SpectrumItems.OBLIVION_PICKAXE, entries);
				addEquipmentEntry(SpectrumItems.RESONANT_PICKAXE, entries);
				addEquipmentEntry(SpectrumItems.DRAGONRENDING_PICKAXE, entries);
				addEquipmentEntry(SpectrumItems.LAGOON_ROD, entries);
				addEquipmentEntry(SpectrumItems.MOLTEN_ROD, entries);
				addEquipmentEntry(SpectrumItems.FETCHLING_HELMET, entries);
				addEquipmentEntry(SpectrumItems.FEROCIOUS_CHESTPLATE, entries);
				addEquipmentEntry(SpectrumItems.SYLPH_LEGGINGS, entries);
				addEquipmentEntry(SpectrumItems.OREAD_BOOTS, entries);
				addEquipmentEntry(SpectrumItems.BEDROCK_PICKAXE, entries);
				addEquipmentEntry(SpectrumItems.BEDROCK_AXE, entries);
				addEquipmentEntry(SpectrumItems.BEDROCK_SHOVEL, entries);
				addEquipmentEntry(SpectrumItems.BEDROCK_SWORD, entries);
				addEquipmentEntry(SpectrumItems.BEDROCK_HOE, entries);
				addEquipmentEntry(SpectrumItems.BEDROCK_BOW, entries);
				addEquipmentEntry(SpectrumItems.BEDROCK_CROSSBOW, entries);
				addEquipmentEntry(SpectrumItems.BEDROCK_SHEARS, entries);
				addEquipmentEntry(SpectrumItems.BEDROCK_FISHING_ROD, entries);
				addEquipmentEntry(SpectrumItems.BEDROCK_HELMET, entries);
				addEquipmentEntry(SpectrumItems.BEDROCK_CHESTPLATE, entries);
				addEquipmentEntry(SpectrumItems.BEDROCK_LEGGINGS, entries);
				addEquipmentEntry(SpectrumItems.BEDROCK_BOOTS, entries);
				addEquipmentEntry(SpectrumItems.MALACHITE_WORKSTAFF, entries);
				addEquipmentEntry(SpectrumItems.MALACHITE_ULTRA_GREATSWORD, entries);
				addEquipmentEntry(SpectrumItems.MALACHITE_CROSSBOW, entries);
				addEquipmentEntry(SpectrumItems.MALACHITE_BIDENT, entries);
				addEquipmentEntry(SpectrumItems.GLASS_CREST_WORKSTAFF, entries);
				addEquipmentEntry(SpectrumItems.GLASS_CREST_ULTRA_GREATSWORD, entries);
				addEquipmentEntry(SpectrumItems.FEROCIOUS_GLASS_CREST_BIDENT, entries);
				addEquipmentEntry(SpectrumItems.FRACTAL_GLASS_CREST_BIDENT, entries);
				addEquipmentEntry(SpectrumItems.GLASS_CREST_CROSSBOW, entries);
				addEquipmentEntry(SpectrumItems.MALACHITE_GLASS_ARROW, entries);
				addEquipmentEntry(SpectrumItems.TOPAZ_GLASS_ARROW, entries);
				addEquipmentEntry(SpectrumItems.AMETHYST_GLASS_ARROW, entries);
				addEquipmentEntry(SpectrumItems.CITRINE_GLASS_ARROW, entries);
				addEquipmentEntry(SpectrumItems.ONYX_GLASS_ARROW, entries);
				addEquipmentEntry(SpectrumItems.MOONSTONE_GLASS_ARROW, entries);
				addEquipmentEntry(SpectrumItems.AZURITE_GLASS_AMPOULE, entries);
				addEquipmentEntry(SpectrumItems.MALACHITE_GLASS_AMPOULE, entries);
				addEquipmentEntry(SpectrumItems.BLOODSTONE_GLASS_AMPOULE, entries);
				addEquipmentEntry(SpectrumItems.DREAMFLAYER, entries);
				addEquipmentEntry(SpectrumItems.NIGHTFALLS_BLADE, entries);
				addEquipmentEntry(SpectrumItems.DRACONIC_TWINSWORD, entries);
				addEquipmentEntry(SpectrumItems.DRAGON_TALON, entries);
				addEquipmentEntry(SpectrumItems.KNOTTED_SWORD, entries);
				addEquipmentEntry(SpectrumItems.NECTAR_LANCE, entries);
				addEquipmentEntry(SpectrumItems.OMNI_ACCELERATOR, entries);
				addEquipmentEntry(SpectrumItems.FANCIFUL_TUFF_RING, entries);
				addEquipmentEntry(SpectrumItems.FANCIFUL_BELT, entries);
				addEquipmentEntry(SpectrumItems.FANCIFUL_PENDANT, entries);
				addEquipmentEntry(SpectrumItems.FANCIFUL_CIRCLET, entries);
				addEquipmentEntry(SpectrumItems.FANCIFUL_GLOVES, entries);
				addEquipmentEntry(SpectrumItems.FANCIFUL_BISMUTH_RING, entries);
				addEquipmentEntry(SpectrumItems.GLOW_VISION_GOGGLES, entries);
				addEquipmentEntry(SpectrumItems.JEOPARDANT, entries);
				addEquipmentEntry(SpectrumItems.SEVEN_LEAGUE_BOOTS, entries);
				entries.add(SpectrumEnchantmentHelper.getMaxEnchantedStack(SpectrumItems.SEVEN_LEAGUE_BOOTS));
				entries.add(SpectrumItems.COTTON_CLOUD_BOOTS);
				entries.add(SpectrumItems.RADIANCE_PIN);
				entries.add(SpectrumItems.TOTEM_PENDANT);
				entries.add(SpectrumItems.TAKE_OFF_BELT);
				entries.add(SpectrumEnchantmentHelper.getMaxEnchantedStack(SpectrumItems.TAKE_OFF_BELT));
				entries.add(SpectrumItems.AZURE_DIKE_BELT);
				entries.add(SpectrumItems.AZURE_DIKE_RING);
				entries.add(SpectrumItems.SHIELDGRASP_AMULET);
				entries.add(SpectrumItems.SHIELDGRASP_AMULET.getFullStack());
				entries.add(SpectrumItems.HEARTSINGERS_REWARD);
				entries.add(SpectrumItems.HEARTSINGERS_REWARD.getFullStack());
				entries.add(SpectrumItems.GLOVES_OF_DAWNS_GRASP);
				entries.add(SpectrumItems.GLOVES_OF_DAWNS_GRASP.getFullStack());
				entries.add(SpectrumItems.RING_OF_PURSUIT);
				entries.add(SpectrumItems.RING_OF_PURSUIT.getFullStack());
				entries.add(SpectrumItems.RING_OF_DENSER_STEPS);
				entries.add(SpectrumItems.RING_OF_DENSER_STEPS.getFullStack());
				entries.add(SpectrumItems.RING_OF_AERIAL_GRACE);
				entries.add(SpectrumItems.RING_OF_AERIAL_GRACE.getFullStack());
				entries.add(SpectrumItems.LAURELS_OF_SERENITY);
				entries.add(SpectrumItems.LAURELS_OF_SERENITY.getFullStack());
				entries.add(SpectrumItems.GLEAMING_PIN);
				entries.add(SpectrumEnchantmentHelper.getMaxEnchantedStack(SpectrumItems.GLEAMING_PIN));
				entries.add(SpectrumItems.LESSER_POTION_PENDANT);
				entries.add(SpectrumItems.GREATER_POTION_PENDANT);
				entries.add(SpectrumItems.ASHEN_CIRCLET);
				entries.add(SpectrumItems.WEEPING_CIRCLET);
				entries.add(SpectrumItems.PUFF_CIRCLET);
				entries.add(SpectrumItems.WHISPY_CIRCLET);
				entries.add(SpectrumItems.AZURESQUE_DIKE_CORE);
				entries.add(SpectrumItems.CIRCLET_OF_ARROGANCE);
				entries.add(SpectrumItems.AETHER_GRACED_NECTAR_GLOVES);
				entries.add(SpectrumItems.NEAT_RING);
				entries.add(SpectrumItems.CRAFTING_TABLET);
				entries.add(SpectrumBlocks.BOTTOMLESS_BUNDLE);
				entries.add(SpectrumEnchantmentHelper.getMaxEnchantedStack(SpectrumBlocks.BOTTOMLESS_BUNDLE.asItem()));
				
				entries.add(SpectrumItems.KNOWLEDGE_GEM);
				entries.add(SpectrumEnchantmentHelper.getMaxEnchantedStack(SpectrumItems.KNOWLEDGE_GEM));
				
				ItemStack knowledgeGemStack = SpectrumItems.KNOWLEDGE_GEM.getDefaultStack();
				ExperienceStorageItem.addStoredExperience(displayContext.lookup(), knowledgeGemStack, SpectrumItems.KNOWLEDGE_GEM.getMaxStoredExperience(displayContext.lookup(), knowledgeGemStack));
				entries.add(knowledgeGemStack);
				
				ItemStack enchantedKnowledgeGemStack = SpectrumEnchantmentHelper.getMaxEnchantedStack(SpectrumItems.KNOWLEDGE_GEM);
				ExperienceStorageItem.addStoredExperience(displayContext.lookup(), enchantedKnowledgeGemStack, SpectrumItems.KNOWLEDGE_GEM.getMaxStoredExperience(displayContext.lookup(), enchantedKnowledgeGemStack));
				entries.add(enchantedKnowledgeGemStack);
				
				entries.add(SpectrumItems.CELESTIAL_POCKETWATCH);
				entries.add(SpectrumItems.GILDED_BOOK);
				entries.add(SpectrumItems.ENCHANTMENT_CANVAS);
				entries.add(SpectrumItems.NIGHT_SALTS);
				entries.add(SpectrumItems.SOOTHING_BOUQUET);
				entries.add(SpectrumItems.CONCEALING_OILS);
				entries.add(SpectrumItems.BITTER_OILS);
				entries.add(SpectrumItems.EVERPROMISE_RIBBON);
				entries.add(SpectrumItems.BAG_OF_HOLDING);
				entries.add(SpectrumItems.RADIANCE_STAFF);
				entries.add(SpectrumItems.NATURES_STAFF);
				entries.add(SpectrumEnchantmentHelper.getMaxEnchantedStack(SpectrumItems.NATURES_STAFF));
				entries.add(SpectrumItems.STAFF_OF_REMEMBRANCE);
				entries.add(SpectrumItems.CONSTRUCTORS_STAFF);
				entries.add(SpectrumItems.EXCHANGING_STAFF);
				displayContext.lookup().getOptionalWrapper(RegistryKeys.ENCHANTMENT).ifPresent(impl -> {
					impl.getOptional(Enchantments.FORTUNE).ifPresent(enchantment -> entries.add(SpectrumEnchantmentHelper.addOrUpgradeEnchantment(SpectrumItems.EXCHANGING_STAFF.getDefaultStack(), enchantment, 3, false, false).getRight()));
					impl.getOptional(Enchantments.SILK_TOUCH).ifPresent(enchantment -> entries.add(SpectrumEnchantmentHelper.addOrUpgradeEnchantment(SpectrumItems.EXCHANGING_STAFF.getDefaultStack(), enchantment, 1, false, false).getRight()));
					impl.getOptional(SpectrumEnchantments.CLOAKED_RESONANCE).ifPresent(enchantment -> entries.add(SpectrumEnchantmentHelper.addOrUpgradeEnchantment(SpectrumItems.EXCHANGING_STAFF.getDefaultStack(), enchantment, 1, false, false).getRight()));
				});
				entries.add(SpectrumItems.BLOCK_FLOODER);
				entries.add(SpectrumItems.ENDER_SPLICE);
				entries.add(SpectrumEnchantmentHelper.getMaxEnchantedStack(SpectrumItems.ENDER_SPLICE));
				entries.add(SpectrumItems.PERTURBED_EYE);
				entries.add(SpectrumBlocks.PARAMETRIC_MINING_DEVICE);
				entries.add(SpectrumBlocks.THREAT_CONFLUX);
				entries.add(SpectrumItems.PIPE_BOMB);
				entries.add(SpectrumItems.CRESCENT_CLOCK);
				entries.add(SpectrumItems.ARTISANS_ATLAS);
				entries.add(SpectrumBlocks.PRIMORDIAL_TORCH);
				entries.add(SpectrumItems.MYSTERIOUS_LOCKET);
				entries.add(SpectrumItems.MYSTERIOUS_COMPASS);
			}).build();
	
	public static final ItemSubGroup FUNCTIONAL = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_FUNCTIONAL, Text.translatable("itemGroup.spectrum.functional"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.add(SpectrumBlocks.PEDESTAL_BASIC_TOPAZ);
				entries.add(SpectrumBlocks.PEDESTAL_BASIC_AMETHYST);
				entries.add(SpectrumBlocks.PEDESTAL_BASIC_CITRINE);
				entries.add(SpectrumBlocks.PEDESTAL_ALL_BASIC);
				entries.add(SpectrumBlocks.PEDESTAL_ONYX);
				entries.add(SpectrumBlocks.PEDESTAL_MOONSTONE);
				entries.add(SpectrumBlocks.FUSION_SHRINE_BASALT);
				entries.add(SpectrumBlocks.FUSION_SHRINE_CALCITE);
				entries.add(SpectrumBlocks.ENCHANTER);
				entries.add(SpectrumBlocks.ITEM_BOWL_BASALT);
				entries.add(SpectrumBlocks.ITEM_BOWL_CALCITE);
				entries.add(SpectrumBlocks.ITEM_ROUNDEL);
				entries.add(SpectrumBlocks.POTION_WORKSHOP);
				entries.add(SpectrumBlocks.SPIRIT_INSTILLER);
				entries.add(SpectrumBlocks.CRYSTALLARIEUM);
				entries.add(SpectrumBlocks.CINDERHEARTH);
				entries.add(SpectrumBlocks.CRYSTAL_APOTHECARY);
				entries.add(SpectrumBlocks.COLOR_PICKER);
				
				entries.add(SpectrumBlocks.UPGRADE_SPEED);
				entries.add(SpectrumBlocks.UPGRADE_SPEED2);
				entries.add(SpectrumBlocks.UPGRADE_SPEED3);
				entries.add(SpectrumBlocks.UPGRADE_EFFICIENCY);
				entries.add(SpectrumBlocks.UPGRADE_EFFICIENCY2);
				entries.add(SpectrumBlocks.UPGRADE_YIELD);
				entries.add(SpectrumBlocks.UPGRADE_YIELD2);
				entries.add(SpectrumBlocks.UPGRADE_EXPERIENCE);
				entries.add(SpectrumBlocks.UPGRADE_EXPERIENCE2);
				
				entries.add(SpectrumBlocks.CONNECTION_NODE);
				entries.add(SpectrumBlocks.PROVIDER_NODE);
				entries.add(SpectrumBlocks.SENDER_NODE);
				entries.add(SpectrumBlocks.STORAGE_NODE);
				entries.add(SpectrumBlocks.BUFFER_NODE);
				entries.add(SpectrumBlocks.GATHER_NODE);
				
				entries.add(SpectrumBlocks.LIGHT_LEVEL_DETECTOR);
				entries.add(SpectrumBlocks.WEATHER_DETECTOR);
				entries.add(SpectrumBlocks.ITEM_DETECTOR);
				entries.add(SpectrumBlocks.PLAYER_DETECTOR);
				entries.add(SpectrumBlocks.CREATURE_DETECTOR);
				entries.add(SpectrumBlocks.REDSTONE_TIMER);
				entries.add(SpectrumBlocks.REDSTONE_CALCULATOR);
				entries.add(SpectrumBlocks.REDSTONE_TRANSCEIVER);
				entries.add(SpectrumBlocks.REDSTONE_SAND);
				entries.add(SpectrumBlocks.ENDER_GLASS);
				entries.add(SpectrumBlocks.BLOCK_DETECTOR);
				entries.add(SpectrumBlocks.BLOCK_PLACER);
				entries.add(SpectrumBlocks.BLOCK_BREAKER);
				
				entries.add(SpectrumBlocks.HEARTBOUND_CHEST);
				entries.add(SpectrumBlocks.COMPACTING_CHEST);
				entries.add(SpectrumBlocks.FABRICATION_CHEST);
				entries.add(SpectrumBlocks.BLACK_HOLE_CHEST);
				
				entries.add(SpectrumBlocks.ENDER_HOPPER);
				entries.add(SpectrumBlocks.ENDER_DROPPER);
				
				entries.add(SpectrumBlocks.PARTICLE_SPAWNER);
				
				entries.add(Registries.BLOCK.get(SpectrumBlocks.GLISTERING_MELON));
				entries.add(SpectrumBlocks.LAVA_SPONGE);
				entries.add(SpectrumBlocks.WET_LAVA_SPONGE);
				entries.add(SpectrumBlocks.ETHEREAL_PLATFORM);
				entries.add(SpectrumBlocks.UNIVERSE_SPYHOLE);
				entries.add(SpectrumBlocks.PRESENT);
				entries.add(SpectrumBlocks.TITRATION_BARREL);
				
				entries.add(SpectrumBlocks.INCANDESCENT_AMALGAM);
				entries.add(SpectrumBlocks.BEDROCK_ANVIL);
				entries.add(SpectrumBlocks.CRACKED_END_PORTAL_FRAME);
				
				entries.add(SpectrumBlocks.SEMI_PERMEABLE_GLASS);
				entries.add(SpectrumBlocks.TINTED_SEMI_PERMEABLE_GLASS);
				entries.add(SpectrumBlocks.RADIANT_SEMI_PERMEABLE_GLASS);
				entries.add(SpectrumBlocks.TOPAZ_SEMI_PERMEABLE_GLASS);
				entries.add(SpectrumBlocks.AMETHYST_SEMI_PERMEABLE_GLASS);
				entries.add(SpectrumBlocks.CITRINE_SEMI_PERMEABLE_GLASS);
				entries.add(SpectrumBlocks.ONYX_SEMI_PERMEABLE_GLASS);
				entries.add(SpectrumBlocks.MOONSTONE_SEMI_PERMEABLE_GLASS);
				
				entries.add(SpectrumBlocks.AXOLOTL_IDOL);
				entries.add(SpectrumBlocks.BAT_IDOL);
				entries.add(SpectrumBlocks.BEE_IDOL);
				entries.add(SpectrumBlocks.BLAZE_IDOL);
				entries.add(SpectrumBlocks.CAT_IDOL);
				entries.add(SpectrumBlocks.CHICKEN_IDOL);
				entries.add(SpectrumBlocks.COW_IDOL);
				entries.add(SpectrumBlocks.CREEPER_IDOL);
				entries.add(SpectrumBlocks.ENDER_DRAGON_IDOL);
				entries.add(SpectrumBlocks.ENDERMAN_IDOL);
				entries.add(SpectrumBlocks.ENDERMITE_IDOL);
				entries.add(SpectrumBlocks.EVOKER_IDOL);
				entries.add(SpectrumBlocks.FISH_IDOL);
				entries.add(SpectrumBlocks.FOX_IDOL);
				entries.add(SpectrumBlocks.GHAST_IDOL);
				entries.add(SpectrumBlocks.GLOW_SQUID_IDOL);
				entries.add(SpectrumBlocks.GOAT_IDOL);
				entries.add(SpectrumBlocks.GUARDIAN_IDOL);
				entries.add(SpectrumBlocks.HORSE_IDOL);
				entries.add(SpectrumBlocks.ILLUSIONER_IDOL);
				entries.add(SpectrumBlocks.OCELOT_IDOL);
				entries.add(SpectrumBlocks.PARROT_IDOL);
				entries.add(SpectrumBlocks.PHANTOM_IDOL);
				entries.add(SpectrumBlocks.PIG_IDOL);
				entries.add(SpectrumBlocks.PIGLIN_IDOL);
				entries.add(SpectrumBlocks.POLAR_BEAR_IDOL);
				entries.add(SpectrumBlocks.PUFFERFISH_IDOL);
				entries.add(SpectrumBlocks.RABBIT_IDOL);
				entries.add(SpectrumBlocks.SHEEP_IDOL);
				entries.add(SpectrumBlocks.SHULKER_IDOL);
				entries.add(SpectrumBlocks.SILVERFISH_IDOL);
				entries.add(SpectrumBlocks.SKELETON_IDOL);
				entries.add(SpectrumBlocks.SLIME_IDOL);
				entries.add(SpectrumBlocks.SNOW_GOLEM_IDOL);
				entries.add(SpectrumBlocks.SPIDER_IDOL);
				entries.add(SpectrumBlocks.SQUID_IDOL);
				entries.add(SpectrumBlocks.STRAY_IDOL);
				entries.add(SpectrumBlocks.STRIDER_IDOL);
				entries.add(SpectrumBlocks.TURTLE_IDOL);
				entries.add(SpectrumBlocks.WITCH_IDOL);
				entries.add(SpectrumBlocks.WITHER_IDOL);
				entries.add(SpectrumBlocks.WITHER_SKELETON_IDOL);
				entries.add(SpectrumBlocks.ZOMBIE_IDOL);
			}).build();
	
	public static final ItemSubGroup CUISINE = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_CUISINE, Text.translatable("itemGroup.spectrum.cuisine"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.add(SpectrumItems.IMBRIFER_COOKBOOK);
				entries.add(SpectrumItems.IMPERIAL_COOKBOOK);
				entries.add(SpectrumItems.MELOCHITES_COOKBOOK_VOL_1);
				entries.add(SpectrumItems.MELOCHITES_COOKBOOK_VOL_2);
				entries.add(SpectrumItems.BREWERS_HANDBOOK);
				//entries.add(SpectrumItems.VARIA_COOKBOOK);
				entries.add(SpectrumItems.POISONERS_HANDBOOK);
				
				entries.add(SpectrumBlocks.SUGAR_STICK);
				entries.add(SpectrumBlocks.TOPAZ_SUGAR_STICK);
				entries.add(SpectrumBlocks.AMETHYST_SUGAR_STICK);
				entries.add(SpectrumBlocks.CITRINE_SUGAR_STICK);
				entries.add(SpectrumBlocks.ONYX_SUGAR_STICK);
				entries.add(SpectrumBlocks.MOONSTONE_SUGAR_STICK);
				entries.add(SpectrumItems.ROCK_CANDY);
				entries.add(SpectrumItems.TOPAZ_ROCK_CANDY);
				entries.add(SpectrumItems.AMETHYST_ROCK_CANDY);
				entries.add(SpectrumItems.CITRINE_ROCK_CANDY);
				entries.add(SpectrumItems.ONYX_ROCK_CANDY);
				entries.add(SpectrumItems.MOONSTONE_ROCK_CANDY);
				
				entries.add(SpectrumItems.TRIPLE_MEAT_POT_PIE);
				entries.add(SpectrumItems.KIMCHI);
				entries.add(SpectrumItems.CLOTTED_CREAM);
				entries.add(SpectrumItems.FRESH_CHOCOLATE);
				entries.add(SpectrumItems.BODACIOUS_BERRY_BAR);
				entries.add(SpectrumItems.HOT_CHOCOLATE);
				entries.add(SpectrumItems.KARAK_CHAI);
				entries.add(SpectrumItems.RESTORATION_TEA);
				entries.add(SpectrumItems.GLISTERING_JELLY_TEA);
				entries.add(SpectrumItems.AZALEA_TEA);
				entries.add(SpectrumItems.DEMON_TEA);
				entries.add(SpectrumItems.ENCHANTED_GOLDEN_CARROT);
				entries.add(SpectrumItems.JADE_JELLY);
				entries.add(SpectrumItems.JARAMEL);
				entries.add(SpectrumItems.MOONSTRUCK_NECTAR);
				entries.add(SpectrumItems.GLASS_PEACH);
				entries.add(SpectrumItems.FISSURE_PLUM);
				entries.add(SpectrumItems.NIGHTDEW_SPROUT);
				entries.add(SpectrumItems.NECTARDEW_BURGEON);
				entries.add(SpectrumItems.BLOODBOIL_SYRUP);
				entries.add(SpectrumItems.MILKY_RESIN);
				entries.add(SpectrumItems.SCONE);
				entries.add(SpectrumItems.STAR_CANDY);
				entries.add(SpectrumItems.ENCHANTED_STAR_CANDY);
				entries.add(SpectrumItems.CHEONG);
				entries.add(SpectrumItems.MERMAIDS_JAM);
				entries.add(SpectrumItems.MERMAIDS_POPCORN);
				entries.add(SpectrumItems.LE_FISHE_AU_CHOCOLAT);
				//entries.add(SpectrumItems.STUFFED_PETALS);
				//entries.add(SpectrumItems.PASTICHE);
				//entries.add(SpectrumItems.VITTORIAS_ROAST);
				entries.add(SpectrumItems.LUCKY_ROLL);
				entries.add(SpectrumItems.HONEY_PASTRY);
				entries.add(SpectrumItems.JARAMEL_TART);
				entries.add(SpectrumItems.SALTED_JARAMEL_TART);
				entries.add(SpectrumItems.ASHEN_TART);
				entries.add(SpectrumItems.WEEPING_TART);
				entries.add(SpectrumItems.WHISPY_TART);
				entries.add(SpectrumItems.PUFF_TART);
				entries.add(SpectrumItems.JARAMEL_TRIFLE);
				entries.add(SpectrumItems.SALTED_JARAMEL_TRIFLE);
				entries.add(SpectrumItems.MONSTER_TRIFLE);
				entries.add(SpectrumItems.DEMON_TRIFLE);
				entries.add(SpectrumItems.MYCEYLON);
				entries.add(SpectrumItems.MYCEYLON_APPLE_PIE);
				entries.add(SpectrumItems.MYCEYLON_PUMPKIN_PIE);
				entries.add(SpectrumItems.MYCEYLON_COOKIE);
				entries.add(SpectrumItems.ALOE_LEAF);
				entries.add(SpectrumItems.SAWBLADE_HOLLY_BERRY);
				entries.add(SpectrumItems.PRICKLY_BAYLEAF);
				entries.add(SpectrumItems.TRIPLE_MEAT_POT_STEW);
				entries.add(SpectrumItems.DRAGONBONE_BROTH);
				entries.add(SpectrumItems.BAGNUN);
				entries.add(SpectrumItems.BANYASH);
				entries.add(SpectrumItems.BERLINER);
				entries.add(SpectrumItems.BRISTLE_MEAD);
				entries.add(SpectrumItems.CHAUVE_SOURIS_AU_VIN);
				entries.add(SpectrumItems.CRAWFISH);
				entries.add(SpectrumItems.CRAWFISH_COCKTAIL);
				entries.add(SpectrumItems.CREAM_PASTRY);
				entries.add(SpectrumItems.FADED_KOI);
				entries.add(SpectrumItems.FISHCAKE);
				entries.add(SpectrumItems.LIZARD_MEAT);
				entries.add(SpectrumItems.COOKED_LIZARD_MEAT);
				entries.add(SpectrumItems.GOLDEN_BRISTLE_TEA);
				entries.add(SpectrumItems.HARE_ROAST);
				entries.add(SpectrumItems.JUNKET);
				entries.add(SpectrumItems.KOI);
				entries.add(SpectrumItems.MEATLOAF);
				entries.add(SpectrumItems.MEATLOAF_SANDWICH);
				entries.add(SpectrumItems.MELLOW_SHALLOT_SOUP);
				entries.add(SpectrumItems.PEACHES_FLAMBE);
				entries.add(SpectrumItems.PEACH_CREAM);
				entries.add(SpectrumItems.PEACH_JAM);
				entries.add(SpectrumItems.RABBIT_CREAM_PIE);
				entries.add(SpectrumItems.SEDATIVES);
				entries.add(SpectrumItems.SLUSHSLIDE);
				entries.add(SpectrumItems.SURSTROMMING);
				entries.add(SpectrumItems.MORCHELLA);
				entries.add(SpectrumItems.NECTERED_VIOGNIER);
				entries.add(SpectrumItems.FREIGEIST);
				
				// adding all beverages from recipes
				if (SpectrumCommon.minecraftServer != null) {
					for (RecipeEntry<ITitrationBarrelRecipe> recipe : SpectrumCommon.minecraftServer.getRecipeManager().listAllOfType(SpectrumRecipeTypes.TITRATION_BARREL)) {
						ItemStack output = recipe.value().getResult(SpectrumCommon.minecraftServer.getRegistryManager()).copy();
						if (output.getItem() instanceof VariantBeverageItem) {
							output.setCount(1);
							entries.add(output);
						}
					}
				}
				
				entries.add(SpectrumItems.PURE_ALCOHOL);
				entries.add(SpectrumItems.REPRISE);
				entries.add(SpectrumItems.SUSPICIOUS_BREW);
				entries.add(SpectrumItems.JADE_WINE);
				entries.add(SpectrumItems.CHRYSOCOLLA);
				entries.add(SpectrumItems.AQUA_REGIA);
				entries.add(SpectrumItems.EVERNECTAR);
			}).build();
	
	public static final ItemSubGroup RESOURCES = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_RESOURCES, Text.translatable("itemGroup.spectrum.resources"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.add(SpectrumItems.TOPAZ_SHARD);
				entries.add(Items.AMETHYST_SHARD);
				entries.add(SpectrumItems.CITRINE_SHARD);
				entries.add(SpectrumItems.ONYX_SHARD);
				entries.add(SpectrumItems.MOONSTONE_SHARD);
				
				entries.add(SpectrumBlocks.TOPAZ_BLOCK);
				entries.add(Blocks.AMETHYST_BLOCK);
				entries.add(SpectrumBlocks.CITRINE_BLOCK);
				entries.add(SpectrumBlocks.ONYX_BLOCK);
				entries.add(SpectrumBlocks.MOONSTONE_BLOCK);
				
				entries.add(SpectrumItems.TOPAZ_POWDER);
				entries.add(SpectrumItems.AMETHYST_POWDER);
				entries.add(SpectrumItems.CITRINE_POWDER);
				entries.add(SpectrumItems.ONYX_POWDER);
				entries.add(SpectrumItems.MOONSTONE_POWDER);
				
				entries.add(SpectrumBlocks.TOPAZ_POWDER_BLOCK);
				entries.add(SpectrumBlocks.AMETHYST_POWDER_BLOCK);
				entries.add(SpectrumBlocks.CITRINE_POWDER_BLOCK);
				entries.add(SpectrumBlocks.ONYX_POWDER_BLOCK);
				entries.add(SpectrumBlocks.MOONSTONE_POWDER_BLOCK);
				
				entries.add(SpectrumBlocks.BUDDING_TOPAZ);
				entries.add(Blocks.BUDDING_AMETHYST);
				entries.add(SpectrumBlocks.BUDDING_CITRINE);
				entries.add(SpectrumBlocks.BUDDING_ONYX);
				entries.add(SpectrumBlocks.BUDDING_MOONSTONE);
				
				entries.add(SpectrumBlocks.SMALL_TOPAZ_BUD);
				entries.add(SpectrumBlocks.MEDIUM_TOPAZ_BUD);
				entries.add(SpectrumBlocks.LARGE_TOPAZ_BUD);
				entries.add(SpectrumBlocks.TOPAZ_CLUSTER);
				
				entries.add(Blocks.SMALL_AMETHYST_BUD);
				entries.add(Blocks.MEDIUM_AMETHYST_BUD);
				entries.add(Blocks.LARGE_AMETHYST_BUD);
				entries.add(Blocks.AMETHYST_CLUSTER);
				
				entries.add(SpectrumBlocks.SMALL_CITRINE_BUD);
				entries.add(SpectrumBlocks.MEDIUM_CITRINE_BUD);
				entries.add(SpectrumBlocks.LARGE_CITRINE_BUD);
				entries.add(SpectrumBlocks.CITRINE_CLUSTER);
				
				entries.add(SpectrumBlocks.SMALL_ONYX_BUD);
				entries.add(SpectrumBlocks.MEDIUM_ONYX_BUD);
				entries.add(SpectrumBlocks.LARGE_ONYX_BUD);
				entries.add(SpectrumBlocks.ONYX_CLUSTER);
				
				entries.add(SpectrumBlocks.SMALL_MOONSTONE_BUD);
				entries.add(SpectrumBlocks.MEDIUM_MOONSTONE_BUD);
				entries.add(SpectrumBlocks.LARGE_MOONSTONE_BUD);
				entries.add(SpectrumBlocks.MOONSTONE_CLUSTER);
				
				entries.add(SpectrumBlocks.TOPAZ_ORE);
				entries.add(SpectrumBlocks.AMETHYST_ORE);
				entries.add(SpectrumBlocks.CITRINE_ORE);
				entries.add(SpectrumBlocks.ONYX_ORE);
				entries.add(SpectrumBlocks.MOONSTONE_ORE);
				entries.add(SpectrumBlocks.DEEPSLATE_TOPAZ_ORE);
				entries.add(SpectrumBlocks.DEEPSLATE_AMETHYST_ORE);
				entries.add(SpectrumBlocks.DEEPSLATE_CITRINE_ORE);
				entries.add(SpectrumBlocks.DEEPSLATE_ONYX_ORE);
				entries.add(SpectrumBlocks.DEEPSLATE_MOONSTONE_ORE);
				entries.add(SpectrumBlocks.BLACKSLAG_TOPAZ_ORE);
				entries.add(SpectrumBlocks.BLACKSLAG_AMETHYST_ORE);
				entries.add(SpectrumBlocks.BLACKSLAG_CITRINE_ORE);
				entries.add(SpectrumBlocks.BLACKSLAG_ONYX_ORE);
				entries.add(SpectrumBlocks.BLACKSLAG_MOONSTONE_ORE);
				entries.add(SpectrumBlocks.SHIMMERSTONE_ORE);
				entries.add(SpectrumBlocks.DEEPSLATE_SHIMMERSTONE_ORE);
				entries.add(SpectrumBlocks.BLACKSLAG_SHIMMERSTONE_ORE);
				entries.add(SpectrumBlocks.AZURITE_ORE);
				entries.add(SpectrumBlocks.DEEPSLATE_AZURITE_ORE);
				entries.add(SpectrumBlocks.BLACKSLAG_AZURITE_ORE);
				entries.add(SpectrumBlocks.STRATINE_ORE);
				entries.add(SpectrumBlocks.PALTAERIA_ORE);
				
				entries.add(SpectrumBlocks.BLACKSLAG_COAL_ORE);
				entries.add(SpectrumBlocks.BLACKSLAG_COPPER_ORE);
				entries.add(SpectrumBlocks.BLACKSLAG_IRON_ORE);
				entries.add(SpectrumBlocks.BLACKSLAG_GOLD_ORE);
				entries.add(SpectrumBlocks.BLACKSLAG_DIAMOND_ORE);
				entries.add(SpectrumBlocks.BLACKSLAG_REDSTONE_ORE);
				entries.add(SpectrumBlocks.BLACKSLAG_LAPIS_ORE);
				entries.add(SpectrumBlocks.BLACKSLAG_EMERALD_ORE);
				
				entries.add(SpectrumItems.BISMUTH_FLAKE);
				entries.add(SpectrumBlocks.SMALL_BISMUTH_BUD);
				entries.add(SpectrumBlocks.LARGE_BISMUTH_BUD);
				entries.add(SpectrumBlocks.BISMUTH_CLUSTER);
				entries.add(SpectrumItems.BISMUTH_CRYSTAL);
				
				entries.add(SpectrumBlocks.MALACHITE_ORE);
				entries.add(SpectrumBlocks.DEEPSLATE_MALACHITE_ORE);
				entries.add(SpectrumBlocks.BLACKSLAG_MALACHITE_ORE);
				entries.add(SpectrumItems.RAW_MALACHITE);
				entries.add(SpectrumBlocks.SMALL_MALACHITE_BUD);
				entries.add(SpectrumBlocks.LARGE_MALACHITE_BUD);
				entries.add(SpectrumBlocks.MALACHITE_CLUSTER);
				entries.add(SpectrumItems.REFINED_MALACHITE);
				
				entries.add(SpectrumItems.RAW_AZURITE);
				entries.add(SpectrumBlocks.SMALL_AZURITE_BUD);
				entries.add(SpectrumBlocks.LARGE_AZURITE_BUD);
				entries.add(SpectrumBlocks.AZURITE_CLUSTER);
				entries.add(SpectrumItems.REFINED_AZURITE);
				
				entries.add(SpectrumItems.RAW_BLOODSTONE);
				entries.add(SpectrumBlocks.SMALL_BLOODSTONE_BUD);
				entries.add(SpectrumBlocks.LARGE_BLOODSTONE_BUD);
				entries.add(SpectrumBlocks.BLOODSTONE_CLUSTER);
				entries.add(SpectrumItems.REFINED_BLOODSTONE);
				
				entries.add(SpectrumItems.FROSTBITE_ESSENCE);
				entries.add(SpectrumBlocks.FROSTBITE_CRYSTAL);
				entries.add(SpectrumItems.INCANDESCENT_ESSENCE);
				entries.add(SpectrumBlocks.BLAZING_CRYSTAL);
				
				entries.add(SpectrumBlocks.CLOVER);
				entries.add(SpectrumBlocks.FOUR_LEAF_CLOVER);
				entries.add(SpectrumItems.BLOOD_ORCHID_PETAL);
				entries.add(SpectrumBlocks.BLOOD_ORCHID);
				entries.add(SpectrumBlocks.QUITOXIC_REEDS);
				entries.add(SpectrumItems.QUITOXIC_POWDER);
				
				entries.add(SpectrumItems.AMARANTH_GRAINS);
				entries.add(SpectrumBlocks.AMARANTH_BUSHEL);
				entries.add(Registries.ITEM.get(SpectrumItems.GLISTERING_MELON_SEEDS));
				
				entries.add(SpectrumBlocks.GLISTERING_SHOOTING_STAR);
				entries.add(SpectrumBlocks.FIERY_SHOOTING_STAR);
				entries.add(SpectrumBlocks.COLORFUL_SHOOTING_STAR);
				entries.add(SpectrumBlocks.PRISTINE_SHOOTING_STAR);
				entries.add(SpectrumBlocks.GEMSTONE_SHOOTING_STAR);
				entries.add(SpectrumItems.STARDUST);
				entries.add(SpectrumBlocks.STARDUST_BLOCK);
				entries.add(SpectrumItems.STAR_FRAGMENT);
				entries.add(SpectrumBlocks.RADIATING_ENDER);
				
				entries.add(SpectrumItems.WHITE_PIGMENT);
				entries.add(SpectrumItems.ORANGE_PIGMENT);
				entries.add(SpectrumItems.MAGENTA_PIGMENT);
				entries.add(SpectrumItems.LIGHT_BLUE_PIGMENT);
				entries.add(SpectrumItems.YELLOW_PIGMENT);
				entries.add(SpectrumItems.LIME_PIGMENT);
				entries.add(SpectrumItems.PINK_PIGMENT);
				entries.add(SpectrumItems.GRAY_PIGMENT);
				entries.add(SpectrumItems.LIGHT_GRAY_PIGMENT);
				entries.add(SpectrumItems.CYAN_PIGMENT);
				entries.add(SpectrumItems.PURPLE_PIGMENT);
				entries.add(SpectrumItems.BLUE_PIGMENT);
				entries.add(SpectrumItems.BROWN_PIGMENT);
				entries.add(SpectrumItems.GREEN_PIGMENT);
				entries.add(SpectrumItems.RED_PIGMENT);
				entries.add(SpectrumItems.BLACK_PIGMENT);
				
				entries.add(SpectrumItems.VEGETAL);
				entries.add(SpectrumItems.NEOLITH);
				entries.add(SpectrumItems.BEDROCK_DUST);
				entries.add(SpectrumItems.MIDNIGHT_ABERRATION);
				entries.add(SpectrumItems.MIDNIGHT_ABERRATION.getStableStack());
				entries.add(SpectrumItems.MIDNIGHT_CHIP);
				
				entries.add(SpectrumItems.SHIMMERSTONE_GEM);
				entries.add(SpectrumItems.PALTAERIA_FRAGMENTS);
				entries.add(SpectrumItems.PALTAERIA_GEM);
				entries.add(SpectrumItems.STRATINE_FRAGMENTS);
				entries.add(SpectrumItems.STRATINE_GEM);
				
				entries.add(SpectrumItems.HIBERNATING_JADE_VINE_BULB);
				entries.add(SpectrumItems.GERMINATED_JADE_VINE_BULB);
				entries.add(SpectrumItems.JADE_VINE_PETALS);
				entries.add(SpectrumBlocks.NEPHRITE_BLOSSOM_BULB);
				entries.add(SpectrumBlocks.JADEITE_LOTUS_BULB);
				entries.add(SpectrumItems.JADEITE_PETALS);
				
				entries.add(SpectrumItems.MERMAIDS_GEM);
				entries.add(SpectrumItems.STORM_STONE);
				entries.add(SpectrumItems.DOOMBLOOM_SEED);
				entries.add(SpectrumItems.RESPLENDENT_FEATHER);
				entries.add(SpectrumItems.DRAGONBONE_CHUNK);
				entries.add(SpectrumItems.BONE_ASH);
				entries.add(SpectrumItems.DOWNSTONE_FRAGMENTS);
				entries.add(SpectrumItems.RESONANCE_SHARD);
				entries.add(SpectrumItems.AETHER_VESTIGES);
				entries.add(SpectrumItems.MOONSTONE_CORE);
				
				entries.add(SpectrumItems.LIQUID_CRYSTAL_BUCKET);
				entries.add(SpectrumItems.GOO_BUCKET);
				entries.add(SpectrumItems.MIDNIGHT_SOLUTION_BUCKET);
				entries.add(SpectrumItems.DRAGONROT_BUCKET);
			}).build();
	
	public static final ItemSubGroup PURE_RESOURCES = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_PURE_RESOURCES, Text.translatable("itemGroup.spectrum.pure_resources"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.add(SpectrumItems.PURE_COAL);
				entries.add(SpectrumBlocks.SMALL_COAL_BUD);
				entries.add(SpectrumBlocks.LARGE_COAL_BUD);
				entries.add(SpectrumBlocks.COAL_CLUSTER);
				entries.add(SpectrumBlocks.PURE_COAL_BLOCK);
				entries.add(SpectrumItems.PURE_COPPER);
				entries.add(SpectrumBlocks.SMALL_COPPER_BUD);
				entries.add(SpectrumBlocks.LARGE_COPPER_BUD);
				entries.add(SpectrumBlocks.COPPER_CLUSTER);
				entries.add(SpectrumBlocks.PURE_COPPER_BLOCK);
				entries.add(SpectrumItems.PURE_IRON);
				entries.add(SpectrumBlocks.SMALL_IRON_BUD);
				entries.add(SpectrumBlocks.LARGE_IRON_BUD);
				entries.add(SpectrumBlocks.IRON_CLUSTER);
				entries.add(SpectrumBlocks.PURE_IRON_BLOCK);
				entries.add(SpectrumItems.PURE_GOLD);
				entries.add(SpectrumBlocks.SMALL_GOLD_BUD);
				entries.add(SpectrumBlocks.LARGE_GOLD_BUD);
				entries.add(SpectrumBlocks.GOLD_CLUSTER);
				entries.add(SpectrumBlocks.PURE_GOLD_BLOCK);
				entries.add(SpectrumItems.PURE_LAPIS);
				entries.add(SpectrumBlocks.SMALL_LAPIS_BUD);
				entries.add(SpectrumBlocks.LARGE_LAPIS_BUD);
				entries.add(SpectrumBlocks.LAPIS_CLUSTER);
				entries.add(SpectrumBlocks.PURE_LAPIS_BLOCK);
				entries.add(SpectrumItems.PURE_REDSTONE);
				entries.add(SpectrumBlocks.SMALL_REDSTONE_BUD);
				entries.add(SpectrumBlocks.LARGE_REDSTONE_BUD);
				entries.add(SpectrumBlocks.REDSTONE_CLUSTER);
				entries.add(SpectrumBlocks.PURE_REDSTONE_BLOCK);
				entries.add(SpectrumItems.PURE_DIAMOND);
				entries.add(SpectrumBlocks.SMALL_DIAMOND_BUD);
				entries.add(SpectrumBlocks.LARGE_DIAMOND_BUD);
				entries.add(SpectrumBlocks.DIAMOND_CLUSTER);
				entries.add(SpectrumBlocks.PURE_DIAMOND_BLOCK);
				entries.add(SpectrumItems.PURE_EMERALD);
				entries.add(SpectrumBlocks.SMALL_EMERALD_BUD);
				entries.add(SpectrumBlocks.LARGE_EMERALD_BUD);
				entries.add(SpectrumBlocks.EMERALD_CLUSTER);
				entries.add(SpectrumBlocks.PURE_EMERALD_BLOCK);
				
				entries.add(SpectrumItems.PURE_PRISMARINE);
				entries.add(SpectrumBlocks.SMALL_PRISMARINE_BUD);
				entries.add(SpectrumBlocks.LARGE_PRISMARINE_BUD);
				entries.add(SpectrumBlocks.PRISMARINE_CLUSTER);
				entries.add(SpectrumBlocks.PURE_PRISMARINE_BLOCK);
				
				entries.add(SpectrumItems.PURE_QUARTZ);
				entries.add(SpectrumBlocks.SMALL_QUARTZ_BUD);
				entries.add(SpectrumBlocks.LARGE_QUARTZ_BUD);
				entries.add(SpectrumBlocks.QUARTZ_CLUSTER);
				entries.add(SpectrumBlocks.PURE_QUARTZ_BLOCK);
				entries.add(SpectrumItems.PURE_GLOWSTONE);
				entries.add(SpectrumBlocks.SMALL_GLOWSTONE_BUD);
				entries.add(SpectrumBlocks.LARGE_GLOWSTONE_BUD);
				entries.add(SpectrumBlocks.GLOWSTONE_CLUSTER);
				entries.add(SpectrumBlocks.PURE_GLOWSTONE_BLOCK);
				entries.add(SpectrumItems.PURE_NETHERITE_SCRAP);
				entries.add(SpectrumBlocks.SMALL_NETHERITE_SCRAP_BUD);
				entries.add(SpectrumBlocks.LARGE_NETHERITE_SCRAP_BUD);
				entries.add(SpectrumBlocks.NETHERITE_SCRAP_CLUSTER);
				entries.add(SpectrumBlocks.PURE_NETHERITE_SCRAP_BLOCK);
				
				entries.add(SpectrumItems.PURE_ECHO);
				entries.add(SpectrumBlocks.SMALL_ECHO_BUD);
				entries.add(SpectrumBlocks.LARGE_ECHO_BUD);
				entries.add(SpectrumBlocks.ECHO_CLUSTER);
				entries.add(SpectrumBlocks.PURE_ECHO_BLOCK);
				
				if (SpectrumIntegrationPacks.isIntegrationPackActive(SpectrumIntegrationPacks.AE2_ID)) {
					entries.add(AE2Compat.PURE_CERTUS_QUARTZ);
					entries.add(AE2Compat.SMALL_CERTUS_QUARTZ_BUD);
					entries.add(AE2Compat.LARGE_CERTUS_QUARTZ_BUD);
					entries.add(AE2Compat.CERTUS_QUARTZ_CLUSTER);
					entries.add(AE2Compat.PURE_CERTUS_QUARTZ_BLOCK);
					
					entries.add(AE2Compat.PURE_FLUIX);
					entries.add(AE2Compat.SMALL_FLUIX_BUD);
					entries.add(AE2Compat.LARGE_FLUIX_BUD);
					entries.add(AE2Compat.FLUIX_CLUSTER);
					entries.add(AE2Compat.PURE_FLUIX_BLOCK);
				}
				
				if (SpectrumIntegrationPacks.isIntegrationPackActive(SpectrumIntegrationPacks.CREATE_ID)) {
					entries.add(CreateCompat.PURE_ZINC);
					entries.add(CreateCompat.SMALL_ZINC_BUD);
					entries.add(CreateCompat.LARGE_ZINC_BUD);
					entries.add(CreateCompat.ZINC_CLUSTER);
					entries.add(CreateCompat.PURE_ZINC_BLOCK);
				}
				
				if (SpectrumIntegrationPacks.isIntegrationPackActive(SpectrumIntegrationPacks.GOBBER_ID)) {
					entries.add(GobberCompat.PURE_GLOBETTE);
					entries.add(GobberCompat.SMALL_GLOBETTE_BUD);
					entries.add(GobberCompat.LARGE_GLOBETTE_BUD);
					entries.add(GobberCompat.GLOBETTE_CLUSTER);
					entries.add(GobberCompat.PURE_GLOBETTE_BLOCK);
					
					entries.add(GobberCompat.PURE_GLOBETTE_NETHER);
					entries.add(GobberCompat.SMALL_GLOBETTE_NETHER_BUD);
					entries.add(GobberCompat.LARGE_GLOBETTE_NETHER_BUD);
					entries.add(GobberCompat.GLOBETTE_NETHER_CLUSTER);
					entries.add(GobberCompat.PURE_GLOBETTE_NETHER_BLOCK);
					
					entries.add(GobberCompat.PURE_GLOBETTE_END);
					entries.add(GobberCompat.SMALL_GLOBETTE_END_BUD);
					entries.add(GobberCompat.LARGE_GLOBETTE_END_BUD);
					entries.add(GobberCompat.GLOBETTE_END_CLUSTER);
					entries.add(GobberCompat.PURE_GLOBETTE_END_BLOCK);
				}
				
			}).build();
	
	public static final ItemSubGroup BLOCKS = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_BLOCKS, Text.translatable("itemGroup.spectrum.blocks"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.add(SpectrumBlocks.SMOOTH_BASALT_SLAB);
				entries.add(SpectrumBlocks.SMOOTH_BASALT_WALL);
				entries.add(SpectrumBlocks.SMOOTH_BASALT_STAIRS);
				entries.add(SpectrumBlocks.POLISHED_BASALT);
				entries.add(SpectrumBlocks.POLISHED_BASALT_PILLAR);
				entries.add(SpectrumBlocks.POLISHED_BASALT_CREST);
				entries.add(SpectrumBlocks.CHISELED_POLISHED_BASALT);
				entries.add(SpectrumBlocks.NOTCHED_POLISHED_BASALT);
				entries.add(SpectrumBlocks.POLISHED_BASALT_SLAB);
				entries.add(SpectrumBlocks.POLISHED_BASALT_WALL);
				entries.add(SpectrumBlocks.POLISHED_BASALT_STAIRS);
				entries.add(SpectrumBlocks.BASALT_BRICKS);
				entries.add(SpectrumBlocks.BASALT_BRICK_SLAB);
				entries.add(SpectrumBlocks.BASALT_BRICK_WALL);
				entries.add(SpectrumBlocks.BASALT_BRICK_STAIRS);
				entries.add(SpectrumBlocks.CRACKED_BASALT_BRICKS);
				entries.add(SpectrumBlocks.BASALT_TILES);
				entries.add(SpectrumBlocks.BASALT_TILE_STAIRS);
				entries.add(SpectrumBlocks.BASALT_TILE_SLAB);
				entries.add(SpectrumBlocks.BASALT_TILE_WALL);
				entries.add(SpectrumBlocks.PLANED_BASALT);
				entries.add(SpectrumBlocks.PLANED_BASALT_SLAB);
				entries.add(SpectrumBlocks.PLANED_BASALT_STAIRS);
				entries.add(SpectrumBlocks.PLANED_BASALT_WALL);
				entries.add(SpectrumBlocks.CRACKED_BASALT_TILES);
				entries.add(SpectrumBlocks.POLISHED_BASALT_BUTTON);
				entries.add(SpectrumBlocks.POLISHED_BASALT_PRESSURE_PLATE);
				
				entries.add(SpectrumBlocks.CALCITE_SLAB);
				entries.add(SpectrumBlocks.CALCITE_WALL);
				entries.add(SpectrumBlocks.CALCITE_STAIRS);
				entries.add(SpectrumBlocks.POLISHED_CALCITE);
				entries.add(SpectrumBlocks.POLISHED_CALCITE_PILLAR);
				entries.add(SpectrumBlocks.POLISHED_CALCITE_CREST);
				entries.add(SpectrumBlocks.CHISELED_POLISHED_CALCITE);
				entries.add(SpectrumBlocks.NOTCHED_POLISHED_CALCITE);
				entries.add(SpectrumBlocks.POLISHED_CALCITE_SLAB);
				entries.add(SpectrumBlocks.POLISHED_CALCITE_WALL);
				entries.add(SpectrumBlocks.POLISHED_CALCITE_STAIRS);
				entries.add(SpectrumBlocks.CALCITE_BRICKS);
				entries.add(SpectrumBlocks.CALCITE_BRICK_SLAB);
				entries.add(SpectrumBlocks.CALCITE_BRICK_WALL);
				entries.add(SpectrumBlocks.CALCITE_BRICK_STAIRS);
				entries.add(SpectrumBlocks.CRACKED_CALCITE_BRICKS);
				entries.add(SpectrumBlocks.CALCITE_TILES);
				entries.add(SpectrumBlocks.CALCITE_TILE_STAIRS);
				entries.add(SpectrumBlocks.CALCITE_TILE_SLAB);
				entries.add(SpectrumBlocks.CALCITE_TILE_WALL);
				entries.add(SpectrumBlocks.PLANED_CALCITE);
				entries.add(SpectrumBlocks.PLANED_CALCITE_SLAB);
				entries.add(SpectrumBlocks.PLANED_CALCITE_STAIRS);
				entries.add(SpectrumBlocks.PLANED_CALCITE_WALL);
				entries.add(SpectrumBlocks.CRACKED_CALCITE_TILES);
				entries.add(SpectrumBlocks.POLISHED_CALCITE_BUTTON);
				entries.add(SpectrumBlocks.POLISHED_CALCITE_PRESSURE_PLATE);
				
				entries.add(SpectrumBlocks.BLACKSLAG);
				entries.add(SpectrumBlocks.BLACKSLAG_SLAB);
				entries.add(SpectrumBlocks.BLACKSLAG_WALL);
				entries.add(SpectrumBlocks.BLACKSLAG_STAIRS);
				entries.add(SpectrumBlocks.COBBLED_BLACKSLAG);
				entries.add(SpectrumBlocks.COBBLED_BLACKSLAG_STAIRS);
				entries.add(SpectrumBlocks.COBBLED_BLACKSLAG_SLAB);
				entries.add(SpectrumBlocks.COBBLED_BLACKSLAG_WALL);
				entries.add(SpectrumBlocks.POLISHED_BLACKSLAG);
				entries.add(SpectrumBlocks.POLISHED_BLACKSLAG_STAIRS);
				entries.add(SpectrumBlocks.POLISHED_BLACKSLAG_SLAB);
				entries.add(SpectrumBlocks.POLISHED_BLACKSLAG_WALL);
				entries.add(SpectrumBlocks.BLACKSLAG_TILES);
				entries.add(SpectrumBlocks.BLACKSLAG_TILE_STAIRS);
				entries.add(SpectrumBlocks.BLACKSLAG_TILE_SLAB);
				entries.add(SpectrumBlocks.BLACKSLAG_TILE_WALL);
				entries.add(SpectrumBlocks.CRACKED_BLACKSLAG_TILES);
				entries.add(SpectrumBlocks.BLACKSLAG_BRICKS);
				entries.add(SpectrumBlocks.BLACKSLAG_BRICK_STAIRS);
				entries.add(SpectrumBlocks.BLACKSLAG_BRICK_SLAB);
				entries.add(SpectrumBlocks.BLACKSLAG_BRICK_WALL);
				entries.add(SpectrumBlocks.CRACKED_BLACKSLAG_BRICKS);
				entries.add(SpectrumBlocks.POLISHED_BLACKSLAG_PILLAR);
				entries.add(SpectrumBlocks.CHISELED_POLISHED_BLACKSLAG);
				entries.add(SpectrumBlocks.ANCIENT_CHISELED_POLISHED_BLACKSLAG);
				entries.add(SpectrumBlocks.POLISHED_BLACKSLAG_BUTTON);
				entries.add(SpectrumBlocks.POLISHED_BLACKSLAG_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.INFESTED_BLACKSLAG);
				entries.add(SpectrumBlocks.SHALE_CLAY);
				entries.add(SpectrumBlocks.TILLED_SHALE_CLAY);
				entries.add(SpectrumBlocks.POLISHED_SHALE_CLAY);
				entries.add(SpectrumBlocks.EXPOSED_POLISHED_SHALE_CLAY);
				entries.add(SpectrumBlocks.WEATHERED_POLISHED_SHALE_CLAY);
				entries.add(SpectrumBlocks.POLISHED_SHALE_CLAY_STAIRS);
				entries.add(SpectrumBlocks.POLISHED_SHALE_CLAY_SLAB);
				entries.add(SpectrumBlocks.EXPOSED_POLISHED_SHALE_CLAY_STAIRS);
				entries.add(SpectrumBlocks.EXPOSED_POLISHED_SHALE_CLAY_SLAB);
				entries.add(SpectrumBlocks.WEATHERED_POLISHED_SHALE_CLAY_STAIRS);
				entries.add(SpectrumBlocks.WEATHERED_POLISHED_SHALE_CLAY_SLAB);
				entries.add(SpectrumBlocks.SHALE_CLAY_BRICKS);
				entries.add(SpectrumBlocks.EXPOSED_SHALE_CLAY_BRICKS);
				entries.add(SpectrumBlocks.WEATHERED_SHALE_CLAY_BRICKS);
				entries.add(SpectrumBlocks.SHALE_CLAY_BRICK_STAIRS);
				entries.add(SpectrumBlocks.SHALE_CLAY_BRICK_SLAB);
				entries.add(SpectrumBlocks.EXPOSED_SHALE_CLAY_BRICK_STAIRS);
				entries.add(SpectrumBlocks.EXPOSED_SHALE_CLAY_BRICK_SLAB);
				entries.add(SpectrumBlocks.WEATHERED_SHALE_CLAY_BRICK_STAIRS);
				entries.add(SpectrumBlocks.WEATHERED_SHALE_CLAY_BRICK_SLAB);
				entries.add(SpectrumBlocks.SHALE_CLAY_TILES);
				entries.add(SpectrumBlocks.EXPOSED_SHALE_CLAY_TILES);
				entries.add(SpectrumBlocks.WEATHERED_SHALE_CLAY_TILES);
				entries.add(SpectrumBlocks.SHALE_CLAY_TILE_STAIRS);
				entries.add(SpectrumBlocks.SHALE_CLAY_TILE_SLAB);
				entries.add(SpectrumBlocks.EXPOSED_SHALE_CLAY_TILE_STAIRS);
				entries.add(SpectrumBlocks.EXPOSED_SHALE_CLAY_TILE_SLAB);
				entries.add(SpectrumBlocks.WEATHERED_SHALE_CLAY_TILE_STAIRS);
				entries.add(SpectrumBlocks.WEATHERED_SHALE_CLAY_TILE_SLAB);
				entries.add(SpectrumBlocks.POLISHED_BONE_ASH);
				entries.add(SpectrumBlocks.POLISHED_BONE_ASH_SLAB);
				entries.add(SpectrumBlocks.POLISHED_BONE_ASH_STAIRS);
				entries.add(SpectrumBlocks.POLISHED_BONE_ASH_WALL);
				entries.add(SpectrumBlocks.BONE_ASH_BRICKS);
				entries.add(SpectrumBlocks.BONE_ASH_BRICK_SLAB);
				entries.add(SpectrumBlocks.BONE_ASH_BRICK_STAIRS);
				entries.add(SpectrumBlocks.BONE_ASH_BRICK_WALL);
				entries.add(SpectrumBlocks.BONE_ASH_TILES);
				entries.add(SpectrumBlocks.BONE_ASH_TILE_SLAB);
				entries.add(SpectrumBlocks.BONE_ASH_TILE_STAIRS);
				entries.add(SpectrumBlocks.BONE_ASH_TILE_WALL);
				entries.add(SpectrumBlocks.POLISHED_BONE_ASH_PILLAR);
				entries.add(SpectrumBlocks.BONE_ASH_SHINGLES);
				entries.add(SpectrumBlocks.BLACK_MATERIA);
				entries.add(SpectrumBlocks.SLUSH);
				entries.add(SpectrumBlocks.OVERGROWN_SLUSH);
				entries.add(SpectrumBlocks.TILLED_SLUSH);
				entries.add(SpectrumBlocks.BLACK_SLUDGE);
				
				entries.add(SpectrumItems.ASH_FLAKES);
				entries.add(SpectrumBlocks.ASH);
				entries.add(SpectrumBlocks.ASH_PILE);
				
				entries.add(SpectrumBlocks.ROCK_CRYSTAL);
				
				entries.add(SpectrumItems.PYRITE_CHUNK);
				entries.add(SpectrumBlocks.PYRITE);
				entries.add(SpectrumBlocks.PYRITE_SLAB);
				entries.add(SpectrumBlocks.PYRITE_STAIRS);
				entries.add(SpectrumBlocks.PYRITE_WALL);
				entries.add(SpectrumBlocks.PYRITE_PILE);
				entries.add(SpectrumBlocks.PYRITE_TILES);
				entries.add(SpectrumBlocks.PYRITE_TILES_SLAB);
				entries.add(SpectrumBlocks.PYRITE_TILES_STAIRS);
				entries.add(SpectrumBlocks.PYRITE_TILES_WALL);
				entries.add(SpectrumBlocks.PYRITE_PLATING);
				entries.add(SpectrumBlocks.PYRITE_TUBING);
				entries.add(SpectrumBlocks.PYRITE_RELIEF);
				entries.add(SpectrumBlocks.PYRITE_STACK);
				entries.add(SpectrumBlocks.PYRITE_PANELING);
				entries.add(SpectrumBlocks.PYRITE_VENT);
				entries.add(SpectrumBlocks.PYRITE_RIPPER);
				entries.add(SpectrumBlocks.PYRITE_PROJECTOR);
				
				entries.add(SpectrumBlocks.BASAL_MARBLE);
				entries.add(SpectrumBlocks.BASAL_MARBLE_STAIRS);
				entries.add(SpectrumBlocks.BASAL_MARBLE_SLAB);
				entries.add(SpectrumBlocks.BASAL_MARBLE_WALL);
				entries.add(SpectrumBlocks.POLISHED_BASAL_MARBLE);
				entries.add(SpectrumBlocks.POLISHED_BASAL_MARBLE_STAIRS);
				entries.add(SpectrumBlocks.POLISHED_BASAL_MARBLE_SLAB);
				entries.add(SpectrumBlocks.POLISHED_BASAL_MARBLE_WALL);
				entries.add(SpectrumBlocks.BASAL_MARBLE_PILLAR);
				entries.add(SpectrumBlocks.BASAL_MARBLE_TILES);
				entries.add(SpectrumBlocks.BASAL_MARBLE_TILE_STAIRS);
				entries.add(SpectrumBlocks.BASAL_MARBLE_TILE_SLAB);
				entries.add(SpectrumBlocks.BASAL_MARBLE_TILE_WALL);
				entries.add(SpectrumBlocks.BASAL_MARBLE_BRICKS);
				entries.add(SpectrumBlocks.BASAL_MARBLE_BRICK_STAIRS);
				entries.add(SpectrumBlocks.BASAL_MARBLE_BRICK_SLAB);
				entries.add(SpectrumBlocks.BASAL_MARBLE_BRICK_WALL);
				entries.add(SpectrumBlocks.LONGING_CHIMERA);
				
				entries.add(SpectrumBlocks.DRAGONBONE);
				entries.add(SpectrumBlocks.CRACKED_DRAGONBONE);
				entries.add(SpectrumBlocks.SAWBLADE_GRASS);
				entries.add(SpectrumBlocks.OVERGROWN_BLACKSLAG);
				entries.add(SpectrumBlocks.SHIMMEL);
				entries.add(SpectrumBlocks.ASHEN_BLACKSLAG);
				entries.add(SpectrumBlocks.ROTTEN_GROUND);
				entries.add(SpectrumBlocks.SLATE_NOXSHROOM);
				entries.add(SpectrumBlocks.SLATE_NOXCAP_BLOCK);
				entries.add(SpectrumBlocks.SLATE_NOXCAP_STEM);
				entries.add(SpectrumBlocks.STRIPPED_SLATE_NOXCAP_STEM);
				entries.add(SpectrumBlocks.SLATE_NOXCAP_HYPHAE);
				entries.add(SpectrumBlocks.STRIPPED_SLATE_NOXCAP_HYPHAE);
				entries.add(SpectrumBlocks.SLATE_NOXCAP_GILLS);
				entries.add(SpectrumBlocks.SLATE_NOXWOOD_PLANKS);
				entries.add(SpectrumBlocks.SLATE_NOXWOOD_STAIRS);
				entries.add(SpectrumBlocks.SLATE_NOXWOOD_SLAB);
				entries.add(SpectrumBlocks.SLATE_NOXWOOD_FENCE);
				entries.add(SpectrumBlocks.SLATE_NOXWOOD_FENCE_GATE);
				entries.add(SpectrumBlocks.SLATE_NOXWOOD_DOOR);
				entries.add(SpectrumBlocks.SLATE_NOXWOOD_TRAPDOOR);
				entries.add(SpectrumBlocks.SLATE_NOXWOOD_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.SLATE_NOXWOOD_BUTTON);
				entries.add(SpectrumBlocks.SLATE_NOXWOOD_PILLAR);
				entries.add(SpectrumBlocks.SLATE_NOXWOOD_AMPHORA);
				entries.add(SpectrumBlocks.SLATE_NOXWOOD_LAMP);
				entries.add(SpectrumBlocks.SLATE_NOXWOOD_LIGHT);
				entries.add(SpectrumBlocks.SLATE_NOXWOOD_LANTERN);
				entries.add(SpectrumBlocks.EBONY_NOXSHROOM);
				entries.add(SpectrumBlocks.EBONY_NOXCAP_BLOCK);
				entries.add(SpectrumBlocks.EBONY_NOXCAP_STEM);
				entries.add(SpectrumBlocks.STRIPPED_EBONY_NOXCAP_STEM);
				entries.add(SpectrumBlocks.EBONY_NOXCAP_HYPHAE);
				entries.add(SpectrumBlocks.STRIPPED_EBONY_NOXCAP_HYPHAE);
				entries.add(SpectrumBlocks.EBONY_NOXCAP_GILLS);
				entries.add(SpectrumBlocks.EBONY_NOXWOOD_PLANKS);
				entries.add(SpectrumBlocks.EBONY_NOXWOOD_STAIRS);
				entries.add(SpectrumBlocks.EBONY_NOXWOOD_SLAB);
				entries.add(SpectrumBlocks.EBONY_NOXWOOD_FENCE);
				entries.add(SpectrumBlocks.EBONY_NOXWOOD_FENCE_GATE);
				entries.add(SpectrumBlocks.EBONY_NOXWOOD_DOOR);
				entries.add(SpectrumBlocks.EBONY_NOXWOOD_TRAPDOOR);
				entries.add(SpectrumBlocks.EBONY_NOXWOOD_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.EBONY_NOXWOOD_BUTTON);
				entries.add(SpectrumBlocks.EBONY_NOXWOOD_PILLAR);
				entries.add(SpectrumBlocks.EBONY_NOXWOOD_AMPHORA);
				entries.add(SpectrumBlocks.EBONY_NOXWOOD_LAMP);
				entries.add(SpectrumBlocks.EBONY_NOXWOOD_LIGHT);
				entries.add(SpectrumBlocks.EBONY_NOXWOOD_LANTERN);
				entries.add(SpectrumBlocks.IVORY_NOXSHROOM);
				entries.add(SpectrumBlocks.IVORY_NOXCAP_BLOCK);
				entries.add(SpectrumBlocks.IVORY_NOXCAP_STEM);
				entries.add(SpectrumBlocks.STRIPPED_IVORY_NOXCAP_STEM);
				entries.add(SpectrumBlocks.IVORY_NOXCAP_HYPHAE);
				entries.add(SpectrumBlocks.STRIPPED_IVORY_NOXCAP_HYPHAE);
				entries.add(SpectrumBlocks.IVORY_NOXCAP_GILLS);
				entries.add(SpectrumBlocks.IVORY_NOXWOOD_PLANKS);
				entries.add(SpectrumBlocks.IVORY_NOXWOOD_STAIRS);
				entries.add(SpectrumBlocks.IVORY_NOXWOOD_SLAB);
				entries.add(SpectrumBlocks.IVORY_NOXWOOD_FENCE);
				entries.add(SpectrumBlocks.IVORY_NOXWOOD_FENCE_GATE);
				entries.add(SpectrumBlocks.IVORY_NOXWOOD_DOOR);
				entries.add(SpectrumBlocks.IVORY_NOXWOOD_TRAPDOOR);
				entries.add(SpectrumBlocks.IVORY_NOXWOOD_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.IVORY_NOXWOOD_BUTTON);
				entries.add(SpectrumBlocks.IVORY_NOXWOOD_PILLAR);
				entries.add(SpectrumBlocks.IVORY_NOXWOOD_AMPHORA);
				entries.add(SpectrumBlocks.IVORY_NOXWOOD_LAMP);
				entries.add(SpectrumBlocks.IVORY_NOXWOOD_LIGHT);
				entries.add(SpectrumBlocks.IVORY_NOXWOOD_LANTERN);
				entries.add(SpectrumBlocks.CHESTNUT_NOXSHROOM);
				entries.add(SpectrumBlocks.CHESTNUT_NOXCAP_BLOCK);
				entries.add(SpectrumBlocks.CHESTNUT_NOXCAP_STEM);
				entries.add(SpectrumBlocks.STRIPPED_CHESTNUT_NOXCAP_STEM);
				entries.add(SpectrumBlocks.CHESTNUT_NOXCAP_HYPHAE);
				entries.add(SpectrumBlocks.STRIPPED_CHESTNUT_NOXCAP_HYPHAE);
				entries.add(SpectrumBlocks.CHESTNUT_NOXCAP_GILLS);
				entries.add(SpectrumBlocks.CHESTNUT_NOXWOOD_PLANKS);
				entries.add(SpectrumBlocks.CHESTNUT_NOXWOOD_STAIRS);
				entries.add(SpectrumBlocks.CHESTNUT_NOXWOOD_SLAB);
				entries.add(SpectrumBlocks.CHESTNUT_NOXWOOD_FENCE);
				entries.add(SpectrumBlocks.CHESTNUT_NOXWOOD_FENCE_GATE);
				entries.add(SpectrumBlocks.CHESTNUT_NOXWOOD_DOOR);
				entries.add(SpectrumBlocks.CHESTNUT_NOXWOOD_TRAPDOOR);
				entries.add(SpectrumBlocks.CHESTNUT_NOXWOOD_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.CHESTNUT_NOXWOOD_BUTTON);
				entries.add(SpectrumBlocks.CHESTNUT_NOXWOOD_PILLAR);
				entries.add(SpectrumBlocks.CHESTNUT_NOXWOOD_AMPHORA);
				entries.add(SpectrumBlocks.CHESTNUT_NOXWOOD_LAMP);
				entries.add(SpectrumBlocks.CHESTNUT_NOXWOOD_LIGHT);
				entries.add(SpectrumBlocks.CHESTNUT_NOXWOOD_LANTERN);
				
				entries.add(SpectrumBlocks.WEEPING_GALA_SPRIG);
				entries.add(SpectrumBlocks.WEEPING_GALA_LEAVES);
				entries.add(SpectrumBlocks.WEEPING_GALA_LOG);
				entries.add(SpectrumBlocks.STRIPPED_WEEPING_GALA_LOG);
				entries.add(SpectrumBlocks.WEEPING_GALA_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_WEEPING_GALA_WOOD);
				entries.add(SpectrumBlocks.WEEPING_GALA_PLANKS);
				entries.add(SpectrumBlocks.WEEPING_GALA_STAIRS);
				entries.add(SpectrumBlocks.WEEPING_GALA_DOOR);
				entries.add(SpectrumBlocks.WEEPING_GALA_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.WEEPING_GALA_FENCE);
				entries.add(SpectrumBlocks.WEEPING_GALA_TRAPDOOR);
				entries.add(SpectrumBlocks.WEEPING_GALA_FENCE_GATE);
				entries.add(SpectrumBlocks.WEEPING_GALA_BUTTON);
				entries.add(SpectrumBlocks.WEEPING_GALA_SLAB);
				entries.add(SpectrumBlocks.WEEPING_GALA_PILLAR);
				entries.add(SpectrumBlocks.WEEPING_GALA_BARREL);
				entries.add(SpectrumBlocks.WEEPING_GALA_AMPHORA);
				entries.add(SpectrumBlocks.WEEPING_GALA_LANTERN);
				entries.add(SpectrumBlocks.WEEPING_GALA_LAMP);
				entries.add(SpectrumBlocks.WEEPING_GALA_LIGHT);
				
				entries.add(SpectrumBlocks.SMALL_RED_DRAGONJAG);
				entries.add(SpectrumBlocks.SMALL_YELLOW_DRAGONJAG);
				entries.add(SpectrumBlocks.SMALL_PINK_DRAGONJAG);
				entries.add(SpectrumBlocks.SMALL_PURPLE_DRAGONJAG);
				entries.add(SpectrumBlocks.SMALL_BLACK_DRAGONJAG);
				entries.add(SpectrumBlocks.BRISTLE_SPROUTS);
				entries.add(SpectrumBlocks.DOOMBLOOM);
				entries.add(SpectrumBlocks.SNAPPING_IVY);
				entries.add(SpectrumBlocks.SWEET_PEA);
				entries.add(SpectrumBlocks.APRICOTTI);
				entries.add(SpectrumBlocks.HUMMING_BELL);
				entries.add(SpectrumBlocks.HUMMINGSTONE);
				entries.add(SpectrumBlocks.WAXED_HUMMINGSTONE);
				entries.add(SpectrumBlocks.HUMMINGSTONE_GLASS);
				entries.add(SpectrumBlocks.HUMMINGSTONE_GLASS_PANE);
				entries.add(SpectrumBlocks.MOSS_BALL);
				entries.add(SpectrumBlocks.GIANT_MOSS_BALL);
				entries.add(SpectrumBlocks.NEPHRITE_BLOSSOM_STEM);
				entries.add(SpectrumBlocks.NEPHRITE_BLOSSOM_LEAVES);
				entries.add(SpectrumBlocks.VARIA_SPROUT);
				entries.add(SpectrumBlocks.JADEITE_LOTUS_STEM);
				entries.add(SpectrumBlocks.JADEITE_LOTUS_FLOWER);
			}).build();
	
	public static final ItemSubGroup DECORATION = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_DECORATION, Text.translatable("itemGroup.spectrum.decoration"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.add(SpectrumBlocks.TOPAZ_STORAGE_BLOCK);
				entries.add(SpectrumBlocks.AMETHYST_STORAGE_BLOCK);
				entries.add(SpectrumBlocks.CITRINE_STORAGE_BLOCK);
				entries.add(SpectrumBlocks.ONYX_STORAGE_BLOCK);
				entries.add(SpectrumBlocks.MOONSTONE_STORAGE_BLOCK);
				
				entries.add(SpectrumBlocks.VEGETAL_BLOCK);
				entries.add(SpectrumBlocks.NEOLITH_BLOCK);
				entries.add(SpectrumBlocks.BEDROCK_STORAGE_BLOCK);
				
				entries.add(SpectrumBlocks.SHIMMERSTONE_BLOCK);
				entries.add(SpectrumBlocks.AZURITE_BLOCK);
				entries.add(SpectrumBlocks.MALACHITE_BLOCK);
				entries.add(SpectrumBlocks.BLOODSTONE_BLOCK);
				entries.add(SpectrumBlocks.BISMUTH_BLOCK);
				
				entries.add(SpectrumBlocks.STRATINE_FLOATBLOCK);
				entries.add(SpectrumBlocks.PALTAERIA_FLOATBLOCK);
				entries.add(SpectrumBlocks.HOVER_BLOCK);
				
				entries.add(SpectrumBlocks.TOPAZ_CALCITE_LIGHT);
				entries.add(SpectrumBlocks.AMETHYST_CALCITE_LIGHT);
				entries.add(SpectrumBlocks.CITRINE_CALCITE_LIGHT);
				entries.add(SpectrumBlocks.ONYX_CALCITE_LIGHT);
				entries.add(SpectrumBlocks.MOONSTONE_CALCITE_LIGHT);
				entries.add(SpectrumBlocks.TOPAZ_BASALT_LIGHT);
				entries.add(SpectrumBlocks.AMETHYST_BASALT_LIGHT);
				entries.add(SpectrumBlocks.CITRINE_BASALT_LIGHT);
				entries.add(SpectrumBlocks.ONYX_BASALT_LIGHT);
				entries.add(SpectrumBlocks.MOONSTONE_BASALT_LIGHT);
				
				entries.add(SpectrumBlocks.BASALT_SHIMMERSTONE_LIGHT);
				entries.add(SpectrumBlocks.CALCITE_SHIMMERSTONE_LIGHT);
				entries.add(SpectrumBlocks.STONE_SHIMMERSTONE_LIGHT);
				entries.add(SpectrumBlocks.GRANITE_SHIMMERSTONE_LIGHT);
				entries.add(SpectrumBlocks.DIORITE_SHIMMERSTONE_LIGHT);
				entries.add(SpectrumBlocks.ANDESITE_SHIMMERSTONE_LIGHT);
				entries.add(SpectrumBlocks.DEEPSLATE_SHIMMERSTONE_LIGHT);
				entries.add(SpectrumBlocks.BLACKSLAG_SHIMMERSTONE_LIGHT);
				
				entries.add(SpectrumBlocks.TOPAZ_CHISELED_BASALT);
				entries.add(SpectrumBlocks.AMETHYST_CHISELED_BASALT);
				entries.add(SpectrumBlocks.CITRINE_CHISELED_BASALT);
				entries.add(SpectrumBlocks.ONYX_CHISELED_BASALT);
				entries.add(SpectrumBlocks.MOONSTONE_CHISELED_BASALT);
				entries.add(SpectrumBlocks.TOPAZ_CHISELED_CALCITE);
				entries.add(SpectrumBlocks.AMETHYST_CHISELED_CALCITE);
				entries.add(SpectrumBlocks.CITRINE_CHISELED_CALCITE);
				entries.add(SpectrumBlocks.ONYX_CHISELED_CALCITE);
				entries.add(SpectrumBlocks.MOONSTONE_CHISELED_CALCITE);
				
				entries.add(SpectrumBlocks.TOPAZ_GLASS);
				entries.add(SpectrumBlocks.AMETHYST_GLASS);
				entries.add(SpectrumBlocks.CITRINE_GLASS);
				entries.add(SpectrumBlocks.ONYX_GLASS);
				entries.add(SpectrumBlocks.MOONSTONE_GLASS);
				entries.add(SpectrumBlocks.RADIANT_GLASS);
				
				entries.add(SpectrumBlocks.TOPAZ_GLASS_PANE);
				entries.add(SpectrumBlocks.AMETHYST_GLASS_PANE);
				entries.add(SpectrumBlocks.CITRINE_GLASS_PANE);
				entries.add(SpectrumBlocks.ONYX_GLASS_PANE);
				entries.add(SpectrumBlocks.MOONSTONE_GLASS_PANE);
				entries.add(SpectrumBlocks.RADIANT_GLASS_PANE);
				
				entries.add(SpectrumBlocks.TOPAZ_CHIME);
				entries.add(SpectrumBlocks.AMETHYST_CHIME);
				entries.add(SpectrumBlocks.CITRINE_CHIME);
				entries.add(SpectrumBlocks.ONYX_CHIME);
				entries.add(SpectrumBlocks.MOONSTONE_CHIME);
				
				entries.add(SpectrumBlocks.AMETHYST_DECOSTONE);
				entries.add(SpectrumBlocks.TOPAZ_DECOSTONE);
				entries.add(SpectrumBlocks.CITRINE_DECOSTONE);
				entries.add(SpectrumBlocks.ONYX_DECOSTONE);
				entries.add(SpectrumBlocks.MOONSTONE_DECOSTONE);
				
				entries.add(SpectrumBlocks.JADE_VINE_PETAL_BLOCK);
				entries.add(SpectrumBlocks.JADE_VINE_PETAL_CARPET);
				
				entries.add(SpectrumBlocks.JADEITE_PETAL_BLOCK);
				entries.add(SpectrumBlocks.JADEITE_PETAL_CARPET);
				
				entries.add(SpectrumBlocks.RESPLENDENT_BLOCK);
				entries.add(SpectrumBlocks.RESPLENDENT_CUSHION);
				entries.add(SpectrumBlocks.RESPLENDENT_CARPET);
				entries.add(SpectrumBlocks.RESPLENDENT_BED);
				
				entries.add(SpectrumBlocks.WHITE_BLOCK);
				entries.add(SpectrumBlocks.ORANGE_BLOCK);
				entries.add(SpectrumBlocks.MAGENTA_BLOCK);
				entries.add(SpectrumBlocks.LIGHT_BLUE_BLOCK);
				entries.add(SpectrumBlocks.YELLOW_BLOCK);
				entries.add(SpectrumBlocks.LIME_BLOCK);
				entries.add(SpectrumBlocks.PINK_BLOCK);
				entries.add(SpectrumBlocks.GRAY_BLOCK);
				entries.add(SpectrumBlocks.LIGHT_GRAY_BLOCK);
				entries.add(SpectrumBlocks.CYAN_BLOCK);
				entries.add(SpectrumBlocks.PURPLE_BLOCK);
				entries.add(SpectrumBlocks.BLUE_BLOCK);
				entries.add(SpectrumBlocks.BROWN_BLOCK);
				entries.add(SpectrumBlocks.GREEN_BLOCK);
				entries.add(SpectrumBlocks.RED_BLOCK);
				entries.add(SpectrumBlocks.BLACK_BLOCK);
				entries.add(SpectrumBlocks.WHITE_LAMP);
				entries.add(SpectrumBlocks.ORANGE_LAMP);
				entries.add(SpectrumBlocks.MAGENTA_LAMP);
				entries.add(SpectrumBlocks.LIGHT_BLUE_LAMP);
				entries.add(SpectrumBlocks.YELLOW_LAMP);
				entries.add(SpectrumBlocks.LIME_LAMP);
				entries.add(SpectrumBlocks.PINK_LAMP);
				entries.add(SpectrumBlocks.GRAY_LAMP);
				entries.add(SpectrumBlocks.LIGHT_GRAY_LAMP);
				entries.add(SpectrumBlocks.CYAN_LAMP);
				entries.add(SpectrumBlocks.PURPLE_LAMP);
				entries.add(SpectrumBlocks.BLUE_LAMP);
				entries.add(SpectrumBlocks.BROWN_LAMP);
				entries.add(SpectrumBlocks.GREEN_LAMP);
				entries.add(SpectrumBlocks.RED_LAMP);
				entries.add(SpectrumBlocks.BLACK_LAMP);
				entries.add(SpectrumBlocks.WHITE_GLOWBLOCK);
				entries.add(SpectrumBlocks.ORANGE_GLOWBLOCK);
				entries.add(SpectrumBlocks.MAGENTA_GLOWBLOCK);
				entries.add(SpectrumBlocks.LIGHT_BLUE_GLOWBLOCK);
				entries.add(SpectrumBlocks.YELLOW_GLOWBLOCK);
				entries.add(SpectrumBlocks.LIME_GLOWBLOCK);
				entries.add(SpectrumBlocks.PINK_GLOWBLOCK);
				entries.add(SpectrumBlocks.GRAY_GLOWBLOCK);
				entries.add(SpectrumBlocks.LIGHT_GRAY_GLOWBLOCK);
				entries.add(SpectrumBlocks.CYAN_GLOWBLOCK);
				entries.add(SpectrumBlocks.PURPLE_GLOWBLOCK);
				entries.add(SpectrumBlocks.BLUE_GLOWBLOCK);
				entries.add(SpectrumBlocks.BROWN_GLOWBLOCK);
				entries.add(SpectrumBlocks.GREEN_GLOWBLOCK);
				entries.add(SpectrumBlocks.RED_GLOWBLOCK);
				entries.add(SpectrumBlocks.BLACK_GLOWBLOCK);
				entries.add(SpectrumBlocks.WHITE_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.ORANGE_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.MAGENTA_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.LIGHT_BLUE_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.YELLOW_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.LIME_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.PINK_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.GRAY_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.LIGHT_GRAY_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.CYAN_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.PURPLE_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.BLUE_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.BROWN_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.GREEN_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.RED_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.BLACK_SPORE_BLOSSOM);
				entries.add(SpectrumBlocks.RESONANT_LILY);
				entries.add(SpectrumItems.PHANTOM_FRAME);
				entries.add(SpectrumItems.GLOW_PHANTOM_FRAME);
				entries.add(SpectrumItems.LOGO_BANNER_PATTERN);
				entries.add(SpectrumItems.AMETHYST_SHARD_BANNER_PATTERN);
				entries.add(SpectrumItems.AMETHYST_CLUSTER_BANNER_PATTERN);
				entries.add(SpectrumItems.ASTROLOGER_BANNER_PATTERN);
				entries.add(SpectrumItems.VELVET_ASTROLOGER_BANNER_PATTERN);
				entries.add(SpectrumItems.POISONBLOOM_BANNER_PATTERN);
				entries.add(SpectrumItems.DEEP_LIGHT_BANNER_PATTERN);
				entries.add(SpectrumItems.MUSIC_DISC_DISCOVERY);
				entries.add(SpectrumItems.MUSIC_DISC_CREDITS);
				entries.add(SpectrumItems.MUSIC_DISC_DIVINITY);
			}).build();
	
	public static final ItemSubGroup COLORED_WOOD = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_COLORED_WOOD, Text.translatable("itemGroup.spectrum.colored_wood"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.add(SpectrumBlocks.WHITE_LOG);
				entries.add(SpectrumBlocks.ORANGE_LOG);
				entries.add(SpectrumBlocks.MAGENTA_LOG);
				entries.add(SpectrumBlocks.LIGHT_BLUE_LOG);
				entries.add(SpectrumBlocks.YELLOW_LOG);
				entries.add(SpectrumBlocks.LIME_LOG);
				entries.add(SpectrumBlocks.PINK_LOG);
				entries.add(SpectrumBlocks.GRAY_LOG);
				entries.add(SpectrumBlocks.LIGHT_GRAY_LOG);
				entries.add(SpectrumBlocks.CYAN_LOG);
				entries.add(SpectrumBlocks.PURPLE_LOG);
				entries.add(SpectrumBlocks.BLUE_LOG);
				entries.add(SpectrumBlocks.BROWN_LOG);
				entries.add(SpectrumBlocks.GREEN_LOG);
				entries.add(SpectrumBlocks.RED_LOG);
				entries.add(SpectrumBlocks.BLACK_LOG);
				entries.add(SpectrumBlocks.STRIPPED_WHITE_LOG);
				entries.add(SpectrumBlocks.STRIPPED_ORANGE_LOG);
				entries.add(SpectrumBlocks.STRIPPED_MAGENTA_LOG);
				entries.add(SpectrumBlocks.STRIPPED_LIGHT_BLUE_LOG);
				entries.add(SpectrumBlocks.STRIPPED_YELLOW_LOG);
				entries.add(SpectrumBlocks.STRIPPED_LIME_LOG);
				entries.add(SpectrumBlocks.STRIPPED_PINK_LOG);
				entries.add(SpectrumBlocks.STRIPPED_GRAY_LOG);
				entries.add(SpectrumBlocks.STRIPPED_LIGHT_GRAY_LOG);
				entries.add(SpectrumBlocks.STRIPPED_CYAN_LOG);
				entries.add(SpectrumBlocks.STRIPPED_PURPLE_LOG);
				entries.add(SpectrumBlocks.STRIPPED_BLUE_LOG);
				entries.add(SpectrumBlocks.STRIPPED_BROWN_LOG);
				entries.add(SpectrumBlocks.STRIPPED_GREEN_LOG);
				entries.add(SpectrumBlocks.STRIPPED_RED_LOG);
				entries.add(SpectrumBlocks.STRIPPED_BLACK_LOG);
				entries.add(SpectrumBlocks.WHITE_WOOD);
				entries.add(SpectrumBlocks.ORANGE_WOOD);
				entries.add(SpectrumBlocks.MAGENTA_WOOD);
				entries.add(SpectrumBlocks.LIGHT_BLUE_WOOD);
				entries.add(SpectrumBlocks.YELLOW_WOOD);
				entries.add(SpectrumBlocks.LIME_WOOD);
				entries.add(SpectrumBlocks.PINK_WOOD);
				entries.add(SpectrumBlocks.GRAY_WOOD);
				entries.add(SpectrumBlocks.LIGHT_GRAY_WOOD);
				entries.add(SpectrumBlocks.CYAN_WOOD);
				entries.add(SpectrumBlocks.PURPLE_WOOD);
				entries.add(SpectrumBlocks.BLUE_WOOD);
				entries.add(SpectrumBlocks.BROWN_WOOD);
				entries.add(SpectrumBlocks.GREEN_WOOD);
				entries.add(SpectrumBlocks.RED_WOOD);
				entries.add(SpectrumBlocks.BLACK_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_WHITE_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_ORANGE_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_MAGENTA_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_LIGHT_BLUE_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_YELLOW_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_LIME_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_PINK_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_GRAY_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_LIGHT_GRAY_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_CYAN_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_PURPLE_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_BLUE_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_BROWN_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_GREEN_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_RED_WOOD);
				entries.add(SpectrumBlocks.STRIPPED_BLACK_WOOD);
				entries.add(SpectrumBlocks.WHITE_LEAVES);
				entries.add(SpectrumBlocks.ORANGE_LEAVES);
				entries.add(SpectrumBlocks.MAGENTA_LEAVES);
				entries.add(SpectrumBlocks.LIGHT_BLUE_LEAVES);
				entries.add(SpectrumBlocks.YELLOW_LEAVES);
				entries.add(SpectrumBlocks.LIME_LEAVES);
				entries.add(SpectrumBlocks.PINK_LEAVES);
				entries.add(SpectrumBlocks.GRAY_LEAVES);
				entries.add(SpectrumBlocks.LIGHT_GRAY_LEAVES);
				entries.add(SpectrumBlocks.CYAN_LEAVES);
				entries.add(SpectrumBlocks.PURPLE_LEAVES);
				entries.add(SpectrumBlocks.BLUE_LEAVES);
				entries.add(SpectrumBlocks.BROWN_LEAVES);
				entries.add(SpectrumBlocks.GREEN_LEAVES);
				entries.add(SpectrumBlocks.RED_LEAVES);
				entries.add(SpectrumBlocks.BLACK_LEAVES);
				entries.add(SpectrumBlocks.WHITE_SAPLING);
				entries.add(SpectrumBlocks.ORANGE_SAPLING);
				entries.add(SpectrumBlocks.MAGENTA_SAPLING);
				entries.add(SpectrumBlocks.LIGHT_BLUE_SAPLING);
				entries.add(SpectrumBlocks.YELLOW_SAPLING);
				entries.add(SpectrumBlocks.LIME_SAPLING);
				entries.add(SpectrumBlocks.PINK_SAPLING);
				entries.add(SpectrumBlocks.GRAY_SAPLING);
				entries.add(SpectrumBlocks.LIGHT_GRAY_SAPLING);
				entries.add(SpectrumBlocks.CYAN_SAPLING);
				entries.add(SpectrumBlocks.PURPLE_SAPLING);
				entries.add(SpectrumBlocks.BLUE_SAPLING);
				entries.add(SpectrumBlocks.BROWN_SAPLING);
				entries.add(SpectrumBlocks.GREEN_SAPLING);
				entries.add(SpectrumBlocks.RED_SAPLING);
				entries.add(SpectrumBlocks.BLACK_SAPLING);
				entries.add(SpectrumBlocks.WHITE_PLANKS);
				entries.add(SpectrumBlocks.ORANGE_PLANKS);
				entries.add(SpectrumBlocks.MAGENTA_PLANKS);
				entries.add(SpectrumBlocks.LIGHT_BLUE_PLANKS);
				entries.add(SpectrumBlocks.YELLOW_PLANKS);
				entries.add(SpectrumBlocks.LIME_PLANKS);
				entries.add(SpectrumBlocks.PINK_PLANKS);
				entries.add(SpectrumBlocks.GRAY_PLANKS);
				entries.add(SpectrumBlocks.LIGHT_GRAY_PLANKS);
				entries.add(SpectrumBlocks.CYAN_PLANKS);
				entries.add(SpectrumBlocks.PURPLE_PLANKS);
				entries.add(SpectrumBlocks.BLUE_PLANKS);
				entries.add(SpectrumBlocks.BROWN_PLANKS);
				entries.add(SpectrumBlocks.GREEN_PLANKS);
				entries.add(SpectrumBlocks.RED_PLANKS);
				entries.add(SpectrumBlocks.BLACK_PLANKS);
				entries.add(SpectrumBlocks.WHITE_STAIRS);
				entries.add(SpectrumBlocks.ORANGE_STAIRS);
				entries.add(SpectrumBlocks.MAGENTA_STAIRS);
				entries.add(SpectrumBlocks.LIGHT_BLUE_STAIRS);
				entries.add(SpectrumBlocks.YELLOW_STAIRS);
				entries.add(SpectrumBlocks.LIME_STAIRS);
				entries.add(SpectrumBlocks.PINK_STAIRS);
				entries.add(SpectrumBlocks.GRAY_STAIRS);
				entries.add(SpectrumBlocks.LIGHT_GRAY_STAIRS);
				entries.add(SpectrumBlocks.CYAN_STAIRS);
				entries.add(SpectrumBlocks.PURPLE_STAIRS);
				entries.add(SpectrumBlocks.BLUE_STAIRS);
				entries.add(SpectrumBlocks.BROWN_STAIRS);
				entries.add(SpectrumBlocks.GREEN_STAIRS);
				entries.add(SpectrumBlocks.RED_STAIRS);
				entries.add(SpectrumBlocks.BLACK_STAIRS);
				entries.add(SpectrumBlocks.WHITE_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.ORANGE_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.MAGENTA_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.LIGHT_BLUE_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.YELLOW_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.LIME_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.PINK_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.GRAY_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.LIGHT_GRAY_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.CYAN_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.PURPLE_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.BLUE_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.BROWN_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.GREEN_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.RED_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.BLACK_PRESSURE_PLATE);
				entries.add(SpectrumBlocks.WHITE_FENCE);
				entries.add(SpectrumBlocks.ORANGE_FENCE);
				entries.add(SpectrumBlocks.MAGENTA_FENCE);
				entries.add(SpectrumBlocks.LIGHT_BLUE_FENCE);
				entries.add(SpectrumBlocks.YELLOW_FENCE);
				entries.add(SpectrumBlocks.LIME_FENCE);
				entries.add(SpectrumBlocks.PINK_FENCE);
				entries.add(SpectrumBlocks.GRAY_FENCE);
				entries.add(SpectrumBlocks.LIGHT_GRAY_FENCE);
				entries.add(SpectrumBlocks.CYAN_FENCE);
				entries.add(SpectrumBlocks.PURPLE_FENCE);
				entries.add(SpectrumBlocks.BLUE_FENCE);
				entries.add(SpectrumBlocks.BROWN_FENCE);
				entries.add(SpectrumBlocks.GREEN_FENCE);
				entries.add(SpectrumBlocks.RED_FENCE);
				entries.add(SpectrumBlocks.BLACK_FENCE);
				entries.add(SpectrumBlocks.WHITE_FENCE_GATE);
				entries.add(SpectrumBlocks.ORANGE_FENCE_GATE);
				entries.add(SpectrumBlocks.MAGENTA_FENCE_GATE);
				entries.add(SpectrumBlocks.LIGHT_BLUE_FENCE_GATE);
				entries.add(SpectrumBlocks.YELLOW_FENCE_GATE);
				entries.add(SpectrumBlocks.LIME_FENCE_GATE);
				entries.add(SpectrumBlocks.PINK_FENCE_GATE);
				entries.add(SpectrumBlocks.GRAY_FENCE_GATE);
				entries.add(SpectrumBlocks.LIGHT_GRAY_FENCE_GATE);
				entries.add(SpectrumBlocks.CYAN_FENCE_GATE);
				entries.add(SpectrumBlocks.PURPLE_FENCE_GATE);
				entries.add(SpectrumBlocks.BLUE_FENCE_GATE);
				entries.add(SpectrumBlocks.BROWN_FENCE_GATE);
				entries.add(SpectrumBlocks.GREEN_FENCE_GATE);
				entries.add(SpectrumBlocks.RED_FENCE_GATE);
				entries.add(SpectrumBlocks.BLACK_FENCE_GATE);
				entries.add(SpectrumBlocks.WHITE_BUTTON);
				entries.add(SpectrumBlocks.ORANGE_BUTTON);
				entries.add(SpectrumBlocks.MAGENTA_BUTTON);
				entries.add(SpectrumBlocks.LIGHT_BLUE_BUTTON);
				entries.add(SpectrumBlocks.YELLOW_BUTTON);
				entries.add(SpectrumBlocks.LIME_BUTTON);
				entries.add(SpectrumBlocks.PINK_BUTTON);
				entries.add(SpectrumBlocks.GRAY_BUTTON);
				entries.add(SpectrumBlocks.LIGHT_GRAY_BUTTON);
				entries.add(SpectrumBlocks.CYAN_BUTTON);
				entries.add(SpectrumBlocks.PURPLE_BUTTON);
				entries.add(SpectrumBlocks.BLUE_BUTTON);
				entries.add(SpectrumBlocks.BROWN_BUTTON);
				entries.add(SpectrumBlocks.GREEN_BUTTON);
				entries.add(SpectrumBlocks.RED_BUTTON);
				entries.add(SpectrumBlocks.BLACK_BUTTON);
				entries.add(SpectrumBlocks.WHITE_SLAB);
				entries.add(SpectrumBlocks.ORANGE_SLAB);
				entries.add(SpectrumBlocks.MAGENTA_SLAB);
				entries.add(SpectrumBlocks.LIGHT_BLUE_SLAB);
				entries.add(SpectrumBlocks.YELLOW_SLAB);
				entries.add(SpectrumBlocks.LIME_SLAB);
				entries.add(SpectrumBlocks.PINK_SLAB);
				entries.add(SpectrumBlocks.GRAY_SLAB);
				entries.add(SpectrumBlocks.LIGHT_GRAY_SLAB);
				entries.add(SpectrumBlocks.CYAN_SLAB);
				entries.add(SpectrumBlocks.PURPLE_SLAB);
				entries.add(SpectrumBlocks.BLUE_SLAB);
				entries.add(SpectrumBlocks.BROWN_SLAB);
				entries.add(SpectrumBlocks.GREEN_SLAB);
				entries.add(SpectrumBlocks.RED_SLAB);
				entries.add(SpectrumBlocks.BLACK_SLAB);
			}).build();
	
	public static final ItemSubGroup MOB_HEADS = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_MOB_HEADS, Text.translatable("itemGroup.spectrum.mob_heads"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				for (Block skullBlock : SpectrumSkullBlock.MOB_HEADS.values()) {
					entries.add(skullBlock.asItem());
				}
			}).build();
	
	public static final ItemSubGroup CREATURES = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_CREATURES, Text.translatable("itemGroup.spectrum.creatures"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.add(SpectrumItems.EGG_LAYING_WOOLY_PIG_SPAWN_EGG);
				entries.add(SpectrumItems.PRESERVATION_TURRET_SPAWN_EGG);
				entries.add(SpectrumItems.KINDLING_SPAWN_EGG);
				entries.add(SpectrumItems.LIZARD_SPAWN_EGG);
				entries.add(SpectrumItems.ERASER_SPAWN_EGG);
				entries.add(SpectrumItems.BUCKET_OF_ERASER);
				MemoryItem.appendEntries(entries);
			}).build();
	
	public static final ItemSubGroup ENERGY = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_ENERGY, Text.translatable("itemGroup.spectrum.energy"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.add(SpectrumItems.INK_FLASK);
				for (InkColor color : InkColors.all()) {
					entries.add(SpectrumItems.INK_FLASK.getFullStack(color));
				}
				entries.add(SpectrumItems.INK_ASSORTMENT);
				entries.add(SpectrumItems.INK_ASSORTMENT.getFullStack());
				entries.add(SpectrumItems.PIGMENT_PALETTE);
				entries.add(SpectrumItems.PIGMENT_PALETTE.getFullStack());
				entries.add(SpectrumItems.ARTISTS_PALETTE);
				entries.add(SpectrumItems.ARTISTS_PALETTE.getFullStack());
			}).build();
	
	public static final ItemSubGroup CREATIVE = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_CREATIVE, Text.translatable("itemGroup.spectrum.creative"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.add(SpectrumItems.PEDESTAL_TIER_1_STRUCTURE_PLACER);
				entries.add(SpectrumItems.PEDESTAL_TIER_2_STRUCTURE_PLACER);
				entries.add(SpectrumItems.PEDESTAL_TIER_3_STRUCTURE_PLACER);
				entries.add(SpectrumItems.FUSION_SHRINE_STRUCTURE_PLACER);
				entries.add(SpectrumItems.ENCHANTER_STRUCTURE_PLACER);
				entries.add(SpectrumItems.SPIRIT_INSTILLER_STRUCTURE_PLACER);
				entries.add(SpectrumItems.CINDERHEARTH_STRUCTURE_PLACER);
				
				entries.add(SpectrumBlocks.CREATIVE_PARTICLE_SPAWNER);
				entries.add(SpectrumItems.CREATIVE_INK_ASSORTMENT);
				entries.add(SpectrumItems.PRIMORDIAL_LIGHTER);
				
				entries.add(SpectrumItems.CONNECTION_NODE_CRYSTAL);
				entries.add(SpectrumItems.PROVIDER_NODE_CRYSTAL);
				entries.add(SpectrumItems.SENDER_NODE_CRYSTAL);
				entries.add(SpectrumItems.STORAGE_NODE_CRYSTAL);
				entries.add(SpectrumItems.BUFFER_NODE_CRYSTAL);
				entries.add(SpectrumItems.GATHER_NODE_CRYSTAL);
				
				entries.add(SpectrumBlocks.DOWNSTONE);
				entries.add(SpectrumBlocks.PRESERVATION_STONE);
				entries.add(SpectrumBlocks.PRESERVATION_STAIRS);
				entries.add(SpectrumBlocks.PRESERVATION_SLAB);
				entries.add(SpectrumBlocks.PRESERVATION_WALL);
				entries.add(SpectrumBlocks.PRESERVATION_BRICKS);
				entries.add(SpectrumBlocks.SHIMMERING_PRESERVATION_BRICKS);
				entries.add(SpectrumBlocks.POWDER_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.DIKE_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.DREAM_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.DEEP_LIGHT_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.TREASURE_ITEM_BOWL);
				entries.add(SpectrumBlocks.PRESERVATION_GLASS);
				entries.add(SpectrumBlocks.TINTED_PRESERVATION_GLASS);
				entries.add(SpectrumBlocks.PRESERVATION_ROUNDEL);
				entries.add(SpectrumBlocks.PRESERVATION_BLOCK_DETECTOR);
				entries.add(SpectrumBlocks.DIKE_GATE_FOUNTAIN);
				entries.add(SpectrumBlocks.DIKE_GATE);
				entries.add(SpectrumBlocks.DREAM_GATE);
				entries.add(SpectrumBlocks.PRESERVATION_CONTROLLER);
				
				entries.add(SpectrumBlocks.BLACK_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.BLUE_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.BROWN_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.CYAN_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.GRAY_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.GREEN_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.LIGHT_BLUE_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.LIGHT_GRAY_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.LIME_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.MAGENTA_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.ORANGE_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.PINK_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.PURPLE_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.RED_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.WHITE_CHISELED_PRESERVATION_STONE);
				entries.add(SpectrumBlocks.YELLOW_CHISELED_PRESERVATION_STONE);
				
				entries.add(SpectrumBlocks.INVISIBLE_WALL);
				entries.add(SpectrumBlocks.COURIER_STATUE);
				entries.add(SpectrumBlocks.PRESERVATION_CHEST);
				
				entries.add(SpectrumItems.DIVINATION_HEART);
				
				//entries.add(SpectrumItems.SPECTRAL_SHARD);
				//entries.add(SpectrumBlocks.SPECTRAL_SHARD_BLOCK);
				//entries.add(SpectrumBlocks.SPECTRAL_SHARD_STORAGE_BLOCK);
			}).build();
	
	public static void addEquipmentEntry(Item item, ItemGroup.Entries entries) {
		if (item instanceof Preenchanted preenchanted) {
			entries.add(preenchanted.getDefaultEnchantedStack(item));
		} else {
			entries.add(item);
		}
	}
}
