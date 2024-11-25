package de.dafuqs.spectrum.api.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.entity.*;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public interface LoomPatternProvider {
	
	Text PATTERN_AVAILABLE_TOOLTIP_TEXT = Text.translatable("item.spectrum.tooltip.loom_pattern_available").formatted(Formatting.GRAY);

	RegistryKey<BannerPattern> getPattern();

	static ImmutableList<RegistryEntry<BannerPattern>> getPatterns(RegistryEntryLookup<BannerPattern> lookup, LoomPatternProvider provider) {
		return lookup.getOptional(provider.getPattern()).map(pattern -> ImmutableList.of((RegistryEntry<BannerPattern>)pattern)).orElse(ImmutableList.of());
	}

	default void addBannerPatternProviderTooltip(List<Text> tooltips) {
		tooltips.add(PATTERN_AVAILABLE_TOOLTIP_TEXT);
	}

}
