package de.dafuqs.spectrum.compat.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.conditions.BookNoneCondition;
import com.klikli_dev.modonomicon.book.page.BookSpotlightPage;
import com.klikli_dev.modonomicon.util.BookGsonHelper;
import com.mojang.datafixers.util.Either;
import de.dafuqs.spectrum.compat.modonomicon.ModonomiconCompat;
import de.dafuqs.spectrum.helpers.NbtHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;

public class BookNbtSpotlightPage extends BookSpotlightPage {

    public BookNbtSpotlightPage(BookTextHolder title, BookTextHolder text, Ingredient item, String anchor, BookCondition condition) {
        super(title, text, Either.right(item), anchor, condition);
    }

    public static BookNbtSpotlightPage fromJson(Identifier entryId, JsonObject json, RegistryWrapper.WrapperLookup provider) {
        var title = BookGsonHelper.getAsBookTextHolder(json, "title", BookTextHolder.EMPTY, provider);
        var item = getAsIngredientWithNbt(JsonHelper.getObject(json, "item"));
        var text = BookGsonHelper.getAsBookTextHolder(json, "text", BookTextHolder.EMPTY, provider);
        var anchor = JsonHelper.getString(json, "anchor", "");
        var condition = json.has("condition")
                ? BookCondition.fromJson(entryId, json.getAsJsonObject("condition"), provider)
                : new BookNoneCondition();
        return new BookNbtSpotlightPage(title, text, item, anchor, condition);
    }

    public static BookNbtSpotlightPage fromNetwork(RegistryByteBuf buffer) {
        var title = BookTextHolder.fromNetwork(buffer);
        var item = Ingredient.PACKET_CODEC.decode(buffer);
        var text = BookTextHolder.fromNetwork(buffer);
        var anchor = buffer.readString();
        var condition = BookCondition.fromNetwork(buffer);
        return new BookNbtSpotlightPage(title, text, item, anchor, condition);
    }

    private static Ingredient getAsIngredientWithNbt(JsonObject item) {
        List<ItemStack> stacks = new ArrayList<>();

        if (item.has("item")) {
            stacks.add(ShapedRecipe.getItem(item).getDefaultStack());
        }

        if (item.has("tag")) {
            Identifier identifier = Identifier.of(JsonHelper.getString(item, "tag"));
            TagKey<Item> key = TagKey.of(RegistryKeys.ITEM, identifier);

            for (RegistryEntry<Item> entry : Registries.ITEM.iterateEntries(key)) {
                stacks.add(entry.value().getDefaultStack());
            }
        }

        if (item.has("nbt")) {
            NbtCompound nbt = NbtHelper.fromJsonObject(JsonHelper.getObject(item, "nbt"));

            for (ItemStack stack : stacks) {
                stack.setNbt(nbt);
            }
        }

        return Ingredient.ofStacks(stacks.stream());
    }

    @Override
    public Identifier getType() {
        return ModonomiconCompat.NBT_SPOTLIGHT_PAGE;
    }

}
