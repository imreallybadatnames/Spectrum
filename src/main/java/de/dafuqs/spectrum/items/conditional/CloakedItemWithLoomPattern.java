package de.dafuqs.spectrum.items.conditional;

import de.dafuqs.spectrum.api.item.*;
import net.minecraft.block.entity.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class CloakedItemWithLoomPattern extends CloakedItem implements LoomPatternProvider {
	
	private final RegistryKey<BannerPattern> patternItemTag;
	
	public CloakedItemWithLoomPattern(Settings settings, Identifier cloakAdvancementIdentifier, Item cloakItem, RegistryKey<BannerPattern> patternItemTag) {
		super(settings, cloakAdvancementIdentifier, cloakItem);
		this.patternItemTag = patternItemTag;
	}
	
	@Override
	public RegistryKey<BannerPattern> getPattern() {
		return patternItemTag;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		addBannerPatternProviderTooltip(tooltip);
	}
	
}
