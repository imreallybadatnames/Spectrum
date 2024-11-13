package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

public class EnchanterEnchantingCriterion extends AbstractCriterion<EnchanterCraftingCriterion.Conditions> {

	public static final Identifier ID = SpectrumCommon.locate("enchanter_enchanting");

	public void trigger(ServerPlayerEntity player, ItemStack itemStack, int spentExperience) {
		this.trigger(player, (conditions) -> conditions.matches(itemStack, spentExperience));
	}

	@Override
	public Codec<EnchanterCraftingCriterion.Conditions> getConditionsCodec() {
		return EnchanterCraftingCriterion.Conditions.CODEC;
	}
}
