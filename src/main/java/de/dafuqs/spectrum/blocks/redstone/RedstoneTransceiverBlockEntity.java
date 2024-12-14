package de.dafuqs.spectrum.blocks.redstone;

import de.dafuqs.spectrum.events.*;
import de.dafuqs.spectrum.events.listeners.*;
import de.dafuqs.spectrum.helpers.EventHelper;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.event.*;
import net.minecraft.world.event.listener.*;
import org.jetbrains.annotations.*;

public class RedstoneTransceiverBlockEntity extends BlockEntity implements WirelessRedstoneSignalEventQueue.Callback<WirelessRedstoneSignalEventQueue.EventEntry> {
	
	private static final int RANGE = 16;
	private final WirelessRedstoneSignalEventQueue listener;
	private int cachedSignal;
	private int currentSignal;
	
	public RedstoneTransceiverBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(SpectrumBlockEntities.REDSTONE_TRANSCEIVER, blockPos, blockState);
		this.listener = new WirelessRedstoneSignalEventQueue(new BlockPositionSource(this.pos), RANGE, this);
	}
	
	private static boolean isSender(World world, BlockPos blockPos) {
		if (world == null) {
			return false;
		}
		return world.getBlockState(blockPos).get(RedstoneTransceiverBlock.SENDER);
	}
	
	public static void serverTick(@NotNull World world, BlockPos pos, BlockState state, @NotNull RedstoneTransceiverBlockEntity blockEntity) {
		if (isSender(world, pos)) {
			if (blockEntity.currentSignal != blockEntity.cachedSignal) {
				blockEntity.currentSignal = blockEntity.cachedSignal;
				blockEntity.getWorld().emitGameEvent(SpectrumGameEvents.WIRELESS_REDSTONE_SIGNAL, blockEntity.getPos(), new GameEvent.Emitter(null, state));
			}
		} else {
			blockEntity.listener.tick(world);
		}
	}
	
	public static DyeColor getChannel(World world, BlockPos pos) {
		if (world == null) {
			return DyeColor.RED;
		}
		return world.getBlockState(pos).get(RedstoneTransceiverBlock.CHANNEL);
	}
	
	@Override
	public void writeNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(tag, registryLookup);
		tag.putInt("signal", this.currentSignal);
		tag.putInt("cached_signal", this.cachedSignal);
	}
	
	@Override
	public void readNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(tag, registryLookup);
		this.currentSignal = tag.getInt("output_signal");
		this.cachedSignal = tag.getInt("cached_signal");
	}
	
	public @Nullable WirelessRedstoneSignalEventQueue getEventListener() {
		return this.listener;
	}
	
	public int getRange() {
		return RANGE;
	}
	
	@Override
	public boolean canAcceptEvent(World world, GameEventListener listener, GameEvent.Message message, Vec3d sourcePos) {
		return !this.isRemoved()
				&& message.getEvent() == SpectrumGameEvents.WIRELESS_REDSTONE_SIGNAL
				&& !isSender(this.getWorld(), this.pos)
				&& EventHelper.getRedstoneEventDyeColor(message) == getChannel(this.getWorld(), this.pos);
	}
	
	@Override
	public void triggerEvent(World world, GameEventListener listener, WirelessRedstoneSignalEventQueue.EventEntry redstoneEvent) {
		if (!isSender(this.getWorld(), this.pos) && EventHelper.getRedstoneEventDyeColor(redstoneEvent.gameEvent()) == getChannel(this.getWorld(), this.pos)) {
			int receivedSignal = EventHelper.getRedstoneEventPower(world, redstoneEvent.gameEvent());
			this.currentSignal = receivedSignal;
			// trigger a block update in all cases, even when powered does not change. That way connected blocks
			// can react on the strength change of the block, since we store the power in the block entity, not the block state
			if (receivedSignal == 0) {
				world.setBlockState(pos, world.getBlockState(pos).with(RedstoneTransceiverBlock.POWERED, false), Block.NOTIFY_LISTENERS);
			} else {
				world.setBlockState(pos, world.getBlockState(pos).with(RedstoneTransceiverBlock.POWERED, true), Block.NOTIFY_LISTENERS);
			}
			world.updateNeighbors(pos, SpectrumBlocks.REDSTONE_TRANSCEIVER);
		}
	}
	
	// since redstone is weird we have to cache a new signal or so
	// if we would start a game event right here it could be triggered
	// multiple times a tick (because neighboring redstone updates > 1/tick)
	// and therefore receivers receiving a wrong (because old) signal
	public void setSignalStrength(int newSignal) {
		if (isSender(this.getWorld(), this.pos)) {
			this.cachedSignal = newSignal;
		} else {
			this.currentSignal = newSignal;
		}
	}
	
	public int getCurrentSignal() {
		if (isSender(this.getWorld(), this.pos)) {
			return 0;
		}
		return this.currentSignal;
	}
	
	public int getCurrentSignalStrength() {
		return this.currentSignal;
	}
	
}
