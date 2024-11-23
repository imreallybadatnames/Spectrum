package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.item.*;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryWrapper;

public class RazorFalchionItem extends SwordItem implements Preenchanted {
	
	public RazorFalchionItem(ToolMaterial toolMaterial, Settings settings) {
		super(toolMaterial, settings);
	}

	@Override
	public void addDefaultEnchantments(RegistryWrapper.Impl<Enchantment> impl, ItemEnchantmentsComponent.Builder builder) {
		impl.getOptional(Enchantments.LOOTING).ifPresent(e -> builder.add(e, 3));
	}

	@Override
	public ItemStack getDefaultStack() {
		return getDefaultEnchantedStack(this);
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}
}
