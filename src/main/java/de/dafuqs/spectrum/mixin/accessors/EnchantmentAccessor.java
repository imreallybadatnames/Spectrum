package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.entity.Entity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.function.Consumer;

@Mixin(Enchantment.class)
public interface EnchantmentAccessor {

    @Invoker
    static <T> void invokeApplyEffects(List<EnchantmentEffectEntry<T>> entries, LootContext lootContext, Consumer<T> effectConsumer) {
        throw new AssertionError();
    }

    @Invoker
    static LootContext invokeCreateEnchantedEntityLootContext(ServerWorld world, int level, Entity entity, Vec3d pos) {
        throw new AssertionError();
    }

}
