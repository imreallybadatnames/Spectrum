package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.server.world.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntity {
	
	protected TridentEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@WrapOperation(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
	private boolean makeBidentDamageReasonable(Entity instance, DamageSource source, float amount, Operation<Boolean> original) {
		if (((Object) this) instanceof BidentBaseEntity bidentEntity) {
			var stack = bidentEntity.getTrackedStack();
			float damage = (float) getDamage(stack);
			
			DamageSource damageSource = SpectrumDamageTypes.impaling(getWorld(), bidentEntity, getOwner());
			if (this.getWorld() instanceof ServerWorld serverWorld) {
				damage += EnchantmentHelper.getDamage(serverWorld, this.getWeaponStack(), instance, damageSource, damage);
			}
			
			return instance.damage(damageSource, damage * 2);
		}
		return original.call(instance, source, amount);
	}
	
	@Unique
	private double getDamage(ItemStack stack) {
		// TODO: is that correct?
		AttributeModifiersComponent attributeModifiersComponent = stack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
		return attributeModifiersComponent.applyOperations(1.0D, EquipmentSlot.MAINHAND);
	}
	
}
