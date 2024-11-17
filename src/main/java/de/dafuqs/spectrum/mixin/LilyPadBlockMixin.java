package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.fluid.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(LilyPadBlock.class)
public class LilyPadBlockMixin {
    @ModifyReturnValue(method = "canPlantOnTop(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Z", at = @At("RETURN"))
    public boolean spectrum$extendLilyPlaceables(boolean original, BlockState floor, BlockView world, BlockPos pos) {
        if (original)
            return true;
        FluidState fluidState = world.getFluidState(pos);
        FluidState fluidState2 = world.getFluidState(pos.up());
		return (fluidState.getFluid() == SpectrumFluids.GOO || fluidState.getFluid() == SpectrumFluids.LIQUID_CRYSTAL) && fluidState2.getFluid() == Fluids.EMPTY;
    }
}
