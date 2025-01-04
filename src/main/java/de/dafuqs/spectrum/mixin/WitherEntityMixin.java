package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.*;
import com.llamalad7.mixinextras.sugar.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.damage.*;
import net.minecraft.entity.effect.*;
import net.minecraft.server.world.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(WitherEntity.class)
public abstract class WitherEntityMixin extends LivingEntity {

	protected WitherEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;setCovetedItem()V"),
			method = "dropEquipment(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/damage/DamageSource;Z)V", locals = LocalCapture.CAPTURE_FAILSOFT)
	private void spawnEntity(ServerWorld world, DamageSource source, boolean causedByPlayer, CallbackInfo ci, ItemEntity itemEntity) {
		Entity attackerEntity = source.getAttacker();
		if (attackerEntity instanceof LivingEntity livingAttacker) {
			int cloversFavorLevel = SpectrumEnchantmentHelper.getLevel(world.getRegistryManager(), SpectrumEnchantments.CLOVERS_FAVOR, livingAttacker.getMainHandStack());
			if (cloversFavorLevel > 0) {
				int additionalCount = (int) (cloversFavorLevel / 2.0F + world.random.nextFloat() * cloversFavorLevel);
				itemEntity.getStack().setCount(itemEntity.getStack().getCount() + additionalCount);
			}
		}
	}

	@ModifyReturnValue(method = "addStatusEffect", at = @At("TAIL"))
	private boolean spectrum$allowWitherNaps(boolean original, @Local(argsOnly = true) StatusEffectInstance effect, @Local(argsOnly = true) Entity source) {
		if (effect.getEffectType().isIn(SpectrumStatusEffectTags.SOPORIFIC)) {
			return super.addStatusEffect(effect, source);
		}
		return original;
	}
}
