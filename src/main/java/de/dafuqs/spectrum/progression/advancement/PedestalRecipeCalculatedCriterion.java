package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

public class PedestalRecipeCalculatedCriterion extends AbstractCriterion<PedestalCraftingCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("pedestal_recipe_calculated");
	
	public void trigger(ServerPlayerEntity player, ItemStack itemStack, int experience, int durationTicks) {
		this.trigger(player, (conditions) -> conditions.matches(itemStack, experience, durationTicks));
	}
	
	@Override
	public Codec<PedestalCraftingCriterion.Conditions> getConditionsCodec() {
		return PedestalCraftingCriterion.Conditions.CODEC;
	}
}
