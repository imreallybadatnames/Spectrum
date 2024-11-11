package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.Map;
import java.util.Optional;

public class SpectrumSaplingGenerators {

    private static final Map<String, SaplingGenerator> GENERATORS = new Object2ObjectArrayMap<>();

    public static final SaplingGenerator WHITE_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/white");
    public static final SaplingGenerator ORANGE_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/orange");
    public static final SaplingGenerator MAGENTA_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/magenta");
    public static final SaplingGenerator LIGHT_BLUE_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/light_blue");
    public static final SaplingGenerator YELLOW_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/yellow");
    public static final SaplingGenerator LIME_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/lime");
    public static final SaplingGenerator PINK_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/pink");
    public static final SaplingGenerator GRAY_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/gray");
    public static final SaplingGenerator LIGHT_GRAY_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/light_gray");
    public static final SaplingGenerator CYAN_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/cyan");
    public static final SaplingGenerator PURPLE_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/purple");
    public static final SaplingGenerator BLUE_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/blue");
    public static final SaplingGenerator BROWN_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/brown");
    public static final SaplingGenerator GREEN_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/green");
    public static final SaplingGenerator RED_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/red");
    public static final SaplingGenerator BLACK_COLORED_SAPLING_GENERATOR = registerRegular("colored_trees/black");

    public static final SaplingGenerator WEEPING_GALA_SAPLING_GENERATOR = registerRegular("weeping_gala");

    public static SaplingGenerator registerRegular(String id) {
        var generator = new SaplingGenerator(id, Optional.empty(), Optional.of(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, SpectrumCommon.locate(id))), Optional.empty());
        GENERATORS.put(id, generator);
        return generator;
    }

    public static SaplingGenerator get(String id) {
        return GENERATORS.get(id);
    }

}
