package de.dafuqs.spectrum.api.predicate.world;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.registry.*;

public abstract class WorldConditionTypes {
	
	public static final WorldConditionType<?> DIMENSION = register("dimension", new DimensionType(DimensionType.Config.CODEC));
	public static final WorldConditionType<?> MOON_PHASE = register("moon_phase", new MoonPhaseType(MoonPhaseType.Config.CODEC));
	public static final WorldConditionType<?> TIME_OF_DAY = register("time_of_day", new TimeOfDayType(TimeOfDayType.Config.CODEC));
	public static final WorldConditionType<?> WEATHER = register("weather", new WeatherType(WeatherType.Config.CODEC));
	public static final WorldConditionType<?> COMMAND = register("command", new CommandType(CommandType.Config.CODEC));
	
	private static <C extends WorldConditionType.Config, F extends WorldConditionType<C>> F register(String name, F feature) {
		return Registry.register(SpectrumRegistries.WORLD_CONDITION_TYPE, SpectrumCommon.locate(name), feature);
	}
	
}
