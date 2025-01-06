package de.dafuqs.spectrum.progression;

import de.dafuqs.spectrum.progression.advancement.*;
import net.minecraft.advancement.criterion.*;

public class SpectrumAdvancementCriteria {

	public static PedestalRecipeCalculatedCriterion PEDESTAL_RECIPE_CALCULATED;
	public static PedestalCraftingCriterion PEDESTAL_CRAFTING;
	public static FusionShrineCraftingCriterion FUSION_SHRINE_CRAFTING;
	public static CompletedMultiblockCriterion COMPLETED_MULTIBLOCK;
	public static BlockBrokenCriterion BLOCK_BROKEN;
	public static LootFunctionTriggerCriterion LOOT_FUNCTION_TRIGGER;
	public static NaturesStaffConversionCriterion NATURES_STAFF_USE;
	public static EnchanterCraftingCriterion ENCHANTER_CRAFTING;
	public static EnchanterEnchantingCriterion ENCHANTER_ENCHANTING;
	public static EnchantmentUpgradedCriterion ENCHANTER_UPGRADING;
	public static InertiaUsedCriterion INERTIA_USED;
	public static AzureDikeChargeCriterion AZURE_DIKE_CHARGE;
	public static TrinketChangeCriterion TRINKET_CHANGE;
	public static PotionWorkshopBrewingCriterion POTION_WORKSHOP_BREWING;
	public static PotionWorkshopCraftingCriterion POTION_WORKSHOP_CRAFTING;
	public static TakeOffBeltJumpCriterion TAKE_OFF_BELT_JUMP;
	public static InkContainerInteractionCriterion INK_CONTAINER_INTERACTION;
	public static JeopardantKillCriterion JEOPARDANT_KILL;
	public static MemoryManifestingCriterion MEMORY_MANIFESTING;
	public static SpiritInstillerCraftingCriterion SPIRIT_INSTILLER_CRAFTING;
	public static SlimeSizingCriterion SLIME_SIZING;
	public static CrystalApothecaryCollectingCriterion CRYSTAL_APOTHECARY_COLLECTING;
	public static UpgradePlaceCriterion UPGRADE_PLACING;
	public static CrystallarieumGrownCriterion CRYSTALLARIEUM_GROWING;
	public static CinderhearthSmeltingCriterion CINDERHEARTH_SMELTING;
	public static InkProjectileKillingCriterion KILLED_BY_INK_PROJECTILE;
	public static SpectrumFishingRodHookedCriterion FISHING_ROD_HOOKED;
	public static TitrationBarrelTappingCriterion TITRATION_BARREL_TAPPING;
	public static ConfirmationButtonPressedCriterion CONFIRMATION_BUTTON_PRESSED;
	public static BloodOrchidPluckingCriterion BLOOD_ORCHID_PLUCKING;
	public static DivinityTickCriterion DIVINITY_TICK;
	public static ConsumedTeaWithSconeCriterion CONSUMED_TEA_WITH_SCONE;
	public static HummingstoneHymnCriterion CREATE_HUMMINGSTONE_HYMN;
	public static PastelNetworkCreationCriterion PASTEL_NETWORK_CREATING;
	public static PastelNodeUpgradeCriterion PASTEL_NODE_UPGRADING;
	public static PreservationCheckCriterion PRESERVATION_CHECK;
	public static FluidDippingCriterion FLUID_DIPPING;

