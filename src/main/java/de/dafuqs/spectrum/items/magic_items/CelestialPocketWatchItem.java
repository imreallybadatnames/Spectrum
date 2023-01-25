package de.dafuqs.spectrum.items.magic_items;

import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.client.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CelestialPocketWatchItem extends Item {
	
	// Since the watch can be triggered from an item frame, too
	// and item frames can turn items in 8 directions this fits real fine
	public static final int TIME_STEP_TICKS = 24000 / 8;
	
	public CelestialPocketWatchItem(Settings settings) {
		super(settings);
	}
	
	public static boolean advanceTime(ServerPlayerEntity player, @NotNull ServerWorld world) {
		GameRules.BooleanRule doDaylightCycleRule = world.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE);
		if (doDaylightCycleRule.get()) {
			if (world.getDimension().hasFixedTime()) {
				SpectrumS2CPacketSender.sendHudMessage(player, Text.translatable("item.spectrum.celestial_pocketwatch.tooltip.use_blocked_fixed_time"), false);
			} else {
				SpectrumS2CPacketSender.startSkyLerping(world, TIME_STEP_TICKS);
				long timeOfDay = world.getTimeOfDay();
				world.setTimeOfDay(timeOfDay + TIME_STEP_TICKS);
				return true;
			}
		} else {
			SpectrumS2CPacketSender.sendHudMessage(player, Text.translatable("item.spectrum.celestial_pocketwatch.tooltip.use_blocked_gamerule"), false);
		}
		return false;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		
		if (!world.isClient) {
			// the clocks use is blocked if the world has a fixed daylight cycle
			if (advanceTime((ServerPlayerEntity) user, (ServerWorld) world)) {
				world.playSound(null, user.getBlockPos(), SpectrumSoundEvents.CELESTIAL_POCKET_WATCH_TICKING, SoundCategory.PLAYERS, 1.0F, 1.0F);
			} else {
				world.playSound(null, user.getBlockPos(), SpectrumSoundEvents.USE_FAIL, SoundCategory.PLAYERS, 1.0F, 1.0F);
			}
			return TypedActionResult.consume(itemStack);
		}
		return TypedActionResult.success(itemStack, true);
	}
	
	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
		super.appendTooltip(itemStack, world, tooltip, tooltipContext);
		
		if (world != null) {
			// the clocks use is blocked if the world has a fixed daylight cycle
			GameRules.BooleanRule doDaylightCycleRule = world.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE);
			if (doDaylightCycleRule.get()) {
				if (world.getDimension().hasFixedTime()) {
					tooltip.add(Text.translatable("item.spectrum.celestial_pocketwatch.tooltip.use_blocked_fixed_time").formatted(Formatting.GRAY));
				} else {
					tooltip.add(Text.translatable("item.spectrum.celestial_pocketwatch.tooltip.working").formatted(Formatting.GRAY));
				}
			} else {
				tooltip.add(Text.translatable("item.spectrum.celestial_pocketwatch.tooltip.use_blocked_gamerule").formatted(Formatting.GRAY));
			}
		}
	}
	
}
