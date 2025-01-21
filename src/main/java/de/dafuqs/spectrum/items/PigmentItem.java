package de.dafuqs.spectrum.items;

import com.google.common.collect.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.items.conditional.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.entity.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class PigmentItem extends CloakedItem implements LoomPatternProvider {
	
	private static final Map<DyeColor, PigmentItem> PIGMENTS = Maps.newEnumMap(DyeColor.class);
	protected final DyeColor color;
	
	public PigmentItem(Settings settings, DyeColor color) {
		super(settings, SpectrumCommon.locate("craft_colored_sapling"), DyeItem.byColor(color));
		this.color = color;
		PIGMENTS.put(color, this);
	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
	public static PigmentItem byColor(DyeColor color) {
		return PIGMENTS.get(color);
	}
	
	@Override
	public RegistryKey<BannerPattern> getPattern() {
		return SpectrumBannerPatterns.PIGMENT;
	}
	
	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		addBannerPatternProviderTooltip(tooltip);
	}
	
}
