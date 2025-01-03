package de.dafuqs.spectrum.api.predicate.entity;

import com.google.gson.*;
import de.dafuqs.spectrum.api.predicate.block.LightPredicate;
import de.dafuqs.spectrum.api.predicate.block.*;
import de.dafuqs.spectrum.api.predicate.world.*;
import net.minecraft.predicate.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

public class EntityFishingPredicate {
	private final FluidPredicate fluidPredicate;
	private final BiomePredicate biomePredicate;
	private final LightPredicate lightPredicate;
	private final DimensionType dimensionPredicate;
	private final MoonPhaseType moonPhasePredicate;
	private final TimeOfDayType timeOfDayPredicate;
	private final WeatherType weatherPredicate;
	private final CommandType commandPredicate;
	
	public EntityFishingPredicate(
		FluidPredicate fluidPredicate,
		BiomePredicate biomePredicate,
		LightPredicate lightPredicate,
		DimensionType dimensionPredicate,
		MoonPhaseType moonPhasePredicate,
		TimeOfDayType timeOfDayPredicate,
		WeatherType weatherPredicate,
		CommandType commandPredicate)
	{
		this.fluidPredicate = fluidPredicate;
		this.biomePredicate = biomePredicate;
		this.lightPredicate = lightPredicate;
		this.dimensionPredicate = dimensionPredicate;
		this.moonPhasePredicate = moonPhasePredicate;
		this.timeOfDayPredicate = timeOfDayPredicate;
		this.weatherPredicate = weatherPredicate;
		this.commandPredicate = commandPredicate;
	}
	
	public static EntityFishingPredicate fromJson(JsonObject jsonObject) {
		return new EntityFishingPredicate(
				FluidPredicate.fromJson(JsonHelper.getObject(jsonObject, "fluid", null)),
				BiomePredicate.fromJson(JsonHelper.getObject(jsonObject, "biome", null)),
				LightPredicate.fromJson(JsonHelper.getObject(jsonObject, "light", null)),
				DimensionType.fromJson(JsonHelper.getObject(jsonObject, "dimension", null)),
				MoonPhaseType.fromJson(JsonHelper.getObject(jsonObject, "moon_phase", null)),
				TimeOfDayType.fromJson(JsonHelper.getObject(jsonObject, "time_of_day", null)),
				WeatherType.fromJson(JsonHelper.getObject(jsonObject, "weather", null)),
				CommandType.fromJson(JsonHelper.getObject(jsonObject, "command", null))
		);
	}
	
	public boolean test(ServerWorld world, BlockPos pos) {
		return (this.fluidPredicate.test(world, pos) &&
				this.biomePredicate.test(world, pos) &&
				this.lightPredicate.test(world, pos) &&
				this.dimensionPredicate.test(world, pos) &&
				this.moonPhasePredicate.test(world, pos) &&
				this.timeOfDayPredicate.test(world, pos) &&
				this.weatherPredicate.test(world, pos) &&
				this.commandPredicate.test(world, pos));
	}
}
