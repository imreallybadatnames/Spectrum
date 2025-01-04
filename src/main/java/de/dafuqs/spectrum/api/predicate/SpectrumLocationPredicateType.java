package de.dafuqs.spectrum.api.predicate;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.api.predicate.location.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;

public abstract class SpectrumLocationPredicateType<SC extends SpectrumLocationPredicateType.Config> {
	
	private final MapCodec<SpectrumLocationPredicate<SpectrumLocationPredicateType<SC>, SC>> codec;
	
	public SpectrumLocationPredicateType(Codec<SC> configCodec) {
		this.codec = configCodec.fieldOf("config").xmap((config) -> new SpectrumLocationPredicate<>(this, config), SpectrumLocationPredicate::config);
	}
	
	public MapCodec<SpectrumLocationPredicate<SpectrumLocationPredicateType<SC>, SC>> getCodec() {
		return this.codec;
	}
	
	public abstract boolean test(SC config, ServerWorld world, BlockPos pos);
	
	public static class Config {
	}
	
}
