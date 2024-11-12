package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.blocks.upgrade.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.*;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.predicate.item.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class CinderhearthSmeltingCriterion extends AbstractCriterion<CinderhearthSmeltingCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("cinderhearth_smelting");
	
	public void trigger(ServerPlayerEntity player, ItemStack input, List<ItemStack> outputs, int experience, Upgradeable.UpgradeHolder upgrades) {
		this.trigger(player, (conditions) -> conditions.matches(input, outputs, experience, upgrades));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
		Optional<LootContextPredicate> player,
		ItemPredicate input,
		ItemPredicate output,
		NumberRange.IntRange gainedExperience,
		NumberRange.IntRange speedMultiplier,
		NumberRange.IntRange yieldMultiplier,
		NumberRange.IntRange efficiencyMultiplier,
		NumberRange.IntRange experienceMultiplier
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(CinderhearthSmeltingCriterion.Conditions::player),
			ItemPredicate.CODEC.fieldOf("input").forGetter(Conditions::input),
			ItemPredicate.CODEC.fieldOf("output").forGetter(Conditions::output),
			NumberRange.IntRange.CODEC.fieldOf("gained_experience").forGetter(Conditions::gainedExperience),
			NumberRange.IntRange.CODEC.fieldOf("speed_multiplier").forGetter(Conditions::speedMultiplier),
			NumberRange.IntRange.CODEC.fieldOf("yield_multiplier").forGetter(Conditions::yieldMultiplier),
			NumberRange.IntRange.CODEC.fieldOf("efficiency_multiplier").forGetter(Conditions::efficiencyMultiplier),
			NumberRange.IntRange.CODEC.fieldOf("experience_multiplier").forGetter(Conditions::experienceMultiplier)
			
		).apply(instance, CinderhearthSmeltingCriterion.Conditions::new));
	
		public boolean matches(ItemStack input, List<ItemStack> outputs, int experience, Upgradeable.UpgradeHolder upgrades) {
			if (!this.input.test(input)) {
				return false;
			}
			if (!this.gainedExperience.test(experience)) {
				return false;
			}
			if (!this.speedMultiplier.test(upgrades.getRawValue(Upgradeable.UpgradeType.SPEED))) {
				return false;
			}
			if (!this.yieldMultiplier.test(upgrades.getRawValue(Upgradeable.UpgradeType.YIELD))) {
				return false;
			}
			if (!this.efficiencyMultiplier.test(upgrades.getRawValue(Upgradeable.UpgradeType.EFFICIENCY))) {
				return false;
			}
			if (!this.experienceMultiplier.test(upgrades.getRawValue(Upgradeable.UpgradeType.EXPERIENCE))) {
				return false;
			}
			if (this.output.equals(ItemPredicate.Builder.create().build())) {
				return true; // empty output predicate
			}
			for (ItemStack output : outputs) {
				if (this.output.test(output)) {
					return true;
				}
			}
			return false;
		}
		
	}
	
}
