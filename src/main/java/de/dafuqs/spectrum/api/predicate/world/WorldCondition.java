package de.dafuqs.spectrum.api.predicate.world;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;

public abstract class WorldCondition<S extends WorldConditionType<SC>, SC extends WorldConditionType.Config> {
	
	public static final Codec<WorldCondition<?, ?>> CODEC = SpectrumRegistries.WORLD_CONDITION_TYPE.getCodec().dispatch((condition) -> condition.type, WorldConditionType::getCodec);
	
	protected final S type;
	protected final SC config;
	
	public WorldCondition(S type, SC config) {
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
