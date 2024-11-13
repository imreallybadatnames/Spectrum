package de.dafuqs.spectrum.recipe.cinderhearth;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.PairCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.matchbooks.recipe.*;
import de.dafuqs.spectrum.api.recipe.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.*;

import java.util.*;

public class CinderhearthRecipeSerializer implements GatedRecipeSerializer<CinderhearthRecipe> {

	private static final MapCodec<Pair<ItemStack, Float>> RESULT_WITH_CHANCE_CODEC = PairCodec.;

	private static final MapCodec<CinderhearthRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
			Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
			Identifier.CODEC.fieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
			IngredientStack.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
			Codec.INT.fieldOf("time").forGetter(recipe -> recipe.time),
			Codec.FLOAT.fieldOf("experience").forGetter(recipe -> recipe.experience),
			RecordCodecBuilder.<Pair<ItemStack, Float>>create(resultInstance -> resultInstance.group(
					ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(Pair::getLeft),
					Codec.FLOAT.optionalFieldOf("chance", 1.0F).forGetter(Pair::getRight)
			).apply(resultInstance, Pair::new)).listOf().fieldOf("results").forGetter(recipe -> recipe.resultsWithChance)
	).apply(instance, CinderhearthRecipe::new));
	private static final PacketCodec<RegistryByteBuf, CinderhearthRecipe> PACKET_CODEC = PacketCodec.ofStatic(CinderhearthRecipeSerializer::write, CinderhearthRecipeSerializer::read);

	public static void write(RegistryByteBuf buf, CinderhearthRecipe recipe) {
		buf.writeString(recipe.group);
		buf.writeBoolean(recipe.secret);
		GatedRecipeSerializer.writeNullableIdentifier(buf, recipe.requiredAdvancementIdentifier);
		recipe.ingredient.write(buf);
		buf.writeInt(recipe.time);
		buf.writeFloat(recipe.experience);
		buf.writeInt(recipe.resultsWithChance.size());
		for (Pair<ItemStack, Float> output : recipe.resultsWithChance) {
			ItemStack.PACKET_CODEC.encode(buf, output.getLeft());
			buf.writeFloat(output.getRight());
		}
	}
	
	public static CinderhearthRecipe read(RegistryByteBuf buf) {
		String group = buf.readString();
		boolean secret = buf.readBoolean();
		Identifier requiredAdvancementIdentifier = GatedRecipeSerializer.readNullableIdentifier(buf);
		IngredientStack ingredient = IngredientStack.fromByteBuf(buf);
		int time = buf.readInt();
		float experience = buf.readFloat();
		int outputCount = buf.readInt();
		List<Pair<ItemStack, Float>> resultsWithChance = new ArrayList<>(outputCount);
		for (int i = 0; i < outputCount; i++) {
			resultsWithChance.add(new Pair<>(ItemStack.PACKET_CODEC.decode(buf), buf.readFloat()));
		}
		return new CinderhearthRecipe(group, secret, requiredAdvancementIdentifier, ingredient, time, experience, resultsWithChance);
	}

	@Override
	public MapCodec<CinderhearthRecipe> codec() {
		return CODEC;
	}

	@Override
	public PacketCodec<RegistryByteBuf, CinderhearthRecipe> packetCodec() {
		return PACKET_CODEC;
	}
}
