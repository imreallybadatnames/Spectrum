package de.dafuqs.spectrum.blocks.conditional.amaranth;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.revelationary.api.revelations.*;
import de.dafuqs.spectrum.registries.*;
import de.dafuqs.spectrum.registries.client.*;
import net.minecraft.block.*;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.effect.*;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class AmaranthBushelBlock extends FlowerBlock implements RevelationAware {

	public static final MapCodec<AmaranthBushelBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			STEW_EFFECT_CODEC.forGetter(FlowerBlock::getStewEffects),
			createSettingsCodec()
	).apply(instance, AmaranthBushelBlock::new));

	public AmaranthBushelBlock(RegistryEntry<StatusEffect> stewEffect, float effectLengthInSeconds, AbstractBlock.Settings settings) {
		this(createStewEffectList(stewEffect, effectLengthInSeconds), settings);
	}

	public AmaranthBushelBlock(SuspiciousStewEffectsComponent stewEffects, AbstractBlock.Settings settings) {
		super(stewEffects, settings);
		RevelationAware.register(this);
	}

	@Override
	public MapCodec<? extends AmaranthBushelBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	public Identifier getCloakAdvancementIdentifier() {
		return SpectrumAdvancements.REVEAL_AMARANTH;
	}
	
	@Override
	public Map<BlockState, BlockState> getBlockStateCloaks() {
		Map<BlockState, BlockState> map = new Hashtable<>();
		map.put(this.getDefaultState(), Blocks.FERN.getDefaultState());
		return map;
	}
	
	@Override
	public @Nullable Pair<Item, Item> getItemCloak() {
		return new Pair<>(this.asItem(), Blocks.FERN.asItem());
	}
	
	@Override
	public void onUncloak() {
		if (SpectrumColorProviders.amaranthBushelBlockColorProvider != null && SpectrumColorProviders.amaranthBushelItemColorProvider != null) {
			SpectrumColorProviders.amaranthBushelBlockColorProvider.setShouldApply(false);
			SpectrumColorProviders.amaranthBushelItemColorProvider.setShouldApply(false);
		}
	}
	
	@Override
	public void onCloak() {
		if (SpectrumColorProviders.amaranthBushelBlockColorProvider != null && SpectrumColorProviders.amaranthBushelItemColorProvider != null) {
			SpectrumColorProviders.amaranthBushelBlockColorProvider.setShouldApply(true);
			SpectrumColorProviders.amaranthBushelItemColorProvider.setShouldApply(true);
		}
	}

}
