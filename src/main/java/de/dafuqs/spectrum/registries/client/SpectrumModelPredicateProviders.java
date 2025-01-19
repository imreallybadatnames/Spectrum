package de.dafuqs.spectrum.registries.client;

import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.energy.storage.*;
import de.dafuqs.spectrum.api.entity.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.component_type.*;
import de.dafuqs.spectrum.items.energy.*;
import de.dafuqs.spectrum.items.magic_items.*;
import de.dafuqs.spectrum.items.tools.*;
import de.dafuqs.spectrum.items.trinkets.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.client.*;
import net.minecraft.client.item.*;
import net.minecraft.client.world.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

// Vanilla models see: ModelPredicateProviderRegistry
public class SpectrumModelPredicateProviders {
	
	public static void registerClient() {
		registerBowPredicates(SpectrumItems.BEDROCK_BOW);
		registerCrossbowPredicates(SpectrumItems.BEDROCK_CROSSBOW);
		registerSpectrumFishingRodItemPredicates(SpectrumItems.LAGOON_ROD);
		registerSpectrumFishingRodItemPredicates(SpectrumItems.MOLTEN_ROD);
		registerSpectrumFishingRodItemPredicates(SpectrumItems.BEDROCK_FISHING_ROD);
		registerEnderSplicePredicates(SpectrumItems.ENDER_SPLICE);
		registerAnimatedWandPredicates(SpectrumItems.NATURES_STAFF);
		registerAnimatedWandPredicates(SpectrumItems.RADIANCE_STAFF);
		registerAnimatedWandPredicates(SpectrumItems.STAFF_OF_REMEMBRANCE);
		registerKnowledgeDropPredicates(SpectrumItems.KNOWLEDGE_GEM);
		registerAshenCircletPredicates(SpectrumItems.ASHEN_CIRCLET);
		registerStampingItemPredicate(SpectrumItems.TUNING_STAMP);
		registerInkColorPredicate(SpectrumItems.INK_FLASK);
		registerInkFillStateItemPredicate(SpectrumItems.INK_FLASK);
		registerMoonPhasePredicates(SpectrumItems.CRESCENT_CLOCK);
		registerActivatableItemPredicate(SpectrumItems.DREAMFLAYER);
		registerOversizedItemPredicate(SpectrumItems.DREAMFLAYER);
		registerOversizedItemPredicate(SpectrumItems.KNOTTED_SWORD);
		registerOversizedItemPredicate(SpectrumItems.NECTAR_LANCE);
		registerOversizedItemPredicate(SpectrumItems.BEDROCK_SWORD);
		registerOversizedItemPredicate(SpectrumItems.BEDROCK_AXE);

		registerOversizedItemPredicate(SpectrumItems.PAINTBRUSH);

		registerOversizedItemPredicate(SpectrumItems.DRACONIC_TWINSWORD);
		registerOversizedItemPredicate(SpectrumItems.DRAGON_TALON);
		registerSlotReservingItem(SpectrumItems.DRAGON_TALON);
		registerSlotReservingItem(SpectrumItems.DRACONIC_TWINSWORD);

		registerOversizedItemPredicate(SpectrumItems.MALACHITE_WORKSTAFF);
		registerOversizedItemPredicate(SpectrumItems.MALACHITE_ULTRA_GREATSWORD);
		registerOversizedItemPredicate(SpectrumItems.MALACHITE_CROSSBOW);
		registerOversizedItemPredicate(SpectrumItems.MALACHITE_BIDENT);
		registerOversizedItemPredicate(SpectrumItems.GLASS_CREST_WORKSTAFF);
		registerOversizedItemPredicate(SpectrumItems.GLASS_CREST_ULTRA_GREATSWORD);
		registerOversizedItemPredicate(SpectrumItems.GLASS_CREST_CROSSBOW);
		registerOversizedItemPredicate(SpectrumItems.FEROCIOUS_GLASS_CREST_BIDENT);
		registerOversizedItemPredicate(SpectrumItems.FRACTAL_GLASS_CREST_BIDENT);
		
		registerBidentThrowingItemPredicate(SpectrumItems.MALACHITE_BIDENT);
		registerBidentThrowingItemPredicate(SpectrumItems.FEROCIOUS_GLASS_CREST_BIDENT);
		registerBidentThrowingItemPredicate(SpectrumItems.FRACTAL_GLASS_CREST_BIDENT);
		
		registerMalachiteCrossbowPredicates(SpectrumItems.MALACHITE_CROSSBOW);
		registerMalachiteCrossbowPredicates(SpectrumItems.GLASS_CREST_CROSSBOW);
		
		registerBottomlessBundlePredicates(SpectrumBlocks.BOTTOMLESS_BUNDLE.asItem());
		registerEnchantmentCanvasPrediates(SpectrumItems.ENCHANTMENT_CANVAS);
		registerPresentPredicates(SpectrumBlocks.PRESENT.asItem());
		registerMysteriousLocketPredicates(SpectrumItems.MYSTERIOUS_LOCKET);
		registerStructureCompassPredicates(SpectrumItems.MYSTERIOUS_COMPASS);
		registerNullableDyeColorPredicate(SpectrumBlocks.CRYSTALLARIEUM.asItem());

		registerPipeBombPredicates(SpectrumItems.PIPE_BOMB);
	}
	
