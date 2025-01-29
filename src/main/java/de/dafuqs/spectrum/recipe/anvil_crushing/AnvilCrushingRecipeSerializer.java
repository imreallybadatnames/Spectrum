package de.dafuqs.spectrum.recipe.anvil_crushing;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.helpers.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;
import net.minecraft.util.*;

public class AnvilCrushingRecipeSerializer implements GatedRecipeSerializer<AnvilCrushingRecipe> {
	
	private static final MapCodec<AnvilCrushingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
			Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
			Identifier.CODEC.optionalFieldOf("required_advancement", null).forGetter(recipe -> recipe.requiredAdvancementIdentifier),
			Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
			ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
			Codec.FLOAT.fieldOf("crushedItemsPerPointOfDamage").forGetter(recipe -> recipe.crushedItemsPerPointOfDamage),
			Codec.FLOAT.fieldOf("experience").forGetter(recipe -> recipe.experience),
			Identifier.CODEC.optionalFieldOf("particleEffectIdentifier", null).forGetter(recipe -> recipe.particleEffectIdentifier),
			Codec.INT.optionalFieldOf("particleCount", 1).forGetter(recipe -> recipe.particleCount),
			Identifier.CODEC.fieldOf("soundEventIdentifier").forGetter(recipe -> recipe.soundEvent)
	).apply(instance, AnvilCrushingRecipe::new));
	
	private static final PacketCodec<RegistryByteBuf, AnvilCrushingRecipe> PACKET_CODEC = PacketCodecHelper.tuple(
			PacketCodecs.STRING, c -> c.group,
			PacketCodecs.BOOL, c -> c.secret,
			PacketCodecHelper.nullable(Identifier.PACKET_CODEC), c -> c.requiredAdvancementIdentifier,
			Ingredient.PACKET_CODEC, c -> c.ingredient,
			ItemStack.PACKET_CODEC, c -> c.result,
			PacketCodecs.FLOAT, c -> c.crushedItemsPerPointOfDamage,
			PacketCodecs.FLOAT, c -> c.experience,
			Identifier.PACKET_CODEC, c -> c.particleEffectIdentifier,
			PacketCodecs.VAR_INT, c -> c.particleCount,
			Identifier.PACKET_CODEC, c -> c.soundEvent,
			AnvilCrushingRecipe::new
	);
	
	@Override
	public MapCodec<AnvilCrushingRecipe> codec() {
		return CODEC;
	}
	
	@Override
	public PacketCodec<RegistryByteBuf, AnvilCrushingRecipe> packetCodec() {
		return PACKET_CODEC;
	}
	
}
