package de.dafuqs.spectrum.api.predicate.location;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.helpers.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
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
	public static final PacketCodec<ByteBuf, MoonPhasePredicate> PACKET_CODEC = PacketCodecHelper.enumOf(MoonPhasePredicate::values);
	
	public boolean test(ServerWorld world) {
		return ordinal() == world.getMoonPhase();
	}
	
	@Override
	public String asString() {
		return name().toLowerCase();
	}
	
}