	private static void registerNullableDyeColorPredicate(Item item) {
		ModelPredicateProviderRegistry.register(item, Identifier.of("color"), (stack, clientWorld, entity, i) -> {
			OptionalInkColorComponent component = stack.get(SpectrumDataComponentTypes.OPTIONAL_INK_COLOR);
			if (component == null || component.color().isEmpty()) {
				return -1;
			}
			return component.color().get().getColorInt();
		});
	}
	
	private static void registerMysteriousLocketPredicates(Item item) {
		ModelPredicateProviderRegistry.register(item, Identifier.of("socketed"), (stack, world, entity, i) -> stack.contains(SpectrumDataComponentTypes.SOCKETED) ? 1.0F : 0.0F);
	}
	
	private static void registerStructureCompassPredicates(Item item) {
		ModelPredicateProviderRegistry.register(item, Identifier.of("angle"), new CompassAnglePredicateProvider((world, stack, entity) -> StructureCompassItem.getStructurePos(stack)));
	}
	
	private static void registerMalachiteCrossbowPredicates(Item crossbowItem) {
		ModelPredicateProviderRegistry.register(crossbowItem, Identifier.of("pull"), (stack, world, user, i) -> {
			if (user == null) {
				return 0.0F;
			} else {
				return CrossbowItem.isCharged(stack) ? 0.0F : (float) (stack.getMaxUseTime(user) - user.getItemUseTimeLeft()) / (float) CrossbowItem.getPullTime(stack, user);
			}
		});
		ModelPredicateProviderRegistry.register(crossbowItem, Identifier.of("pulling"), (stack, world, entity, i) ->
				entity != null && entity.isUsingItem() && entity.getActiveItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
		);
		ModelPredicateProviderRegistry.register(crossbowItem, Identifier.of("charged"), (stack, world, entity, i) ->
				entity != null && CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
		);
		ModelPredicateProviderRegistry.register(crossbowItem, Identifier.of("projectile"), (stack, world, entity, seed) -> {
			if (stack == null) {
				return 0F;
			}
			ItemStack projectile = MalachiteCrossbowItem.getFirstProjectile(MinecraftClient.getInstance().world.getRegistryManager(), stack);
			if(projectile.isEmpty()) {
				return 0F;
			}
			
			// Well, this is awkward
			if (projectile.isOf(Items.FIREWORK_ROCKET)) {
				return 0.1F;
			} else if (projectile.isOf(SpectrumItems.MALACHITE_GLASS_ARROW)) {
				return 0.2F;
			} else if (projectile.isOf(SpectrumItems.TOPAZ_GLASS_ARROW)) {
				return 0.3F;
			} else if (projectile.isOf(SpectrumItems.AMETHYST_GLASS_ARROW)) {
				return 0.4F;
			} else if (projectile.isOf(SpectrumItems.CITRINE_GLASS_ARROW)) {
				return 0.5F;
			} else if (projectile.isOf(SpectrumItems.ONYX_GLASS_ARROW)) {
				return 0.6F;
			} else if (projectile.isOf(SpectrumItems.MOONSTONE_GLASS_ARROW)) {
				return 0.7F;
			}
			return 0F;
		});
	}
	
	/**
	 * 0.0: not throwing
	 * 0.5: throwing in hand
	 * 1.0: as projectile
	 */
	private static void registerBidentThrowingItemPredicate(Item item) {
		ModelPredicateProviderRegistry.register(item, Identifier.of("bident_throwing"), (stack, world, entity, i) -> {
			/* I believe this is unused now... nothing noticeable seems to have happened, but I would prefer to be safe than sorry.
			if (currentItemRenderMode == ModelTransformationMode.NONE) {
				if (stack.getItem() instanceof FractalBidentItem fractal) {
					return fractal.isDisabled(stack) ? 0.5F : 1F;
				}
				return 1.0F;
			}*/
			return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 0.5F : 0.0F;
		});
	}
	
