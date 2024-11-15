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

public class TreasureHunterDropCriterion extends AbstractCriterion<TreasureHunterDropCriterion.Conditions> {

	public static final Identifier ID = SpectrumCommon.locate("treasure_hunter_drop");

	public void trigger(ServerPlayerEntity player, ItemStack droppedStack) {
		this.trigger(player, (conditions) -> conditions.matches(droppedStack));
	}

	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}

	public record Conditions(
		Optional<LootContextPredicate> player,
		Optional<ItemPredicate> droppedItem
	) implements AbstractCriterion.Conditions {

		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
			ItemPredicate.CODEC.optionalFieldOf("dropped_item").forGetter(Conditions::droppedItem)
		).apply(instance, Conditions::new));

		public boolean matches(ItemStack droppedStack) {
			return this.droppedItem.isPresent() && this.droppedItem.get().test(droppedStack);
		}
	}

}
