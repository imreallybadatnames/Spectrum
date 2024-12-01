package de.dafuqs.spectrum.config;

import me.shedaniel.autoconfig.*;
import me.shedaniel.autoconfig.annotation.*;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.*;

import java.util.*;

@Config(name = "Spectrum")
public class SpectrumConfig implements ConfigData {
	
	@Comment("The duration in milliseconds ingame recipe/unlock popups stay on the screen")
	public long ToastTimeMilliseconds = 7500;
	
	@Comment("The reverb decay time for sound effects in Spectrum's dimension")
	public float DimensionReverbDecayTime = 8.0F;
	
	@Comment("The reverb density for sound effects in Spectrum's dimension")
	public float DimensionReverbDensity = 0.5F;
	
	@Comment("Graphical options for spectrum's dimension")
	public boolean WindSim = true;
	public boolean ReducedParticles = false;
	
	@Comment("Affects how often the wind simulation updates - A lower number makes the simulation smoother, but increases the performance impact significantly")
	public int WindSimInterval = 3;

	@Comment("Story accurate dimension lighting. Deepens the dimension's darkness and prevents night vision from cutting through it. WARNING - This makes traversal significantly harder")
	public boolean ExtraDarkDimension = false;

	@Comment("Adjusts the general brightness of the dimension (an increase of 1 is a lot, for reference)")
	public float DimensionBrightnessMod = 1.0F;

	@Comment("Mod Integration Packs to not load (in case of mod compat errors)")
	public List<String> IntegrationPacksToSkipLoading = new ArrayList<>();
	
	@Comment("Add some advanced tooltips to items, like if Sculk Shriekers are able to summon the Warden")
	public boolean AddItemTooltips = true;

	@Comment("Replaces the ornate models of spectrum's transfer system with something simpler for increased visibility. Great for technical players!")
	public boolean MinimalNodes = false;

	@Comment("""
			The vanilla anvil caps enchantment levels at the max level for the enchantment
			So enchanted books that exceed the enchantments natural max level get capped
			If true the bedrock anvil will not cap the enchantments level to it's natural max level""")
	public boolean BedrockAnvilCanExceedMaxVanillaEnchantmentLevel = false;
	
	@Comment("""
			The chance that an Enderman is holding a special treasure block on spawn
			Separate value for Endermen spawning in the end, since there are LOTS of them there
			Those blocks do not gate progression, so it is not that drastic not finding any right away.
			Better to let players stumble about them organically instead of forcing it.""")
	public float EndermanHoldingEnderTreasureChance = 0.1F;
	public float EndermanHoldingEnderTreasureInEndChance = 0.0075F;
	
	@Comment("Worlds where shooting stars spawn for players. Shooting Stars will only spawn for players with sufficient progress in the mod")
	public List<String> ShootingStarWorlds = new ArrayList<>();
	
	@Comment("Worlds where lightning strikes can spawn Storm Stones")
	public List<String> StormStonesWorlds = new ArrayList<>();
	
	@Comment("Chance for a lightning strike to spawn a Storm Stone")
	public float StormStonesChance = 0.4F;

	@Comment("""
			Enables quitoxic reed tag-based spawning.
			By default, the reeds spawn in any biome that is
			wet, hot, and not an ocean.
			This can be swapped to using a tag, for more delicate filtering.
			""")
	public boolean QuitoxicSpawnTag = false;

	@Comment("""
			Shooting star spawns are checked every night between time 13000 and 22000, every 100 ticks (so 100 chances per night).
			By default, there is a 0.0075 ^= 0.75 % chance at each of those check times. Making it ~1 shooting star spawn
			per night per player that unlocked the required progression.""")
	public float ShootingStarChance = 0.0075F;
	
	@Comment("""
			The time in ticks it takes a Pigment Pedestal to craft a vanilla Crafting Table recipe without upgrades
			Setting this to <=0 will make the Pedestal not able to be used for crafting Crafting Table recipes.
			""")
	public int VanillaRecipeCraftingTimeTicks = 40;
	
