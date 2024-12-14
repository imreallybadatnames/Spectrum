package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.items.bundles.ExtendedBundleItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BundleItem.class)
public class BundleItemMixin {

    @ModifyVariable(method = "onStackClicked(Lnet/minecraft/item/ItemStack;Lnet/minecraft/screen/slot/Slot;Lnet/minecraft/util/ClickType;Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At(value = "STORE"))
    private BundleContentsComponent.Builder spectrum$onStackClicked$replaceBuilder(BundleContentsComponent.Builder builder, ItemStack stack) {
        return getBuilder(stack);
    }

    @ModifyVariable(method = "onClicked(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lnet/minecraft/screen/slot/Slot;Lnet/minecraft/util/ClickType;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/inventory/StackReference;)Z", at = @At(value = "STORE"))
    private BundleContentsComponent.Builder spectrum$onClicked$replaceBuilder(BundleContentsComponent.Builder builder, ItemStack stack) {
        return getBuilder(stack);
    }

    @Unique
    private static BundleContentsComponent.Builder getBuilder(ItemStack stack) {
        var component = stack.getOrDefault(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT);
        if (stack.getItem() instanceof ExtendedBundleItem bundle)
            return new ExtendedBundleItem.ComponentBuilder(component, bundle.getMaxOccupancy(), bundle.getMaxStacks());
        return new BundleContentsComponent.Builder(component);
    }
}
