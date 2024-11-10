package de.dafuqs.spectrum.items.tooltip;

import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipData;

import java.util.*;

public record PresentTooltipData(List<ItemStack> itemStacks) implements TooltipData {

}
