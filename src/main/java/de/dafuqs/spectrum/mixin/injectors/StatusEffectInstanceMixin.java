package de.dafuqs.spectrum.mixin.injectors;

import com.llamalad7.mixinextras.sugar.*;
import com.llamalad7.mixinextras.sugar.ref.*;
import de.dafuqs.spectrum.injectors.*;
import net.minecraft.entity.effect.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(StatusEffectInstance.class)
public abstract class StatusEffectInstanceMixin implements StatusEffectInstanceInjector {
	
	@Shadow
	public int duration;
	@Shadow
	public int amplifier;
	@Unique
	private boolean incurable;
	
	// TODO: crashes with
	// java.lang.IllegalAccessError: Update to static final field net.minecraft.entity.effect.StatusEffectInstance.CODEC attempted from a different method (mixinextras$bridge$CODEC$52) than the initializer method <clinit>
	// Maybe making it mutable using an Access Widener?
	/*@WrapOperation(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/effect/StatusEffectInstance;CODEC:Lcom/mojang/serialization/Codec;"))
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
	}*/
	
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
