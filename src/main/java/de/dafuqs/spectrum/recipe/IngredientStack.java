package de.dafuqs.spectrum.recipe;

import com.mojang.serialization.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import net.fabricmc.fabric.api.recipe.v1.ingredient.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.predicate.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;

import java.util.*;

public class IngredientStack implements CustomIngredient {
	
	private final Ingredient ingredient;
	private final ComponentPredicate componentPredicate;
	private final int count;
	
	public static final IngredientStack EMPTY = new IngredientStack(Ingredient.EMPTY, null, 0);
	
	public IngredientStack(Ingredient ingredient, ComponentPredicate componentPredicate, int count) {
		this.ingredient = ingredient;
		this.componentPredicate = componentPredicate;
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}
	
	public static IngredientStack ofItems(int count, Item item) {
		return new IngredientStack(Ingredient.ofItems(item), null, count);
	}
	
	public static IngredientStack ofTag(TagKey<Item> tag, int count) {
		return new IngredientStack(Ingredient.fromTag(tag), null, count);
	}
	
	@Override
	public boolean test(ItemStack itemStack) {
		return this.ingredient.test(itemStack) && this.componentPredicate.test(itemStack.getComponents()) && this.count == itemStack.getCount();
	}
	
	@Override
	public List<ItemStack> getMatchingStacks() {
		return Arrays.stream(this.ingredient.getMatchingStacks()).toList();
	}
	
	@Override
	public boolean requiresTesting() {
		return true;
	}
	
	public boolean isEmpty() {
		return this == EMPTY || this.ingredient.isEmpty();
	}
	
	@Override
	public CustomIngredientSerializer<?> getSerializer() {
		return Serializer.INSTANCE;
	}
	
	public static class Serializer implements CustomIngredientSerializer<IngredientStack> {
		
		public static Serializer INSTANCE = new Serializer();
		
		public static final StructEndec<IngredientStack> ENDEC = StructEndecBuilder.of(
			CodecUtils.toEndec(Ingredient.DISALLOW_EMPTY_CODEC).fieldOf("ingredient", o -> o.ingredient),
			CodecUtils.toEndec(ComponentPredicate.CODEC).fieldOf("components", o -> o.componentPredicate),
			Endec.INT.fieldOf("count", o -> o.count),
			IngredientStack::new
		);
		
		@Override
		public Identifier getIdentifier() {
			return Identifier.of("spectrum:ingredient_stack");
		}
		
		@Override
		public MapCodec<IngredientStack> getCodec(boolean allowEmpty) {
			return CodecUtils.toMapCodec(ENDEC);
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, IngredientStack> getPacketCodec() {
			return CodecUtils.toPacketCodec(ENDEC);
		}
	}
}
