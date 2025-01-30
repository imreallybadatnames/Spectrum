package de.dafuqs.spectrum.recipe.crystallarieum;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CrystallarieumRecipe extends GatedSpectrumRecipe<SingleStackRecipeInput> {

	public static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/blocks/crystallarieum");

	protected final static Map<BlockState, RecipeEntry<CrystallarieumRecipe>> STATE_CACHE = new HashMap<>();

	protected final Ingredient ingredient;
	protected final List<BlockState> growthStages;
	protected final int secondsPerGrowthStage;
	protected final InkColor inkColor;
	protected final int inkPerSecond;
	protected final boolean growsWithoutCatalyst;
	protected final List<CrystallarieumCatalyst> catalysts;
	protected final List<ItemStack> additionalResults; // these aren't actual results, but recipe managers will treat it as such, showing this recipe as a way to get them. Use for drops of the growth blocks, for example

	public CrystallarieumRecipe(String group, boolean secret, Identifier requiredAdvancementIdentifier, Ingredient ingredient, List<BlockState> growthStages, int secondsPerGrowthStage, InkColor inkColor, int inkPerSecond, boolean growsWithoutCatalyst, List<CrystallarieumCatalyst> catalysts, List<ItemStack> additionalResults) {
		super(group, secret, requiredAdvancementIdentifier);

		this.ingredient = ingredient;
		this.growthStages = growthStages;
		this.secondsPerGrowthStage = secondsPerGrowthStage;
		this.inkColor = inkColor;
		this.inkPerSecond = inkPerSecond;
		this.growsWithoutCatalyst = growsWithoutCatalyst;
		this.catalysts = catalysts;
		this.additionalResults = additionalResults;
		
		registerInToastManager(getType(), this);
	}
	
	@Nullable
	public static RecipeEntry<CrystallarieumRecipe> getRecipeForState(World world, BlockState state) {
		return STATE_CACHE.computeIfAbsent(state, s -> {
			var recipes = world.getRecipeManager().listAllOfType(SpectrumRecipeTypes.CRYSTALLARIEUM);
			for (var recipe : recipes) {
				if (recipe.value().growthStages.contains(s))
					return recipe;
			}
			return null;
		});
	}
	
	@Override
	public boolean matches(SingleStackRecipeInput input, World world) {
		return ingredient.test(input.getStackInSlot(0));
	}

	@Override
	@Deprecated
	public ItemStack craft(SingleStackRecipeInput inv, RegistryWrapper.WrapperLookup registryLookup) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean fits(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registryLookup) {
		List<BlockState> states = getGrowthStages();
		return states.getLast().getBlock().asItem().getDefaultStack();
	}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(SpectrumBlocks.CRYSTALLARIEUM);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.CRYSTALLARIEUM_RECIPE_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return SpectrumRecipeTypes.CRYSTALLARIEUM;
	}
	
	@Override
	public Identifier getRecipeTypeUnlockIdentifier() {
		return UNLOCK_IDENTIFIER;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "crystallarieum_growing";
	}
	
	@Override
	public DefaultedList<Ingredient> getIngredients() {
		DefaultedList<Ingredient> defaultedList = DefaultedList.of();
		defaultedList.add(ingredient);
		return defaultedList;
	}
	
	public Ingredient getIngredientStack() {
		return ingredient;
	}
	
	public CrystallarieumCatalyst getCatalyst(ItemStack itemStack) {
		for (CrystallarieumCatalyst catalyst : this.catalysts) {
			if (catalyst.ingredient().test(itemStack)) {
				return catalyst;
			}
		}
		return CrystallarieumCatalyst.EMPTY;
	}
	
	public List<BlockState> getGrowthStages() {
		return growthStages;
	}
	
	public int getSecondsPerGrowthStage() {
		return secondsPerGrowthStage;
	}
	
	public InkColor getInkColor() {
		return inkColor;
	}
	
	public int getInkPerSecond() {
		return inkPerSecond;
	}

	public boolean growsWithoutCatalyst() {
		return growsWithoutCatalyst;
	}

	public List<CrystallarieumCatalyst> getCatalysts() {
		return this.catalysts;
	}
	
	public List<ItemStack> getAdditionalResults() {
		return additionalResults;
	}
	
	public Optional<BlockState> getNextState(RecipeEntry<CrystallarieumRecipe> recipe, BlockState currentState) {
		for (Iterator<BlockState> it = recipe.value().getGrowthStages().iterator(); it.hasNext(); ) {
			BlockState state = it.next();
			if (state.equals(currentState)) {
				if (it.hasNext()) {
					return Optional.of(it.next());
				}
			}
		}
		return Optional.empty();
	}

}
