package de.dafuqs.spectrum.items.trinkets;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.item.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class RingOfDenserStepsItem extends GravityRingItem implements GravitableItem {

	public RingOfDenserStepsItem(Settings settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/ring_of_denser_steps"), InkColors.BROWN);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		tooltip.add(Text.translatable("item.spectrum.ring_of_denser_steps.tooltip").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.ring_of_denser_steps.tooltip2").formatted(Formatting.GRAY));
		super.appendTooltip(stack, context, tooltip, type);
	}
	
	public static Identifier ATTRIBUTE_ID = SpectrumCommon.locate("ring_of_denser_steps_gravity");
	
	@Override
	protected Identifier getAttributeID() {
		return ATTRIBUTE_ID;
	}
	
	@Override
	protected boolean negativeGravity() {
		return false;
	}

}
