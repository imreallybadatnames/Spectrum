package de.dafuqs.spectrum.recipe.fusion_shrine;

import com.google.gson.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.predicate.world.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.recipe.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class FusionShrineRecipeSerializer implements GatedRecipeSerializer<FusionShrineRecipe> {

	public static final StructEndec<FusionShrineRecipe> ENDEC = StructEndecBuilder.<FusionShrineRecipe>of(
		Endec.STRING.optionalFieldOf("group", recipe -> recipe.group, ""),
		Endec.BOOLEAN.optionalFieldOf("secret", recipe -> recipe.secret, false),
		MinecraftEndecs.IDENTIFIER.fieldOf("required_advancement", recipe -> recipe.requiredAdvancementIdentifier),
		IngredientStack.Serializer.ENDEC.listOf().fieldOf("ingredients", recipe -> recipe.craftingInputs),
		FluidIngredient.ENDEC.optionalFieldOf("fluid", recipe -> recipe.fluid, FluidIngredient.EMPTY),
		MinecraftEndecs.ITEM_STACK.fieldOf("output", recipe -> recipe.output),
		Endec.FLOAT.optionalFieldOf("experience", recipe -> recipe.experience, 0f),
		Endec.INT.optionalFieldOf("time", recipe -> recipe.craftingTime, 200),
		Endec.BOOLEAN.optionalFieldOf("disable_yield_upgrades", recipe -> recipe.yieldUpgradesDisabled, false),
		Endec.BOOLEAN.optionalFieldOf("play_crafting_finished_effects", recipe -> recipe.playCraftingFinishedEffects, true),
		Endec.BOOLEAN.optionalFieldOf("copy_components", recipe -> recipe.copyNbt, false),
		// TODO - List of WorldConditionPredicates
		// TODO - FusionShrineRecipeWorldEffect when starting
		// TODO - List of FusionShrineRecipeWorldEffect during the craft
		// TODO - FusionShrineRecipeWorldEffect when craft is finished
		MinecraftEndecs.TEXT.optionalFieldOf("description", recipe -> recipe.description, Text.EMPTY)
	);
	
	@Override
	public FusionShrineRecipe read(Identifier identifier, JsonObject jsonObject) {
		String group = readGroup(jsonObject);
		boolean secret = readSecret(jsonObject);
		Identifier requiredAdvancementIdentifier = readRequiredAdvancementIdentifier(jsonObject);

		JsonArray ingredientArray = JsonHelper.getArray(jsonObject, "ingredients");
		List<IngredientStack> craftingInputs = RecipeParser.ingredientStacksFromJson(ingredientArray, ingredientArray.size());
		if (craftingInputs.size() > 7) {
			throw new JsonParseException("Recipe cannot have more than 7 ingredients. Has " + craftingInputs.size());
		}
		
		FluidIngredient fluid = FluidIngredient.EMPTY;
		if (JsonHelper.hasJsonObject(jsonObject, "fluid")) {
			JsonObject fluidObject = JsonHelper.getObject(jsonObject, "fluid");
			FluidIngredient.JsonParseResult result = FluidIngredient.fromJson(fluidObject);
			fluid = result.result();
			if (result.malformed()) {
				// Currently handling malformed input leniently. May throw an error in the future.
				SpectrumCommon.logError("Fusion Recipe " + identifier + "contains a malformed fluid input tag! This recipe will not be craftable.");
			} else if (result.result() == FluidIngredient.EMPTY && !result.isTag()) { // tags get populated after recipes are
				SpectrumCommon.logError("Fusion Recipe " + identifier + " specifies fluid " + result.id() + " that does not exist! This recipe will not be craftable.");
			}
		}
		
		ItemStack output;
		if (JsonHelper.hasJsonObject(jsonObject, "result")) {
			output = RecipeUtils.itemStackWithNbtFromJson(JsonHelper.getObject(jsonObject, "result"));
		} else {
			output = ItemStack.EMPTY;
		}
		float experience = JsonHelper.getFloat(jsonObject, "experience", 0);
		int craftingTime = JsonHelper.getInt(jsonObject, "time", 200);
		boolean yieldUpgradesDisabled = JsonHelper.getBoolean(jsonObject, "disable_yield_upgrades", false);
		boolean playCraftingFinishedEffects = JsonHelper.getBoolean(jsonObject, "play_crafting_finished_effects", true);
		
		List<WorldConditionType<?>> worldConditions = new ArrayList<>();
		if (JsonHelper.hasArray(jsonObject, "world_conditions")) {
			for (JsonElement element : JsonHelper.getArray(jsonObject, "world_conditions")) {
				worldConditions.add(WorldConditionType.fromJson(element));
			}
		}
		
		FusionShrineRecipeWorldEffect startWorldEffect = FusionShrineRecipeWorldEffect.fromString(JsonHelper.getString(jsonObject, "start_crafting_effect", null));
		List<FusionShrineRecipeWorldEffect> duringWorldEffects = new ArrayList<>();
		if (JsonHelper.hasArray(jsonObject, "during_crafting_effects")) {
			JsonArray worldEffectsArray = JsonHelper.getArray(jsonObject, "during_crafting_effects");
			for (int i = 0; i < worldEffectsArray.size(); i++) {
				duringWorldEffects.add(FusionShrineRecipeWorldEffect.fromString(worldEffectsArray.get(i).getAsString()));
			}
		}
		FusionShrineRecipeWorldEffect finishWorldEffect = FusionShrineRecipeWorldEffect.fromString(JsonHelper.getString(jsonObject, "finish_crafting_effect", null));
		
		Text description;
		if (JsonHelper.hasString(jsonObject, "description")) {
			description = Text.translatable(JsonHelper.getString(jsonObject, "description"));
		} else {
			description = null;
		}
		boolean copyNbt = JsonHelper.getBoolean(jsonObject, "copy_nbt", false);
		if (copyNbt && output.isEmpty()) {
			throw new JsonParseException("Recipe does have copy_nbt set, but has no output!");
		}

		return this.recipeFactory.create(identifier, group, secret, requiredAdvancementIdentifier,
				craftingInputs, fluid, output, experience, craftingTime, yieldUpgradesDisabled, playCraftingFinishedEffects, copyNbt,
				worldConditions, startWorldEffect, duringWorldEffects, finishWorldEffect, description);
	}
	
	
	@Override
	public void write(PacketByteBuf packetByteBuf, FusionShrineRecipe recipe) {
		packetByteBuf.writeString(recipe.group);
		packetByteBuf.writeBoolean(recipe.secret);
		writeNullableIdentifier(packetByteBuf, recipe.requiredAdvancementIdentifier);
		
		packetByteBuf.writeShort(recipe.craftingInputs.size());
		for (IngredientStack ingredientStack : recipe.craftingInputs) {
			ingredientStack.write(packetByteBuf);
		}
		
		writeFluidIngredient(packetByteBuf, recipe.fluid);
		packetByteBuf.writeItemStack(recipe.output);
		packetByteBuf.writeFloat(recipe.experience);
		packetByteBuf.writeInt(recipe.craftingTime);
		packetByteBuf.writeBoolean(recipe.yieldUpgradesDisabled);
		packetByteBuf.writeBoolean(recipe.playCraftingFinishedEffects);
		
		if (recipe.getDescription().isEmpty()) {
			packetByteBuf.writeText(Text.literal(""));
		} else {
			packetByteBuf.writeText(recipe.getDescription().get());
		}
		packetByteBuf.writeBoolean(recipe.copyNbt);
	}
	
	
	@Override
	public FusionShrineRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
		String group = packetByteBuf.readString();
		boolean secret = packetByteBuf.readBoolean();
		Identifier requiredAdvancementIdentifier = readNullableIdentifier(packetByteBuf);
		
		short craftingInputCount = packetByteBuf.readShort();
		List<IngredientStack> ingredients = IngredientStack.decodeByteBuf(packetByteBuf, craftingInputCount);
		
		FluidIngredient fluid = readFluidIngredient(packetByteBuf);
		ItemStack output = packetByteBuf.readItemStack();
		float experience = packetByteBuf.readFloat();
		int craftingTime = packetByteBuf.readInt();
		boolean yieldUpgradesDisabled = packetByteBuf.readBoolean();
		boolean playCraftingFinishedEffects = packetByteBuf.readBoolean();

		Text description = packetByteBuf.readText();
		boolean copyNbt = packetByteBuf.readBoolean();
		
		return this.recipeFactory.create(identifier, group, secret, requiredAdvancementIdentifier,
				ingredients, fluid, output, experience, craftingTime, yieldUpgradesDisabled, playCraftingFinishedEffects, copyNbt,
				List.of(), FusionShrineRecipeWorldEffect.NOTHING, List.of(), FusionShrineRecipeWorldEffect.NOTHING, description);
	}
	
}
