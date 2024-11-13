package de.dafuqs.spectrum.api.recipe;

import com.google.gson.*;
import net.minecraft.network.*;
import net.minecraft.recipe.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public interface GatedRecipeSerializer<T extends Recipe<?>> extends RecipeSerializer<T> {
	
	static void writeNullableIdentifier(PacketByteBuf buf, @Nullable Identifier identifier) {
		if (identifier == null) {
			buf.writeBoolean(false);
		} else {
			buf.writeBoolean(true);
			buf.writeIdentifier(identifier);
		}
	}
	
	static @Nullable Identifier readNullableIdentifier(PacketByteBuf buf) {
		boolean notNull = buf.readBoolean();
		if (notNull) {
			return buf.readIdentifier();
		}
		return null;
	}

	static @NotNull FluidIngredient readFluidIngredient(PacketByteBuf buf) {
		boolean isTag = buf.readBoolean();
		Identifier id = readNullableIdentifier(buf);
		return FluidIngredient.fromIdentifier(id, isTag);
	}

	static void writeFluidIngredient(PacketByteBuf buf, @NotNull FluidIngredient ingredient) {
		Objects.requireNonNull(ingredient);
		buf.writeBoolean(ingredient.isTag());
		writeNullableIdentifier(buf, ingredient.id());
	}

}
