package de.dafuqs.spectrum.mixin.client;

import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Environment(EnvType.CLIENT)
@Mixin(TranslucentBlock.class)
public abstract class TranslucentBlockMixin {

	@Inject(method = "isSideInvisible", at = @At("HEAD"), cancellable = true)
	public void spectrum$dontRenderVanillaPlayerOnlyGlass(BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
		if (state.isOf(Blocks.GLASS) && stateFrom.isOf(SpectrumBlocks.SEMI_PERMEABLE_GLASS)
				|| state.isOf(Blocks.TINTED_GLASS) && stateFrom.isOf(SpectrumBlocks.TINTED_SEMI_PERMEABLE_GLASS))
			callbackInfoReturnable.setReturnValue(true);
	}
	
}