	@Comment("""
			How fast decay will be spreading on random tick
			can be used to slow down propagation speed of decay in the worlds
			decay does use very few resources, but if your fear of someone letting decay
			spread free or using higher random tick rates than vanilla you can limit the spreading rate here
			
			Fading and Failing do no real harm to the world. If you turn up these values too high players
			may lack the feedback they need that what they are doing is correct
			
			1.0: Every random tick (default)
			0.5: Every second random tick
			0.0: Never (forbidden - players would be unable to progress)""")
	public float FadingDecayTickRate = 1.0F;
	public float FailingDecayTickRate = 1.0F;
	public float RuinDecayTickRate = 1.0F;
	public float ForfeitureDecayTickRate = 1.0F;
	
	@Comment("Whether bottles can be used to pick up decay. Default is true.")
	public boolean CanBottleUpFading = true;
	public boolean CanBottleUpFailing = true;
	public boolean CanBottleUpRuin = true;
	public boolean CanBottleUpForfeiture = true;
	
	@Comment("Whether decay can take over block entities. Defaults to false.")
	public boolean FadingCanDestroyBlockEntities = false;
	public boolean FailingCanDestroyBlockEntities = false;
	public boolean RuinCanDestroyBlockEntities = false;
	public boolean ForfeitureCanDestroyBlockEntities = true;
	
	@Comment("When a player places decay, add an entry to the server log")
	public boolean LogPlacingOfDecay = true;
	
	@Comment("The audio volume for Spectrums crafting blocks. Set to 0.0 to turn those sounds off completely.")
	public float BlockSoundVolume = 0.5F;
	
	@ConfigEntry.Gui.Tooltip
	@Comment("When empty, enchantments that the player has not unlocked show up with a scattered name. You can use a different name here")
	public String NameForUnrevealedEnchantments = "";
	
	@Comment("Exuberance increases experience gained when killing mobs. With 25% bonus XP and 5 levels this would mean 2,25x XP on max level")
	public float ExuberanceBonusExperiencePercentPerLevel = 0.25F;
	
	@Comment("In vanilla, crits are a flat 50 % damage bonus. Improved Critical increases this damage by additional 50 % per level by default")
	public float ImprovedCriticalExtraDamageMultiplierPerLevel = 0.5F;
	
	@Comment("Flat additional damage dealt with each level of the First Strike enchantment")
	public float FirstStrikeDamagePerLevel = 2.0F;

	//TODO: Re-add the settings to disable certain enchants

	@Comment("The percentile a mobs armor/hand stacks are being dropped when hit with a Disarming enchanted weapon per the enchantments level")
	public float DisarmingChancePerLevelMobs = 0.01F;
	@Comment("If > 0 the Disarming Enchantment is able to remove armor and hand tools from a hit player. Should be a far smaller chance than for mobs")
	public float DisarmingChancePerLevelPlayers = 0.001F;
	@Comment("The duration a glow ink sac gives night vision when wearing a glow vision helmet in seconds")
	public int GlowVisionGogglesDuration = 240;
	@Comment("If the Omni Accelerator should be able to have interactions in PvP that can drain the targets XP, modify their equipment, ... (configured via the requires_omni_accelerator_pvp_enabled item tag)")
	public boolean OmniAcceleratorPvP = false;
	
	public int GemstoneArmorHelmetProtection = 3;
	public int GemstoneArmorChestplateProtection = 7;
	public int GemstoneArmorLeggingsProtection = 5;
	public int GemstoneArmorBootsProtection = 3;
	public float GemstoneArmorToughness = 0.0F;
	public float GemstoneArmorKnockbackResistance = 0.0F;
	
	public int GemstoneArmorWeaknessAmplifier = 1;
	public int GemstoneArmorSlownessAmplifier = 1;
	public int GemstoneArmorAbsorptionAmplifier = 0;
	public int GemstoneArmorResistanceAmplifier = 0;
	public int GemstoneArmorRegenerationAmplifier = 0;
	public int GemstoneArmorSpeedAmplifier = 1;
	
