package de.dafuqs.spectrum.registries;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.enchantment.effect.SpectrumAttributeEnchantmentEffect;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentLocationBasedEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.List;
import java.util.function.UnaryOperator;

public class SpectrumEnchantmentEffectComponentTypes {

    public static ComponentType<List<SpectrumAttributeEnchantmentEffect>> ATTRIBUTES;

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, SpectrumCommon.locate(id), builderOperator.apply(ComponentType.builder()).build());
    }

    private static <T> ComponentType<T> registerLocationBased(String id, MapCodec<? extends EnchantmentLocationBasedEffect> codec, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        var component = register(id, builderOperator);
        Registry.register(Registries.ENCHANTMENT_LOCATION_BASED_EFFECT_TYPE, SpectrumCommon.locate(id), codec);
        return component;
    }

    public static void register() {
        ATTRIBUTES = registerLocationBased("attributes", SpectrumAttributeEnchantmentEffect.CODEC, builder -> builder.codec(SpectrumAttributeEnchantmentEffect.CODEC.codec().listOf()));
    }

}
