package de.dafuqs.spectrum.blocks.conditional;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.mixin.accessors.ExperienceDroppingBlockAccessor;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.util.math.intprovider.*;

public class GemstoneOreBlock extends CloakedOreBlock {

	public final MapCodec<GemstoneOreBlock> codec;
	private final GemstoneColor gemstoneColor;
	
	public GemstoneOreBlock(IntProvider experienceDropped, Settings settings, GemstoneColor gemstoneColor, Identifier cloakAdvancementIdentifier, BlockState cloakBlockState) {
		super(experienceDropped, settings, cloakAdvancementIdentifier, cloakBlockState);
		this.gemstoneColor = gemstoneColor;
		this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
				IntProvider.createValidatingCodec(0, 10).fieldOf("experience").forGetter(b -> ((ExperienceDroppingBlockAccessor) b).getExperienceDropped()),
				createSettingsCodec(),
				gemstoneColor.getCodec().fieldOf("color").forGetter(b -> b.gemstoneColor),
				Identifier.CODEC.fieldOf("advancement").forGetter(CloakedOreBlock::getCloakAdvancementIdentifier),
				BlockState.CODEC.fieldOf("cloak").forGetter(b -> b.getBlockStateCloaks().get(b.getDefaultState()))
		).apply(instance, GemstoneOreBlock::new));
	}

	@Override
	public MapCodec<? extends GemstoneOreBlock> getCodec() {
		return codec;
	}

}
