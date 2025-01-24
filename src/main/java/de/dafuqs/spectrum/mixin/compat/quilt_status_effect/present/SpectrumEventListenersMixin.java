package de.dafuqs.spectrum.mixin.compat.quilt_status_effect.present;

import de.dafuqs.spectrum.mixin.injectors.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.server.world.*;
import org.spongepowered.asm.mixin.*;


@Mixin(value = SpectrumEventListeners.class, remap = false)
public class SpectrumEventListenersMixin {
	// used by ASM; See QSLCompatASM. Do not touch without first consulting QSLCompatASM.
	// MUST: have 3 arguments, arguments must be of (LivingEntity entity, StatusEffectInstance effect, Object reason),
	// return FAPI TriState -- MUST NOT USE TriState anywhere EXCEPT as return value.
	@Unique
	private static TriState _shouldRemove(LivingEntity entity, StatusEffectInstance effect, Object reason) {
		if (StatusEffectInstanceInjector.isIncurable(effect) && !affectedByImmunity(entity, effect.getAmplifier())) {
			if (effect.getDuration() > 1200) {
				effect.spectrum$setDuration(effect.getDuration() - 1200);
				if (!entity.getWorld().isClient()) {
					((ServerWorld) entity.getWorld()).getChunkManager().sendToNearbyPlayers(entity, new EntityStatusEffectS2CPacket(entity.getId(), effect));
				}
			}
			return TriState.FALSE;
		}
		return TriState.DEFAULT;
	}

	@Unique
	private static boolean affectedByImmunity(LivingEntity instance, int amplifier) {
		var immunity = instance.getStatusEffect(SpectrumStatusEffects.IMMUNITY);
		var cost = 1200 + 600 * amplifier;

		if (immunity != null && immunity.getDuration() >= cost) {
			immunity.spectrum$setDuration(Math.max(5, immunity.getDuration() - cost));
			if (!instance.getWorld().isClient()) {
				((ServerWorld) instance.getWorld()).getChunkManager().sendToNearbyPlayers(instance, new EntityStatusEffectS2CPacket(instance.getId(), immunity));
			}
			return true;
		}
		return false;
	}
}
