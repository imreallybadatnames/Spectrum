package de.dafuqs.spectrum.items.tooltip;

import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipData;

public record BottomlessBundleTooltipData(ItemStack itemStack, long amount) implements TooltipData {

}
