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
        var original = stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT).getEnchantments();
        original.removeAll(getDefaultEnchantments().getEnchantments());
        return original.isEmpty();
    }
}
