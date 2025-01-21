package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.render.*;
import de.dafuqs.spectrum.component_type.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class GlassCrestWorkstaffItem extends WorkstaffItem implements SlotBackgroundEffectProvider {
    
    public static final int COOLDOWN_DURATION_TICKS = 10;
    public static final InkCost PROJECTILE_COST = new InkCost(InkColors.WHITE, 50); // TODO: make pricier once ink networking is in
    
    public GlassCrestWorkstaffItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    
    public static boolean canShoot(ItemStack stack) {
		return stack.getOrDefault(SpectrumDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT).canShoot();
    }
    
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        TypedActionResult<ItemStack> result = super.use(world, user, hand);
        if (!result.getResult().isAccepted()) {
            ItemStack stack = user.getStackInHand(hand);
            if (canShoot(stack) && InkPowered.tryDrainEnergy(user, PROJECTILE_COST)) {
                user.getItemCooldownManager().set(this, COOLDOWN_DURATION_TICKS);
                if (!world.isClient) {
                    user.playSoundToPlayer(SpectrumSoundEvents.LIGHT_CRYSTAL_RING, SoundCategory.PLAYERS, 0.5F, 0.75F + user.getRandom().nextFloat());
                    MiningProjectileEntity.shoot(world, user, user.getStackInHand(hand));
                }
                stack.damage(2, user, EquipmentSlot.MAINHAND);
                
                return TypedActionResult.consume(stack);
            } else {
                return TypedActionResult.fail(stack);
            }
        }
        return result;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
    
        if (canShoot(stack)) {
            tooltip.add(Text.translatable("item.spectrum.workstaff.tooltip.projectile").formatted(Formatting.GRAY));
        } else {
            tooltip.add(Text.translatable("item.spectrum.workstaff.tooltip.projectiles_disabled").formatted(Formatting.DARK_RED));
        }
    }
	
	@Override
	public SlotBackgroundEffectProvider.SlotEffect backgroundType(@Nullable PlayerEntity player, ItemStack stack) {
		var usable = InkPowered.hasAvailableInk(player, PROJECTILE_COST);
		return usable ? SlotBackgroundEffectProvider.SlotEffect.BORDER_FADE : SlotBackgroundEffectProvider.SlotEffect.NONE;
	}
	
	@Override
	public int getBackgroundColor(@Nullable PlayerEntity player, ItemStack stack, float tickDelta) {
		if (player != null) {
			var lookup = player.getWorld().getRegistryManager();
			var resonance = SpectrumEnchantmentHelper.getLevel(lookup, SpectrumEnchantments.RESONANCE, stack) > 0;
			var silkTouch = SpectrumEnchantmentHelper.getLevel(lookup, Enchantments.SILK_TOUCH, stack) > 0;
			var fortune = SpectrumEnchantmentHelper.getLevel(lookup, Enchantments.FORTUNE, stack) > 0;
			
			if (resonance)
				return InkColors.WHITE_COLOR;
			
			if (silkTouch)
				return InkColors.CYAN_COLOR;
			
			if (fortune)
				return InkColors.LIGHT_BLUE_COLOR;
		}
		
		return InkColors.LIGHT_GRAY_COLOR;
	}
}
