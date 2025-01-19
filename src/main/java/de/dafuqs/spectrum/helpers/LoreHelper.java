package de.dafuqs.spectrum.helpers;

import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.item.*;
import net.minecraft.text.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class LoreHelper {
	
	public static @NotNull List<Text> getLoreTextArrayFromString(@NotNull String string) {
		List<Text> loreText = new ArrayList<>();
		
		for (String split : string.split("\\\\n")) {
			loreText.addFirst(Text.literal(split));
		}
		
		return loreText;
	}
	
	public static @NotNull String getStringFromLoreTextArray(@NotNull List<Text> lore) {
		if (lore.isEmpty()) {
			return "";
		} else {
			StringBuilder loreString = new StringBuilder();
			for (int i = 0; i < lore.size(); i++) {
				loreString.append(lore.get(i).getString());
				if (i != lore.size() - 1) {
					loreString.append("\\n");
				}
			}
			return loreString.toString();
		}
	}
	
	public static void setLore(@NotNull ItemStack itemStack, @Nullable List<Text> lore) {
		if (lore == null || lore.isEmpty()) {
			itemStack.remove(DataComponentTypes.LORE);
		} else {
			LoreComponent component = new LoreComponent(lore);
			itemStack.set(DataComponentTypes.LORE, component);
		}
	}
	
	public static void setLore(@NotNull ItemStack stack, @Nullable Text lore) {
		if (lore == null) {
			stack.remove(DataComponentTypes.LORE);
		} else {
			LoreComponent component = new LoreComponent(List.of(lore));
			stack.set(DataComponentTypes.LORE, component);
		}
	}
	
	public static void removeLore(@NotNull ItemStack itemStack) {
		itemStack.remove(DataComponentTypes.LORE);
	}
	
	public static boolean hasLore(@NotNull ItemStack itemStack) {
		return itemStack.get(DataComponentTypes.LORE) == null;
	}
	
	public static @NotNull List<Text> getLoreList(@NotNull ItemStack itemStack) {
		LoreComponent component = itemStack.get(DataComponentTypes.LORE);
		if (component == null) {
			return new ArrayList<>();
		}
		return component.lines();
	}
	
	public static boolean equalsLore(List<Text> lore, ItemStack stack) {
		if (hasLore(stack)) {
			LoreComponent component = stack.get(DataComponentTypes.LORE);
			if (component == null) {
				return lore.isEmpty();
			}
			return component.lines().equals(lore);
		}
		return false;
	}
	
}
