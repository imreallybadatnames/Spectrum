package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.predicate.item.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class ConditionalFoodEatenCriterion extends AbstractCriterion<ConditionalFoodEatenCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("consumed_conditional_food");
	
	public void trigger(ServerPlayerEntity player, ItemStack teaStack, ItemStack sconeStack) {
		this.trigger(player, (conditions) -> conditions.matches(teaStack, sconeStack));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
		Optional<LootContextPredicate> player,
		ItemPredicate teaItem,
		ItemPredicate sconeItem
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<ConditionalFoodEatenCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(ConditionalFoodEatenCriterion.Conditions::player),
				ItemPredicate.CODEC.fieldOf("eaten_item").forGetter(ConditionalFoodEatenCriterion.Conditions::teaItem),
				ItemPredicate.CODEC.fieldOf("conditional_item").forGetter(ConditionalFoodEatenCriterion.Conditions::sconeItem)
		).apply(instance, ConditionalFoodEatenCriterion.Conditions::new));
		
		public boolean matches(ItemStack teaStack, ItemStack sconeStack) {
			return teaItem.test(teaStack) && sconeItem.test(sconeStack);
		}
	}
	
}