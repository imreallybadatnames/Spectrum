package de.dafuqs.spectrum.particle.effect;

import net.minecraft.particle.*;
import net.minecraft.world.event.*;

public abstract class SimpleTransmissionParticleEffect implements ParticleEffect {
	
	protected final PositionSource destination;
	protected final int arrivalInTicks;
	
	public SimpleTransmissionParticleEffect(PositionSource positionSource, int arrivalInTicks) {
		this.destination = positionSource;
		this.arrivalInTicks = arrivalInTicks;
	}
	
	public PositionSource getDestination() {
		return this.destination;
	}
	
	public int getArrivalInTicks() {
		return this.arrivalInTicks;
	}
	
}
