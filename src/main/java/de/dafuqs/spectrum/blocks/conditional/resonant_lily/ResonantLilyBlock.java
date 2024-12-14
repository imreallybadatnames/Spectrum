package de.dafuqs.spectrum.blocks.conditional.resonant_lily;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.revelationary.api.revelations.*;
import de.dafuqs.spectrum.*;
import net.minecraft.block.*;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.effect.*;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.*;

import java.util.*;

public class ResonantLilyBlock extends FlowerBlock implements RevelationAware {
	
	public static final Identifier ADVANCEMENT_IDENTIFIER = SpectrumCommon.locate("midgame/collect_resonant_lily");

	public static final MapCodec<ResonantLilyBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			STEW_EFFECT_CODEC.forGetter(FlowerBlock::getStewEffects),
			createSettingsCodec()
	).apply(instance, ResonantLilyBlock::new));

	public ResonantLilyBlock(RegistryEntry<StatusEffect> stewEffect, float effectLengthInSeconds, AbstractBlock.Settings settings) {
		this(createStewEffectList(stewEffect, effectLengthInSeconds), settings);
	}

	public ResonantLilyBlock(SuspiciousStewEffectsComponent stewEffects, AbstractBlock.Settings settings) {
		super(stewEffects, settings);
		RevelationAware.register(this);
	}

	@Override
	public MapCodec<? extends ResonantLilyBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	public Identifier getCloakAdvancementIdentifier() {
		return ADVANCEMENT_IDENTIFIER;
	}
	
	@Override
	public Map<BlockState, BlockState> getBlockStateCloaks() {
		return Map.of(this.getDefaultState(), Blocks.WHITE_TULIP.getDefaultState());
	}
	
	@Override
	public Pair<Item, Item> getItemCloak() {
		return new Pair<>(this.asItem(), Items.WHITE_TULIP);
	}
	
}
