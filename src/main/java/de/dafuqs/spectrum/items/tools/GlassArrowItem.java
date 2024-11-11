package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.entity.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class GlassArrowItem extends ArrowItem {
	
	public final GlassArrowVariant variant;
	
	public GlassArrowItem(Item.Settings settings, GlassArrowVariant variant, ParticleEffect particleEffect) {
		super(settings);
		this.variant = variant;
		variant.setData(this, particleEffect);
	}
	
	@Override
	public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
		GlassArrowEntity entity = new GlassArrowEntity(world, shooter);
		entity.setVariant(variant);
		return entity;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		
		
		tooltip.add(Text.translatable("item.spectrum.glass_arrow.tooltip").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.glass_arrow.tooltip2").formatted(Formatting.GRAY));
		if (variant != GlassArrowVariant.MALACHITE) {
			tooltip.add(Text.translatable(getTranslationKey() + ".tooltip").formatted(Formatting.GRAY));
		}
	}
	
}
