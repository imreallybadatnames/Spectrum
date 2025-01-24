package de.dafuqs.spectrum.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.*;
import com.llamalad7.mixinextras.sugar.ref.*;
import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.effect.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

public interface StatusEffectInstanceInjector {
	
	default boolean spectrum$isIncurable() { return false; }
	default void spectrum$setIncurable(boolean incurable) { }
	default void spectrum$setDuration(int newDuration) { }
	default void spectrum$setAmplifier(int newAmplifier) { }
	
	//TODO this needs a better name. What even is this.
	static boolean isIncurable(StatusEffectInstance instance) {
		var type = instance.getEffectType();
		if (type == SpectrumStatusEffects.ETERNAL_SLUMBER || type == SpectrumStatusEffects.FATAL_SLUMBER)
			return false;
		
		return instance.spectrum$isIncurable();
	}
	
	@Mixin(StatusEffectInstance.class)
	abstract class _Mixin implements StatusEffectInstanceInjector {
		
		@Shadow public int duration;
		@Shadow public int amplifier;
		@Unique private boolean incurable;

		@WrapOperation(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/effect/StatusEffectInstance;CODEC:Lcom/mojang/serialization/Codec;"))
		private static void wrapCodec(Codec<StatusEffectInstance> codec, Operation<Void> original) {
			original.call(codec.mapResult(new Codec.ResultFunction<>() {
				@Override
				public <T> DataResult<Pair<StatusEffectInstance, T>> apply(DynamicOps<T> ops, T input, DataResult<Pair<StatusEffectInstance, T>> result) {
					return result.map(pair -> {
						ops.get(input, "incurable").flatMap(ops::getBooleanValue).ifSuccess(v -> pair.getFirst().spectrum$setIncurable(v));
						return pair;
					});
				}
				
				@Override
				public <T> DataResult<T> coApply(DynamicOps<T> ops, StatusEffectInstance inst, DataResult<T> result) {
					return result.map(output -> ops.set(output, "incurable", ops.createBoolean(inst.spectrum$isIncurable())));
				}
			}));
		}
		
		@Inject(method = "upgrade", at = @At("RETURN"))
		private void readIncurable(StatusEffectInstance that, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) LocalBooleanRef changed) {
			if (incurable != that.spectrum$isIncurable()) {
				spectrum$setIncurable(that.spectrum$isIncurable());
				changed.set(true);
			}
		}

		@Override
		public boolean spectrum$isIncurable() {
			return incurable;
		}

		@Override
		public void spectrum$setIncurable(boolean incurable) {
			this.incurable = incurable;
		}

		@Override
		public void spectrum$setDuration(int newDuration) {
			this.duration = newDuration;
		}

		@Override
		public void spectrum$setAmplifier(int newAmplifier) {
			this.amplifier = newAmplifier;
		}
		
	}
	
}
