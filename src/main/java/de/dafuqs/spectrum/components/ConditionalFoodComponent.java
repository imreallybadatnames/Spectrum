package de.dafuqs.spectrum.components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.progression.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.predicate.item.*;
import net.minecraft.server.network.*;
import net.minecraft.world.*;

public record ConditionalFoodComponent(ItemPredicate itemPredicate, boolean consumeAndApplyRequiredStack, FoodComponent bonusFoodComponent) {
	
	public static final Codec<ConditionalFoodComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ItemPredicate.CODEC.fieldOf("item_predicate").forGetter(ConditionalFoodComponent::itemPredicate),
			Codec.BOOL.fieldOf("consume_and_apply_required_stack").forGetter(ConditionalFoodComponent::consumeAndApplyRequiredStack),
			FoodComponent.CODEC.fieldOf("bonus_food_component").forGetter(ConditionalFoodComponent::bonusFoodComponent)
	).apply(instance, ConditionalFoodComponent::new));
	
	public static final PacketCodec<RegistryByteBuf, ConditionalFoodComponent> PACKET_CODEC = PacketCodec.tuple(
			ItemPredicate.PACKET_CODEC, ConditionalFoodComponent::itemPredicate, // TODO: that feels like it cannot work somehow. Hmmmmm
			PacketCodecs.BOOL, ConditionalFoodComponent::consumeAndApplyRequiredStack,
			FoodComponent.PACKET_CODEC, ConditionalFoodComponent::bonusFoodComponent,
			ConditionalFoodComponent::new
	);
	
	public void tryEatFood(World world, LivingEntity livingEntity, ItemStack eatenStack) {
		if (!(livingEntity instanceof PlayerEntity player)) {
			return;
		}
		
		// does the entity have a matching stack in their inv?
		int requiredSlotStack = -1;
		for (int i = 0; i < player.getInventory().size(); i++) {
			if (this.itemPredicate.test(player.getInventory().getStack(i))) {
				requiredSlotStack = i;
				break;
			}
		}
		if (requiredSlotStack == -1) {
			return;
		}
		ItemStack foundRequiredStack = player.getInventory().getStack(requiredSlotStack);
		
		// should the required stack be consumed, too?
		if (consumeAndApplyRequiredStack) {
			FoodComponent component = foundRequiredStack.get(DataComponentTypes.FOOD);
			if (component != null) {
				player.eatFood(world, foundRequiredStack, component);
			}
		}
		
		if (player instanceof ServerPlayerEntity serverPlayer) {
			SpectrumAdvancementCriteria.CONDITIONAL_FOOD_EATEN.trigger(serverPlayer, eatenStack, foundRequiredStack);
		}
	}
	
}
