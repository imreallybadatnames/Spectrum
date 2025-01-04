package de.dafuqs.spectrum.events.listeners;

import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.effect.*;
import net.minecraft.entity.*;
import net.minecraft.registry.entry.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.event.*;

public class ExperienceOrbEventQueue extends EventQueue<ExperienceOrbEventQueue.EventEntry> {
	
	public ExperienceOrbEventQueue(PositionSource positionSource, int range, Callback<EventEntry> listener) {
		super(positionSource, range, listener);
	}
	
	@Override
	public void acceptEvent(World world, GameEvent.Message event, Vec3d sourcePos) {
		if (world instanceof ServerWorld && event.getEmitter().sourceEntity() instanceof ExperienceOrbEntity experienceOrbEntity) {
			Vec3d pos = event.getEmitterPos();
			EventEntry eventEntry = new EventEntry(event.getEvent(), experienceOrbEntity, MathHelper.floor(pos.distanceTo(sourcePos)));
			int delay = eventEntry.distance * 2;
			this.schedule(eventEntry, delay);
			SpectrumS2CPacketSender.playTransmissionParticle((ServerWorld) world, new TypedTransmission(pos, this.positionSource, delay, TypedTransmission.Variant.EXPERIENCE));
		}
	}
	
	public static class EventEntry {
		public final RegistryEntry<GameEvent> event;
		public final ExperienceOrbEntity experienceOrbEntity;
		public final int distance;
		
		public EventEntry(RegistryEntry<GameEvent> event, ExperienceOrbEntity experienceOrbEntity, int distance) {
			this.event = event;
			this.experienceOrbEntity = experienceOrbEntity;
			this.distance = distance;
		}
	}
	
}