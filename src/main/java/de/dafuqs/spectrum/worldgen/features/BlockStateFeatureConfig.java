package de.dafuqs.spectrum.worldgen.features;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.helpers.*;
import net.minecraft.block.*;
import net.minecraft.world.gen.feature.*;

public record BlockStateFeatureConfig(BlockState blockState) implements FeatureConfig {

    public static final Codec<BlockStateFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			CodecHelper.BLOCK_STATE.fieldOf("state").forGetter((config) -> config.blockState)
    ).apply(instance, BlockStateFeatureConfig::new));

}
