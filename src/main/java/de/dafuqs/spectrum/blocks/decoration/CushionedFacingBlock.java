package de.dafuqs.spectrum.blocks.decoration;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CushionedFacingBlock extends SpectrumFacingBlock {

    public static final MapCodec<CushionedFacingBlock> CODEC = createCodec(CushionedFacingBlock::new);

    public CushionedFacingBlock(Settings settings) {
        super(settings);
    }

	@Override
	public MapCodec<? extends CushionedFacingBlock> getCodec() {
		return CODEC;
	}

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {}
}
