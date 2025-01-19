package de.dafuqs.spectrum.blocks.shooting_star;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.blocks.*;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

public class ShootingStarBlock extends PlacedItemBlock implements ShootingStar {
	
	public static final MapCodec<ShootingStarBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			createSettingsCodec(),
			ShootingStar.Type.CODEC.fieldOf("shooting_star_type").forGetter(ShootingStarBlock::getShootingStarType)
	).apply(instance, ShootingStarBlock::new));
	
	protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
	public final Type shootingStarType;
	
	public ShootingStarBlock(Settings settings, Type shootingStarType) {
		super(settings);
		this.shootingStarType = shootingStarType;
	}
	
	public Type getShootingStarType() {
		return shootingStarType;
	}
	
	@Override
	public MapCodec<? extends ShootingStarBlock> getCodec() {
		return CODEC;
	}

	@Override
	public boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

}
