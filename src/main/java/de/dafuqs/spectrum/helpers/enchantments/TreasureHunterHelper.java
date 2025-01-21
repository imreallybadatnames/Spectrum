package de.dafuqs.spectrum.helpers.enchantments;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.*;
import net.minecraft.item.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;

public class TreasureHunterHelper {
	
	public static void doTreasureHunterForPlayer(ServerPlayerEntity thisEntity, DamageSource source) {
		if (!thisEntity.isSpectator() && source.getAttacker() instanceof LivingEntity) {
			ServerWorld serverWorld = ((ServerWorld) thisEntity.getWorld());
			int damageSourceTreasureHunt = SpectrumEnchantmentHelper.getEquipmentLevel(
					serverWorld.getRegistryManager(),
					SpectrumEnchantments.TREASURE_HUNTER,
					(LivingEntity) source.getAttacker());
			if (damageSourceTreasureHunt > 0) {
				boolean shouldDropHead = serverWorld.getRandom().nextFloat() < 0.2 * damageSourceTreasureHunt;
				if (shouldDropHead) {
					ItemStack headItemStack = new ItemStack(Items.PLAYER_HEAD);
					headItemStack.set(DataComponentTypes.PROFILE, new ProfileComponent(thisEntity.getGameProfile()));
					
					ItemEntity headEntity = new ItemEntity(serverWorld, thisEntity.getX(), thisEntity.getY(), thisEntity.getZ(), headItemStack);
					serverWorld.spawnEntity(headEntity);
				}
			}
		}
	}
	
}
