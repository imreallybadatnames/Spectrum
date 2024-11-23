package de.dafuqs.spectrum.recipe.fluid_converting;

import de.dafuqs.spectrum.api.recipe.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.recipe.*;

public class FluidConvertingRecipeSerializer extends EndecRecipeSerializer<FluidConvertingRecipe> implements GatedRecipeSerializer<FluidConvertingRecipe> {
	
	public static final StructEndec<FluidConvertingRecipe> ENDEC = StructEndecBuilder.of(
		Endec.STRING.optionalFieldOf("group", recipe -> recipe.group, ""),
		Endec.BOOLEAN.optionalFieldOf("secret", recipe -> recipe.secret, false),
		MinecraftEndecs.IDENTIFIER.fieldOf("required_advancement", recipe -> recipe.requiredAdvancementIdentifier),
		CodecUtils.toEndec(Ingredient.DISALLOW_EMPTY_CODEC).fieldOf("ingredient", recipe -> recipe.input),
		MinecraftEndecs.ITEM_STACK.fieldOf("result", recipe -> recipe.output),
		FluidConvertingRecipe::new
	);
	
	public FluidConvertingRecipeSerializer() {
		super(ENDEC);
	}
	
//	@Override
//	public R read(Identifier identifier, JsonObject jsonObject) {
//		String group = readGroup(jsonObject);
//		boolean secret = readSecret(jsonObject);
//		Identifier requiredAdvancementIdentifier = readRequiredAdvancementIdentifier(jsonObject);
//
//		JsonElement jsonElement = JsonHelper.getObject(jsonObject, "ingredient");
//		Ingredient ingredient = Ingredient.fromJson(jsonElement);
//		ItemStack outputItemStack = RecipeUtils.itemStackWithNbtFromJson(JsonHelper.getObject(jsonObject, "result"));
//
//		return this.recipeFactory.create(identifier, group, secret, requiredAdvancementIdentifier, ingredient, outputItemStack);
//	}
//
//	@Override
//	public void write(PacketByteBuf packetByteBuf, R recipe) {
//		packetByteBuf.writeString(recipe.group);
//		packetByteBuf.writeBoolean(recipe.secret);
//		writeNullableIdentifier(packetByteBuf, recipe.requiredAdvancementIdentifier);
//
//		recipe.input.write(packetByteBuf);
//		packetByteBuf.writeItemStack(recipe.output);
//	}
//
//	@Override
//	public R read(Identifier identifier, PacketByteBuf packetByteBuf) {
//		String group = packetByteBuf.readString();
//		boolean secret = packetByteBuf.readBoolean();
//		Identifier requiredAdvancementIdentifier = readNullableIdentifier(packetByteBuf);
//
//		Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
//		ItemStack outputItemStack = packetByteBuf.readItemStack();
//		return this.recipeFactory.create(identifier, group, secret, requiredAdvancementIdentifier, ingredient, outputItemStack);
//	}
	
}
