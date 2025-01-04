package de.dafuqs.spectrum.api.predicate.entity;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.predicate.location.*;
import net.minecraft.predicate.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;

import java.util.*;

public class EntityFishingPredicate {
	private final Optional<FluidPredicate> fluidPredicate;
	private final Optional<BiomeLocationPredicate> biomeLocationPredicate;
	private final Optional<net.minecraft.predicate.LightPredicate> lightPredicate;
	private final Optional<DimensionLocationPredicate> dimensionPredicate;
	private final Optional<MoonPhaseLocationPredicate> moonPhasePredicate;
	private final Optional<TimeOfDayLocationPredicate> timeOfDayPredicate;
	private final Optional<WeatherLocationPredicate> weatherPredicate;
	private final Optional<CommandLocationPredicate> commandPredicate;
	
	public EntityFishingPredicate(
			Optional<FluidPredicate> fluidPredicate,
			Optional<BiomeLocationPredicate> biomeLocationPredicate,
			Optional<net.minecraft.predicate.LightPredicate> lightPredicate,
			Optional<DimensionLocationPredicate> dimensionPredicate,
			Optional<MoonPhaseLocationPredicate> moonPhasePredicate,
			Optional<TimeOfDayLocationPredicate> timeOfDayPredicate,
			Optional<WeatherLocationPredicate> weatherPredicate,
			Optional<CommandLocationPredicate> commandPredicate)
	{
		this.fluidPredicate = fluidPredicate;
		this.biomeLocationPredicate = biomeLocationPredicate;
		this.lightPredicate = lightPredicate;
		this.dimensionPredicate = dimensionPredicate;
		this.moonPhasePredicate = moonPhasePredicate;
		this.timeOfDayPredicate = timeOfDayPredicate;
		this.weatherPredicate = weatherPredicate;
		this.commandPredicate = commandPredicate;
	}
	
	public static final Codec<EntityFishingPredicate> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			FluidPredicate.CODEC.optionalFieldOf("fluid").forGetter(entityFishingPredicate -> entityFishingPredicate.fluidPredicate),
			BiomeLocationPredicate.CODEC.optionalFieldOf("biome").forGetter(entityFishingPredicate -> entityFishingPredicate.biomeLocationPredicate),
			net.minecraft.predicate.LightPredicate.CODEC.optionalFieldOf("light").forGetter(entityFishingPredicate -> entityFishingPredicate.lightPredicate),
			DimensionLocationPredicate.CODEC.optionalFieldOf("dimension").forGetter(entityFishingPredicate -> entityFishingPredicate.dimensionPredicate),
			MoonPhaseLocationPredicate.CODEC.optionalFieldOf("moon_phase").forGetter(entityFishingPredicate -> entityFishingPredicate.moonPhasePredicate),
			TimeOfDayLocationPredicate.CODEC.optionalFieldOf("time_of_day").forGetter(entityFishingPredicate -> entityFishingPredicate.timeOfDayPredicate),
			WeatherLocationPredicate.CODEC.optionalFieldOf("weather").forGetter(entityFishingPredicate -> entityFishingPredicate.weatherPredicate),
			CommandLocationPredicate.CODEC.optionalFieldOf("command").forGetter(entityFishingPredicate -> entityFishingPredicate.commandPredicate)
	).apply(instance, EntityFishingPredicate::new));
	
	public boolean test(ServerWorld world, BlockPos pos) {
		return (this.fluidPredicate.isEmpty() || this.fluidPredicate.get().test(world, pos))
				&& (this.biomeLocationPredicate.isEmpty() || this.biomeLocationPredicate.get().test(world, pos))
				&& (this.lightPredicate.isEmpty() || this.lightPredicate.get().test(world, pos))
				&& (this.dimensionPredicate.isEmpty() || this.dimensionPredicate.get().test(world, pos))
				&& (this.moonPhasePredicate.isEmpty() || this.moonPhasePredicate.get().test(world, pos))
				&& (this.timeOfDayPredicate.isEmpty() || this.timeOfDayPredicate.get().test(world, pos))
				&& (this.weatherPredicate.isEmpty() || this.weatherPredicate.get().test(world, pos))
				&& (this.commandPredicate.isEmpty() || this.commandPredicate.get().test(world, pos));
	}
}
