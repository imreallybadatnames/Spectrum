package de.dafuqs.spectrum.recipe.potion_workshop;

import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PotionWorkshopReactingRecipe extends GatedSpectrumRecipe<RecipeInput> implements DescriptiveGatedRecipe<RecipeInput> {
	
	protected static final HashMap<Item, List<PotionMod>> reagents = new HashMap<>();
	
	protected final Item item;
	protected final List<PotionMod> modifiers;
	
	public PotionWorkshopReactingRecipe(String group, boolean secret, Identifier requiredAdvancementIdentifier, Item item, List<PotionMod> modifiers) {
		super(group, secret, requiredAdvancementIdentifier);
		this.item = item;
		this.modifiers = modifiers;
		
		reagents.put(item, modifiers);
		
		registerInToastManager(getType(), this);
	}
	
	@Override
	public boolean matches(@NotNull RecipeInput inv, World world) {
		return false;
	}
	
	@Override
	public ItemStack craft(RecipeInput inventory, RegistryWrapper.WrapperLookup registryManager) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean fits(int width, int height) {
		return false;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registryManager) {
		return item.getDefaultStack();
	}
	
	@Override
	public ItemStack createIcon() {
		return SpectrumBlocks.POTION_WORKSHOP.asItem().getDefaultStack();
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.POTION_WORKSHOP_REACTING_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return SpectrumRecipeTypes.POTION_WORKSHOP_REACTING;
	}
	
	@Override
	public DefaultedList<Ingredient> getIngredients() {
		DefaultedList<Ingredient> defaultedList = DefaultedList.of();
		defaultedList.add(Ingredient.ofItems(this.item));
		return defaultedList;
	}
	
	@Override
	public Identifier getRecipeTypeUnlockIdentifier() {
		return PotionWorkshopRecipe.UNLOCK_IDENTIFIER;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "potion_workshop_reacting";
	}
	
	@Override
	public Text getDescription() {
		Identifier identifier = Registries.ITEM.getId(this.item);
		return Text.translatable("spectrum.rei.potion_workshop_reacting." + identifier.getNamespace() + "." + identifier.getPath());
	}
	
	@Override
	public Item getItem() {
		return this.item;
	}
	
	public static boolean isReagent(Item item) {
		return reagents.containsKey(item);
	}
	
	public static PotionMod combine(PotionMod potionMod, ItemStack reagentStack, Random random) {
		Item reagent = reagentStack.getItem();
		List<PotionMod> reagentMods = reagents.getOrDefault(reagent, null);
		if (reagentMods != null) {
			potionMod.modifyFrom(reagentMods.get(random.nextInt(reagentMods.size())));
		}
		return potionMod;
	}
	
}
