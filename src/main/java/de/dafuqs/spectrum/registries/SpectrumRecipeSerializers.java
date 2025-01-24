package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.recipe.anvil_crushing.*;
import de.dafuqs.spectrum.recipe.cinderhearth.*;
import de.dafuqs.spectrum.recipe.crafting.dynamic.*;
import de.dafuqs.spectrum.recipe.crystallarieum.*;
import de.dafuqs.spectrum.recipe.enchanter.*;
import de.dafuqs.spectrum.recipe.enchantment_upgrade.*;
import de.dafuqs.spectrum.recipe.fluid_converting.*;
import de.dafuqs.spectrum.recipe.fluid_converting.dynamic.*;
import de.dafuqs.spectrum.recipe.fusion_shrine.*;
import de.dafuqs.spectrum.recipe.fusion_shrine.dynamic.*;
import de.dafuqs.spectrum.recipe.ink_converting.*;
import de.dafuqs.spectrum.recipe.pedestal.*;
import de.dafuqs.spectrum.recipe.pedestal.dynamic.*;
import de.dafuqs.spectrum.recipe.potion_workshop.*;
import de.dafuqs.spectrum.recipe.primordial_fire_burning.*;
import de.dafuqs.spectrum.recipe.primordial_fire_burning.dynamic.*;
import de.dafuqs.spectrum.recipe.spirit_instiller.*;
import de.dafuqs.spectrum.recipe.spirit_instiller.dynamic.*;
import de.dafuqs.spectrum.recipe.spirit_instiller.dynamic.spawner_manipulation.*;
import de.dafuqs.spectrum.recipe.titration_barrel.*;
import de.dafuqs.spectrum.recipe.titration_barrel.dynamic.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;

public class SpectrumRecipeSerializers {
	
	private static final Deferrer DEFERRER = new Deferrer();
	
	// VANILLA
	public static final RecipeSerializer<RepairAnythingRecipe> REPAIR_ANYTHING_SERIALIZER = register("repair_anything", new EmptyRecipeSerializer<>(RepairAnythingRecipe::new));
	public static final RecipeSerializer<ClearInkRecipe> CLEAR_INK_SERIALIZER = register("clear_ink", new EmptyRecipeSerializer<>(ClearInkRecipe::new));
	public static final RecipeSerializer<ClearEnderSpliceRecipe> CLEAR_ENDER_SPLICE_SERIALIZER = register("clear_ender_splice", new EmptyRecipeSerializer<>(ClearEnderSpliceRecipe::new));
	public static final RecipeSerializer<ClearPotionFillableRecipe> CLEAR_POTION_FILLABLE_SERIALIZER = register("clear_potion_fillable", new EmptyRecipeSerializer<>(ClearPotionFillableRecipe::new));
	public static final RecipeSerializer<ClearCraftingTabletRecipe> CLEAR_CRAFTING_TABLET_SERIALIZER = register("clear_crafting_tablet", new EmptyRecipeSerializer<>(ClearCraftingTabletRecipe::new));
	public static final RecipeSerializer<ColorEverpromiseRibbonRecipe> COLOR_EVERPROMISE_RIBBON_SERIALIZER = register("color_everpromise_ribbon", new EmptyRecipeSerializer<>(ColorEverpromiseRibbonRecipe::new));
	public static final RecipeSerializer<WrapPresentRecipe> WRAP_PRESENT_SERIALIZER = register("wrap_present", new EmptyRecipeSerializer<>(WrapPresentRecipe::new));
	
	// Pedestal
	public static final RecipeSerializer<ShapedPedestalRecipe> SHAPED_PEDESTAL_RECIPE_SERIALIZER = register("pedestal", new ShapedPedestalRecipeSerializer());
	public static final RecipeSerializer<ShapelessPedestalRecipe> SHAPELESS_PEDESTAL_RECIPE_SERIALIZER = register("pedestal_shapeless", new ShapelessPedestalRecipeSerializer());
	public static final RecipeSerializer<StarCandyRecipe> PEDESTAL_STAR_CANDY = register("pedestal_star_candy", new EmptyRecipeSerializer<>(StarCandyRecipe::new));
	public static final RecipeSerializer<ExplosionModificationRecipe> MODULAR_EXPLOSIVE_MODIFICATION = register("modular_explosive_modification", new EmptyRecipeSerializer<>(ExplosionModificationRecipe::new));
	
