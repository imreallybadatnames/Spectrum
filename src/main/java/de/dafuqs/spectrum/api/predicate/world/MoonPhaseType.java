package de.dafuqs.spectrum.api.predicate.world;

import com.google.gson.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;

public class MoonPhaseType extends WorldConditionType<MoonPhaseType.Config> {
	
	public MoonPhaseType(Codec<Config> codec) {
		super(codec);
	}
	
	@Override
	public boolean test(Config config, ServerWorld world, BlockPos pos) {
		return config.moonPhase == world.getMoonPhase();
	}
	
	public static class Config extends WorldConditionType.Config {
		
		public static final Codec<Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
		
		).apply(instance, Config::new));
		public final int moonPhase;
		
		public Config(int moonPhase) {
			this.moonPhase = moonPhase;
		}
		
		public static MoonPhaseType fromJson(JsonObject json) {
			JsonElement jsonElement = json.get("moon_phase");
			String s = jsonElement.getAsString();
			if ("full_moon".equals(s)) {
				return new MoonPhaseType(0);
			} else if ("new_moon".equals(s)) {
				return new MoonPhaseType(4);
			} else {
				return new MoonPhaseType(jsonElement.getAsInt());
			}
		}
		
	}
	
}