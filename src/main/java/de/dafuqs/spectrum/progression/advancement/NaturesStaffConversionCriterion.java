package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.block.*;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.registry.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class NaturesStaffConversionCriterion extends AbstractCriterion<NaturesStaffConversionCriterion.Conditions> {

	public static final Identifier ID = SpectrumCommon.locate("natures_staff_conversion");
	
	public void trigger(ServerPlayerEntity player, BlockState sourceBlockState, BlockState targetBlockState) {
		this.trigger(player, (conditions) -> conditions.matches(sourceBlockState, targetBlockState));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
		Optional<LootContextPredicate> player,
		Block sourceBlock,
		StatePredicate sourceBlockState,
		Block targetBlock,
		StatePredicate targetBlockState
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
			Registries.BLOCK.getCodec().fieldOf("source_block").forGetter(Conditions::sourceBlock),
			StatePredicate.CODEC.fieldOf("source_state").forGetter(Conditions::sourceBlockState),
			Registries.BLOCK.getCodec().fieldOf("target_block").forGetter(Conditions::targetBlock),
			StatePredicate.CODEC.fieldOf("target_state").forGetter(Conditions::targetBlockState)
			).apply(instance, Conditions::new));


		public boolean matches(BlockState sourceBlockState, BlockState targetBlockState) {
			if (this.sourceBlock != null && !sourceBlockState.isOf(this.sourceBlock)) {
				return false;
			}
			if (!this.sourceBlockState.test(sourceBlockState)) {
				return false;
			}
			if (this.targetBlock != null && !targetBlockState.isOf(this.targetBlock)) {
				return false;
			} else {
				return this.targetBlockState.test(targetBlockState);
			}
		}

	}

}
