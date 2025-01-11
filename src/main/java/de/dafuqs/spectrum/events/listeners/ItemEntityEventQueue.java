package de.dafuqs.spectrum.events.listeners;

import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.particle.effect.*;
import net.minecraft.entity.*;
import net.minecraft.registry.entry.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.event.*;

public class ItemEntityEventQueue extends EventQueue<ItemEntityEventQueue.EventEntry> {
	
	public ItemEntityEventQueue(PositionSource positionSource, int range, Callback<EventEntry> listener) {
		super(positionSource, range, listener);
	}
	
	@Override
	public void acceptEvent(World world, GameEvent.Message event, Vec3d sourcePos) {
		if (world instanceof ServerWorld && event.getEmitter().sourceEntity() instanceof ItemEntity itemEntity) {
			Vec3d pos = event.getEmitterPos();
			EventEntry eventEntry = new EventEntry(event.getEvent(), itemEntity, MathHelper.floor(pos.distanceTo(sourcePos)));
			int delay = eventEntry.distance * 2;
			this.schedule(eventEntry, delay);
			TypedTransmissionPayload.playTransmissionParticle((ServerWorld) world, new TypedTransmission(pos, this.positionSource, delay, TypedTransmission.Variant.ITEM));
		}
	}
	
	public static class EventEntry {
		public final RegistryEntry<GameEvent> event;
		public final ItemEntity itemEntity;
		public final int distance;
		
		public EventEntry(RegistryEntry<GameEvent> event, ItemEntity itemEntity, int distance) {
			this.event = event;
			this.itemEntity = itemEntity;
			this.distance = distance;
		}
	}
	
}