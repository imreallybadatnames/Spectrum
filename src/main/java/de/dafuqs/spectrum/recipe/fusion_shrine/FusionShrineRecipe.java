package de.dafuqs.spectrum.recipe.fusion_shrine;


import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.api.predicate.location.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.blocks.fusion_shrine.*;
import de.dafuqs.spectrum.blocks.upgrade.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import io.wispforest.owo.serialization.endec.*;
import net.fabricmc.fabric.api.transfer.v1.fluid.*;
import net.fabricmc.fabric.api.transfer.v1.storage.base.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.server.world.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class FusionShrineRecipe extends GatedStackSpectrumRecipe<FusionShrineBlockEntity> {
	
	public static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("build_fusion_shrine");
	
	protected final List<IngredientStack> craftingInputs;
	protected final FluidIngredient fluid;
	protected final ItemStack output;
	protected final float experience;
	protected final int craftingTime;
	// since there are a few recipes that are basically compacting recipes
	// they could be crafted ingots>block and block>ingots back
	// In that case:
	// - the player should not get XP
	// - Yield upgrades disabled (item multiplication)
	protected final boolean yieldUpgradesDisabled;
	protected final boolean playCraftingFinishedEffects;
	
	protected final List<WorldConditionsPredicate> worldConditionsPredicates;
	@NotNull
	protected final FusionShrineRecipeWorldEffect startWorldEffect;
	@NotNull
	protected final List<FusionShrineRecipeWorldEffect> duringWorldEffects;
	@NotNull
	protected final FusionShrineRecipeWorldEffect finishWorldEffect;
	@Nullable
	protected final Text description;
	// copy all components from the first stack in the ingredients to the output stack
	protected final boolean copyComponents;
	
	public FusionShrineRecipe(
		String group,
		boolean secret,
		Identifier requiredAdvancementIdentifier,
		List<IngredientStack> craftingInputs,
		FluidIngredient fluid,
		ItemStack output,
		float experience,
		int craftingTime,
		boolean yieldUpgradesDisabled,
		boolean playCraftingFinishedEffects,
		boolean copyComponents,
		List<WorldConditionsPredicate> worldConditionsPredicates,
		@NotNull FusionShrineRecipeWorldEffect startWorldEffect,
		@NotNull List<FusionShrineRecipeWorldEffect> duringWorldEffects,
		@NotNull FusionShrineRecipeWorldEffect finishWorldEffect,
		@Nullable Text description
	) {
		super(group, secret, requiredAdvancementIdentifier);
		
		this.craftingInputs = craftingInputs;
		this.fluid = fluid;
		this.output = output;
		this.experience = experience;
		this.craftingTime = craftingTime;
		this.yieldUpgradesDisabled = yieldUpgradesDisabled;
		this.playCraftingFinishedEffects = playCraftingFinishedEffects;
		
		this.worldConditionsPredicates = worldConditionsPredicates;
		this.startWorldEffect = startWorldEffect;
		this.duringWorldEffects = duringWorldEffects;
		this.finishWorldEffect = finishWorldEffect;
		this.description = description;
		this.copyComponents = copyComponents;

		registerInToastManager(getType(), this);
	}
	
	/**
	 * Only tests the items.
	 * The required fluid has to be tested manually by the crafting block.
	 */
	@Override
	public boolean matches(FusionShrineBlockEntity inv, World world) {
		SingleVariantStorage<FluidVariant> fluidStorage = inv.getFluidStorage();
		if (!this.fluid.test(fluidStorage.variant)) {
			return false;
		}
		if (this.fluid != FluidIngredient.EMPTY) {
			if (fluidStorage.getAmount() != fluidStorage.getCapacity()) {
				return false;
			}
		}
		return matchIngredientStacksExclusively(inv, getIngredientStacks());
	}
	
	@Override
	public ItemStack craft(FusionShrineBlockEntity inv, RegistryWrapper.WrapperLookup drm) {
		return output.copy();
	}
	
	@Override
	public boolean fits(int width, int height) {
		return craftingInputs.size() <= width * height;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registryManager) {
		return output;
	}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(SpectrumBlocks.FUSION_SHRINE_BASALT);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.FUSION_SHRINE_RECIPE_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return SpectrumRecipeTypes.FUSION_SHRINE;
	}
	
	@Override
	public List<IngredientStack> getIngredientStacks() {
		return this.craftingInputs;
	}
	
	public float getExperience() {
		return experience;
	}
	
	/**
	 * Returns a boolean depending on if any of the recipe conditions are met.
	 * These can always be true, be a specific day or moon phase, weather, a command, biome, etc.
	 */
	public boolean areConditionMetCurrently(ServerWorld world, BlockPos pos) {
		return this.worldConditionsPredicates.stream().anyMatch(p -> p.test(world, pos));
	}
	
	public FluidIngredient getFluid() {
		return this.fluid;
	}
	
	public int getCraftingTime() {
		return this.craftingTime;
	}
	
	/**
	 * @param tick The crafting tick if the fusion shrine recipe
	 * @return The effect that should be played for the given recipe tick
	 */
	public FusionShrineRecipeWorldEffect getWorldEffectForTick(int tick, int totalTicks) {
		if (tick == 1) {
			return this.startWorldEffect;
		}
		if (tick == totalTicks) {
			return this.finishWorldEffect;
		}
		if (this.duringWorldEffects.isEmpty()) {
			return null;
		}
		if (this.duringWorldEffects.size() == 1) {
			return this.duringWorldEffects.getFirst();
		}
		
		// we really have to calculate the current effect, huh?
		float parts = (float) totalTicks / this.duringWorldEffects.size();
		int index = (int) (tick / (parts));
		FusionShrineRecipeWorldEffect effect = this.duringWorldEffects.get(index);
		if (effect.isOneTimeEffect() && index != (int) parts) {
			return null;
		}
		
		return effect;
	}
	
	public Optional<Text> getDescription() {
		if (this.description == null) {
			return Optional.empty();
		}
		return Optional.of(this.description);
	}

	@Override
	public Identifier getRecipeTypeUnlockIdentifier() {
		return UNLOCK_IDENTIFIER;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "fusion_shrine";
	}
	
	// calculate the max number of items that will be crafted.
	// note that we only check each ingredient once if a match was found.
	// custom recipes therefore should not use items / tags that match multiple items
	// at once, since we cannot rely on positions in a grid like vanilla does in its crafting table.
	public void craft(World world, FusionShrineBlockEntity fusionShrineBlockEntity) {
		ItemStack firstStack = ItemStack.EMPTY;
		
		int maxAmount = 1;
		ItemStack output = craft(fusionShrineBlockEntity, world.getRegistryManager());
		if (!output.isEmpty()) {
			maxAmount = output.getMaxCount();
			for (IngredientStack ingredientStack : getIngredientStacks()) {
				for (int i = 0; i < fusionShrineBlockEntity.size(); i++) {
					ItemStack currentStack = fusionShrineBlockEntity.getStack(i);
					if (ingredientStack.test(currentStack)) {
						if (firstStack.isEmpty()) {
							firstStack = currentStack;
						}
						int ingredientStackAmount = ingredientStack.getCount();
						maxAmount = Math.min(maxAmount, currentStack.getCount() / ingredientStackAmount);
						break;
					}
				}
			}

			if (maxAmount > 0) {
				double efficiencyModifier = fusionShrineBlockEntity.getUpgradeHolder().getEffectiveValue(Upgradeable.UpgradeType.EFFICIENCY);
				decrementIngredients(world, fusionShrineBlockEntity, maxAmount, efficiencyModifier);
			}
		} else {
			for (IngredientStack ingredientStack : getIngredientStacks()) {
				double efficiencyModifier = fusionShrineBlockEntity.getUpgradeHolder().getEffectiveValue(Upgradeable.UpgradeType.EFFICIENCY);

				for (int i = 0; i < fusionShrineBlockEntity.size(); i++) {
					ItemStack currentStack = fusionShrineBlockEntity.getStack(i);
					if (ingredientStack.test(currentStack)) {
						int reducedAmountAfterMod = Support.getIntFromDecimalWithChance(ingredientStack.getCount() / efficiencyModifier, world.random);
						currentStack.decrement(reducedAmountAfterMod);
						break;
					}
				}
			}
		}

		if (this.copyComponents) {
			output = copyNbt(firstStack, output);
		}
		
		spawnCraftingResultAndXP(world, fusionShrineBlockEntity, output, maxAmount); // spawn results
	}
	
	private void decrementIngredients(World world, FusionShrineBlockEntity fusionShrineBlockEntity, int recipesCrafted, double efficiencyModifier) {
		for (IngredientStack ingredientStack : getIngredientStacks()) {
			for (int i = 0; i < fusionShrineBlockEntity.size(); i++) {
				ItemStack currentStack = fusionShrineBlockEntity.getStack(i);
				if (ingredientStack.test(currentStack)) {
					int reducedAmount = recipesCrafted * ingredientStack.getCount();
					int reducedAmountAfterMod = efficiencyModifier == 1 ? reducedAmount : Support.getIntFromDecimalWithChance(reducedAmount / efficiencyModifier, world.random);
					
					ItemStack currentRemainder = currentStack.getRecipeRemainder();
					currentStack.decrement(reducedAmountAfterMod);
					
					if (!currentRemainder.isEmpty()) {
						currentRemainder = currentRemainder.copy();
						currentRemainder.setCount(reducedAmountAfterMod);
						InventoryHelper.smartAddToInventory(currentRemainder, fusionShrineBlockEntity, null);
					}
					
					break;
				}
			}
		}
	}
	
	protected void spawnCraftingResultAndXP(@NotNull World world, @NotNull FusionShrineBlockEntity fusionShrineBlockEntity, @NotNull ItemStack stack, int recipeCount) {
		int resultAmountBeforeMod = recipeCount * stack.getCount();
		double yieldModifier = yieldUpgradesDisabled ? 1.0 : fusionShrineBlockEntity.getUpgradeHolder().getEffectiveValue(Upgradeable.UpgradeType.YIELD);
		int resultAmountAfterMod = Support.getIntFromDecimalWithChance(resultAmountBeforeMod * yieldModifier, world.random);
		
		int intExperience = Support.getIntFromDecimalWithChance(recipeCount * experience, world.random);
		MultiblockCrafter.spawnItemStackAsEntitySplitViaMaxCount(world, fusionShrineBlockEntity.getPos().up(2), stack, resultAmountAfterMod, MultiblockCrafter.RECIPE_STACK_VELOCITY);
		
		if (experience > 0) {
			MultiblockCrafter.spawnExperience(world, fusionShrineBlockEntity.getPos(), intExperience);
		}
		
		//only triggered on server side. Therefore, has to be sent to client via S2C packet
		fusionShrineBlockEntity.grantPlayerFusionCraftingAdvancement(stack, intExperience);
	}
	
	public boolean shouldPlayCraftingFinishedEffects() {
		return this.playCraftingFinishedEffects;
	}
	
	public static class Serializer implements GatedRecipeSerializer<FusionShrineRecipe> {
		
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
	
}
