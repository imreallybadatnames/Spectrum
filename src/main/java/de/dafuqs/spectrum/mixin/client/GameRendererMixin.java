package de.dafuqs.spectrum.mixin.client;

import com.llamalad7.mixinextras.injector.*;
import de.dafuqs.spectrum.deeper_down.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.client.render.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = GameRenderer.class, priority = 9999)
public abstract class GameRendererMixin {

    @ModifyReturnValue(method = "getNightVisionStrength(Lnet/minecraft/entity/LivingEntity;F)F", at = @At("RETURN"))
    private static float spectrum$nerfNightVisionInDimension(float original, LivingEntity entity, float tickDelta) {
		if (SpectrumDimensions.DIMENSION_KEY == entity.getWorld().getRegistryKey()) {
			original /= 6F;
		}

        if (DarknessEffects.darkenTicks > 0) {
            original *= 1F - DarknessEffects.getDarknessInterpolation();
        }

        return original;
    }

}
