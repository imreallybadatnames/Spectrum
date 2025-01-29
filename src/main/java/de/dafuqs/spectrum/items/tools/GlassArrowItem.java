package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.entity.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.particle.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

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
		GlassArrowEntity entity = new GlassArrowEntity(world, shooter, stack.copyWithCount(1), shotFrom);
		entity.setVariant(variant);
		return entity;
	}
	
	@Override
	public PersistentProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
		GlassArrowEntity arrowEntity = new GlassArrowEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack.copyWithCount(1), null);
		arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		arrowEntity.setVariant(variant);
		return arrowEntity;
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
