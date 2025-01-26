package de.dafuqs.spectrum.recipe.ink_converting;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import io.wispforest.owo.serialization.endec.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.world.*;

import java.util.*;

public class InkConvertingRecipe extends GatedSpectrumRecipe<RecipeInput> {
	
	public static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("midgame/place_color_picker");
	protected static final List<Item> INPUT_ITEMS = new ArrayList<>();
	
	protected final Ingredient inputIngredient;
	protected final InkColor color;
	protected final long amount;
	
	public InkConvertingRecipe(String group, boolean secret, Identifier requiredAdvancementIdentifier, Ingredient inputIngredient, InkColor color, long amount) {
		super(group, secret, requiredAdvancementIdentifier);
		
		this.inputIngredient = inputIngredient;
		this.color = color;
		this.amount = amount;
		
		for (ItemStack itemStack : inputIngredient.getMatchingStacks()) {
			Item item = itemStack.getItem();
			if (!INPUT_ITEMS.contains(item)) {
				INPUT_ITEMS.add(item);
			}
		}
		
		registerInToastManager(getType(), this);
	}
	
	public static boolean isInput(Item item) {
		return INPUT_ITEMS.contains(item);
	}
	
	@Override
	public boolean matches(RecipeInput inv, World world) {
		return this.inputIngredient.test(inv.getStackInSlot(0));
	}
	
	@Override
	public ItemStack craft(RecipeInput inv, RegistryWrapper.WrapperLookup drm) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean fits(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registryManager) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(SpectrumBlocks.COLOR_PICKER);
	}
	
	@Override
	public Identifier getRecipeTypeUnlockIdentifier() {
		return UNLOCK_IDENTIFIER;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.INK_CONVERTING_RECIPE_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return SpectrumRecipeTypes.INK_CONVERTING;
	}
	
	@Override
	public DefaultedList<Ingredient> getIngredients() {
		DefaultedList<Ingredient> defaultedList = DefaultedList.of();
		defaultedList.add(this.inputIngredient);
		return defaultedList;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "ink_converting";
	}
	
	public InkColor getInkColor() {
		return this.color;
	}
	
	public long getInkAmount() {
		return this.amount;
	}
	
	public static class Serializer implements GatedRecipeSerializer<InkConvertingRecipe> {
		
		public static final StructEndec<InkConvertingRecipe> ENDEC = StructEndecBuilder.of(
				Endec.STRING.optionalFieldOf("group", recipe -> recipe.group, ""),
				Endec.BOOLEAN.optionalFieldOf("secret", recipe -> recipe.secret, false),
				MinecraftEndecs.IDENTIFIER.fieldOf("required_advancement", recipe -> recipe.requiredAdvancementIdentifier),
				CodecUtils.toEndec(Ingredient.DISALLOW_EMPTY_CODEC).fieldOf("ingredient", recipe -> recipe.inputIngredient),
				CodecUtils.toEndec(InkColor.CODEC).fieldOf("ink_color", recipe -> recipe.color),
				Endec.LONG.fieldOf("amount", recipe -> recipe.amount),
				InkConvertingRecipe::new
		);
		
		@Override
		public MapCodec<InkConvertingRecipe> codec() {
			return CodecUtils.toMapCodec(ENDEC);
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, InkConvertingRecipe> packetCodec() {
			return CodecUtils.toPacketCodec(ENDEC);
		}
		
	}
	
}
