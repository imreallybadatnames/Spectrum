package de.dafuqs.spectrum.items.trinkets;

import com.google.common.collect.*;
import de.dafuqs.spectrum.*;
import dev.emi.trinkets.api.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.registry.entry.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class AttackRingItem extends SpectrumTrinketItem {
	
	public static final Identifier ATTACK_RING_DAMAGE_ID = SpectrumCommon.locate("jeopardant");
	public static final String ATTACK_RING_DAMAGE_NAME = "spectrum:jeopardant";
	
	public AttackRingItem(Settings settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/jeopardant"));
	}
	
	public static double getAttackModifierForEntity(LivingEntity entity) {
		if (entity == null) {
			return 0;
		} else {
			double mod = entity.getMaxHealth() / (entity.getHealth() * entity.getHealth() + 1); // starting with 1 % damage at 14 health up to 300 % damage at 1/20 health
			return Math.max(0, 1 + Math.log10(mod));
		}
	}
	
	@Override
	public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		super.onUnequip(stack, slot, entity);
		if (entity.getAttributes().hasModifierForAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE, AttackRingItem.ATTACK_RING_DAMAGE_ID)) {
			Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> map = Multimaps.newMultimap(Maps.newLinkedHashMap(), ArrayList::new);
			map.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(AttackRingItem.ATTACK_RING_DAMAGE_ID, AttackRingItem.getAttackModifierForEntity(entity), EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
			entity.getAttributes().removeModifiers(map);
		}
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		MinecraftClient client = MinecraftClient.getInstance();
		long mod = Math.round(getAttackModifierForEntity(client.player) * 100);
		if (mod == 0) {
			tooltip.add(Text.translatable("item.spectrum.jeopardant.tooltip.damage_zero"));
		} else {
			tooltip.add(Text.translatable("item.spectrum.jeopardant.tooltip.damage", mod));
		}
	}
	
}
