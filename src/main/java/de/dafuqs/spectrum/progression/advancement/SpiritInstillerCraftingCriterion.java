package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

public class SpiritInstillerCraftingCriterion extends AbstractCriterion<FusionShrineCraftingCriterion.Conditions> {

	public static final Identifier ID = SpectrumCommon.locate("crafted_with_spirit_instiller");

	public void trigger(ServerPlayerEntity player, ItemStack itemStack, int experience) {
		this.trigger(player, (conditions) -> conditions.matches(itemStack, experience));
	}

	@Override
	public Codec<FusionShrineCraftingCriterion.Conditions> getConditionsCodec() {
		return FusionShrineCraftingCriterion.Conditions.CODEC;
	}
}