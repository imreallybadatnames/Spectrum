package de.dafuqs.spectrum.recipe.crystallarieum;

import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import net.minecraft.recipe.*;

public record CrystallarieumCatalyst(Ingredient ingredient, float growthAccelerationMod, float inkConsumptionMod, float consumeChancePerSecond) {

	public static final CrystallarieumCatalyst EMPTY = new CrystallarieumCatalyst(Ingredient.EMPTY, 0, 0, 0);
	
	public static final StructEndec<CrystallarieumCatalyst> ENDEC = StructEndecBuilder.of(
		CodecUtils.toEndec(Ingredient.DISALLOW_EMPTY_CODEC).fieldOf("ingredient", CrystallarieumCatalyst::ingredient),
		Endec.FLOAT.fieldOf("growth_acceleration_mod", CrystallarieumCatalyst::growthAccelerationMod),
		Endec.FLOAT.fieldOf("ink_consumption_mod", CrystallarieumCatalyst::inkConsumptionMod),
		Endec.FLOAT.fieldOf("consume_chance_per_second", CrystallarieumCatalyst::consumeChancePerSecond),
		CrystallarieumCatalyst::new
	);
}
