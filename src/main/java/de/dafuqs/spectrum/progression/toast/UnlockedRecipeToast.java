package de.dafuqs.spectrum.progression.toast;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import it.unimi.dsi.fastutil.objects.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.font.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.sound.*;
import net.minecraft.client.toast.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.effect.*;
import net.minecraft.item.*;
import net.minecraft.registry.entry.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class UnlockedRecipeToast implements Toast {
	
	private final Identifier TEXTURE = SpectrumCommon.locate("textures/gui/toasts.png");
	private final Text title;
	private final Text text;
	private final List<ItemStack> itemStacks;
	private final SoundEvent soundEvent = SpectrumSoundEvents.NEW_RECIPE;
	private boolean soundPlayed;
	
	public UnlockedRecipeToast(Text title, Text text, List<ItemStack> itemStacks) {
		this.title = title;
		this.text = text;
		this.itemStacks = itemStacks;
		this.soundPlayed = false;
	}
	
	public static void showRecipeToast(@NotNull MinecraftClient client, ItemStack itemStack, Text title) {
		Text text = getTextForItemStack(itemStack);
		client.getToastManager().add(new UnlockedRecipeToast(title, text, new ArrayList<>() {{
			add(itemStack);
		}}));
	}
	
	public static void showRecipeGroupToast(@NotNull MinecraftClient client, String groupName, List<ItemStack> itemStacks, Text title) {
		Text text = Text.translatable("recipeGroup.spectrum." + groupName);
		client.getToastManager().add(new UnlockedRecipeToast(title, text, itemStacks));
	}
	
	public static void showLotsOfRecipesToast(@NotNull MinecraftClient client, List<ItemStack> itemStacks) {
		client.getToastManager().add(new UnlockedRecipeToast(
				Text.translatable("spectrum.toast.lots_of_recipes_unlocked.title"),
				Text.translatable("spectrum.toast.lots_of_recipes_unlocked.description", itemStacks.size()),
				itemStacks));
	}
	
	public static Text getTextForItemStack(@NotNull ItemStack itemStack) {
		if (itemStack.isOf(Items.ENCHANTED_BOOK)) {
			// special handling for enchanted books
			// Instead of the text "enchanted book" the toast will
			// read the first stored enchantment in the book
			var enchantments = itemStack.getEnchantments().getEnchantmentEntries();
			// TODO - Review
			if (!enchantments.isEmpty()) {
				Object2IntMap.Entry<RegistryEntry<Enchantment>> enchantEntry = enchantments.iterator().next();
				return Text.translatable(enchantEntry.getKey().getIdAsString());
			}
		} else if (itemStack.isOf(Items.POTION)) {
			// special handling for potions
			// use the name of the first custom potion effect
			List<StatusEffectInstance> effects = itemStack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT).customEffects();
			if (!effects.isEmpty()) {
				return Text.translatable(effects.getFirst().getTranslationKey()).append(" ").append(Text.translatable("item.minecraft.potion"));
			}
		}
		return itemStack.getName();
	}
	
	@Override
	public Toast.Visibility draw(DrawContext drawContext, @NotNull ToastManager manager, long startTime) {
		drawContext.drawTexture(TEXTURE, 0, 0, 0, 32, this.getWidth(), this.getHeight());
		
		MinecraftClient client = manager.getClient();
		TextRenderer textRenderer = client.textRenderer;
		drawContext.drawText(textRenderer, title, 30, 7, RenderHelper.GREEN_COLOR, false);
		drawContext.drawText(textRenderer, text, 30, 18, 0, false);
		
		long toastTimeMilliseconds = SpectrumCommon.CONFIG.ToastTimeMilliseconds;
		if (!this.soundPlayed && startTime > 0L) {
			this.soundPlayed = true;
			if (this.soundEvent != null) {
				manager.getClient().getSoundManager().play(PositionedSoundInstance.master(this.soundEvent, 1.0F, 1.0F));
			}
		}
		
		int itemStackIndex = (int) (startTime / Math.max(1, toastTimeMilliseconds / this.itemStacks.size()) % this.itemStacks.size());
		drawContext.drawItem(itemStacks.get(itemStackIndex), 8, 8);

		return startTime >= toastTimeMilliseconds ? Visibility.HIDE : Visibility.SHOW;
	}
	
}
