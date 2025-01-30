package de.dafuqs.spectrum.compat.modonomicon.client.pages;

import com.mojang.blaze3d.systems.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.compat.modonomicon.pages.*;
import de.dafuqs.spectrum.items.magic_items.*;
import de.dafuqs.spectrum.recipe.enchanter.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.world.*;

public class BookEnchanterCraftingPageRenderer extends BookGatedRecipePageRenderer<EnchanterRecipe, BookGatedRecipePage<EnchanterRecipe>> {

    private static final Identifier BACKGROUND_TEXTURE = SpectrumCommon.locate("textures/gui/modonomicon/enchanter_crafting.png");

    public BookEnchanterCraftingPageRenderer(BookGatedRecipePage<EnchanterRecipe> page) {
        super(page);
    }

    @Override
    protected int getRecipeHeight() {
        return 94;
    }

    @Override
    protected void drawRecipe(DrawContext drawContext, RecipeEntry<EnchanterRecipe> recipeEntry, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        EnchanterRecipe recipe = recipeEntry.value();
        World world = MinecraftClient.getInstance().world;
        if (world == null) return;

        RenderSystem.enableBlend();
        drawContext.drawTexture(BACKGROUND_TEXTURE, recipeX, recipeY, 0, 0, 100, 80, 256, 256);

        renderTitle(drawContext, recipeY, second);

        // the ingredients
        DefaultedList<Ingredient> ingredients = recipe.getIngredients();

        int ingredientX = recipeX - 3;

        // surrounding input slots
        parentScreen.renderIngredient(drawContext, ingredientX + 16, recipeY, mouseX, mouseY, ingredients.get(1));
        parentScreen.renderIngredient(drawContext, ingredientX + 40, recipeY, mouseX, mouseY, ingredients.get(2));
        parentScreen.renderIngredient(drawContext, ingredientX + 56, recipeY + 16, mouseX, mouseY, ingredients.get(3));
        parentScreen.renderIngredient(drawContext, ingredientX + 56, recipeY + 40, mouseX, mouseY, ingredients.get(4));
        parentScreen.renderIngredient(drawContext, ingredientX + 40, recipeY + 56, mouseX, mouseY, ingredients.get(5));
        parentScreen.renderIngredient(drawContext, ingredientX + 16, recipeY + 56, mouseX, mouseY, ingredients.get(6));
        parentScreen.renderIngredient(drawContext, ingredientX, recipeY + 40, mouseX, mouseY, ingredients.get(7));
        parentScreen.renderIngredient(drawContext, ingredientX, recipeY + 16, mouseX, mouseY, ingredients.get(8));

        // center input slot
        parentScreen.renderIngredient(drawContext, ingredientX + 28, recipeY + 28, mouseX, mouseY, ingredients.get(0));

        // Knowledge Gem and Enchanter
        ItemStack knowledgeDropStackWithXP = KnowledgeGemItem.getKnowledgeDropStackWithXP(recipe.getRequiredExperience(), true);
        parentScreen.renderItemStack(drawContext, recipeX + 81, recipeY + 9, mouseX, mouseY, knowledgeDropStackWithXP);
        parentScreen.renderItemStack(drawContext, recipeX + 81, recipeY + 46, mouseX, mouseY, SpectrumBlocks.ENCHANTER.asItem().getDefaultStack());

        // the output
        parentScreen.renderItemStack(drawContext, recipeX + 81, recipeY + 31, mouseX, mouseY, recipe.getResult(world.getRegistryManager()));
    }

}