	private static void registerPresentPredicates(Item item) {
		ModelPredicateProviderRegistry.register(item, Identifier.of("variant"), (stack, world, entity, i) -> {
			WrappedPresentComponent component = stack.get(SpectrumDataComponentTypes.WRAPPED_PRESENT);
			return component == null ? 0.0F : component.variant().ordinal() / 10F;
		});
	}
	
	private static void registerBottomlessBundlePredicates(Item item) {
		ModelPredicateProviderRegistry.register(item, Identifier.of("locked"), (stack, world, entity, i) -> {
			NbtCompound compound = stack.getNbt();
			if (compound == null)
				return 0.0F;
			return compound.contains("Locked") ? 1.0F : 0.0F;
		});
		ModelPredicateProviderRegistry.register(SpectrumBlocks.BOTTOMLESS_BUNDLE, Identifier.of("filled"), (stack, world, entity, i) -> {
			NbtCompound compound = stack.getNbt();
			if (compound == null)
				return 0.0F;
			return compound.contains("StoredStack") ? 1.0F : 0.0F;
		});
	}
	
	private static void registerMoonPhasePredicates(Item item) {
		ModelPredicateProviderRegistry.register(item, Identifier.of("phase"), (stack, world, entity, i) -> {
			Entity holder = entity != null ? entity : stack.getHolder();
			if (entity == null) {
				return 0.0F;
			} else {
				if (world == null && holder.getWorld() instanceof ClientWorld clientWorld) {
					world = clientWorld;
				}
				
				if (world == null) {
					return 0.0F;
				} else if (!world.getDimension().natural()) {
					return 1.0F;
				} else {
					return world.getMoonPhase() / 8F;
				}
			}
		});
	}
	
	private static void registerActivatableItemPredicate(Item item) {
		ModelPredicateProviderRegistry.register(item, Identifier.of(ActivatableItem.NBT_STRING), (stack, world, entity, i) -> {
			if (ActivatableItem.isActivated(stack)) {
				return 1.0F;
			} else {
				return 0.0F;
			}
		});
	}

	private static void registerStampingItemPredicate(Item item) {
		ModelPredicateProviderRegistry.register(item, Identifier.of("stamped"), ((stack, world, entity, seed) -> {
			var nbt = stack.getOrCreateNbt();
			if (nbt.contains(Stampable.STAMPING_DATA_TAG))
				return 1F;

			return 0F;
		}));
	}
	
	private static void registerSlotReservingItem(Item item) {
		ModelPredicateProviderRegistry.register(item, Identifier.of(SlotReservingItem.NBT_STRING), (stack, world, entity, i) -> {
			if (stack.getItem() instanceof SlotReservingItem reserver && reserver.isReservingSlot(stack)) {
				return 1.0F;
			} else {
				return 0.0F;
			}
		});
	}
	
	private static void registerOversizedItemPredicate(Item item) {
		ModelPredicateProviderRegistry.register(item, Identifier.of("oversized"), (stack, world, entity, seed) -> seed == 817210941 ? 1.0F : 0.0F);
	}
	
