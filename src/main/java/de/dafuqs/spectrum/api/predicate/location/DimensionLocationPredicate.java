package de.dafuqs.spectrum.api.predicate.location;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.predicate.*;
import net.minecraft.registry.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class DimensionLocationPredicate extends SpectrumLocationPredicateType<DimensionLocationPredicate.Config> {
	
	public DimensionLocationPredicate(Codec<Config> codec) {
		super(codec);
	}
	
	@Override
	public boolean test(Config config, ServerWorld world, BlockPos pos) {
		return config.dimensionKeys.contains(world.getRegistryKey());
	}
	
	public static class Config extends SpectrumLocationPredicateType.Config {
		
		public static final Codec<Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				RegistryKey.createCodec(RegistryKeys.WORLD).listOf().fieldOf("dimensions").forGetter((config) -> config.dimensionKeys)
		).apply(instance, Config::new));
		
		public final List<RegistryKey<World>> dimensionKeys;
		
		public Config(List<RegistryKey<World>> dimensionKeys) {
			this.dimensionKeys = dimensionKeys;
		}
		
	}
	
}