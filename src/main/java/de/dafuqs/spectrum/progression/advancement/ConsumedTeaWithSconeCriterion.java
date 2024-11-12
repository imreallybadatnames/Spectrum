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

public class ConsumedTeaWithSconeCriterion extends AbstractCriterion<ConsumedTeaWithSconeCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("consumed_tea_with_scone");
	
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
		
		public static final Codec<ConsumedTeaWithSconeCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(ConsumedTeaWithSconeCriterion.Conditions::player),
			ItemPredicate.CODEC.fieldOf("tea_items").forGetter(ConsumedTeaWithSconeCriterion.Conditions::teaItem),
			ItemPredicate.CODEC.fieldOf("scone_items").forGetter(ConsumedTeaWithSconeCriterion.Conditions::sconeItem)
		).apply(instance, ConsumedTeaWithSconeCriterion.Conditions::new));
		
		public boolean matches(ItemStack teaStack, ItemStack sconeStack) {
			return teaItem.test(teaStack) && sconeItem.test(sconeStack);
		}
	}
	
}