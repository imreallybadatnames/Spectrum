package de.dafuqs.spectrum.blocks.idols;

import net.minecraft.block.*;
import net.minecraft.client.item.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.*;
import net.minecraft.server.world.*;
import net.minecraft.text.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class KnockbackIdolBlock extends IdolBlock {
	
	protected final float horizontalKnockback;
	protected final float verticalKnockback;
	
	public KnockbackIdolBlock(Settings settings, ParticleEffect particleEffect, float horizontalKnockback, float verticalKnockback) {
		super(settings, particleEffect);
		this.horizontalKnockback = horizontalKnockback;
		this.verticalKnockback = verticalKnockback;
	}
	
	@Override
	public boolean trigger(ServerWorld world, BlockPos blockPos, BlockState state, @Nullable Entity entity, Direction side) {
		if (entity != null) {
			switch (side) {
				case NORTH -> {
					entity.addVelocity(0, verticalKnockback, -horizontalKnockback);
					entity.velocityModified = true;
				}
				case EAST -> {
					entity.addVelocity(horizontalKnockback, verticalKnockback, 0);
					entity.velocityModified = true;
				}
				case SOUTH -> {
					entity.addVelocity(0, verticalKnockback, horizontalKnockback);
					entity.velocityModified = true;
				}
				case WEST -> {
					entity.addVelocity(-horizontalKnockback, verticalKnockback, 0);
					entity.velocityModified = true;
				}
				case UP -> {
					entity.addVelocity(0, (horizontalKnockback / 4), 0);
					entity.velocityModified = true;
				}
				default -> {
					entity.addVelocity(0, -(horizontalKnockback / 4), 0);
					entity.velocityModified = true;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("block.spectrum.knockback_idol.tooltip"));
	}
	
}
