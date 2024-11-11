package de.dafuqs.spectrum.blocks.decoration;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class CushionedCarpetBlock extends CarpetBlock {

    public static final MapCodec<CushionedCarpetBlock> CODEC = createCodec(CushionedCarpetBlock::new);

    public CushionedCarpetBlock(Settings settings) {
        super(settings);
    }

	@Override
	public MapCodec<? extends CushionedCarpetBlock> getCodec() {
		return CODEC;
	}

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {}
}
