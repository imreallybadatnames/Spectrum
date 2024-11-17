package de.dafuqs.spectrum.enchantment.effect;

import com.google.common.collect.HashMultimap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentLocationBasedEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Vec3d;

public record SpectrumAttributeEnchantmentEffect(
        Identifier id,
        RegistryEntry<EntityAttribute> attribute,
        EnchantmentLevelBasedValue amount,
        EntityAttributeModifier.Operation operation,
        Identifier requiredAdvancementId
) implements EnchantmentLocationBasedEffect {
    public static final MapCodec<SpectrumAttributeEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Identifier.CODEC.fieldOf("id").forGetter(SpectrumAttributeEnchantmentEffect::id),
                            EntityAttribute.CODEC.fieldOf("attribute").forGetter(SpectrumAttributeEnchantmentEffect::attribute),
                            EnchantmentLevelBasedValue.CODEC.fieldOf("amount").forGetter(SpectrumAttributeEnchantmentEffect::amount),
                            EntityAttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(SpectrumAttributeEnchantmentEffect::operation),
                            Identifier.CODEC.fieldOf("required_advancement").forGetter(SpectrumAttributeEnchantmentEffect::requiredAdvancementId)
                    )
                    .apply(instance, SpectrumAttributeEnchantmentEffect::new)
    );

    private Identifier getModifierId(StringIdentifiable suffix) {
        return this.id.withSuffixedPath("/" + suffix.asString());
    }

    public EntityAttributeModifier createAttributeModifier(int value, StringIdentifiable suffix) {
        return new EntityAttributeModifier(this.getModifierId(suffix), this.amount().getValue(value), this.operation());
    }

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos, boolean newlyApplied) {
        if (user instanceof PlayerEntity playerEntity && AdvancementHelper.hasAdvancement(playerEntity, requiredAdvancementId))
            return;
        if (newlyApplied && user instanceof LivingEntity livingEntity) {
            livingEntity.getAttributes().addTemporaryModifiers(this.getModifiers(level, context.slot()));
        }
    }

    @Override
    public void remove(EnchantmentEffectContext context, Entity user, Vec3d pos, int level) {
        if (user instanceof LivingEntity livingEntity) {
            livingEntity.getAttributes().removeModifiers(this.getModifiers(level, context.slot()));
        }
    }

    private HashMultimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(int level, EquipmentSlot slot) {
        HashMultimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> hashMultimap = HashMultimap.create();
        hashMultimap.put(this.attribute, this.createAttributeModifier(level, slot));
        return hashMultimap;
    }

    @Override
    public MapCodec<SpectrumAttributeEnchantmentEffect> getCodec() {
        return CODEC;
    }
}
