package de.dafuqs.spectrum.api.item;

import com.mojang.serialization.Codec;
import net.minecraft.item.*;
import net.minecraft.util.*;

public interface GemstoneColor extends StringIdentifiable {
	
	DyeColor getDyeColor();

	Item getGemstonePowderItem();

	Codec<GemstoneColor> getCodec();
	
}