package de.dafuqs.spectrum.recipe.titration_barrel;

import com.google.gson.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.recipe.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import java.util.*;

public class TitrationBarrelRecipeSerializer extends EndecRecipeSerializer<TitrationBarrelRecipe> implements GatedRecipeSerializer<TitrationBarrelRecipe> {
	
	public static final StructEndec<TitrationBarrelRecipe> ENDEC = StructEndecBuilder.<RegistryByteBuf, TitrationBarrelRecipe>of(
		CodecUtils.toEndec(Ingredient.ALLOW_EMPTY_CODEC).fieldOf("ingredients", s -> s.ingredients),
		TitrationBarrelRecipe::new
	);
	
	public TitrationBarrelRecipeSerializer() {
		super(ENDEC);
	}
	
	
}
