package de.dafuqs.spectrum.items.tools;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class GlintlessPickaxe extends SpectrumPickaxeItem {

    public GlintlessPickaxe(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        var defaults = getDefaultEnchantments();
		var comp = stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
		for (var entry : comp.getEnchantmentEntries()) {
			var key = entry.getKey().getKey();
			if (key.isEmpty()) continue;
			if (entry.getIntValue() > defaults.getOrDefault(key.get(), 0))
				return true;
		}
		return false;
    }
}
