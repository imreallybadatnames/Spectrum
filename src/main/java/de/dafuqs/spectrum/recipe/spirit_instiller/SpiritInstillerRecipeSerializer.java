package de.dafuqs.spectrum.recipe.spirit_instiller;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.recipe.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;

public class SpiritInstillerRecipeSerializer implements GatedRecipeSerializer<SpiritInstillerRecipe> {
	
	public static final StructEndec<SpiritInstillerRecipe> ENDEC = StructEndecBuilder.of(
		Endec.STRING.optionalFieldOf("group", recipe -> recipe.group, ""),
		Endec.BOOLEAN.optionalFieldOf("secret", recipe -> recipe.secret, false),
		MinecraftEndecs.IDENTIFIER.fieldOf("required_advancement", recipe -> recipe.requiredAdvancementIdentifier),
		IngredientStack.Serializer.ENDEC.fieldOf("center_ingredient", recipe -> recipe.centerIngredient),
		IngredientStack.Serializer.ENDEC.fieldOf("ingredient1", recipe -> recipe.bowlIngredient1),
		IngredientStack.Serializer.ENDEC.fieldOf("ingredient2", recipe -> recipe.bowlIngredient2),
		MinecraftEndecs.ITEM_STACK.fieldOf("result", recipe -> recipe.output),
		Endec.INT.optionalFieldOf("time", recipe -> recipe.craftingTime, 200),
		Endec.FLOAT.optionalFieldOf("experience", recipe -> recipe.experience, 1.0f),
		Endec.BOOLEAN.optionalFieldOf("disable_yield_and_efficiency_upgrades", recipe -> recipe.noBenefitsFromYieldAndEfficiencyUpgrades, false),
		SpiritInstillerRecipe::new
	);
	
	@Override
	public MapCodec<SpiritInstillerRecipe> codec() {
		return CodecUtils.toMapCodec(ENDEC);
	}
	
	@Override
	public PacketCodec<RegistryByteBuf, SpiritInstillerRecipe> packetCodec() {
		return CodecUtils.toPacketCodec(ENDEC);
	}
}
