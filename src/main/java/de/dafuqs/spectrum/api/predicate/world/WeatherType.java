package de.dafuqs.spectrum.api.predicate.world;

import com.google.gson.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;

import java.util.*;

public class WeatherType extends WorldConditionType<WeatherType.Config> {
	
	public WeatherType(Codec<Config> codec) {
		super(codec);
	}
	
	public enum WeatherCondition {
		CLEAR_SKY,
		RAIN, // rain or thunder
		STRICT_RAIN, // rain without thunder
		THUNDER,
		NOT_THUNDER
	}
	
	@Override
	public boolean test(Config config, ServerWorld world, BlockPos pos) {
		switch (config.weatherCondition) {
			case CLEAR_SKY -> {
				return !world.isRaining();
			}
			case RAIN -> {
				return world.isRaining();
			}
			case STRICT_RAIN -> {
				return world.isRaining() && !world.isThundering();
			}
			case THUNDER -> {
				return world.isThundering();
			}
			case NOT_THUNDER -> {
				return !world.isThundering();
			}
		}
		return true;
	}
	
	public static class Config extends WorldConditionType.Config {
		
		public static WeatherType fromJson(JsonObject json) {
			return new WeatherType(WeatherCondition.valueOf(json.get("weather_condition").getAsString().toUpperCase(Locale.ROOT)));
		}
		
		public static final Codec<Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
		
		).apply(instance, Config::new));
		public final WeatherCondition weatherCondition;
		
		public Config(WeatherCondition weatherCondition) {
			this.weatherCondition = weatherCondition;
		}
		
	}
	
}