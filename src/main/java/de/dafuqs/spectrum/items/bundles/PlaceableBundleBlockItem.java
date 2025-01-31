package de.dafuqs.spectrum.items.bundles;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.screen.slot.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.math.*;

import java.util.*;

public class PlaceableBundleBlockItem extends BlockItem {

    private final BundleItem bundle;
	
	public PlaceableBundleBlockItem(int maxStacks, Block block, Settings settings) {
        this(Fraction.getFraction(maxStacks, 1), maxStacks, new Item.Settings(), block, settings);
    }
	
	public PlaceableBundleBlockItem(Fraction maxOccupancy, int maxStacks, Settings bundleSettings, Block block, Settings settings) {
        super(block, settings);
		bundle = null;   //  new ExtendedBundleItem(maxOccupancy, maxStacks, bundleSettings); // TODO: instanciating a new item here makes the game crash because this ExtendedBundleItem is not registered
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        return bundle.onStackClicked(stack, slot, clickType, player);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        return bundle.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return bundle.isItemBarVisible(stack);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return bundle.getItemBarStep(stack);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return bundle.getItemBarColor(stack);
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        return bundle.getTooltipData(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        bundle.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        bundle.onItemEntityDestroyed(entity);
        super.onItemEntityDestroyed(entity);
    }

}
