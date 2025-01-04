package de.dafuqs.spectrum.loot.conditions;

import com.google.common.collect.*;
import com.google.gson.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.loot.*;
import de.dafuqs.spectrum.progression.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.*;
import net.minecraft.registry.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class RandomChanceWithTreasureHunterLootCondition implements LootCondition {
	
	private final float chance;
	private final ItemStack advancementTriggerItemStack;
	
	private RandomChanceWithTreasureHunterLootCondition(float chance, Item item) {
		this.chance = chance;
		this.advancementTriggerItemStack = new ItemStack(item);
	}
	
	public static Builder builder(float chance, Item advancementTriggerItem) {
		return () -> new RandomChanceWithTreasureHunterLootCondition(chance, advancementTriggerItem);
	}
	
	@Override
	public LootConditionType getType() {
		return SpectrumLootConditionTypes.RANDOM_CHANCE_WITH_TREASURE_HUNTER;
	}
	
	@Override
	public Set<LootContextParameter<?>> getRequiredParameters() {
		return ImmutableSet.of(LootContextParameters.KILLER_ENTITY);
	}
	
	@Override
	public boolean test(LootContext lootContext) {
		DamageSource damageSource = lootContext.get(LootContextParameters.DAMAGE_SOURCE);
		if (damageSource != null && damageSource.isIn(SpectrumDamageTypeTags.ALWAYS_DROPS_MOB_HEAD)) {
			return true;
		}
		
		Entity entity = lootContext.get(LootContextParameters.KILLER_ENTITY);
		int treasureHunterLevel = 0;
		if (entity instanceof PlayerEntity playerEntity) {
			treasureHunterLevel = SpectrumEnchantmentHelper.getEquipmentLevel(
					playerEntity.getWorld().getRegistryManager(),
					SpectrumEnchantments.TREASURE_HUNTER,
					(LivingEntity) entity);
		}
		
		if (treasureHunterLevel == 0) {
			// No Treasure Hunter => no drop
			return false;
		} else {
			boolean success = lootContext.getRandom().nextFloat() < this.chance * treasureHunterLevel;
			if (success) {
				Entity killerEntity = lootContext.get(LootContextParameters.KILLER_ENTITY);
				if (killerEntity instanceof ServerPlayerEntity serverPlayerEntity) {
					SpectrumAdvancementCriteria.TREASURE_HUNTER_DROP.trigger(serverPlayerEntity, advancementTriggerItemStack);
				}
			}
			return success;
		}
	}
	
	public static class Serializer implements JsonSerializer<RandomChanceWithTreasureHunterLootCondition> {
		
		public Serializer() {
		}
		
		@Override
		public void toJson(JsonObject jsonObject, RandomChanceWithTreasureHunterLootCondition randomChanceWithLootingLootCondition, JsonSerializationContext jsonSerializationContext) {
			jsonObject.addProperty("chance", randomChanceWithLootingLootCondition.chance);
			jsonObject.addProperty("advancement_trigger_itemstack", Registries.ITEM.getId(randomChanceWithLootingLootCondition.advancementTriggerItemStack.getItem()).toString());
		}
		
		@Override
		public RandomChanceWithTreasureHunterLootCondition fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			return new RandomChanceWithTreasureHunterLootCondition(
					JsonHelper.getFloat(jsonObject, "chance"),
					Registries.ITEM.get(Identifier.of(JsonHelper.getString(jsonObject, "advancement_trigger_itemstack")))
			);
		}
	}
}
