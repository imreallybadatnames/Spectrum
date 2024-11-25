package de.dafuqs.spectrum.recipe.primordial_fire_burning;

import de.dafuqs.spectrum.api.recipe.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.recipe.*;

public class PrimordialFireBurningRecipeSerializer extends EndecRecipeSerializer<PrimordialFireBurningRecipe> implements GatedRecipeSerializer<PrimordialFireBurningRecipe> {
	
	public static final StructEndec<PrimordialFireBurningRecipe> ENDEC = StructEndecBuilder.of(
		Endec.STRING.optionalFieldOf("group", recipe -> recipe.group, ""),
		Endec.BOOLEAN.optionalFieldOf("secret", recipe -> recipe.secret, false),
		MinecraftEndecs.IDENTIFIER.fieldOf("required_advancement", recipe -> recipe.requiredAdvancementIdentifier),
		CodecUtils.toEndec(Ingredient.DISALLOW_EMPTY_CODEC).fieldOf("ingredient", recipe -> recipe.input),
		MinecraftEndecs.ITEM_STACK.fieldOf("result", recipe -> recipe.output),
		PrimordialFireBurningRecipe::new
	);
	
	public PrimordialFireBurningRecipeSerializer() {
		super(ENDEC);
	}
	
}
