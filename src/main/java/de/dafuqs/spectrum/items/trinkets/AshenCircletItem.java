package de.dafuqs.spectrum.items.trinkets;

import com.google.common.collect.*;
import de.dafuqs.additionalentityattributes.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.cca.*;
import dev.emi.trinkets.api.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.effect.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.registry.entry.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class AshenCircletItem extends SpectrumTrinketItem {
	
	public static final int FIRE_RESISTANCE_EFFECT_DURATION = 600;
	public static final long COOLDOWN_TICKS = 3000;
	
	public static final double LAVA_MOVEMENT_SPEED_MOD = 0.4; // vanilla uses 0.5 to slow the player down to half its speed
	public static final double LAVA_VIEW_DISTANCE_MOD = 24.0;
	
	public AshenCircletItem(Settings settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/ashen_circlet"));
	}
	
	public static long getCooldownTicks(@NotNull ItemStack ashenCircletStack, @NotNull World world) {
		var nbt = ashenCircletStack.get(DataComponentTypes.CUSTOM_DATA);
		if (nbt == null || !nbt.contains("last_cooldown_start")) {
			return 0;
		} else {
			long lastCooldownStart = nbt.copyNbt().getLong("last_cooldown_start");
			return Math.max(0, lastCooldownStart - world.getTime() + COOLDOWN_TICKS);
		}
	}
	
	private static void setCooldown(@NotNull ItemStack ashenCircletStack, @NotNull World world) {
		ashenCircletStack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT,
				comp -> comp.apply(nbt -> nbt.putLong("last_cooldown_start", world.getTime())));
	}
	
	public static void grantFireResistance(@NotNull ItemStack ashenCircletStack, @NotNull LivingEntity livingEntity) {
		if (!livingEntity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
			livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, FIRE_RESISTANCE_EFFECT_DURATION, 0, true, true));
			livingEntity.getWorld().playSound(null, livingEntity.getBlockPos(), SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
			setCooldown(ashenCircletStack, livingEntity.getWorld());
		}
	}
	
	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		super.tick(stack, slot, entity);
		if (entity.isOnFire()) {
			entity.setFireTicks(0);
		}
		if (getCooldownTicks(stack, entity.getWorld()) == 0 && OnPrimordialFireComponent.putOut(entity)) {
			entity.getWorld().playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
			setCooldown(stack, entity.getWorld());
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.ashen_circlet.tooltip").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.ashen_circlet.tooltip2").formatted(Formatting.GRAY));

		if (world != null) {
			long cooldownTicks = getCooldownTicks(stack, world);
			if (cooldownTicks == 0) {
				tooltip.add(Text.translatable("item.spectrum.ashen_circlet.tooltip.cooldown_full"));
			} else {
				tooltip.add(Text.translatable("item.spectrum.ashen_circlet.tooltip.cooldown_seconds", cooldownTicks / 20));
			}
		}
	}
	
	@Override
	public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, Identifier slotIdentifier) {
		Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, slotIdentifier);
		
		modifiers.put(AdditionalEntityAttributes.LAVA_SPEED, new EntityAttributeModifier(uuid, "spectrum:ashen_circlet", LAVA_MOVEMENT_SPEED_MOD, EntityAttributeModifier.Operation.ADD_VALUE));
		modifiers.put(AdditionalEntityAttributes.LAVA_VISIBILITY, new EntityAttributeModifier(uuid, "spectrum:ashen_circlet", LAVA_VIEW_DISTANCE_MOD, EntityAttributeModifier.Operation.ADD_VALUE));
		
		return modifiers;
	}
	
}
