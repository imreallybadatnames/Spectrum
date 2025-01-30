package de.dafuqs.spectrum.recipe.fluid_converting;

import de.dafuqs.spectrum.api.recipe.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.util.*;

public class FluidConvertingRecipeSerializer<T extends FluidConvertingRecipe> extends EndecRecipeSerializer<T> implements GatedRecipeSerializer<T> {
	
	public FluidConvertingRecipeSerializer(StructEndecBuilder.Function5<String, Boolean, Identifier, Ingredient, ItemStack, T> factory) {
		super(StructEndecBuilder.of(
				Endec.STRING.optionalFieldOf("group", recipe -> recipe.group, ""),
				Endec.BOOLEAN.optionalFieldOf("secret", recipe -> recipe.secret, false),
				MinecraftEndecs.IDENTIFIER.fieldOf("required_advancement", recipe -> recipe.requiredAdvancementIdentifier),
				CodecUtils.toEndec(Ingredient.DISALLOW_EMPTY_CODEC).fieldOf("ingredient", recipe -> recipe.input),
				MinecraftEndecs.ITEM_STACK.fieldOf("result", recipe -> recipe.output),
				factory
		));
	}
	
}
