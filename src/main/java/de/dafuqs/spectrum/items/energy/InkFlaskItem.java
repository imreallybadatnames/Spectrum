package de.dafuqs.spectrum.items.energy;

import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.energy.storage.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.api.render.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class InkFlaskItem extends Item implements InkStorageItem<SingleInkStorage>, LoomPatternProvider, ExtendedItemBarProvider {
	
	private final long maxEnergy;
	
	public InkFlaskItem(Settings settings, long maxEnergy) {
		super(settings);
		this.maxEnergy = maxEnergy;
	}
	
	@Override
	public Drainability getDrainability() {
		return Drainability.ALWAYS;
	}
	
	@Override
	public SingleInkStorage getEnergyStorage(ItemStack itemStack) {
		var storage = itemStack.get(SpectrumDataComponentTypes.INK_STORAGE);
		if (storage != null)
			for (var entry : storage.storedEnergy().entrySet())
				return new SingleInkStorage(storage.maxEnergyTotal(), entry.getKey(), entry.getValue());
		return new SingleInkStorage(this.maxEnergy);
	}
	
	// Omitting this would crash outside the dev env o.O
	@Override
	public ItemStack getDefaultStack() {
		return super.getDefaultStack();
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		getEnergyStorage(stack).addTooltip(tooltip);
		addBannerPatternProviderTooltip(tooltip);
	}
	
	public ItemStack getFullStack(InkColor color) {
		ItemStack stack = this.getDefaultStack();
		SingleInkStorage storage = getEnergyStorage(stack);
		storage.fillCompletely();
		storage.convertColor(color);
		setEnergyStorage(stack, storage);
		return stack;
	}
	
	@Override
	public RegistryKey<BannerPattern> getPattern() {
		return SpectrumBannerPatterns.INK_FLASK;
	}
	
	@Override
	public int barCount(ItemStack stack) {
		return 1;
	}
	
	@Override
	public BarSignature getSignature(@Nullable PlayerEntity player, @NotNull ItemStack stack, int index) {
		var storage = getEnergyStorage(stack);
		
		if (storage.isEmpty())
			return ExtendedItemBarProvider.PASS;
		
		var color = storage.getStoredColor();
		var progress = Support.getSensiblePercent(storage.getCurrentTotal(), storage.getMaxTotal(), 14);
		return new BarSignature(1, 13, 14, progress, 1, color.getColorInt(), 2, ExtendedItemBarProvider.DEFAULT_BACKGROUND_COLOR);
	}
}
