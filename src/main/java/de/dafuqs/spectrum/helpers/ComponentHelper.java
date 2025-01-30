package de.dafuqs.spectrum.helpers;

import net.minecraft.component.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class ComponentHelper {
	
	public static <T> void setOrRemove(ItemStack stack, ComponentType<T> type, T value, boolean set) {
		if (set)
			stack.set(type, value);
		else
			stack.remove(type);
	}
	
	public static void setOrRemove(ItemStack stack, ComponentType<Unit> type, boolean set) {
		setOrRemove(stack, type, Unit.INSTANCE, set);
	}
	
}
