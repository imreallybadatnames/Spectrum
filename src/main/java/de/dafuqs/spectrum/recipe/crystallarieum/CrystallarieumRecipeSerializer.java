package de.dafuqs.spectrum.recipe.crystallarieum;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.exceptions.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.recipe.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.*;

import java.util.*;

public class CrystallarieumRecipeSerializer implements GatedRecipeSerializer<CrystallarieumRecipe> {

	private static final MapCodec<CrystallarieumRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
			Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
			Identifier.CODEC.fieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
			Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
			BlockState.CODEC.listOf().fieldOf("growth_stage_states").forGetter(recipe -> recipe.growthStages),
			Codec.INT.fieldOf("seconds_per_growth_stage").forGetter(recipe -> recipe.secondsPerGrowthStage),
			InkColor.CODEC.fieldOf("ink_color").forGetter(recipe -> recipe.inkColor),
			Codec.INT.xmap(d -> d == 0 ? 0 : (int) Math.pow(2, d - 1), e -> e).fieldOf("ink_cost_tier").forGetter(recipe -> recipe.inkPerSecond),
			Codec.BOOL.optionalFieldOf("grows_without_catalyst", false).forGetter(recipe -> recipe.growsWithoutCatalyst),
			CrystallarieumCatalyst.CODEC.listOf().fieldOf("catalysts").forGetter(recipe -> recipe.catalysts),
			Identifier.CODEC.xmap(d -> new ItemStack(Registries.ITEM.get(d)), e -> null).listOf()
					.optionalFieldOf("additional_recipe_manager_results", ImmutableList.of()).forGetter(recipe -> recipe.additionalResults)
	).apply(instance, CrystallarieumRecipe::new));
	private static final PacketCodec<RegistryByteBuf, CrystallarieumRecipe> PACKET_CODEC = PacketCodec.ofStatic(CrystallarieumRecipeSerializer::write, CrystallarieumRecipeSerializer::read);
	
	public void write(RegistryByteBuf buf, CrystallarieumRecipe recipe) {
		buf.writeString(recipe.group);
		buf.writeBoolean(recipe.secret);
		GatedRecipeSerializer.writeNullableIdentifier(buf, recipe.requiredAdvancementIdentifier);
		
		Ingredient.PACKET_CODEC.encode(buf, recipe.ingredient);
		buf.writeInt(recipe.growthStages.size());
		for (BlockState state : recipe.growthStages) {
			buf.writeString(RecipeUtils.blockStateToString(state));
		}
		buf.writeInt(recipe.secondsPerGrowthStage);
		buf.writeIdentifier(recipe.inkColor.getID());
		buf.writeInt(recipe.inkPerSecond);
		buf.writeBoolean(recipe.growsWithoutCatalyst);
		buf.writeInt(recipe.catalysts.size());
		for (CrystallarieumCatalyst catalyst : recipe.catalysts) {
			catalyst.write(buf);
		}
		buf.writeInt(recipe.additionalOutputs.size());
		for (ItemStack additionalOutput : recipe.additionalOutputs) {
			ItemStack.PACKET_CODEC.encode(buf, additionalOutput);
		}
	}
	
	public CrystallarieumRecipe read(RegistryByteBuf buf) {
		String group = buf.readString();
		boolean secret = buf.readBoolean();
		Identifier requiredAdvancementIdentifier = GatedRecipeSerializer.readNullableIdentifier(buf);
		
		Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
		List<BlockState> growthStages = new ArrayList<>();
		int count = buf.readInt();
		for (int i = 0; i < count; i++) {
			String blockStateString = buf.readString();
			try {
				growthStages.add(RecipeUtils.blockStateFromString(blockStateString));
			} catch (CommandSyntaxException e) {
				SpectrumCommon.logError("Recipe " + identifier + " specifies block state " + blockStateString + " that does not seem valid or the block does not exist. Recipe will be ignored.");
				return null;
			}
		}

		int secondsPerGrowthStage = buf.readInt();
		InkColor inkColor = InkColor.ofId(buf.readIdentifier()).get();
		int inkPerSecond = buf.readInt();
		boolean growthWithoutCatalyst = buf.readBoolean();
		List<CrystallarieumCatalyst> catalysts = new ArrayList<>();
		count = buf.readInt();
		for (int i = 0; i < count; i++) {
			catalysts.add(CrystallarieumCatalyst.PACKET_CODEC.decode(buf));
		}
		List<ItemStack> additionalResults = new ArrayList<>();
		count = buf.readInt();
		for (int i = 0; i < count; i++) {
			additionalResults.add(ItemStack.PACKET_CODEC.decode(buf));
		}

		return new CrystallarieumRecipe(group, secret, requiredAdvancementIdentifier, ingredient, growthStages, secondsPerGrowthStage, inkColor, inkPerSecond, growthWithoutCatalyst, catalysts, additionalResults);
	}

	@Override
	public MapCodec<CrystallarieumRecipe> codec() {
		return CODEC;
	}

	@Override
	public PacketCodec<RegistryByteBuf, CrystallarieumRecipe> packetCodec() {
		return PACKET_CODEC;
	}
}
