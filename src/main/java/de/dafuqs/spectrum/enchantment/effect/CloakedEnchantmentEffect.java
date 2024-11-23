package de.dafuqs.spectrum.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.*;

public record CloakedEnchantmentEffect(
        Identifier advancementId,
        RegistryEntry<Enchantment> enchantment
) {

    public static final MapCodec<CloakedEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("advancement").forGetter(CloakedEnchantmentEffect::advancementId),
            Enchantment.ENTRY_CODEC.fieldOf("enchantment").forGetter(CloakedEnchantmentEffect::enchantment)
    ).apply(instance, CloakedEnchantmentEffect::new));

}
