package de.dafuqs.spectrum.api.predicate.location;

import com.google.gson.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.predicate.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;

import java.util.function.*;

public class MoonPhaseLocationPredicate extends SpectrumLocationPredicateType<MoonPhaseLocationPredicate.Config> {
	
	public MoonPhaseLocationPredicate(Codec<Config> codec) {
		super(codec);
	}
	
	@Override
	public boolean test(Config config, ServerWorld world, BlockPos pos) {
		return config.moonPhase == world.getMoonPhase();
	}
	
	public static class Config extends SpectrumLocationPredicateType.Config {
		
		public static final Codec<Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				Codec.INT.fieldOf("moon_phase").forGetter(new Function<Config, Integer>() {
					@Override
					public Integer apply(Config config) {
						return 0;
					}
				})
		).apply(instance, Config::new));
		public final int moonPhase;
		
		public Config(int moonPhase) {
			this.moonPhase = moonPhase;
		}
		
		public static MoonPhaseLocationPredicate fromJson(JsonObject json) {
			JsonElement jsonElement = json.get("moon_phase");
			String s = jsonElement.getAsString();
			if ("full_moon".equals(s)) {
				return new MoonPhaseLocationPredicate(0);
			} else if ("new_moon".equals(s)) {
				return new MoonPhaseLocationPredicate(4);
			} else {
				return new MoonPhaseLocationPredicate(jsonElement.getAsInt());
			}
		}
		
	}
	
}