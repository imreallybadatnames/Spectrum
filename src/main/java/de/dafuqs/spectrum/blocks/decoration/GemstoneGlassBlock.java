package de.dafuqs.spectrum.blocks.decoration;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.api.item.*;
import net.fabricmc.api.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public class GemstoneGlassBlock extends TransparentBlock {

	public final MapCodec<GemstoneGlassBlock> codec;

	@Nullable
	final GemstoneColor gemstoneColor;
	
	public GemstoneGlassBlock(Settings settings, @Nullable GemstoneColor gemstoneColor) {
		super(settings);
		this.gemstoneColor = gemstoneColor;
		this.codec = RecordCodecBuilder.mapCodec(i -> i.group(
				createSettingsCodec(),
				gemstoneColor.getCodec().fieldOf("color").forGetter(b -> b.gemstoneColor)
		).apply(i, GemstoneGlassBlock::new));
	}

	@Override
	public MapCodec<? extends GemstoneGlassBlock> getCodec() {
		return codec;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
		if (stateFrom.isOf(this)) {
			return true;
		}
		
		if (state.getBlock() instanceof GemstoneGlassBlock sourceGemstoneGlassBlock && stateFrom.getBlock() instanceof GemstoneGlassBlock targetGemstoneGlassBlock) {
			return sourceGemstoneGlassBlock.gemstoneColor == targetGemstoneGlassBlock.gemstoneColor;
		}
		return super.isSideInvisible(state, stateFrom, direction);
	}
	
	@Override
	public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}
	
}
