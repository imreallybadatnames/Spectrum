package de.dafuqs.spectrum.items.bundles;

import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import org.apache.commons.lang3.math.Fraction;

import java.util.List;
import java.util.Optional;

public class PlaceableBundleItem extends BlockItem {

    private final BundleItem bundle;

    public PlaceableBundleItem(BundleItem bundle, Block block, Settings settings) {
        super(block, settings);
        this.bundle = bundle;
    }

    public PlaceableBundleItem(int maxStacks, Block block, Settings settings) {
        this(Fraction.getFraction(maxStacks, 1), maxStacks, new Item.Settings(), block, settings);
    }

    public PlaceableBundleItem(Fraction maxOccupancy, Block block, Settings settings) {
        this(maxOccupancy, Integer.MAX_VALUE, new Item.Settings(), block, settings);
    }

    public PlaceableBundleItem(Fraction maxOccupancy, int maxStacks, Settings bundleSettings, Block block, Settings settings) {
        super(block, settings);
        bundle = new ExtendedBundleItem(maxOccupancy, maxStacks, bundleSettings);
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
