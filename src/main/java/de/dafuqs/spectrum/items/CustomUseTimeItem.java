package de.dafuqs.spectrum.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;

public class CustomUseTimeItem extends Item {
	
	private final int useTime;
	
	public CustomUseTimeItem(Settings settings, int useTime) {
		super(settings);
		this.useTime = useTime;
	}
	
	@Override
	public int getMaxUseTime(ItemStack stack, LivingEntity user) {
		return useTime;
	}
}
