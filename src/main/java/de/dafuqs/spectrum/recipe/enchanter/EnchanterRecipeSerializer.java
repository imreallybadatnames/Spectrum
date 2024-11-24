package de.dafuqs.spectrum.recipe.enchanter;

import de.dafuqs.spectrum.api.recipe.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.recipe.*;

import java.util.*;

public class EnchanterRecipeSerializer extends EndecRecipeSerializer<EnchanterRecipe> implements GatedRecipeSerializer<EnchanterRecipe> {
	
	public static final StructEndec<EnchanterRecipe> ENDEC = StructEndecBuilder.of(
		Endec.STRING.optionalFieldOf("group", recipe -> recipe.group, ""),
		Endec.BOOLEAN.optionalFieldOf("secret", recipe -> recipe.secret, false),
		MinecraftEndecs.IDENTIFIER.fieldOf("required_advancement", recipe -> recipe.requiredAdvancementIdentifier),
		CodecUtils.toEndec(Ingredient.DISALLOW_EMPTY_CODEC).listOf().optionalFieldOf("ingredients", recipe -> recipe.inputs, List.of()),
		MinecraftEndecs.ITEM_STACK.fieldOf("output", recipe -> recipe.output),
		Endec.INT.optionalFieldOf("required_experience", recipe -> recipe.requiredExperience, 0),
		Endec.INT.optionalFieldOf("time", recipe -> recipe.craftingTime, 200),
		Endec.BOOLEAN.optionalFieldOf("disable_yield_and_efficiency_upgrades", recipe -> recipe.noBenefitsFromYieldAndEfficiencyUpgrades, false),
		Endec.BOOLEAN.optionalFieldOf("copy_components", recipe -> recipe.copyNbt, false),
		EnchanterRecipe::new
	);
	
	public EnchanterRecipeSerializer() {
		super(ENDEC);
	}
}
