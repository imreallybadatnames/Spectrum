package de.dafuqs.spectrum.api.predicate.location;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.structure.*;

import java.util.*;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public record WorldConditionsPredicate(
		Optional<MoonPhasePredicate> moonPhase,
		Optional<TimeOfDayPredicate> timeOfDay,
		Optional<WeatherPredicate> weather,
		Optional<CommandPredicate> command,
		LocationPredicate location
) {
	
	public WorldConditionsPredicate(
			Optional<MoonPhasePredicate> moonPhase,
			Optional<TimeOfDayPredicate> timeOfDay,
			Optional<WeatherPredicate> weather,
			Optional<CommandPredicate> command,
			Optional<RegistryEntryList<Biome>> biomes,
			Optional<RegistryEntryList<Structure>> structures,
			Optional<RegistryKey<World>> dimension,
			Optional<LightPredicate> light,
			Optional<BlockPredicate> block,
			Optional<FluidPredicate> fluid,
			Optional<Boolean> smokey,
			Optional<Boolean> canSeeSky
	) {
		this(moonPhase, timeOfDay, weather, command, new LocationPredicate(Optional.empty(), biomes, structures, dimension, smokey, light, block, fluid, canSeeSky));
	}
	
	public static final Codec<WorldConditionsPredicate> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			MoonPhasePredicate.CODEC.optionalFieldOf("moon_phase").forGetter(p -> p.moonPhase),
			TimeOfDayPredicate.CODEC.optionalFieldOf("time_of_day").forGetter(p -> p.timeOfDay),
			WeatherPredicate.CODEC.optionalFieldOf("weather").forGetter(p -> p.weather),
			CommandPredicate.CODEC.optionalFieldOf("command").forGetter(p -> p.command),
			RegistryCodecs.entryList(RegistryKeys.BIOME).optionalFieldOf("biomes").forGetter(p -> p.location.biomes()),
			RegistryCodecs.entryList(RegistryKeys.STRUCTURE).optionalFieldOf("structures").forGetter(p -> p.location.structures()),
			RegistryKey.createCodec(RegistryKeys.WORLD).optionalFieldOf("dimension").forGetter(p -> p.location.dimension()),
			LightPredicate.CODEC.optionalFieldOf("light").forGetter(p -> p.location.light()),
			BlockPredicate.CODEC.optionalFieldOf("block").forGetter(p -> p.location.block()),
			FluidPredicate.CODEC.optionalFieldOf("fluid").forGetter(p -> p.location.fluid()),
			Codec.BOOL.optionalFieldOf("smokey").forGetter(p -> p.location.smokey()),
			Codec.BOOL.optionalFieldOf("can_see_sky").forGetter(p -> p.location.canSeeSky())
	).apply(instance, WorldConditionsPredicate::new));
	
	public boolean test(ServerWorld world, BlockPos pos) {
		return location.test(world, pos.getX(), pos.getY(), pos.getZ())
				&& moonPhase.map(p -> p.test(world)).orElse(true)
				&& timeOfDay.map(p -> p.test(world)).orElse(true)
				&& weather.map(p -> p.test(world)).orElse(true)
				&& command.map(p -> p.test(world, pos)).orElse(true);
	}
}
