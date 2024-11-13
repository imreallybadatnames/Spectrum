package de.dafuqs.spectrum.recipe.anvil_crushing;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.api.recipe.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.util.*;

public class AnvilCrushingRecipeSerializer implements GatedRecipeSerializer<AnvilCrushingRecipe> {

	private static final MapCodec<AnvilCrushingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
			Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
			Identifier.CODEC.fieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
			Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
			ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
			Codec.FLOAT.fieldOf("crushedItemsPerPointOfDamage").forGetter(recipe -> recipe.crushedItemsPerPointOfDamage),
			Codec.FLOAT.fieldOf("experience").forGetter(recipe -> recipe.experience),
			Identifier.CODEC.optionalFieldOf("particleEffectIdentifier", null).forGetter(recipe -> recipe.particleEffectIdentifier),
			Codec.INT.optionalFieldOf("particleCount", 1).forGetter(recipe -> recipe.particleCount),
			Identifier.CODEC.fieldOf("soundEventIdentifier").forGetter(recipe -> recipe.soundEvent)
	).apply(instance, AnvilCrushingRecipe::new));
	private static final PacketCodec<RegistryByteBuf, AnvilCrushingRecipe> PACKET_CODEC =
			PacketCodec.ofStatic(AnvilCrushingRecipeSerializer::write, AnvilCrushingRecipeSerializer::read);

	private static void write(RegistryByteBuf buf, AnvilCrushingRecipe recipe) {
		buf.writeString(recipe.group);
		buf.writeBoolean(recipe.secret);
		GatedRecipeSerializer.writeNullableIdentifier(buf, recipe.requiredAdvancementIdentifier);
		Ingredient.PACKET_CODEC.encode(buf, recipe.ingredient);
		ItemStack.PACKET_CODEC.encode(buf, recipe.result);
		buf.writeFloat(recipe.crushedItemsPerPointOfDamage);
		buf.writeFloat(recipe.experience);
		buf.writeIdentifier(recipe.particleEffectIdentifier);
		buf.writeInt(recipe.particleCount);
		buf.writeIdentifier(recipe.soundEvent);
	}

	private static AnvilCrushingRecipe read(RegistryByteBuf buf) {
		String group = buf.readString();
		boolean secret = buf.readBoolean();
		Identifier requiredAdvancementIdentifier = GatedRecipeSerializer.readNullableIdentifier(buf);
		Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
		ItemStack result = ItemStack.PACKET_CODEC.decode(buf);
		float crushedItemsPerPointOfDamage = buf.readFloat();
		float experience = buf.readFloat();
		Identifier particleEffectIdentifier = buf.readIdentifier();
		int particleCount = buf.readInt();
		Identifier soundEventIdentifier = buf.readIdentifier();
		return new AnvilCrushingRecipe(group, secret, requiredAdvancementIdentifier, ingredient, result, crushedItemsPerPointOfDamage, experience, particleEffectIdentifier, particleCount, soundEventIdentifier);
	}

	@Override
	public MapCodec<AnvilCrushingRecipe> codec() {
		return CODEC;
	}

	@Override
	public PacketCodec<RegistryByteBuf, AnvilCrushingRecipe> packetCodec() {
		return PACKET_CODEC;
	}
}
