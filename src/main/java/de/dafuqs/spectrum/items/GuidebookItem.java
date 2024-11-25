package de.dafuqs.spectrum.items;

import com.klikli_dev.modonomicon.client.gui.*;
import de.dafuqs.revelationary.*;
import de.dafuqs.revelationary.advancement_criteria.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.minecraft.advancement.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.*;
import net.minecraft.server.network.*;
import net.minecraft.stat.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class GuidebookItem extends Item implements LoomPatternProvider {
	
	public static final Identifier GUIDEBOOK_ID = SpectrumCommon.locate("guidebook");
	
	public GuidebookItem(Settings settings) {
		super(settings);
	}
	
	
	private static final Set<UUID> alreadyReprocessedPlayers = new HashSet<>();
	
	public static void reprocessAdvancementUnlocks(ServerPlayerEntity serverPlayerEntity) {
		if (serverPlayerEntity.getServer() == null || SpectrumCommon.minecraftServer == null) {
			return;
		}
		
		UUID uuid = serverPlayerEntity.getUuid();
		if (alreadyReprocessedPlayers.contains(uuid)) {
			return;
		}
		alreadyReprocessedPlayers.add(uuid);
		
		PlayerAdvancementTracker tracker = serverPlayerEntity.getAdvancementTracker();
		
		for (var advancement : serverPlayerEntity.getServer().getAdvancementLoader().getAdvancements()) {
			var hasAdvancement = tracker.getProgress(advancement);
			if (!hasAdvancement.isDone()) {
				for (var criterionEntry : advancement.value().criteria().entrySet()) {
					var conditions = criterionEntry.getValue().conditions();
					if (conditions instanceof AdvancementGottenCriterion.Conditions hasAdvancementConditions) {
						var advancementCriterionAdvancement = SpectrumCommon.minecraftServer.getAdvancementLoader().get(hasAdvancementConditions.getAdvancementIdentifier());
						if (advancementCriterionAdvancement != null) {
							var hasAdvancementCriterionAdvancement = tracker.getProgress(advancementCriterionAdvancement);
							if (hasAdvancementCriterionAdvancement.isDone()) {
								tracker.grantCriterion(advancement, criterionEntry.getKey());
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (world.isClient()) {
			// if the player has never opened the book before
			// automatically open the introduction page
			openGuidebook();
			
			/** TODO: this can be removed by putting
			 * "entry_to_open": "spectrum:general/intro",
			 * "open_entry_to_open_only_once": true,
			 *
			 * in the general category entry in 1.21
			 * https://klikli-dev.github.io/modonomicon/docs/basics/structure/categories
			 */
			if (!hasOpenedGuidebookBefore()) {
				openGuidebook(SpectrumCommon.locate("general/intro"), 0);
			}
		} else if (user instanceof ServerPlayerEntity serverPlayerEntity) {
			// Process new advancement unlocks that got added
			// after spectrum has been installed / updated
			reprocessAdvancementUnlocks(serverPlayerEntity);
			
			// there is no "use_item" advancement trigger smh
			Support.grantAdvancementCriterion(serverPlayerEntity, "hidden/opened_guidebook", "opened_guidebook");
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		
		return TypedActionResult.success(user.getStackInHand(hand), world.isClient);
	}
	
	/**
	 * If clientside and the client does not have stats synced yet (not opened the stats screen)
	 * this is always false 💀
	 */
	@Environment(EnvType.CLIENT)
	private boolean hasOpenedGuidebookBefore() {
		return ClientAdvancements.hasDone(SpectrumCommon.locate("hidden/opened_guidebook"));
	}
	
	public void openGuidebook() {
		BookGuiManager.get().openBook(GUIDEBOOK_ID);
	}
	
	public void openGuidebook(Identifier entry, int page) {
		BookGuiManager.get().openEntry(GUIDEBOOK_ID, entry, page);
	}
	
	@Override
	public RegistryKey<BannerPattern> getPattern() {
		return SpectrumBannerPatterns.GUIDEBOOK;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		addBannerPatternProviderTooltip(tooltip);
	}
	
}
