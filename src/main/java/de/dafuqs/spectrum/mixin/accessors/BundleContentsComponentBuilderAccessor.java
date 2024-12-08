package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(BundleContentsComponent.Builder.class)
public interface BundleContentsComponentBuilderAccessor {

    @Accessor
    List<ItemStack> getStacks();

    @Accessor
    void setOccupancy(Fraction occupancy);

}
