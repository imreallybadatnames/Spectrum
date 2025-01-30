package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.entity.entity.*;
import net.minecraft.entity.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Mixin(FishingHookPredicate.class)
public abstract class FishingHookPredicateMixin {
	
	@Shadow
	@Final
	private Optional<Boolean> inOpenWater;
	
	@Inject(method = "test(Lnet/minecraft/entity/Entity;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/Vec3d;)Z", at = @At(value = "HEAD"), cancellable = true)
	public void spectrum$test(Entity entity, ServerWorld world, Vec3d pos, CallbackInfoReturnable<Boolean> cir) {
		if (entity instanceof SpectrumFishingBobberEntity spectrumFishingBobberEntity) {
			if (this.inOpenWater.isEmpty()) {
				cir.setReturnValue(true);
			}
			cir.setReturnValue(this.inOpenWater.get() == spectrumFishingBobberEntity.isInOpenWater());
		}
	}
	
}
