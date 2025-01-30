package de.dafuqs.spectrum.items.food.beverages;

import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.event.*;

import java.util.*;

public class RepriseItem extends BeverageItem {
	
	public RepriseItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (world instanceof ServerWorld serverWorld) {
			randomTeleport(serverWorld, user, getTeleportRange(stack));
		}
		return super.finishUsing(stack, world, user);
	}

	public static long getTeleportRange(ItemStack itemStack) {
		var alcPercent = itemStack.getOrDefault(SpectrumDataComponentTypes.BEVERAGE, BeverageComponent.DEFAULT).alcoholPercent();
		return (long) Math.ceil(Math.pow(2, alcPercent));
	}

	public void randomTeleport(ServerWorld world, LivingEntity user, long maxRange) {
		double d = user.getX();
		double e = user.getY();
		double f = user.getZ();

		for (int i = 0; i < 16; ++i) {
			double newX = user.getX() + (user.getRandom().nextDouble() - 0.5D) * maxRange;
			double newY = user.getY();
			double newZ = user.getZ() + (user.getRandom().nextDouble() - 0.5D) * maxRange;
			BlockPos destination = world.getWorldBorder().clamp(newX, newY, newZ);

			Optional<BlockPos> safeDestination = Support.getNexReplaceableBlockPosUpDown(world, destination, 20);
			if (safeDestination.isPresent()) {
				destination = safeDestination.get();

				world.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, new ChunkPos(destination), 1, user.getId());
				if (user.isSleeping()) {
					user.wakeUp();
				}
				if (user.hasVehicle()) {
					user.stopRiding();
				}

				user.requestTeleport(destination.getX(), destination.getY(), destination.getZ());

				world.emitGameEvent(GameEvent.TELEPORT, user.getPos(), GameEvent.Emitter.of(user));
				SoundEvent soundEvent = user instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
				world.playSound(null, d, e, f, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
				user.playSound(soundEvent, 1.0F, 1.0F);
				break;
			}
		}

		if (user instanceof PlayerEntity) {
			((PlayerEntity) user).getItemCooldownManager().set(this, 20);
		}
	}
	
	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.reprise.tooltip.teleport", getTeleportRange(stack)).formatted(Formatting.GRAY));
	}
	
}
