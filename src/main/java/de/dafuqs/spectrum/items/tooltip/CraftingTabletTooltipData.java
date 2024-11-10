package de.dafuqs.spectrum.items.tooltip;

import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.recipe.*;
import net.minecraft.text.*;
import net.minecraft.world.World;

public class CraftingTabletTooltipData implements TooltipData {
	
	private final ItemStack itemStack;
	private final Text description;
	
	public CraftingTabletTooltipData(Recipe<?> recipe, World world) {
		this.itemStack = recipe.getResult(world.getRegistryManager());
		this.description = Text.translatable("item.spectrum.crafting_tablet.tooltip.recipe", this.itemStack.getCount(), this.itemStack.getName());
	}
	
	public ItemStack getItemStack() {
		return this.itemStack;
	}
	
	public Text getDescription() {
		return this.description;
	}
	
}
