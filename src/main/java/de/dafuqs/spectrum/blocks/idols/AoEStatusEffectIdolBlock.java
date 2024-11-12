package de.dafuqs.spectrum.blocks.idols;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.*;
import net.minecraft.text.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class AoEStatusEffectIdolBlock extends IdolBlock {
	
	protected final int range;
	protected final RegistryEntry<StatusEffect> statusEffect;
	protected final int amplifier;
	protected final int duration;
	
	public AoEStatusEffectIdolBlock(Settings settings, ParticleEffect particleEffect, RegistryEntry<StatusEffect> statusEffect, int amplifier, int duration, int range) {
		super(settings, particleEffect);
		this.statusEffect = statusEffect;
		this.amplifier = amplifier;
		this.duration = duration;
		this.range = range;
	}

	@Override
	public MapCodec<? extends AoEStatusEffectIdolBlock> getCodec() {
		//TODO: Make the codec
		return null;
	}
	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("block.spectrum.echolocating_idol.tooltip", range));
	}
	
	@Override
	public boolean trigger(ServerWorld world, BlockPos blockPos, BlockState state, @Nullable Entity entity, Direction side) {
		int boxSize = range + range;
		List<LivingEntity> livingEntities = world.getNonSpectatingEntities(LivingEntity.class, Box.of(Vec3d.ofCenter(blockPos), boxSize, boxSize, boxSize));
		for (LivingEntity livingEntity : livingEntities) {
			livingEntity.addStatusEffect(new StatusEffectInstance(statusEffect, duration, amplifier));
		}
		return true;
	}
	
}
