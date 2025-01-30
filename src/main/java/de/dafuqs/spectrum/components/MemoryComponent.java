package de.dafuqs.spectrum.components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;

public record MemoryComponent(int ticksToManifest, boolean spawnAsAdult, boolean brokenPromise, boolean unrecognizable) {
	
	// zero or negative ticks to manifest: never hatch
	
	public static final MemoryComponent DEFAULT = new MemoryComponent(-1, false, false, false);
	
	public static final Codec<MemoryComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
		Codec.INT.fieldOf("ticks_to_manifest").forGetter(MemoryComponent::ticksToManifest),
		Codec.BOOL.fieldOf("spawn_as_adult").forGetter(MemoryComponent::spawnAsAdult),
		Codec.BOOL.fieldOf("broken_promise").forGetter(MemoryComponent::brokenPromise),
		Codec.BOOL.fieldOf("unrecognizable").forGetter(MemoryComponent::unrecognizable)
	).apply(i, MemoryComponent::new));
	
	public static final PacketCodec<ByteBuf, MemoryComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, MemoryComponent::ticksToManifest,
			PacketCodecs.BOOL, MemoryComponent::spawnAsAdult,
			PacketCodecs.BOOL, MemoryComponent::brokenPromise,
			PacketCodecs.BOOL, MemoryComponent::unrecognizable,
			MemoryComponent::new
	);
	
	public static class Builder {
		private int ticksToManifest;
		private boolean spawnAsAdult;
		private boolean brokenPromise;
		private boolean unrecognizable;
		
		public Builder(MemoryComponent component) {
			this.ticksToManifest = component.ticksToManifest;
			this.spawnAsAdult = component.spawnAsAdult;
			this.brokenPromise = component.brokenPromise;
			this.unrecognizable = component.unrecognizable;
		}
		
		public Builder ticksToManifest(int ticksToManifest) {
			this.ticksToManifest = ticksToManifest;
			return this;
		}
		
		public Builder spawnAsAdult(boolean spawnAsAdult) {
			this.spawnAsAdult = spawnAsAdult;
			return this;
		}
		
		public Builder brokenPromise(boolean brokenPromise) {
			this.brokenPromise = brokenPromise;
			return this;
		}
		
		public Builder unrecognizable() {
			this.unrecognizable = true;
			return this;
		}
		
		public MemoryComponent build() {
			return new MemoryComponent(this.ticksToManifest, this.spawnAsAdult, this.brokenPromise, this.unrecognizable);
		}
		
	}
	
}
