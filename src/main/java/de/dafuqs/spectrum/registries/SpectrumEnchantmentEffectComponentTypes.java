package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.enchantment.effect.CloakedEnchantmentEffect;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.List;
import java.util.function.UnaryOperator;

public class SpectrumEnchantmentEffectComponentTypes {

    public static ComponentType<List<CloakedEnchantmentEffect>> CLOAKED;

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, SpectrumCommon.locate(id), builderOperator.apply(ComponentType.builder()).build());
    }

    public static void register() {
        CLOAKED = register("cloaked", builder -> builder.codec(CloakedEnchantmentEffect.CODEC.codec().listOf()));
    }

}
