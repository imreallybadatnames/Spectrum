package de.dafuqs.spectrum.blocks.decoration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public class ProjectorBlock extends Block implements BlockEntityProvider {

	public static final MapCodec<ProjectorBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			createSettingsCodec(),
			Identifier.CODEC.fieldOf("texture").forGetter(b -> b.texture),
			Codec.DOUBLE.fieldOf("width").forGetter(b -> b.width),
			Codec.DOUBLE.fieldOf("height").forGetter(b -> b.height),
			Codec.FLOAT.fieldOf("heightOffset").forGetter(b -> b.heightOffset),
			Codec.FLOAT.fieldOf("bobMultiplier").forGetter(b -> b.bobMultiplier),
			Codec.FLOAT.fieldOf("scaling").forGetter(b -> b.scaling)
	).apply(i, ProjectorBlock::new));

	private final double width, height;
	private final VoxelShape shape;
	final float heightOffset, bobMultiplier, scaling;
	final Identifier texture;

	public ProjectorBlock(Settings settings, String path, double width, double height, float heightOffset, float bobMultiplier, float scaling) {
		this(settings, SpectrumCommon.locate("textures/block/" + path + ".png"), width, height, heightOffset, bobMultiplier, scaling);
	}

	public ProjectorBlock(Settings settings, Identifier path, double width, double height, float heightOffset, float bobMultiplier, float scaling) {
		super(settings);
		this.heightOffset = heightOffset;
		this.bobMultiplier = bobMultiplier;
		this.scaling = scaling;
		this.width = width;
		this.height = height;
		var min = (16 - width) / 2;
		var max = width + min;
		shape = Block.createCuboidShape(min, 0, min, max, height, max);
		this.texture = SpectrumCommon.locate("textures/block/" + path + ".png");
	}

	@Override
	public MapCodec<? extends ProjectorBlock> getCodec() {
		return CODEC;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return shape;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ProjectorBlockEntity(pos, state);
	}
}
