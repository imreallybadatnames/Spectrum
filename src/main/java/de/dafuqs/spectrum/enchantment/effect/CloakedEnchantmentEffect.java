package de.dafuqs.spectrum.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.*;

public record CloakedEnchantmentEffect(
        Identifier advancementId,
        Identifier enchantmentId
) {

    public static final MapCodec<CloakedEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("advancement").forGetter(CloakedEnchantmentEffect::advancementId),
            Identifier.CODEC.fieldOf("enchantment").forGetter(CloakedEnchantmentEffect::enchantmentId)
    ).apply(instance, CloakedEnchantmentEffect::new));

}
