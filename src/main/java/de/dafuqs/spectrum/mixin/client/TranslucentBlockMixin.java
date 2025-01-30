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
public abstract class TranslucentBlockMixin extends Block {
	
	public TranslucentBlockMixin(Settings settings) {
		super(settings);
	}

	@Inject(method = "isSideInvisible", at = @At("HEAD"), cancellable = true)
	public void dontRenderVanillaPlayerOnlyGlass(BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
		if (this == Blocks.GLASS && stateFrom.getBlock() == SpectrumBlocks.SEMI_PERMEABLE_GLASS || this == Blocks.TINTED_GLASS && stateFrom.getBlock() == SpectrumBlocks.TINTED_SEMI_PERMEABLE_GLASS) {
			callbackInfoReturnable.setReturnValue(true);
		}
	}
	
}