package de.dafuqs.spectrum.items;

import de.dafuqs.spectrum.api.energy.InkPowered;
import de.dafuqs.spectrum.api.item.InkPoweredPotionFillable;
import de.dafuqs.spectrum.items.food.DrinkItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;

import java.util.List;

public class ConcealingOilsItem extends DrinkItem implements InkPoweredPotionFillable {

    public static final String OIL_EFFECT_ID = "ConcealedOilEffect";
    public static final String EFFECT_KEY = "Effect";
    public static final String POISONER_KEY = "Poisoner";
    public static final int POISONED_COLOUR = 0x3d1125;

    public ConcealingOilsItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (!getEffects(stack).isEmpty())
            tooltip.add(Text.translatable("item.spectrum.concealing_oils.tooltip").styled(s -> s.withFormatting(Formatting.GRAY).withItalic(true)));
        appendPotionFillableTooltip(stack, tooltip, Text.translatable("item.spectrum.concealing_oils.when_poisoned"), true, context.getUpdateTickRate());
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT)
            return false;

        var appliedStack = slot.getStack();

        if (!appliedStack.contains(DataComponentTypes.FOOD))
            return false;

        if (!isFull(stack))
            return false;

        if (tryApplyOil(stack, appliedStack, player)) {
            if (!player.getAbilities().creativeMode)
                stack.decrement(1);
            player.playSound(SoundEvents.ITEM_BOTTLE_EMPTY, 1, 1);
            return true;
        }

        return false;
    }

    private boolean tryApplyOil(ItemStack oil, ItemStack food, PlayerEntity user) {
        if (food.getItem() instanceof DrinkItem)
            return false;
        var nbtComponent = food.get(DataComponentTypes.CUSTOM_DATA);
        if (nbtComponent != null && nbtComponent.contains(OIL_EFFECT_ID))
            return false;

        var effect = getEffects(oil).get(0);
        if (!InkPowered.tryDrainEnergy(user, effect.getInkCost().getColor(), effect.getInkCost().getCost()))
            return false;

        var foodComponent = food.get(DataComponentTypes.FOOD);
        if (foodComponent != null &&
                foodComponent
                .effects()
                .stream()
                .map(FoodComponent.StatusEffectEntry::effect)
                .anyMatch(e -> e.equals(effect.getStatusEffectInstance().getEffectType())))
            return false;

        food.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(nbt -> {
            var compound = new NbtCompound();
            compound.putUuid(POISONER_KEY, user.getUuid());
            compound.put(EFFECT_KEY, effect.getStatusEffectInstance().writeNbt());
            nbt.put(OIL_EFFECT_ID, compound);
        }));
        return true;
    }

    @Override
    public int maxEffectCount() {
        return 1;
    }

    @Override
    public int maxEffectAmplifier() {
        return 3;
    }
}
