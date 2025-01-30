package de.dafuqs.spectrum.registries;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.api.pastel.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.entity.variants.*;
import de.dafuqs.spectrum.explosion.*;
import de.dafuqs.spectrum.items.tools.*;
import net.fabricmc.fabric.api.event.registry.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;
import net.minecraft.util.math.random.Random;

import java.util.*;

public class SpectrumRegistries {
	
	private static final Identifier GEMSTONE_COLORS_ID = SpectrumCommon.locate("gemstone_color");
	public static final RegistryKey<Registry<GemstoneColor>> GEMSTONE_COLORS_KEY = RegistryKey.ofRegistry(GEMSTONE_COLORS_ID);
	public static final SpectrumRegistry<GemstoneColor> GEMSTONE_COLORS = register(GEMSTONE_COLORS_KEY);
	
	private static final Identifier INK_COLORS_ID = SpectrumCommon.locate("ink_color");
	public static final RegistryKey<Registry<InkColor>> INK_COLORS_KEY = RegistryKey.ofRegistry(INK_COLORS_ID);
	public static final SpectrumRegistry<InkColor> INK_COLORS = register(INK_COLORS_KEY);

	private static final Identifier WORLD_EFFECTS_ID = SpectrumCommon.locate("world_effect");
	public static final RegistryKey<Registry<FusionShrineRecipeWorldEffect>> WORLD_EFFECTS_KEY = RegistryKey.ofRegistry(WORLD_EFFECTS_ID);
	public static final SpectrumRegistry<FusionShrineRecipeWorldEffect> WORLD_EFFECTS = register(WORLD_EFFECTS_KEY);
	
	private static final Identifier LIZARD_FRILL_VARIANT_ID = SpectrumCommon.locate("lizard_frill_variant");
	public static final RegistryKey<Registry<LizardFrillVariant>> LIZARD_FRILL_VARIANT_KEY = RegistryKey.ofRegistry(LIZARD_FRILL_VARIANT_ID);
	public static final SpectrumRegistry<LizardFrillVariant> LIZARD_FRILL_VARIANT = register(LIZARD_FRILL_VARIANT_KEY);

	private static final Identifier LIZARD_HORN_VARIANT_ID = SpectrumCommon.locate("lizard_horn_variant");
	public static final RegistryKey<Registry<LizardHornVariant>> LIZARD_HORN_VARIANT_KEY = RegistryKey.ofRegistry(LIZARD_HORN_VARIANT_ID);
	public static final SpectrumRegistry<LizardHornVariant> LIZARD_HORN_VARIANT = register(LIZARD_HORN_VARIANT_KEY);
	
	private static final Identifier KINDLING_VARIANT_ID = SpectrumCommon.locate("kindling_variant");
	public static final RegistryKey<Registry<KindlingVariant>> KINDLING_VARIANT_KEY = RegistryKey.ofRegistry(KINDLING_VARIANT_ID);
	public static final SpectrumRegistry<KindlingVariant> KINDLING_VARIANT = register(KINDLING_VARIANT_KEY);

	private static final Identifier GLASS_ARROW_VARIANT_ID = SpectrumCommon.locate("glass_arrow_variant");
	public static final RegistryKey<Registry<GlassArrowVariant>> GLASS_ARROW_VARIANT_KEY = RegistryKey.ofRegistry(GLASS_ARROW_VARIANT_ID);
	public static final SpectrumRegistry<GlassArrowVariant> GLASS_ARROW_VARIANT = register(GLASS_ARROW_VARIANT_KEY);

	private static final Identifier EXPLOSION_MODIFIER_TYPES_ID = SpectrumCommon.locate("explosion_effect_family");
	public static final RegistryKey<Registry<ExplosionModifierType>> EXPLOSION_MODIFIER_TYPES_KEY = RegistryKey.ofRegistry(EXPLOSION_MODIFIER_TYPES_ID);
	public static final SpectrumRegistry<ExplosionModifierType> EXPLOSION_MODIFIER_TYPES = register(EXPLOSION_MODIFIER_TYPES_KEY);

	private static final Identifier EXPLOSION_MODIFIERS_ID = SpectrumCommon.locate("explosion_effect_modifier");
	public static final RegistryKey<Registry<ExplosionModifier>> EXPLOSION_MODIFIERS_KEY = RegistryKey.ofRegistry(EXPLOSION_MODIFIERS_ID);
	public static final SpectrumRegistry<ExplosionModifier> EXPLOSION_MODIFIERS = register(EXPLOSION_MODIFIERS_KEY);

	private static final Identifier PASTEL_UPGRADE_ID = SpectrumCommon.locate("pastel_upgrade");
	public static final RegistryKey<Registry<PastelUpgradeSignature>> PASTEL_UPGRADE_KEY = RegistryKey.ofRegistry(PASTEL_UPGRADE_ID);
	public static final SpectrumRegistry<PastelUpgradeSignature> PASTEL_UPGRADE = register(PASTEL_UPGRADE_KEY);
	
	private static <T> SpectrumRegistry<T> register(RegistryKey<Registry<T>> key) {
		return FabricRegistryBuilder.from(new SpectrumRegistry<>(key, Lifecycle.stable())).attribute(RegistryAttribute.SYNCED).buildAndRegister();
	}
	
	public static <T> T getRandomTagEntry(Registry<T> registry, TagKey<T> tag, Random random, T fallback) {
		Optional<RegistryEntryList.Named<T>> tagEntries = registry.getEntryList(tag);
		if (tagEntries.isPresent()) {
			return tagEntries.get().get(random.nextInt(tagEntries.get().size())).value();
		} else {
			return fallback;
		}
	}

}
