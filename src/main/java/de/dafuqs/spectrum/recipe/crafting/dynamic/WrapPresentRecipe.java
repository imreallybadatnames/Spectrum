package de.dafuqs.spectrum.recipe.crafting.dynamic;

import de.dafuqs.spectrum.blocks.present.*;
import de.dafuqs.spectrum.items.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.*;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class WrapPresentRecipe extends SpecialCraftingRecipe {
	public static final RecipeSerializer<WrapPresentRecipe> SERIALIZER = new SpecialRecipeSerializer<>(WrapPresentRecipe::new);
	
	public WrapPresentRecipe(CraftingRecipeCategory category) {
		super(CraftingRecipeCategory.MISC);
	}
	
	@Override
	public DefaultedList<Ingredient> getIngredients() {
		DefaultedList<Ingredient> list = DefaultedList.ofSize(1, Ingredient.EMPTY);
		list.set(0, Ingredient.ofStacks(SpectrumBlocks.PRESENT.asItem().getDefaultStack()));
		return list;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registryLookup) {
		ItemStack stack = SpectrumBlocks.PRESENT.asItem().getDefaultStack();
		PresentItem.wrap(stack, PresentBlock.WrappingPaper.RED, Map.of());
		return stack;
	}
	
	@Override
	public boolean matches(@NotNull CraftingRecipeInput input, World world) {
		boolean presentItemFound = false;
		boolean wrappingItemFound = false;
		
		for (int j = 0; j < input.getSize(); ++j) {
			ItemStack itemStack = input.getStackInSlot(j);
			if (!itemStack.isEmpty()) {
				if (itemStack.getItem() instanceof PresentItem) {
					if (presentItemFound || PresentItem.isWrapped(itemStack)) {
						return false;
					}
					presentItemFound = true;
				} else if (!wrappingItemFound && getPresentVariantForStack(itemStack) != null) {
					wrappingItemFound = true;
				} else if (!(itemStack.getItem() instanceof PigmentItem)) {
					return false;
				}
			}
		}
		
		return presentItemFound;
	}
	
	@Override
	public ItemStack craft(@NotNull CraftingRecipeInput input, RegistryWrapper.WrapperLookup registryLookup) {
		ItemStack presentStack = ItemStack.EMPTY;
		PresentBlock.WrappingPaper wrappingPaper = PresentBlock.WrappingPaper.RED;
		Map<DyeColor, Integer> colors = new HashMap<>();
		
		for (int j = 0; j < input.getSize(); ++j) {
			ItemStack stack = input.getStackInSlot(j);
			if (stack.getItem() instanceof PresentItem) {
				presentStack = stack.copy();
			} else if (stack.getItem() instanceof PigmentItem pigmentItem) {
				DyeColor color = pigmentItem.getColor();
				if (colors.containsKey(color)) {
					colors.put(color, colors.get(color) + 1);
				} else {
					colors.put(color, 1);
				}
			}
			PresentBlock.WrappingPaper stackWrappingPaper = getPresentVariantForStack(stack);
			if (stackWrappingPaper != null) {
				wrappingPaper = stackWrappingPaper;
			}
		}
		
		if (!presentStack.isEmpty()) {
			PresentItem.wrap(presentStack, wrappingPaper, colors);
		}
		return presentStack;
	}
	
	public @Nullable PresentBlock.WrappingPaper getPresentVariantForStack(@NotNull ItemStack stack) {
		Item item = stack.getItem();
		if (item == Items.RED_DYE) {
			return PresentBlock.WrappingPaper.RED;
		} else if (item == Items.BLUE_DYE) {
			return PresentBlock.WrappingPaper.BLUE;
		} else if (item == Items.CYAN_DYE) {
			return PresentBlock.WrappingPaper.CYAN;
		} else if (item == Items.GREEN_DYE) {
			return PresentBlock.WrappingPaper.GREEN;
		} else if (item == Items.PURPLE_DYE) {
			return PresentBlock.WrappingPaper.PURPLE;
		} else if (item == Items.CAKE) {
			return PresentBlock.WrappingPaper.CAKE;
		} else if (stack.isIn(ItemTags.FLOWERS)) {
			return PresentBlock.WrappingPaper.STRIPED;
		} else if (item == Items.FIREWORK_STAR) {
			return PresentBlock.WrappingPaper.STARRY;
		} else if (item == Items.SNOWBALL) {
			return PresentBlock.WrappingPaper.WINTER;
		} else if (item == Items.SPORE_BLOSSOM) {
			return PresentBlock.WrappingPaper.PRIDE;
		}
		return null;
	}
	
	@Override
	public boolean fits(int width, int height) {
		return width * height >= 1;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}
	
}
