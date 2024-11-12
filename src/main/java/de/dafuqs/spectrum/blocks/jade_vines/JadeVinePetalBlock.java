package de.dafuqs.spectrum.blocks.jade_vines;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

public class JadeVinePetalBlock extends Block {

	public static final MapCodec<JadeVinePetalBlock> CODEC = createCodec(JadeVinePetalBlock::new);

	public JadeVinePetalBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends JadeVinePetalBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.fullCube();
	}
	
	// makes blocks like torches being unable to be placed against it
	@Override
	public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
		return VoxelShapes.empty();
	}
	
	@Override
	public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
		return 2;
	}
	
}
