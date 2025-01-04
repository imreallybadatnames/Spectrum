package de.dafuqs.spectrum.recipe.titration_barrel;

import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.recipe.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.registry.*;

public class TitrationBarrelRecipeSerializer extends EndecRecipeSerializer<TitrationBarrelRecipe> implements GatedRecipeSerializer<TitrationBarrelRecipe> {
	
	public static final StructEndec<TitrationBarrelRecipe> ENDEC = StructEndecBuilder.of(
		Endec.STRING.optionalFieldOf("group", recipe -> recipe.group, ""),
		Endec.BOOLEAN.optionalFieldOf("secret", recipe -> recipe.secret, false),
		MinecraftEndecs.IDENTIFIER.fieldOf("required_advancement", recipe -> recipe.requiredAdvancementIdentifier),
		IngredientStack.Serializer.ENDEC.listOf().fieldOf("ingredients", recipe -> recipe.inputStacks),
		FluidIngredient.ENDEC.fieldOf("fluid", recipe -> recipe.fluid),
		MinecraftEndecs.ITEM_STACK.fieldOf("result", recipe -> recipe.outputItemStack),
		MinecraftEndecs.ofRegistry(Registries.ITEM).fieldOf("tapping_item", recipe -> recipe.tappingItem),
		Endec.INT.fieldOf("min_fermentation_time_hours", recipe -> recipe.minFermentationTimeHours),
		FermentationData.ENDEC.fieldOf("fermentation_data", recipe -> recipe.fermentationData),
		TitrationBarrelRecipe::new
	);
	
	public TitrationBarrelRecipeSerializer() {
		super(ENDEC);
	}
}
