package de.dafuqs.spectrum.items.tools;

import de.dafuqs.additionalentityattributes.*;
import de.dafuqs.spectrum.api.render.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public abstract class ParryingSwordItem extends SwordItem implements ExtendedItemBarProvider {

	public static final int DEFAULT_MAX_BLOCK_TIME = 40;
	public static final int DEFAULT_PERFECT_PARRY_WINDOW = 5;

	public ParryingSwordItem(ToolMaterial material, int attackDamage, float attackSpeed, float crit, float reach, Settings settings) {
		super(material, settings.attributeModifiers(AttributeModifiersComponent.builder()
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, material.getAttackDamage() + attackDamage, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.add(AdditionalEntityAttributes.CRITICAL_BONUS_DAMAGE, new EntityAttributeModifier(SpectrumEntityAttributes.CRIT_MODIFIER_ID, crit, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(SpectrumEntityAttributes.REACH_MODIFIER_ID, reach, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.build()));
	}
	
	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		super.onStoppedUsing(stack, world, user, remainingUseTicks);
		var usedTime = getMaxShieldingTime(user, stack) - remainingUseTicks;

		if (!(user instanceof PlayerEntity player))
			return;

		cooldownAndDamage(stack, player, usedTime);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		onStoppedUsing(stack, world, user, 0);
		return stack;
	}

	private void cooldownAndDamage(ItemStack stack, PlayerEntity player, int usedTime) {
		if (usedTime > 1) {
			player.getItemCooldownManager().set(this, Math.max(usedTime, 10));
		}
		stack.damage(1, player, LivingEntity.getSlotForHand(player.getActiveHand()));
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}

	public abstract float getBlockingMultiplier(DamageSource source, ItemStack stack, LivingEntity entity, int usedTime);

	public boolean canPerfectParry(ItemStack stack, LivingEntity entity, int usedTime) {
		return usedTime <= getPerfectParryWindow(entity, stack);
	}

	public boolean canBluffParry(ItemStack stack, LivingEntity entity, int usedTime) {
		return usedTime <= getPerfectParryWindow(entity, stack) * 2;
	}

	public boolean canDeflect(DamageSource source, boolean perfect) {
		if (source.isIn(DamageTypeTags.NO_IMPACT) || source.isIn(DamageTypeTags.BYPASSES_ARMOR))
			return false;

		if (source.isIn(DamageTypeTags.BYPASSES_SHIELD)) {
			return perfect;
		}

		return true;
	}

	@Override
	public int getMaxUseTime(ItemStack stack, LivingEntity user) {
		return DEFAULT_MAX_BLOCK_TIME;
	}

	public int getMaxShieldingTime(LivingEntity user, ItemStack stack) {
		return getMaxUseTime(stack, user);
	}

	@SuppressWarnings("unused")
	public int getPerfectParryWindow(LivingEntity user, ItemStack stack) {
		return DEFAULT_PERFECT_PARRY_WINDOW;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	public int barCount(ItemStack stack) {
		return 1;
	}

	protected abstract int getBarColor();

	@Override
	public BarSignature getSignature(@Nullable PlayerEntity player, @NotNull ItemStack stack, int index) {
		if (player == null || !player.isUsingItem())
			return ExtendedItemBarProvider.PASS;

		var activeStack = player.getStackInHand(player.getActiveHand());
		if (activeStack != stack)
			return ExtendedItemBarProvider.PASS;


		var progress = Math.round(MathHelper.clampedLerp(13, 0, ((float) player.getItemUseTime() / getMaxShieldingTime(player, stack))));
		return new BarSignature(2, 13, 13, progress, 1, getBarColor(), 2, ExtendedItemBarProvider.DEFAULT_BACKGROUND_COLOR);
	}
}
