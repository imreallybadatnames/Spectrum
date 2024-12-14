package de.dafuqs.spectrum.recipe.ink_converting;

import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.recipe.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.recipe.*;

public class InkConvertingRecipeSerializer extends EndecRecipeSerializer<InkConvertingRecipe> implements GatedRecipeSerializer<InkConvertingRecipe> {

	public InkConvertingRecipeSerializer() {
		super(ENDEC);
	}

	public static final StructEndec<InkConvertingRecipe> ENDEC = StructEndecBuilder.of(
		Endec.STRING.optionalFieldOf("group", recipe -> recipe.group, ""),
		Endec.BOOLEAN.optionalFieldOf("secret", recipe -> recipe.secret, false),
		MinecraftEndecs.IDENTIFIER.fieldOf("required_advancement", recipe -> recipe.requiredAdvancementIdentifier),
		CodecUtils.toEndec(Ingredient.DISALLOW_EMPTY_CODEC).fieldOf("ingredient", recipe -> recipe.inputIngredient),
		CodecUtils.toEndec(InkColor.CODEC).fieldOf("ink_color", recipe -> recipe.color),
		Endec.LONG.fieldOf("amount", recipe -> recipe.amount),
		InkConvertingRecipe::new
	);
	
}
