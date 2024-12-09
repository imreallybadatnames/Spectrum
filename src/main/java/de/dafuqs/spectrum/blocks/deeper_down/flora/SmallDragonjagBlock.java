package de.dafuqs.spectrum.blocks.deeper_down.flora;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

import java.util.*;

public class SmallDragonjagBlock extends PlantBlock implements Dragonjag, Fertilizable {

	public static final MapCodec<SmallDragonjagBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			createSettingsCodec(),
			Dragonjag.Variant.CODEC.fieldOf("variant").forGetter(SmallDragonjagBlock::getVariant)
	).apply(i, SmallDragonjagBlock::new));

    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 8.0, 14.0);
    protected static final Map<Dragonjag.Variant, Block> VARIANTS = new HashMap<>();
    protected final Dragonjag.Variant variant;

    public SmallDragonjagBlock(Settings settings, Dragonjag.Variant variant) {
		super(settings);
		this.variant = variant;
		VARIANTS.put(variant, this);
	}

	@Override
	public MapCodec<? extends SmallDragonjagBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}
	
	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return Dragonjag.canPlantOnTop(floor, world, pos);
	}
	
	@Override
	public Variant getVariant() {
		return variant;
	}
	
	public static Block getBlockForVariant(Variant variant) {
		return VARIANTS.get(variant);
    }

    @Override
	public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
		return true;
	}

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        TallDragonjagBlock tallVariant = TallDragonjagBlock.getBlockForVariant(variant);
        if (tallVariant.getDefaultState().canPlaceAt(world, pos) && world.isAir(pos.up())) {
            TallDragonjagBlock.placeAt(world, tallVariant.getDefaultState(), pos, 2);
        }
    }

}