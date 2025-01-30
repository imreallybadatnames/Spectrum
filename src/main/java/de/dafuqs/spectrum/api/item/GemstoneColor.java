package de.dafuqs.spectrum.api.item;

import net.minecraft.item.*;
import net.minecraft.util.*;

public interface GemstoneColor extends StringIdentifiable {
	
	DyeColor getDyeColor();

	Item getGemstonePowderItem();

}