	// Anvil Crushing
	public static final RecipeSerializer<AnvilCrushingRecipe> ANVIL_CRUSHING_RECIPE_SERIALIZER = register("anvil_crushing", new AnvilCrushingRecipeSerializer());
	
	// Fusion Shrine
	public static final RecipeSerializer<FusionShrineRecipe> FUSION_SHRINE_RECIPE_SERIALIZER = register("fusion_shrine", new FusionShrineRecipeSerializer());
	public static final RecipeSerializer<ShootingStarHardeningRecipe> SHOOTING_STAR_HARDENING = register("shooting_star_hardening", new EmptyRecipeSerializer<>(ShootingStarHardeningRecipe::new));
	
	// Enchanter
	public static final RecipeSerializer<EnchanterRecipe> ENCHANTER_RECIPE_SERIALIZER = register("enchanter", new EnchanterRecipeSerializer());
	public static final RecipeSerializer<EnchantmentUpgradeRecipe> ENCHANTMENT_UPGRADE_RECIPE_SERIALIZER = register("enchantment_upgrade", new EnchantmentUpgradeRecipeSerializer());
	
	// Potion Workshop
	public static final RecipeSerializer<PotionWorkshopBrewingRecipe> POTION_WORKSHOP_BREWING_RECIPE_SERIALIZER = register("potion_workshop_brewing", new PotionWorkshopBrewingRecipeSerializer(PotionWorkshopBrewingRecipe::new));
	public static final RecipeSerializer<PotionWorkshopCraftingRecipe> POTION_WORKSHOP_CRAFTING_RECIPE_SERIALIZER = register("potion_workshop_crafting", new PotionWorkshopCraftingRecipeSerializer(PotionWorkshopCraftingRecipe::new));
	public static final RecipeSerializer<PotionWorkshopReactingRecipe> POTION_WORKSHOP_REACTING_SERIALIZER = register("potion_workshop_reacting", new PotionWorkshopReactingRecipeSerializer());
	
	// Fluid converting
	public static final FluidConvertingRecipeSerializer GOO_CONVERTING_SERIALIZER = register("goo_converting", new FluidConvertingRecipeSerializer());
	public static final FluidConvertingRecipeSerializer LIQUID_CRYSTAL_CONVERTING_SERIALIZER = register("liquid_crystal_converting", new FluidConvertingRecipeSerializer());
	public static final FluidConvertingRecipeSerializer MIDNIGHT_SOLUTION_CONVERTING_SERIALIZER = register("midnight_solution_converting", new FluidConvertingRecipeSerializer());
	public static final FluidConvertingRecipeSerializer DRAGONROT_CONVERTING_SERIALIZER = register("dragonrot_converting", new FluidConvertingRecipeSerializer());
	public static final RecipeSerializer<MeatToRottenFleshRecipe> DRAGONROT_MEAT_TO_ROTTEN_FLESH = register("meat_rotting", new EmptyRecipeSerializer<>(MeatToRottenFleshRecipe::new));
	
