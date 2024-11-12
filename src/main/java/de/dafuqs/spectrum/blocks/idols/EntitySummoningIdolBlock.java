package de.dafuqs.spectrum.blocks.idols;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.*;
import net.minecraft.server.world.*;
import net.minecraft.text.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

import java.util.*;

public abstract class EntitySummoningIdolBlock extends IdolBlock {
	
	protected final EntityType<?> entityType;
	
	public EntitySummoningIdolBlock(Settings settings, ParticleEffect particleEffect, EntityType<?> entityType) {
		super(settings, particleEffect);
		this.entityType = entityType;
	}

	@Override
	public MapCodec<? extends EntitySummoningIdolBlock> getCodec() {
		//TODO: Make the codec
		return null;
	}

	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("block.spectrum.entity_summoning_idol.tooltip", entityType.getName()));
	}
	
	@Override
	public boolean trigger(ServerWorld world, BlockPos blockPos, BlockState state, @Nullable Entity entity, Direction side) {
		// alignPosition: center the mob in the center of the blockPos
		Entity summonedEntity = entityType.create(world);
		if (summonedEntity != null) {
			summonedEntity.refreshPositionAndAngles(blockPos.up(), 0.0F, 0.0F);
			if (summonedEntity instanceof MobEntity mobEntity) {
				mobEntity.initialize(world, world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, null);
			}
			afterSummon(world, summonedEntity);
			world.spawnEntityAndPassengers(summonedEntity);
		}
		return true;
	}
	
	public abstract void afterSummon(ServerWorld world, Entity entity);
	
}
