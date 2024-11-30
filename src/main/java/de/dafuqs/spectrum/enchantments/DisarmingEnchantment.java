package de.dafuqs.spectrum.enchantments;

import de.dafuqs.spectrum.mixin.accessors.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.item.*;
import net.minecraft.sound.*;

import java.util.*;

public class DisarmingEnchantment {
	
	public static void disarmEntity(LivingEntity livingEntity) {
		// since endermen save their carried block as blockState, not in hand
		// we have to use custom logic for them
		if (livingEntity instanceof EndermanEntity endermanEntity) {
			BlockState carriedBlockState = endermanEntity.getCarriedBlock();
			if (carriedBlockState != null) {
				Item item = carriedBlockState.getBlock().asItem();
				if (item != null) {
					endermanEntity.dropStack(item.getDefaultStack());
					endermanEntity.setCarriedBlock(null);
				}
			}
			return;
		}
		
		// choose a random slot and drop its content
		List<EquipmentSlot> slots = new ArrayList<>(List.of(EquipmentSlot.values()));
		Collections.shuffle(slots);
		for (EquipmentSlot slot : slots) {
			ItemStack slotStack = livingEntity.getEquippedStack(slot);
			if (slotStack.isEmpty()) {
				continue;
			}
			
			// set to cannot drop? Skip that slot
			if (livingEntity instanceof MobEntity mobEntity && ((MobEntityAccessor) mobEntity).invokeGetDropChance(slot) <= 0) {
				continue;
			}
			
			livingEntity.dropStack(slotStack);
			livingEntity.equipStack(slot, ItemStack.EMPTY);
			livingEntity.getWorld().playSound(null, livingEntity.getBlockPos(), SoundEvents.ITEM_BUNDLE_DROP_CONTENTS, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			break;
		}
	}

}
