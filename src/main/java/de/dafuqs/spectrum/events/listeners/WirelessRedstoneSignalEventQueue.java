package de.dafuqs.spectrum.events.listeners;

import de.dafuqs.spectrum.events.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.effect.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.event.*;

public class WirelessRedstoneSignalEventQueue extends EventQueue<WirelessRedstoneSignalEventQueue.EventEntry> {
	
	public WirelessRedstoneSignalEventQueue(PositionSource positionSource, int range, EventQueue.Callback<EventEntry> listener) {
		super(positionSource, range, listener);
	}
	
	@Override
	public void acceptEvent(World world, GameEvent.Message event, Vec3d sourcePos) {
		if (world instanceof ServerWorld && event.getEvent() == SpectrumGameEvents.WIRELESS_REDSTONE_SIGNAL) {
			Vec3d pos = event.getEmitterPos();
			var eventEntry = new WirelessRedstoneSignalEventQueue.EventEntry(event, MathHelper.floor(pos.distanceTo(sourcePos)));
			int delay = eventEntry.distance * 2;
			this.schedule(eventEntry, delay);
			SpectrumS2CPacketSender.playTransmissionParticle((ServerWorld) world, new TypedTransmission(pos, this.positionSource, delay, TypedTransmission.Variant.REDSTONE));
		}
	}

	public record EventEntry(GameEvent.Message gameEvent, int distance) {
	}
	
}