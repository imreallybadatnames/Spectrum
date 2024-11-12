package de.dafuqs.spectrum.blocks.particle_spawner;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.api.item.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class CreativeParticleSpawnerBlock extends AbstractParticleSpawnerBlock implements CreativeOnlyItem {

	public static final MapCodec<CreativeParticleSpawnerBlock> CODEC = createCodec(CreativeParticleSpawnerBlock::new);

	public CreativeParticleSpawnerBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends CreativeParticleSpawnerBlock> getCodec() {
		return CODEC;
	}

	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("block.spectrum.creative_particle_spawner.tooltip").formatted(Formatting.GRAY));
		CreativeOnlyItem.appendTooltip(tooltip);
	}
	
	@Override
	public boolean shouldSpawnParticles(World world, BlockPos pos) {
		return true;
	}
	
}
