package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BundleContentsComponent.class)
public interface BundleContentsComponentAccessor {

    @Invoker
    static Fraction invokeGetOccupancy(ItemStack stack) {
        throw new AssertionError();
    }

}
