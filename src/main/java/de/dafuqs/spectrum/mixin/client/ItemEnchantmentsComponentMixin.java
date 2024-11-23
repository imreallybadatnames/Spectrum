package de.dafuqs.spectrum.mixin.client;

import de.dafuqs.spectrum.registries.SpectrumEnchantmentEffectComponentTypes;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemEnchantmentsComponent.class)
public class ItemEnchantmentsComponentMixin {

    @Redirect(method = "appendTooltip(Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/Object2IntOpenHashMap;getInt(Ljava/lang/Object;)I"))
    public int spectrum$appendTooltip$object2IntEntrySet(Object2IntOpenHashMap<RegistryEntry<Enchantment>> enchantments, RegistryEntry<Enchantment> registryEntry) {
        return registryEntry.value().getEffect(SpectrumEnchantmentEffectComponentTypes.CLOAKED).isEmpty() ? enchantments.getInt(registryEntry) : 0;
    }

}
