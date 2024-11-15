package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.item.*;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryWrapper;

public class SpectrumPickaxeItem extends PickaxeItem implements Preenchanted {
	
	public SpectrumPickaxeItem(ToolMaterial material, Settings settings) {
		super(material, settings);
	}
	
	@Override
	public ItemEnchantmentsComponent getDefaultEnchantments(RegistryWrapper.WrapperLookup registryLookup) {
		return ItemEnchantmentsComponent.DEFAULT;
	}
	
	@Override
	public ItemStack getDefaultStack() {
		return getDefaultEnchantedStack(this);
	}
	
}
