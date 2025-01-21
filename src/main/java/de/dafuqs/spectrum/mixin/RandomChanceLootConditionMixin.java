package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.*;
import de.dafuqs.spectrum.helpers.enchantments.*;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(RandomChanceLootCondition.class)
public abstract class RandomChanceLootConditionMixin {
	
	@Shadow
	@Final
	private float chance;
	
	@ModifyReturnValue(at = @At("RETURN"), method = "test(Lnet/minecraft/loot/context/LootContext;)Z")
	public boolean spectrum$applyRareLootEnchantment(boolean original, LootContext context) {
		// if the result was to not drop a drop before reroll
		// gets more probable with each additional level of Clovers Favor
		if (!original) {
			original = context.getRandom().nextFloat() < CloversFavorHelper.rollChance(this.chance, context.get(LootContextParameters.ATTACKING_ENTITY));
		}
		return original;
	}
	
}
