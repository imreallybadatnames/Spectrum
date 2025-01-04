package de.dafuqs.spectrum.api.predicate.location;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.predicate.*;
import net.minecraft.command.argument.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.biome.*;

public class BiomeLocationPredicate extends SpectrumLocationPredicateType<BiomeLocationPredicate.Config> {
	
	public static final Codec<BiomeLocationPredicate> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
	
	).apply(instance, BiomeLocationPredicate::new));
	
	public BiomeLocationPredicate(Codec<Config> configCodec) {
		super(configCodec);
	}
	
	public static class Config extends SpectrumLocationPredicateType.Config {
		
		RegistryEntryPredicateArgumentType.EntryPredicate<Biome> biome;
		
		public static final Codec<BiomeLocationPredicate.Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
		
		).apply(instance, Config::new));
		
		public Config(RegistryEntryPredicateArgumentType.EntryPredicate<Biome> biome) {
			this.biome = biome;
		}
		
	}
	
	@Override
	public boolean test(Config config, ServerWorld world, BlockPos pos) {
		return config.biome.test(world.getBiome(pos));
	}
    
}
