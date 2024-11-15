package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import net.minecraft.predicate.*;

import java.util.*;

public record LongRange(Optional<Long> min, Optional<Long> max, Optional<Long> minSquared, Optional<Long> maxSquared) implements NumberRange<Long> {
	
	public static final Codec<LongRange> CODEC = NumberRange.createCodec(Codec.LONG, LongRange::new);
	public static final LongRange ANY = new LongRange(null, null);
	
	private LongRange(Optional<Long> min, Optional<Long> max) {
		this(min, max, square(min), square(max));
	}
	
	private static Optional<Long> square(Optional<Long> value) {
		return value.map(d -> d * d);
	}
	
	public static LongRange exactly(long value) {
		return new LongRange(Optional.of(value), Optional.of(value));
	}
	
	public static LongRange between(long min, long max) {
		return new LongRange(Optional.of(min), Optional.of(max));
	}
	
	public static LongRange atLeast(long value) {
		return new LongRange(Optional.of(value), Optional.empty());
	}
	
	public static LongRange atMost(long value) {
		return new LongRange(Optional.empty(), Optional.of(value));
	}
	
	// TODO - Review logic
	public boolean test(long value) {
		if (this.min.isPresent() && this.min.get() > value) {
			return false;
		} else {
			return this.max.isEmpty() || this.max.get() >= value;
		}
	}
}