package de.dafuqs.spectrum.blocks.conditional;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.api.item.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.util.math.intprovider.*;

public class GemstoneOreBlock extends CloakedOreBlock {
	
	private final GemstoneColor gemstoneColor;
	
	public GemstoneOreBlock(UniformIntProvider experienceDropped, Settings settings, GemstoneColor gemstoneColor, Identifier cloakAdvancementIdentifier, BlockState cloakBlockState) {
		super(experienceDropped, settings, cloakAdvancementIdentifier, cloakBlockState);
		this.gemstoneColor = gemstoneColor;
	}

	@Override
	public MapCodec<? extends GemstoneOreBlock> getCodec() {
		//TODO: Make the codec
		return null;
	}
	
	public GemstoneColor getGemstoneColor() {
		return gemstoneColor;
	}
	
}
