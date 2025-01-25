package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.player.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class MoltenRodItem extends SpectrumFishingRodItem {
	
	public static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/equipment/molten_rod");
	
	public MoltenRodItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public boolean canFishIn(FluidState fluidState) {
		return fluidState.isIn(SpectrumFluidTags.MOLTEN_ROD_FISHABLE_IN);
	}
	
	@Override
	public void spawnBobber(PlayerEntity user, World world, int luckBonus, int waitTimeReductionTicks, int exuberanceLevel, int bigCatchLevel, int serendipityReelLevel, boolean inventoryInsertion, boolean shouldSmeltDrops) {
		world.spawnEntity(new MoltenFishingBobberEntity(user, world, luckBonus, waitTimeReductionTicks, exuberanceLevel, bigCatchLevel, serendipityReelLevel, inventoryInsertion));
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.molten_rod.tooltip").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.molten_rod.tooltip2").formatted(Formatting.GRAY));
	}
	
}