	private static void registerBowPredicates(Item bowItem) {
		ModelPredicateProviderRegistry.register(bowItem, Identifier.of("pull"), (stack, world, entity, i) -> {
			if (entity == null) {
				return 0.0F;
			} else {
				return entity.getActiveItem() != stack ? 0.0F : (float) (stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft()) / 20.0F;
			}
		});
		ModelPredicateProviderRegistry.register(bowItem, Identifier.of("pulling"), (stack, world, entity, i) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
	}
	
	private static void registerCrossbowPredicates(Item crossbowItem) {
		ModelPredicateProviderRegistry.register(crossbowItem, Identifier.of("pull"), (stack, world, entity, i) -> {
			if (entity == null) {
				return 0.0F;
			} else {
				return CrossbowItem.isCharged(stack) ? 0.0F : (float) (stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft()) / (float) CrossbowItem.getPullTime(stack, entity);
			}
		});
		
		ModelPredicateProviderRegistry.register(crossbowItem, Identifier.of("pulling"), (stack, world, entity, i) ->
				entity != null && entity.isUsingItem() && entity.getActiveItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
		);
		
		ModelPredicateProviderRegistry.register(crossbowItem, Identifier.of("charged"), (stack, world, entity, i) ->
				entity != null && CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
		);
		
		ModelPredicateProviderRegistry.register(crossbowItem, Identifier.of("firework"), (stack, world, entity, seed) -> {
			ChargedProjectilesComponent chargedProjectilesComponent = stack.get(DataComponentTypes.CHARGED_PROJECTILES);
			return chargedProjectilesComponent != null && chargedProjectilesComponent.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
		});
	}

	private static void registerPipeBombPredicates(Item pipeBombItem) {
		ModelPredicateProviderRegistry.register(pipeBombItem, Identifier.of("armed"), (stack, world, entity, seed) -> PipeBombItem.isArmed(stack) ? 1.0F : 0.0F);
	}
	
	private static void registerSpectrumFishingRodItemPredicates(Item fishingRodItem) {
		ModelPredicateProviderRegistry.register(fishingRodItem, Identifier.of("cast"), (stack, world, entity, i) -> {
			if (entity == null) {
				return 0.0F;
			} else {
				boolean isInMainHand = entity.getMainHandStack() == stack;
				boolean isInOffhand = entity.getOffHandStack() == stack;
				if (entity.getMainHandStack().getItem() instanceof SpectrumFishingRodItem) {
					isInOffhand = false;
				}
				return (isInMainHand || isInOffhand) && entity instanceof PlayerEntity && ((PlayerEntityAccessor) entity).getSpectrumBobber() != null ? 1.0F : 0.0F;
			}
		});
	}
	
	private static void registerEnderSplicePredicates(Item enderSpliceItem) {
		ModelPredicateProviderRegistry.register(enderSpliceItem, Identifier.of("bound"), (stack, world, entity, i) -> {
			NbtCompound compoundTag = stack.getNbt();
			if (compoundTag != null && (compoundTag.contains("PosX") || compoundTag.contains("TargetPlayerUUID"))) {
				return 1.0F;
			} else {
				return 0.0F;
			}
		});
	}
	
	private static void registerAshenCircletPredicates(Item ashenCircletItem) {
		ModelPredicateProviderRegistry.register(ashenCircletItem, Identifier.of("cooldown"), (stack, world, entity, i) -> {
			if (entity != null && AshenCircletItem.getCooldownTicks(stack, entity.getWorld()) == 0) {
				return 0.0F;
			} else {
				return 1.0F;
			}
		});
	}
	
	private static void registerAnimatedWandPredicates(Item item) {
		ModelPredicateProviderRegistry.register(item, Identifier.of("in_use"), (stack, world, entity, i) ->
				(entity != null && entity.isUsingItem() && entity.getActiveItem() == stack) ? 1.0F : 0.0F
		);
	}
	
	private static void registerKnowledgeDropPredicates(Item item) {
		ModelPredicateProviderRegistry.register(item, Identifier.of("stored_experience_10000"), (stack, world, entity, i) -> {
			if (SpectrumItems.KNOWLEDGE_GEM instanceof ExperienceStorageItem) {
				return ExperienceStorageItem.getStoredExperience(stack) / 10000F;
			} else {
				return 0;
			}
		});
	}
	
	private static void registerInkColorPredicate(InkFlaskItem item) {
		ModelPredicateProviderRegistry.register(SpectrumItems.INK_FLASK, Identifier.of("color"), (stack, world, entity, i) -> {
			SingleInkStorage storage = SpectrumItems.INK_FLASK.getEnergyStorage(stack);
			InkColor color = storage.getStoredColor();
			
			if (color == null) {
				return 0F;
			}
			return (1F + color.getDyeColor().getId()) / 100F;
		});
	}
	
	private static void registerInkFillStateItemPredicate(InkFlaskItem item) {
		ModelPredicateProviderRegistry.register(SpectrumItems.INK_FLASK, Identifier.of("fill_state"), (stack, world, entity, i) -> {
			SingleInkStorage storage = SpectrumItems.INK_FLASK.getEnergyStorage(stack);
			long current = storage.getCurrentTotal();
			if (current == 0) {
				return 0.0F;
			} else {
				long max = storage.getMaxTotal();
				return (float) Math.max(0.01, (double) current / (double) max);
			}
		});
	}
	
	private static void registerEnchantmentCanvasPrediates(Item item) {
		ModelPredicateProviderRegistry.register(item, Identifier.of("bound"), (stack, world, entity, i) -> {
			NbtCompound nbt = stack.getNbt();
			if (nbt != null && nbt.contains("BoundItem", NbtElement.STRING_TYPE)) {
				return 1;
			}
			return 0;
		});
	}
	
}
