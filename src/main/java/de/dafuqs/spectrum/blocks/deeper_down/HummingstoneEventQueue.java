package de.dafuqs.spectrum.blocks.deeper_down;

import de.dafuqs.spectrum.events.*;
import de.dafuqs.spectrum.events.listeners.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.effect.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.event.*;

public class HummingstoneEventQueue extends EventQueue<HummingstoneEventQueue.EventEntry> {
    
    public HummingstoneEventQueue(PositionSource positionSource, int range, Callback<EventEntry> listener) {
        super(positionSource, range, listener);
    }
    
    @Override
    public void acceptEvent(World world, GameEvent.Message message, Vec3d sourcePos) {
        Vec3d pos = message.getEmitterPos();
        EventEntry eventEntry = new EventEntry(message, MathHelper.floor(pos.distanceTo(sourcePos)));
        int delay = eventEntry.distance * 2;
		this.schedule(eventEntry, delay);
	
		if (message.getEvent() == SpectrumGameEvents.HUMMINGSTONE_HUMMING) {
			SpectrumS2CPacketSender.playTransmissionParticle((ServerWorld) world, new TypedTransmission(pos, this.positionSource, delay, TypedTransmission.Variant.HUMMINGSTONE));
			if (getQueuedEventCount() > 20) {
				world.emitGameEvent(message.getEmitter().sourceEntity(), SpectrumGameEvents.HUMMINGSTONE_HYMN, pos);
			}
		}
	}

	public record EventEntry(GameEvent.Message message, int distance) { }

}