	// Spirit Instiller
	public static final RecipeSerializer<SpiritInstillerRecipe> SPIRIT_INSTILLING_SERIALIZER = register("spirit_instiller", new SpiritInstillerRecipeSerializer());
	public static final RecipeSerializer<SpawnerCreatureChangeRecipe> SPIRIT_INSTILLER_SPAWNER_CREATURE_CHANGE = register("spirit_instiller_spawner_creature_change", new EmptyRecipeSerializer<>(SpawnerCreatureChangeRecipe::new));
	public static final RecipeSerializer<SpawnerMaxNearbyEntitiesChangeRecipe> SPIRIT_INSTILLER_SPAWNER_MAX_NEARBY_ENTITIES_CHANGE = register("spirit_instiller_spawner_max_nearby_entities_change", new EmptyRecipeSerializer<>(SpawnerMaxNearbyEntitiesChangeRecipe::new));
	public static final RecipeSerializer<SpawnerRequiredPlayerRangeChangeRecipe> SPIRIT_INSTILLER_SPAWNER_SPAWNER_PLAYER_RANGE_CHANGE = register("spirit_instiller_spawner_spawner_player_range_change", new EmptyRecipeSerializer<>(SpawnerRequiredPlayerRangeChangeRecipe::new));
	public static final RecipeSerializer<SpawnerSpawnCountChangeRecipe> SPIRIT_INSTILLER_SPAWNER_SPAWN_COUNT_CHANGE = register("spirit_instiller_spawner_spawn_count_change", new EmptyRecipeSerializer<>(SpawnerSpawnCountChangeRecipe::new));
	public static final RecipeSerializer<SpawnerSpawnDelayChangeRecipe> SPIRIT_INSTILLER_SPAWNER_SPAWN_DELAY_CHANGE = register("spirit_instiller_spawner_spawn_delay_change", new EmptyRecipeSerializer<>(SpawnerSpawnDelayChangeRecipe::new));
	public static final RecipeSerializer<HardcorePlayerRevivalRecipe> SPIRIT_INSTILLER_HARDCORE_PLAYER_REVIVAL = register("spirit_instiller_hardcore_player_revival", new EmptyRecipeSerializer<>(HardcorePlayerRevivalRecipe::new));
	public static final RecipeSerializer<MemoryToHeadRecipe> SPIRIT_INSTILLER_MEMORY_TO_HEAD = register("spirit_instiller_memory_to_head", new EmptyRecipeSerializer<>(MemoryToHeadRecipe::new));
	
	// Color Picker
	public static final RecipeSerializer<InkConvertingRecipe> INK_CONVERTING_RECIPE_SERIALIZER = register("ink_converting", new InkConvertingRecipeSerializer());
	
	// Crystallarieum
	public static final RecipeSerializer<CrystallarieumRecipe> CRYSTALLARIEUM_RECIPE_SERIALIZER = register("crystallarieum_growing", new CrystallarieumRecipeSerializer());
	
	// Cinderhearth
	public static final RecipeSerializer<CinderhearthRecipe> CINDERHEARTH_RECIPE_SERIALIZER = register("cinderhearth", new CinderhearthRecipeSerializer());
	
	// Titration Barrel
	public static final RecipeSerializer<TitrationBarrelRecipe> TITRATION_BARREL = register("titration_barrel", new TitrationBarrelRecipeSerializer());
	public static final RecipeSerializer<JadeWineRecipe> TITRATION_BARREL_JADE_WINE = register("titration_barrel_jade_wine", new EmptyRecipeSerializer<>(JadeWineRecipe::new));
	public static final RecipeSerializer<AquaRegiaRecipe> TITRATION_BARREL_AQUA_REGIA = register("titration_barrel_aqua_regia", new EmptyRecipeSerializer<>(AquaRegiaRecipe::new));
	public static final RecipeSerializer<NecteredViognierRecipe> TITRATION_BARREL_NECTERED_VIOGNIER = register("titration_barrel_nectered_viognier", new EmptyRecipeSerializer<>(NecteredViognierRecipe::new));
	public static final RecipeSerializer<SuspiciousBrewRecipe> TITRATION_BARREL_SUSPICIOUS_BREW = register("titration_barrel_suspicious_brew", new EmptyRecipeSerializer<>(SuspiciousBrewRecipe::new));
	public static final RecipeSerializer<CheongRecipe> TITRATION_BARREL_CHEONG = register("titration_barrel_cheong", new EmptyRecipeSerializer<>(CheongRecipe::new));
	
	// Primordial Fire
	public static final RecipeSerializer<PrimordialFireBurningRecipe> PRIMORDIAL_FIRE_BURNING_RECIPE_SERIALIZER = register("primordial_fire_burning", new PrimordialFireBurningRecipeSerializer());
	public static final RecipeSerializer<MemoryDementiaRecipe> MEMORY_DEMENTIA = register("memory_dementia", new EmptyRecipeSerializer<>(MemoryDementiaRecipe::new));
	public static final RecipeSerializer<EnchantedBookUnsoulingRecipe> ENCHANTED_BOOK_UNSOULING = register("enchanted_book_unsouling", new EmptyRecipeSerializer<>(EnchantedBookUnsoulingRecipe::new));
	
	
	static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
		return DEFERRER.defer(serializer, v -> Registry.register(Registries.RECIPE_SERIALIZER, SpectrumCommon.locate(id), v));
	}
	
	public static void register() {
		DEFERRER.flush();
	}
	
}
