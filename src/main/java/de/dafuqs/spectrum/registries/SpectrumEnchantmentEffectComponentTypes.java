package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.item.Item;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;

import java.util.List;
import java.util.function.UnaryOperator;

public class SpectrumEnchantmentEffectComponentTypes {

    public static ComponentType<List<EnchantmentEffectEntry<RegistryEntry<Enchantment>>>> CLOAKED;
    public static ComponentType<RegistryEntryList<Item>> BLACKLISTED_ITEMS;

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, SpectrumCommon.locate(id), builderOperator.apply(ComponentType.builder()).build());
    }

    public static void register() {
        CLOAKED = register("cloaked", builder -> builder.codec(EnchantmentEffectEntry.createCodec(Enchantment.ENTRY_CODEC, LootContextTypes.ENCHANTED_ENTITY).listOf()));
        BLACKLISTED_ITEMS = register("blacklisted_items", builder -> builder.codec(RegistryCodecs.entryList(RegistryKeys.ITEM)));
    }

}
