package de.dafuqs.spectrum.blocks.conditional.colored_tree;

import com.google.common.collect.*;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.revelationary.api.revelations.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

import java.util.*;

public class ColoredLogBlock extends PillarBlock implements RevelationAware, ColoredTree {

	public static final MapCodec<ColoredLogBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			createSettingsCodec(),
			DyeColor.CODEC.fieldOf("color").forGetter(ColoredLogBlock::getColor)
	).apply(instance, ColoredLogBlock::new));

	private static final Map<DyeColor, ColoredLogBlock> LOGS = Maps.newEnumMap(DyeColor.class);
	protected final DyeColor color;
	
	public ColoredLogBlock(Settings settings, DyeColor color) {
		super(settings);
		this.color = color;
		LOGS.put(color, this);
		RevelationAware.register(this);
	}

	@Override
	public MapCodec<? extends ColoredLogBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	public Identifier getCloakAdvancementIdentifier() {
		return ColoredTree.getTreeCloakAdvancementIdentifier(ColoredTree.TreePart.LOG, this.color);
	}
	
	@Override
	public Map<BlockState, BlockState> getBlockStateCloaks() {
		Map<BlockState, BlockState> map = new Hashtable<>();
		for (Direction.Axis axis : PillarBlock.AXIS.getValues()) {
			map.put(this.getDefaultState().with(PillarBlock.AXIS, axis), Blocks.OAK_LOG.getDefaultState().with(PillarBlock.AXIS, axis));
		}
		return map;
	}
	
	@Override
	public Pair<Item, Item> getItemCloak() {
		return new Pair<>(this.asItem(), Blocks.OAK_LOG.asItem());
	}
	
	@Override
	public DyeColor getColor() {
		return this.color;
	}
	
	public static ColoredLogBlock byColor(DyeColor color) {
		return LOGS.get(color);
	}
	
	public static Collection<ColoredLogBlock> all() {
		return LOGS.values();
	}
	
}
