package de.dafuqs.spectrum.blocks.decoration;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.api.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

public class GemstonePlayerOnlyGlassBlock extends GemstoneGlassBlock {
	
	public GemstonePlayerOnlyGlassBlock(Settings settings, GemstoneColor gemstoneColor) {
		super(settings, gemstoneColor);
	}

	@Override
	public MapCodec<? extends GemstonePlayerOnlyGlassBlock> getCodec() {
		//TODO: Make the codec
		return null;
	}

	@Override
	public boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}

	@Override
	@Deprecated
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (context instanceof EntityShapeContext entityShapeContext) {
			Entity entity = entityShapeContext.getEntity();
			if (entity instanceof PlayerEntity) {
				return VoxelShapes.empty();
			}
		}
		return state.getOutlineShape(world, pos);
	}
	
}
