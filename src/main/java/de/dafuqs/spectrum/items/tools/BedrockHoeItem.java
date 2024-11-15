package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.item.*;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

public class BedrockHoeItem extends HoeItem implements Preenchanted {
	
	public BedrockHoeItem(ToolMaterial material, Settings settings) {
		super(material, settings);
	}

	@Override
	public ItemEnchantmentsComponent getDefaultEnchantments(RegistryWrapper.WrapperLookup registryLookup) {
		var builder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);
		registryLookup
				.getOptionalWrapper(RegistryKeys.ENCHANTMENT)
				.flatMap(registryEntryLookup -> registryEntryLookup.getOptional(Enchantments.FORTUNE))
				.ifPresent(e -> builder.add(e, 4));
		return builder.build();
	}
	
	@Override
	public ItemStack getDefaultStack() {
		return getDefaultEnchantedStack(this);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}

}
