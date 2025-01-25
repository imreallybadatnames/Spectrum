package de.dafuqs.spectrum.recipe.fusion_shrine;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.api.predicate.location.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.recipe.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.text.*;

import java.util.*;

public class FusionShrineRecipeSerializer implements GatedRecipeSerializer<FusionShrineRecipe> {

	public static final StructEndec<FusionShrineRecipe> ENDEC = StructEndecBuilder.of(
		Endec.STRING.optionalFieldOf("group", recipe -> recipe.group, ""),
		Endec.BOOLEAN.optionalFieldOf("secret", recipe -> recipe.secret, false),
		MinecraftEndecs.IDENTIFIER.fieldOf("required_advancement", recipe -> recipe.requiredAdvancementIdentifier),
		IngredientStack.Serializer.ENDEC.listOf().validate(stacks -> {
			if (stacks.size() > 7) throw new AssertionError("Recipe cannot have more than 7 ingredients. Has " + stacks.size());
		}).fieldOf("ingredients", recipe -> recipe.craftingInputs),
		FluidIngredient.ENDEC.optionalFieldOf("fluid", recipe -> recipe.fluid, FluidIngredient.EMPTY),
		MinecraftEndecs.ITEM_STACK.optionalFieldOf("output", recipe -> recipe.output, ItemStack.EMPTY),
		Endec.FLOAT.optionalFieldOf("experience", recipe -> recipe.experience, 0f),
		Endec.INT.optionalFieldOf("time", recipe -> recipe.craftingTime, 200),
		Endec.BOOLEAN.optionalFieldOf("disable_yield_upgrades", recipe -> recipe.yieldUpgradesDisabled, false),
		Endec.BOOLEAN.optionalFieldOf("play_crafting_finished_effects", recipe -> recipe.playCraftingFinishedEffects, true),
		Endec.BOOLEAN.optionalFieldOf("copy_components", recipe -> recipe.copyComponents, false),
		CodecUtils.toEndec(CodecHelper.singleOrList(WorldConditionsPredicate.CODEC)).optionalFieldOf("world_conditions", recipe -> recipe.worldConditionsPredicates, List.of()),
		FusionShrineRecipeWorldEffect.ENDEC.fieldOf("start_crafting_effect", recipe -> recipe.startWorldEffect),
		FusionShrineRecipeWorldEffect.ENDEC.listOf().optionalFieldOf("during_crafting_effects", recipe -> recipe.duringWorldEffects, List.of()),
		FusionShrineRecipeWorldEffect.ENDEC.fieldOf("finish_crafting_effect", recipe -> recipe.finishWorldEffect),
		MinecraftEndecs.TEXT.optionalFieldOf("description", recipe -> recipe.description, Text.empty()),
		FusionShrineRecipe::new
	);
	
	@Override
	public MapCodec<FusionShrineRecipe> codec() {
		return CodecUtils.toMapCodec(ENDEC);
	}
	
	@Override
	public PacketCodec<RegistryByteBuf, FusionShrineRecipe> packetCodec() {
		return CodecUtils.toPacketCodec(ENDEC);
	}

}
