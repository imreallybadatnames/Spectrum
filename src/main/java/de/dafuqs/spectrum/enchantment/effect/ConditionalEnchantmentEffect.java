package de.dafuqs.spectrum.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.*;

public record ConditionalEnchantmentEffect(
        Identifier advancementId,
        Identifier enchantmentId
) {

    public static final MapCodec<ConditionalEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("advancement").forGetter(ConditionalEnchantmentEffect::advancementId),
            Identifier.CODEC.fieldOf("enchantment").forGetter(ConditionalEnchantmentEffect::enchantmentId)
    ).apply(instance, ConditionalEnchantmentEffect::new));

}
