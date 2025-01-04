package de.dafuqs.spectrum.api.predicate;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.predicate.location.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.registry.*;

public abstract class SpectrumLocationPredicateTypes {
	
	public static final SpectrumLocationPredicateType<?> DIMENSION = register("dimension", new DimensionLocationPredicate(DimensionLocationPredicate.Config.CODEC));
	public static final SpectrumLocationPredicateType<?> MOON_PHASE = register("moon_phase", new MoonPhaseLocationPredicate(MoonPhaseLocationPredicate.Config.CODEC));
	public static final SpectrumLocationPredicateType<?> TIME_OF_DAY = register("time_of_day", new TimeOfDayLocationPredicate(TimeOfDayLocationPredicate.Config.CODEC));
	public static final SpectrumLocationPredicateType<?> WEATHER = register("weather", new WeatherLocationPredicate(WeatherLocationPredicate.Config.CODEC));
	public static final SpectrumLocationPredicateType<?> COMMAND = register("command", new CommandLocationPredicate(CommandLocationPredicate.Config.CODEC));
	public static final SpectrumLocationPredicateType<?> BIOME = register("biome", new BiomeLocationPredicate(BiomeLocationPredicate.Config.CODEC));
	
	private static <C extends SpectrumLocationPredicateType.Config, F extends SpectrumLocationPredicateType<C>> F register(String name, F feature) {
		return Registry.register(SpectrumRegistries.WORLD_CONDITION_TYPE, SpectrumCommon.locate(name), feature);
	}
	
	public static void register() {
	
	}
	
}
