package de.dafuqs.spectrum.api.predicate.world;

import com.mojang.serialization.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;

public abstract class WorldConditionType<SC extends WorldConditionType.Config> {
	
	private final MapCodec<WorldCondition<WorldConditionType<SC>, SC>> codec;
	
	public WorldConditionType(Codec<SC> configCodec) {
		this.codec = configCodec.fieldOf("config").xmap((config) -> new WorldCondition<>(this, config), WorldCondition::config);
	}
	
	public MapCodec<WorldCondition<WorldConditionType<SC>, SC>> getCodec() {
		return this.codec;
	}
	
	public abstract boolean test(SC config, ServerWorld world, BlockPos pos);
	
	public static class Config {
	}
	
}
