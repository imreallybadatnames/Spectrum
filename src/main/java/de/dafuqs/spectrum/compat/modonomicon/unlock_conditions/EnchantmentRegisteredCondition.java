package de.dafuqs.spectrum.compat.modonomicon.unlock_conditions;

import com.google.gson.*;
import com.klikli_dev.modonomicon.book.conditions.*;
import com.klikli_dev.modonomicon.book.conditions.context.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.compat.modonomicon.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.registry.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class EnchantmentRegisteredCondition extends BookCondition {
    
    protected static final String TOOLTIP = "book.condition.tooltip." + SpectrumCommon.MOD_ID + ".enchantment_registered";
    
    protected Identifier enchantmentID;
    
    public EnchantmentRegisteredCondition(Text tooltip, Identifier enchantmentID) {
        super(tooltip);
        this.enchantmentID = enchantmentID;
    }
    
    public static EnchantmentRegisteredCondition fromJson(Identifier conditionParentId, JsonObject json, RegistryWrapper.WrapperLookup provider) {
        Identifier enchantmentID = Identifier.of(JsonHelper.getString(json, "enchantment_id"));
        Text tooltip = tooltipFromJson(json, provider);
        return new EnchantmentRegisteredCondition(tooltip, enchantmentID);
    }
    
    public static EnchantmentRegisteredCondition fromNetwork(RegistryByteBuf buffer) {
        var tooltip = buffer.readBoolean() ? TextCodecs.REGISTRY_PACKET_CODEC.decode(buffer) : null;
        var entryId = buffer.readIdentifier();
        return new EnchantmentRegisteredCondition(tooltip, entryId);
    }
    
    @Override
    public Identifier getType() {
        return ModonomiconCompat.ENCHANTMENT_REGISTERED;
    }
    
    @Override
    public void toNetwork(RegistryByteBuf buffer) {
        buffer.writeBoolean(this.tooltip != null);
        if (this.tooltip != null) {
            TextCodecs.REGISTRY_PACKET_CODEC.encode(buffer, this.tooltip);
        }
        buffer.writeIdentifier(this.enchantmentID);
    }
    
    @Override
    public boolean test(BookConditionContext context, PlayerEntity player) {
        return Registries.ENCHANTMENT.containsId(this.enchantmentID);
    }
    
    @Override
    public List<Text> getTooltip(PlayerEntity player, BookConditionContext context) {
        if (this.tooltip == null && context instanceof BookConditionEntryContext entryContext) {
            this.tooltip = Text.translatable(TOOLTIP, Text.translatable(entryContext.getBook().getEntry(this.enchantmentID).getName()));
        }
        return super.getTooltip(player, context);
    }
}
