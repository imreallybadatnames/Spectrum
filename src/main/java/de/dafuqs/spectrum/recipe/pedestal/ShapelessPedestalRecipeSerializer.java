package de.dafuqs.spectrum.recipe.pedestal;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.recipe.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;

public class ShapelessPedestalRecipeSerializer extends PedestalRecipeSerializer<ShapelessPedestalRecipe> {
	
	public static final StructEndec<ShapelessPedestalRecipe> ENDEC = StructEndecBuilder.of(
		Endec.STRING.optionalFieldOf("group", recipe -> recipe.group, ""),
		Endec.BOOLEAN.optionalFieldOf("secret", recipe -> recipe.secret, false),
		MinecraftEndecs.IDENTIFIER.fieldOf("required_advancement", recipe -> recipe.requiredAdvancementIdentifier),
		PedestalRecipeTier.ENDEC.optionalFieldOf("tier", recipe -> recipe.tier, PedestalRecipeTier.BASIC),
		IngredientStack.Serializer.ENDEC.listOf().fieldOf("ingredients", recipe -> recipe.inputs),
		GemstoneColorInput.ENDEC.listOf().fieldOf("gemstone_powder", recipe -> GemstoneColorInput.convertToList(recipe.powderInputs)),
		MinecraftEndecs.ITEM_STACK.fieldOf("result", recipe -> recipe.output),
		Endec.FLOAT.optionalFieldOf("experience", recipe -> recipe.experience, 0f),
		Endec.INT.optionalFieldOf("time", recipe -> recipe.craftingTime, 200),
		Endec.BOOLEAN.optionalFieldOf("skip_recipe_remainders", recipe -> recipe.skipRecipeRemainders, false),
		Endec.BOOLEAN.optionalFieldOf("disable_yield_upgrades", recipe -> recipe.noBenefitsFromYieldUpgrades, false),
		ShapelessPedestalRecipe::new
	);

	@Override
	public MapCodec<ShapelessPedestalRecipe> codec() {
		return CodecUtils.toMapCodec(ENDEC);
	}

	@Override
	public PacketCodec<RegistryByteBuf, ShapelessPedestalRecipe> packetCodec() {
		return CodecUtils.toPacketCodec(ENDEC);
	}
}
