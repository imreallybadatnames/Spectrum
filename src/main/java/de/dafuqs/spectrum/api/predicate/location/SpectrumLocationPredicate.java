package de.dafuqs.spectrum.api.predicate.location;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.api.predicate.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;

public class SpectrumLocationPredicate<S extends SpectrumLocationPredicateType<SC>, SC extends SpectrumLocationPredicateType.Config> {
	
	public static final Codec<SpectrumLocationPredicate<?, ?>> CODEC = SpectrumRegistries.WORLD_CONDITION_TYPE.getCodec().dispatch((condition) -> condition.type, SpectrumLocationPredicateType::getCodec);
	
	protected final S type;
	protected final SC config;
	
	public SpectrumLocationPredicate(S type, SC config) {
		this.type = type;
		this.config = config;
	}
	
	public SC config() {
		return config;
	}
	
	public boolean test(ServerWorld world, BlockPos pos) {
		return this.type.test(config, world, pos);
	}
	
}
