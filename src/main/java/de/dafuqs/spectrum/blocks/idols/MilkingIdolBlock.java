package de.dafuqs.spectrum.blocks.idols;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.mixin.accessors.*;
import net.minecraft.block.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

import java.util.*;

import static de.dafuqs.spectrum.helpers.InWorldInteractionHelper.*;

public class MilkingIdolBlock extends IdolBlock {
	
	protected static final int BUCKET_SEARCH_RANGE = 7;
	protected final int milkingRange;
	
	public MilkingIdolBlock(Settings settings, ParticleEffect particleEffect, int milkingRange) {
		super(settings, particleEffect);
		this.milkingRange = milkingRange;
	}

	@Override
	public MapCodec<? extends MilkingIdolBlock> getCodec() {
		//TODO: Make the codec
		return null;
	}
	
	@Override
	public boolean trigger(@NotNull ServerWorld world, BlockPos blockPos, BlockState state, @Nullable Entity entity, Direction side) {
		int boxSize = milkingRange + milkingRange;
		
		// Goats
		List<GoatEntity> goatEntities = world.getNonSpectatingEntities(GoatEntity.class, Box.of(Vec3d.ofCenter(blockPos), boxSize, boxSize, boxSize));
		for (GoatEntity goatEntity : goatEntities) {
			if (!goatEntity.isBaby()) {
				boolean emptyBucketFound = findAndDecreaseClosestItemEntityOfItem(world, goatEntity.getPos(), Items.BUCKET, BUCKET_SEARCH_RANGE);
				if (emptyBucketFound) {
					SoundEvent soundEvent = goatEntity.isScreaming() ? SoundEvents.ENTITY_GOAT_SCREAMING_MILK : SoundEvents.ENTITY_GOAT_MILK;
					world.playSound(null, goatEntity.getBlockPos(), soundEvent, SoundCategory.NEUTRAL, 1.0F, 1.0F);
					spawnItemStackAtEntity(world, goatEntity, Items.MILK_BUCKET.getDefaultStack());
				}
			}
		}

		// Cows (includes Mooshrooms)
		List<CowEntity> cowEntities = world.getNonSpectatingEntities(CowEntity.class, Box.of(Vec3d.ofCenter(blockPos), boxSize, boxSize, boxSize));
		for (CowEntity cowEntity : cowEntities) {
			if (!cowEntity.isBaby()) {
				boolean emptyBucketFound = findAndDecreaseClosestItemEntityOfItem(world, cowEntity.getPos(), Items.BUCKET, BUCKET_SEARCH_RANGE);
				if (emptyBucketFound) {
					world.playSound(null, cowEntity.getBlockPos(), SoundEvents.ENTITY_COW_MILK, SoundCategory.NEUTRAL, 1.0F, 1.0F);
					spawnItemStackAtEntity(world, cowEntity, Items.MILK_BUCKET.getDefaultStack());
				}
			}
		}
		
		// Egg Laying Wooly Pigs
		List<EggLayingWoolyPigEntity> eggLayingWoolyPigEntities = world.getNonSpectatingEntities(EggLayingWoolyPigEntity.class, Box.of(Vec3d.ofCenter(blockPos), boxSize, boxSize, boxSize));
		for (EggLayingWoolyPigEntity eggLayingWoolyPigEntity : eggLayingWoolyPigEntities) {
			if (!eggLayingWoolyPigEntity.isBaby()) {
				boolean emptyBucketFound = findAndDecreaseClosestItemEntityOfItem(world, eggLayingWoolyPigEntity.getPos(), Items.BUCKET, BUCKET_SEARCH_RANGE);
				if (emptyBucketFound) {
					world.playSound(null, eggLayingWoolyPigEntity.getBlockPos(), SoundEvents.ENTITY_COW_MILK, SoundCategory.NEUTRAL, 1.0F, 1.0F);
					spawnItemStackAtEntity(world, eggLayingWoolyPigEntity, Items.MILK_BUCKET.getDefaultStack());
				}
			}
		}
		
		// Mooshrooms (Mooshroom Stew / Suspicious Stew)
		List<MooshroomEntity> mooshroomEntities = world.getNonSpectatingEntities(MooshroomEntity.class, Box.of(Vec3d.ofCenter(blockPos), boxSize, boxSize, boxSize));
		for (MooshroomEntity mooshroomEntity : mooshroomEntities) {
			if (!mooshroomEntity.isBaby()) {
				boolean emptyBowlFound = findAndDecreaseClosestItemEntityOfItem(world, mooshroomEntity.getPos(), Items.BOWL, BUCKET_SEARCH_RANGE);
				if (emptyBowlFound) {
					MooshroomEntityAccessor accessor = (MooshroomEntityAccessor) mooshroomEntity;
					
					SoundEvent soundEvent;
					ItemStack resultStack;
					if (accessor.getStewEffects() != null) {
						resultStack = new ItemStack(Items.SUSPICIOUS_STEW);
						resultStack.set(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, accessor.getStewEffects());
						accessor.setStewEffects(null);
						soundEvent = SoundEvents.ENTITY_MOOSHROOM_SUSPICIOUS_MILK;
					} else {
						resultStack = new ItemStack(Items.MUSHROOM_STEW);
						soundEvent = SoundEvents.ENTITY_MOOSHROOM_MILK;
					}
					
					world.playSound(null, mooshroomEntity.getBlockPos(), soundEvent, SoundCategory.NEUTRAL, 1.0F, 1.0F);
					spawnItemStackAtEntity(world, mooshroomEntity, resultStack);
				}
			}
		}
		return true;
	}
	
	private void spawnItemStackAtEntity(ServerWorld world, @NotNull LivingEntity livingEntity, ItemStack itemStack) {
		ItemEntity itemEntity = new ItemEntity(world, livingEntity.getPos().getX(), livingEntity.getPos().getY() + 0.5, livingEntity.getPos().getZ(), itemStack);
		itemEntity.addVelocity(0, -0.2F, 0);
		world.spawnEntity(itemEntity);
	}

	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("block.spectrum.milking_idol.tooltip", this.milkingRange));
		tooltip.add(Text.translatable("block.spectrum.milking_idol.tooltip2"));
	}
	
}
