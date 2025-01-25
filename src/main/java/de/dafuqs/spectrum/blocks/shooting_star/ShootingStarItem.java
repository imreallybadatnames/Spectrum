package de.dafuqs.spectrum.blocks.shooting_star;

import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.stat.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ShootingStarItem extends BlockItem implements ShootingStar {
	
	private final Type shootingStarType;
	
	public ShootingStarItem(ShootingStarBlock block, Settings settings) {
		super(block, settings);
		this.shootingStarType = block.shootingStarType;
	}
	
	public static @NotNull ItemStack getWithRemainingHits(@NotNull ShootingStarItem shootingStarItem, int remainingHits, boolean hardened) {
		return getWithRemainingHits(shootingStarItem.getDefaultStack(), remainingHits, hardened);
	}
	
	public static @NotNull ItemStack getWithRemainingHits(@NotNull ItemStack stack, int remainingHits, boolean hardened) {
		ShootingStarComponent component = new ShootingStarComponent(remainingHits, hardened);
		stack.set(SpectrumDataComponentTypes.SHOOTING_STAR, component);
		return stack;
	}
	
	@Override
	public ActionResult useOnBlock(@NotNull ItemUsageContext context) {
		if (context.getPlayer().isSneaking()) {
			// place as block
			return super.useOnBlock(context);
		} else {
			// place as entity
			World world = context.getWorld();
			
			if (!world.isClient) {
				ItemStack itemStack = context.getStack();
				Vec3d hitPos = context.getHitPos();
				PlayerEntity user = context.getPlayer();

				ShootingStarEntity shootingStarEntity = getEntityForStack(context.getWorld(), hitPos, itemStack);
				shootingStarEntity.setYaw(user.getYaw());
				if (!world.isSpaceEmpty(shootingStarEntity, shootingStarEntity.getBoundingBox())) {
					return ActionResult.FAIL;
				} else {
					world.spawnEntity(shootingStarEntity);
					world.emitGameEvent(user, GameEvent.ENTITY_PLACE, context.getBlockPos());
					if (!user.getAbilities().creativeMode) {
						itemStack.decrement(1);
					}
					
					user.incrementStat(Stats.USED.getOrCreateStat(this));
				}
			}
			
			return ActionResult.success(world.isClient);
		}
	}

	@NotNull
	public ShootingStarEntity getEntityForStack(@NotNull World world, Vec3d pos, ItemStack stack) {
		return new ShootingStarEntity(world, pos.x, pos.y, pos.z, this.shootingStarType, true, getRemainingHits(stack), isHardened(stack));
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		if (isHardened(stack)) {
			tooltip.add(Text.translatable("item.spectrum.shooting_star.tooltip.hardened").formatted(Formatting.GRAY));
		}
	}
	
	public ShootingStar.Type getShootingStarType() {
		return this.shootingStarType;
	}
	
	public static boolean isHardened(ItemStack stack) {
		return stack.getOrDefault(SpectrumDataComponentTypes.SHOOTING_STAR, ShootingStarComponent.DEFAULT).hardened();
	}
	
	public static int getRemainingHits(@NotNull ItemStack stack) {
		return stack.getOrDefault(SpectrumDataComponentTypes.SHOOTING_STAR, ShootingStarComponent.DEFAULT).remainingHits();
	}
	
	public static void setHardened(ItemStack stack) {
		ShootingStarComponent component = stack.getOrDefault(SpectrumDataComponentTypes.SHOOTING_STAR, ShootingStarComponent.DEFAULT);
		if (component == null) {
			component = new ShootingStarComponent(ShootingStarComponent.DEFAULT.remainingHits(), true);
		} else {
			component = new ShootingStarComponent(component.remainingHits(), true);
		}
		
		stack.set(SpectrumDataComponentTypes.SHOOTING_STAR, component);
	}

}
