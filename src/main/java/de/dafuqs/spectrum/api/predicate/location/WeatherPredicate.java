package de.dafuqs.spectrum.api.predicate.location;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.helpers.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.function.*;

public enum WeatherPredicate implements StringIdentifiable {
	CLEAR_SKY(world -> !world.isRaining()),
	RAIN(World::isRaining),
	STRICT_RAIN(world -> world.isRaining() && !world.isThundering()),
	THUNDER(World::isThundering),
	NOT_THUNDER(world -> !world.isThundering());
	
	public static final Codec<WeatherPredicate> CODEC = StringIdentifiable.createCodec(WeatherPredicate::values);
	public static final PacketCodec<ByteBuf, WeatherPredicate> PACKET_CODEC = PacketCodecHelper.enumOf(WeatherPredicate::values);
	
	private final Function<ServerWorld, Boolean> test;
	
	WeatherPredicate(Function<ServerWorld, Boolean> test) {
		this.test = test;
	}
	
	public boolean test(ServerWorld world) {
		return test.apply(world);
	}
	
	@Override
	public String asString() {
		return name().toLowerCase();
	}
	
}