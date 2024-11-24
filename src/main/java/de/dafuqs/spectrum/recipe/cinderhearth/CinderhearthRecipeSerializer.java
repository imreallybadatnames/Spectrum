package de.dafuqs.spectrum.recipe.cinderhearth;

import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.recipe.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class CinderhearthRecipeSerializer extends EndecRecipeSerializer<CinderhearthRecipe> implements GatedRecipeSerializer<CinderhearthRecipe> {
	
	public static final StructEndec<Pair<ItemStack, Float>> STACK_WITH_CHANCE_ENDEC = StructEndecBuilder.of(
		MinecraftEndecs.ITEM_STACK.fieldOf("result", Pair::getLeft),
		Endec.FLOAT.optionalFieldOf("chance", Pair::getRight, 1.0f),
		Pair::new
	);
	
	public static final StructEndec<CinderhearthRecipe> ENDEC = StructEndecBuilder.of(
		Endec.STRING.optionalFieldOf("group", recipe -> recipe.group, ""),
		Endec.BOOLEAN.optionalFieldOf("secret", recipe -> recipe.secret, false),
		MinecraftEndecs.IDENTIFIER.fieldOf("required_advancement", recipe -> recipe.requiredAdvancementIdentifier),
		IngredientStack.Serializer.ENDEC.fieldOf("ingredient", recipe -> recipe.ingredient),
		Endec.INT.fieldOf("time", recipe -> recipe.time),
		Endec.FLOAT.optionalFieldOf("experience", recipe -> recipe.experience, 0f),
		STACK_WITH_CHANCE_ENDEC.listOf().fieldOf("results", recipe -> recipe.resultsWithChance),
		CinderhearthRecipe::new
	);

	public CinderhearthRecipeSerializer() {
		super(ENDEC);
	}
}
