package de.dafuqs.spectrum.items.food;

import de.dafuqs.spectrum.api.render.*;
import de.dafuqs.spectrum.items.conditional.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class NectardewBurgeonItem extends CloakedItem implements SlotBackgroundEffectProvider {

    private final Text lore;

    public NectardewBurgeonItem(Settings settings, String lore, Identifier cloakAdvancementIdentifier, Item cloakItem) {
        super(settings, cloakAdvancementIdentifier, cloakItem);
        this.lore = Text.translatable(lore).formatted(Formatting.GRAY);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(lore);
    }

    @Override
    public SlotEffect backgroundType(@Nullable PlayerEntity player, ItemStack stack) {
        return isVisibleTo(player) ? SlotEffect.PULSE : SlotEffect.NONE;
    }

    @Override
    public int getBackgroundColor(@Nullable PlayerEntity player, ItemStack stack, float tickDelta) {
        return isVisibleTo(player) ? SpectrumStatusEffects.ETERNAL_SLUMBER_COLOR : 0x0;
    }
}
