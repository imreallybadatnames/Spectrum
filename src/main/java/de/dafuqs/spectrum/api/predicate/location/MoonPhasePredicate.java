package de.dafuqs.spectrum.api.predicate.location;

import com.mojang.serialization.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;

public enum MoonPhasePredicate implements StringIdentifiable {
	FULL_MOON,
	WANING_GIBBOUS,
	THIRD_QUARTER,
	WANING_CRESCENT,
	NEW_MOON,
	WAXING_CRESCENT,
	FIRST_QUARTER,
	WAXING_GIBBOUS;
	
	public static final Codec<MoonPhasePredicate> CODEC = StringIdentifiable.createCodec(MoonPhasePredicate::values);
	
	public boolean test(ServerWorld world) {
		return ordinal() == world.getMoonPhase();
	}
	
	@Override
	public String asString() {
		return name().toLowerCase();
	}
	
}