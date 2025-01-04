package de.dafuqs.spectrum.items.trinkets;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.item.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class RingOfAerialGraceItem extends GravityRingItem implements GravitableItem {

	public RingOfAerialGraceItem(Settings settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/ring_of_aerial_grace"), InkColors.WHITE);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.ring_of_aerial_grace.tooltip").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.ring_of_aerial_grace.tooltip2").formatted(Formatting.GRAY));
	}
	
	public static Identifier ATTRIBUTE_ID = SpectrumCommon.locate("ring_of_aerial_grace_gravity");
	
	@Override
	protected Identifier getAttributeID() {
		return ATTRIBUTE_ID;
	}
	
	@Override
	protected boolean negativeGravity() {
		return true;
	}
	
}
