package de.dafuqs.spectrum.recipe.crystallarieum;

import com.google.common.collect.*;
import com.mojang.serialization.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.recipe.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.block.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;

public class CrystallarieumRecipeSerializer implements GatedRecipeSerializer<CrystallarieumRecipe> {

	private static final StructEndec<CrystallarieumRecipe> ENDEC = StructEndecBuilder.of(
			Endec.STRING.optionalFieldOf("group", recipe -> recipe.group, ""),
			Endec.BOOLEAN.optionalFieldOf("secret", recipe -> recipe.secret, false),
			MinecraftEndecs.IDENTIFIER.fieldOf("required_advancement", recipe -> recipe.requiredAdvancementIdentifier),
			CodecUtils.toEndec(Ingredient.DISALLOW_EMPTY_CODEC).fieldOf("ingredient", recipe -> recipe.ingredient),
			CodecUtils.toEndec(BlockState.CODEC).listOf().fieldOf("growth_stage_states", recipe -> recipe.growthStages),
			Endec.INT.fieldOf("seconds_per_growth_stage", recipe -> recipe.secondsPerGrowthStage),
			CodecUtils.toEndec(InkColor.CODEC).fieldOf("ink_color", recipe -> recipe.inkColor),
			Endec.INT.xmap(d -> d == 0 ? 0 : (int) Math.pow(2, d- 1), e -> e).fieldOf("ink_cost_tier", recipe -> recipe.inkPerSecond),
			Endec.BOOLEAN.optionalFieldOf("grows_without_catalyst", recipe -> recipe.growsWithoutCatalyst, false),
			CrystallarieumCatalyst.ENDEC.listOf().fieldOf("catalysts", recipe -> recipe.catalysts),
		MinecraftEndecs.ITEM_STACK.listOf().optionalFieldOf("additional_recipe_manager_results", recipe -> recipe.additionalResults, ImmutableList.of()),
		CrystallarieumRecipe::new
	);
	
	private static final PacketCodec<RegistryByteBuf, CrystallarieumRecipe> PACKET_CODEC = CodecUtils.toPacketCodec(ENDEC);

	@Override
	public MapCodec<CrystallarieumRecipe> codec() {
		return CodecUtils.toMapCodec(ENDEC);
	}

	@Override
	public PacketCodec<RegistryByteBuf, CrystallarieumRecipe> packetCodec() {
		return PACKET_CODEC;
	}
}
