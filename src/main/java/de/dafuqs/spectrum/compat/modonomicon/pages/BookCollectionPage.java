package de.dafuqs.spectrum.compat.modonomicon.pages;

import com.google.gson.*;
import com.klikli_dev.modonomicon.*;
import com.klikli_dev.modonomicon.book.*;
import com.klikli_dev.modonomicon.book.conditions.*;
import com.klikli_dev.modonomicon.book.entries.BookContentEntry;
import com.klikli_dev.modonomicon.book.page.*;
import com.klikli_dev.modonomicon.util.*;
import com.mojang.brigadier.*;
import com.mojang.brigadier.exceptions.*;
import de.dafuqs.spectrum.compat.modonomicon.*;
import net.minecraft.client.resource.language.*;
import net.minecraft.command.*;
import net.minecraft.command.argument.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class BookCollectionPage extends BookTextPage {

    private final List<String> itemStrings;
    private final List<ItemStack> items;

    public BookCollectionPage(BookTextHolder title, BookTextHolder text, boolean useMarkdownInTitle, boolean showTitleSeparator, String anchor, BookCondition condition, List<String> itemStrings) {
        super(title, text, useMarkdownInTitle, showTitleSeparator, anchor, condition);
        this.itemStrings = itemStrings;
        this.items = new ArrayList<>(itemStrings.size());
    }

    public static BookCollectionPage fromJson(Identifier entryId, JsonObject json, RegistryWrapper.WrapperLookup provider) {
        var title = BookGsonHelper.getAsBookTextHolder(json, "title", BookTextHolder.EMPTY, provider);
        var useMarkdownInTitle = JsonHelper.getBoolean(json, "use_markdown_title", false);
        var showTitleSeparator = JsonHelper.getBoolean(json, "show_title_separator", true);
        var text = BookGsonHelper.getAsBookTextHolder(json, "text", BookTextHolder.EMPTY, provider);
        var anchor = JsonHelper.getString(json, "anchor", "");
        var condition = json.has("condition")
                ? BookCondition.fromJson(entryId, json.getAsJsonObject("condition"), provider)
                : new BookNoneCondition();
        var items = JsonHelper.getArray(json, "items", new JsonArray()).asList().stream().map(JsonElement::getAsString).toList();
        return new BookCollectionPage(title, text, useMarkdownInTitle, showTitleSeparator, anchor, condition, items);
    }

    public static BookCollectionPage fromNetwork(RegistryByteBuf buffer) {
        var title = BookTextHolder.fromNetwork(buffer);
        var useMarkdownInTitle = buffer.readBoolean();
        var showTitleSeparator = buffer.readBoolean();
        var text = BookTextHolder.fromNetwork(buffer);
        var anchor = buffer.readString();
        var condition = BookCondition.fromNetwork(buffer);
        var items = buffer.readList(PacketByteBuf::readString);
        return new BookCollectionPage(title, text, useMarkdownInTitle, showTitleSeparator, anchor, condition, items);
    }

    public List<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public Identifier getType() {
        return ModonomiconCompat.COLLECTION_PAGE;
    }

    @Override
    public void build(World world, BookContentEntry parentEntry, int pageNum) {
        super.build(world, parentEntry, pageNum);

        for (String itemString : itemStrings) {
            try {
                CommandRegistryAccess access = CommandRegistryAccess.of(world.getRegistryManager(), world.getEnabledFeatures());
                ItemStackArgumentType argumentType = new ItemStackArgumentType(access);
                ItemStackArgument argument = argumentType.parse(new StringReader(itemString));
                items.add(argument.createStack(1, false));
            } catch (CommandSyntaxException e) {
                Modonomicon.LOG.warn("Unable to parse stack {} in collection page", itemString);
            }
        }
    }

    @Override
    public void toNetwork(RegistryByteBuf buffer) {
        super.toNetwork(buffer);
        buffer.writeCollection(this.itemStrings, PacketByteBuf::writeString);
    }

    @Override
    public boolean matchesQuery(String query) {
        return super.matchesQuery(query)
                || items.stream()
                .map(ItemStack::getTranslationKey)
                .map(I18n::translate)
                .map(String::toLowerCase)
                .anyMatch(string -> string.contains(query));
    }

}