	public int BedrockArmorHelmetProtection = 5;
	public int BedrockArmorLeggingsProtection = 9;
	public int BedrockArmorChestplateProtection = 13;
	public int BedrockArmorBootsProtection = 5;
	public float BedrockArmorToughness = 3.0F;
	public float BedrockArmorKnockbackResistance = 0.3F;
	
	public int MaxLevelForEffectsInLesserPotionPendant = 3;
	public int MaxLevelForEffectsInGreaterPotionPendant = 1;
	
	@Comment("""
			True will prevent the spread of Decay blocks in claims.
			Only enable when necessary and communicate to your players that those blocks will not work in their claims.
			If any player comes to the Spectrum devs claiming that decay does not spread for them, and therefore they could not progress
			You will get put on the 'bad pack devs' list and this config setting removed again
			""")
	public boolean DecayIsStoppedByClaimMods = false;
	
	@Comment("""
			By Default, Roughly Enough Items will show a 'recipe not unlocked yet' screen for not yet unlocked recipes.
			Setting this value to false will instead not show this screen, showing no recipes whatsoever, until unlocked
			""")
	public boolean REIListsRecipesAsNotUnlocked = true;
	
	@Comment("""
			If the player has Azure Dike Charges: Where should they be rendered on the screen. Default: Over the food bar
			Only touch those values if you have other mods that render GUI overlays!
			""")
	public int AzureDikeHudOffsetX = 0;
	public int AzureDikeHudOffsetY = 0;
	public int AzureDikeHudOffsetYWithArmor = -10;
	public int AzureDikeHudOffsetYForEachRowOfExtraHearts = -10;

	@Override
	public void validatePostLoad() {
		if (VanillaRecipeCraftingTimeTicks <= 0) {
			VanillaRecipeCraftingTimeTicks = 40;
		}
		if (FadingDecayTickRate <= 0.1) {
			FadingDecayTickRate = 1.0F;
		}
		if (FailingDecayTickRate <= 0.1) {
			FadingDecayTickRate = 1.0F;
		}
		if (RuinDecayTickRate <= 0.1) {
			RuinDecayTickRate = 1.0F;
		}
		if (ShootingStarChance <= 0.001) {
			ShootingStarChance = 0.01F;
		}
		if (StormStonesChance <= 0.03) {
			StormStonesChance = 0.3F;
		}
		if (EndermanHoldingEnderTreasureChance <= 0) {
			EndermanHoldingEnderTreasureChance = 0.05F;
		}
		if (ExuberanceBonusExperiencePercentPerLevel <= 0) {
			ExuberanceBonusExperiencePercentPerLevel = 0.2F;
		}
		if (ImprovedCriticalExtraDamageMultiplierPerLevel <= 0) {
			ImprovedCriticalExtraDamageMultiplierPerLevel = 0.5F;
		}
		if (FirstStrikeDamagePerLevel <= 0) {
			FirstStrikeDamagePerLevel = 3.0F;
		}
		if (ShootingStarWorlds.isEmpty()) {
			ShootingStarWorlds.add("minecraft:overworld");
			ShootingStarWorlds.add("starry_skies:overworld");
			ShootingStarWorlds.add("paradise_lost:paradise_lost");
		}
		if (StormStonesWorlds.isEmpty()) {
			StormStonesWorlds.add("minecraft:overworld");
			StormStonesWorlds.add("starry_skies:overworld");
			StormStonesWorlds.add("paradise_lost:paradise_lost");
		}
		
		if (WindSimInterval <= 0) {
			WindSimInterval = 1;
		} else if (WindSimInterval > 10) {
			WindSimInterval = 10;
		}
	}
	
	public boolean canPedestalCraftVanillaRecipes() {
		return VanillaRecipeCraftingTimeTicks > 0;
	}
	
	
}