	public static void register() {
		PEDESTAL_RECIPE_CALCULATED = Criteria.register(PedestalRecipeCalculatedCriterion.ID.toString(), new PedestalRecipeCalculatedCriterion());
		PEDESTAL_CRAFTING = Criteria.register(PedestalCraftingCriterion.ID.toString(), new PedestalCraftingCriterion());
		FUSION_SHRINE_CRAFTING = Criteria.register(FusionShrineCraftingCriterion.ID.toString(), new FusionShrineCraftingCriterion());
		COMPLETED_MULTIBLOCK = Criteria.register(CompletedMultiblockCriterion.ID.toString(), new CompletedMultiblockCriterion());
		BLOCK_BROKEN = Criteria.register(BlockBrokenCriterion.ID.toString(), new BlockBrokenCriterion());
		LOOT_FUNCTION_TRIGGER = Criteria.register(LootFunctionTriggerCriterion.ID.toString(), new LootFunctionTriggerCriterion());
		NATURES_STAFF_USE = Criteria.register(NaturesStaffConversionCriterion.ID.toString(), new NaturesStaffConversionCriterion());
		ENCHANTER_CRAFTING = Criteria.register(EnchanterCraftingCriterion.ID.toString(), new EnchanterCraftingCriterion());
		ENCHANTER_ENCHANTING = Criteria.register(EnchanterEnchantingCriterion.ID.toString(), new EnchanterEnchantingCriterion());
		ENCHANTER_UPGRADING = Criteria.register(EnchantmentUpgradedCriterion.ID.toString(), new EnchantmentUpgradedCriterion());
		INERTIA_USED = Criteria.register(InertiaUsedCriterion.ID.toString(), new InertiaUsedCriterion());
		AZURE_DIKE_CHARGE = Criteria.register(AzureDikeChargeCriterion.ID.toString(), new AzureDikeChargeCriterion());
		TRINKET_CHANGE = Criteria.register(TrinketChangeCriterion.ID.toString(), new TrinketChangeCriterion());
		POTION_WORKSHOP_BREWING = Criteria.register(PotionWorkshopBrewingCriterion.ID.toString(), new PotionWorkshopBrewingCriterion());
		POTION_WORKSHOP_CRAFTING = Criteria.register(PotionWorkshopCraftingCriterion.ID.toString(), new PotionWorkshopCraftingCriterion());
		TAKE_OFF_BELT_JUMP = Criteria.register(TakeOffBeltJumpCriterion.ID.toString(), new TakeOffBeltJumpCriterion());
		INK_CONTAINER_INTERACTION = Criteria.register(InkContainerInteractionCriterion.ID.toString(), new InkContainerInteractionCriterion());
		JEOPARDANT_KILL = Criteria.register(JeopardantKillCriterion.ID.toString(), new JeopardantKillCriterion());
		MEMORY_MANIFESTING = Criteria.register(MemoryManifestingCriterion.ID.toString(), new MemoryManifestingCriterion());
		SPIRIT_INSTILLER_CRAFTING = Criteria.register(SpiritInstillerCraftingCriterion.ID.toString(), new SpiritInstillerCraftingCriterion());
		SLIME_SIZING = Criteria.register(SlimeSizingCriterion.ID.toString(), new SlimeSizingCriterion());
		CRYSTAL_APOTHECARY_COLLECTING = Criteria.register(CrystalApothecaryCollectingCriterion.ID.toString(), new CrystalApothecaryCollectingCriterion());
		UPGRADE_PLACING = Criteria.register(UpgradePlaceCriterion.ID.toString(), new UpgradePlaceCriterion());
		CRYSTALLARIEUM_GROWING = Criteria.register(CrystallarieumGrownCriterion.ID.toString(), new CrystallarieumGrownCriterion());
		CINDERHEARTH_SMELTING = Criteria.register(CinderhearthSmeltingCriterion.ID.toString(), new CinderhearthSmeltingCriterion());
		KILLED_BY_INK_PROJECTILE = Criteria.register(InkProjectileKillingCriterion.ID.toString(), new InkProjectileKillingCriterion());
		FISHING_ROD_HOOKED = Criteria.register(SpectrumFishingRodHookedCriterion.ID.toString(), new SpectrumFishingRodHookedCriterion());
		TITRATION_BARREL_TAPPING = Criteria.register(TitrationBarrelTappingCriterion.ID.toString(), new TitrationBarrelTappingCriterion());
		CONFIRMATION_BUTTON_PRESSED = Criteria.register(ConfirmationButtonPressedCriterion.ID.toString(), new ConfirmationButtonPressedCriterion());
		BLOOD_ORCHID_PLUCKING = Criteria.register(BloodOrchidPluckingCriterion.ID.toString(), new BloodOrchidPluckingCriterion());
		DIVINITY_TICK = Criteria.register(DivinityTickCriterion.ID.toString(), new DivinityTickCriterion());
		CONSUMED_TEA_WITH_SCONE = Criteria.register(ConsumedTeaWithSconeCriterion.ID.toString(), new ConsumedTeaWithSconeCriterion());
		CREATE_HUMMINGSTONE_HYMN = Criteria.register(HummingstoneHymnCriterion.ID.toString(), new HummingstoneHymnCriterion());
		PASTEL_NETWORK_CREATING = Criteria.register(PastelNetworkCreationCriterion.ID.toString(), new PastelNetworkCreationCriterion());
		PASTEL_NODE_UPGRADING = Criteria.register(PastelNodeUpgradeCriterion.ID.toString(), new PastelNodeUpgradeCriterion());
		PRESERVATION_CHECK = Criteria.register(PreservationCheckCriterion.ID.toString(), new PreservationCheckCriterion());
		FLUID_DIPPING = Criteria.register(FluidDippingCriterion.ID.toString(), new FluidDippingCriterion());
	}
	
}
