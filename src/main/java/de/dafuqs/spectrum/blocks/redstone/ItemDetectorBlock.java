package de.dafuqs.spectrum.blocks.redstone;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class ItemDetectorBlock extends DetectorBlock {

	public static final MapCodec<ItemDetectorBlock> CODEC = createCodec(ItemDetectorBlock::new);

	public ItemDetectorBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends ItemDetectorBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	protected void updateState(BlockState state, World world, BlockPos pos) {
		List<ItemEntity> items = world.getEntitiesByType(EntityType.ITEM, getBoxWithRadius(pos, 10), Entity::isAlive);
		
		int power;
		if (items.size() > 0) {
			int amount = 0;
			for (ItemEntity itementity : items) {
				ItemStack itemStack = itementity.getStack();
				amount += itemStack.getCount();
				if (amount >= 64) {
					break;
				}
			}
			power = Math.max(1, Math.min(amount / 4, 15));
		} else {
			power = 0;
		}
		
		power = state.get(INVERTED) ? 15 - power : power;
		if (state.get(POWER) != power) {
			world.setBlockState(pos, state.with(POWER, power), 3);
		}
	}
	
	@Override
	int getUpdateFrequencyTicks() {
		return 20;
	}
	
}
