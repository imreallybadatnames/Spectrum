package de.dafuqs.spectrum.blocks.structure;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

public class InvisibleWallBlock extends TransparentBlock {

	public static final MapCodec<InvisibleWallBlock> CODEC = createCodec(InvisibleWallBlock::new);

	public InvisibleWallBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends InvisibleWallBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (context.isHolding(this.asItem())) {
			return VoxelShapes.fullCube();
		} else {
			return VoxelShapes.empty();
		}
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}
	
	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 1.0F;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.fullCube();
	}
	
}
