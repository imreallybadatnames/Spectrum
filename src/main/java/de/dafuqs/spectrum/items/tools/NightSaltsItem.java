package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.cca.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.client.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class NightSaltsItem extends Item implements SleepAlteringItem {

    private static final MutableText TOOLTIP = Text.translatable("item.spectrum.night_salts.tooltip");

    public NightSaltsItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TOOLTIP.formatted(Formatting.GRAY));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            var component = MiscPlayerDataComponent.get(player);

            component.setSleepTimers(20 * 10, 20 * 10, 0);
            component.setLastSleepItem(this);
			
			player.addStatusEffect(new StatusEffectInstance(SpectrumStatusEffects.CALMING, 20 * 20, 2)); // TODO: this should probs be moved to a food component, so the effect shows up as tooltip
            if (!player.getAbilities().creativeMode)
                stack.decrement(1);
        }
        else {
            user.addStatusEffect(new StatusEffectInstance(SpectrumStatusEffects.SOMNOLENCE, 20 * 15));
            user.sleep(user.getBlockPos());
            stack.decrement(1);
        }

        world.playSoundFromEntity(null, user, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1F, 1.2F);
        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 40;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public SoundEvent getDrinkSound() {
        return SoundEvents.ENTITY_SNIFFER_SCENTING;
    }

    @Override
    public void applyPenalties(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(SpectrumStatusEffects.VULNERABILITY, 20 * 30));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 30));
    }
}
