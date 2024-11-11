package de.dafuqs.spectrum.blocks.boom;

import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.explosion.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.*;

import java.util.*;

public class ModularExplosionBlockItem extends BlockItem implements ModularExplosionProvider {
	
	private final int maxModifierCount;
	private final double baseBlastRadius;
	private final float baseDamage;
	
	public ModularExplosionBlockItem(Block block, double baseBlastRadius, float baseDamage, int maxModifierCount, Settings settings) {
		super(block, settings);
		this.maxModifierCount = maxModifierCount;
		this.baseBlastRadius = baseBlastRadius;
		this.baseDamage = baseDamage;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		ModularExplosionDefinition.getFromStack(stack).appendTooltip(tooltip, this);
	}
	
	@Override
	public double getBaseExplosionBlastRadius() {
		return baseBlastRadius;
	}
	
	@Override
	public float getBaseExplosionDamage() {
		return baseDamage;
	}
	
	@Override
	public int getMaxExplosionModifiers() {
		return maxModifierCount;
	}
	
}
