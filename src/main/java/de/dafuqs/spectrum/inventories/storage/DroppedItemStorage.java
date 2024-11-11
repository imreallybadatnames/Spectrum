package de.dafuqs.spectrum.inventories.storage;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleItemStorage;
import net.minecraft.component.ComponentChanges;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class DroppedItemStorage extends SingleItemStorage {

    public DroppedItemStorage(ItemStack itemStack) {
        this.variant = ItemVariant.of(itemStack);
        this.amount = itemStack.getCount();
    }

    public DroppedItemStorage(Item item, ComponentChanges componentChanges) {
        this.variant = ItemVariant.of(item, componentChanges);
        this.amount = 1;
    }

    @Override
    protected long getCapacity(ItemVariant variant) {
        return 1;
    }
}