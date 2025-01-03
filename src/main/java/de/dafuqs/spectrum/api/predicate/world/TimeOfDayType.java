package de.dafuqs.spectrum.api.predicate.world;

import com.google.gson.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.helpers.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;

import java.util.*;

public class TimeOfDayType extends WorldConditionType<TimeOfDayType.Config> {
	
	public TimeOfDayType(Codec<Config> codec) {
		super(codec);
	}
	
	@Override
	public boolean test(Config config, ServerWorld world, BlockPos pos) {
		TimeHelper.TimeOfDay worldTimeOfDay = TimeHelper.getTimeOfDay(world);
		switch (config.timeOfDay) {
			case DAY -> {
				return worldTimeOfDay.isDay();
			}
			case NIGHT -> {
				return worldTimeOfDay.isNight();
			}
			default -> {
				return config.timeOfDay == worldTimeOfDay;
			}
		}
	}
	
	public static class Config extends WorldConditionType.Config {
		
		public static TimeOfDayType fromJson(JsonObject json) {
			if (json == null || json.isJsonNull()) return ANY;
			return new TimeOfDayType(TimeHelper.TimeOfDay.valueOf(json.get("time").getAsString().toUpperCase(Locale.ROOT)));
		}
		
		public static final Codec<Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
		
		).apply(instance, Config::new));
		
		public final TimeHelper.TimeOfDay timeOfDay;
		
		public Config(TimeHelper.TimeOfDay timeOfDay) {
			this.timeOfDay = timeOfDay;
		}
		
	}
	
}