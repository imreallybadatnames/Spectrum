package de.dafuqs.spectrum.recipe.titration_barrel;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.blocks.titration_barrel.*;
import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.helpers.TimeHelper;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.transfer.v1.fluid.*;
import net.fabricmc.fabric.api.transfer.v1.storage.base.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.effect.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class TitrationBarrelRecipe extends GatedStackSpectrumRecipe<TitrationBarrelBlockEntity> implements ITitrationBarrelRecipe {
	
	public static final List<Integer> FERMENTATION_DURATION_DISPLAY_TIME_MULTIPLIERS = new ArrayList<>() {{
		add(1);
		add(10);
		add(100);
	}};
	
	public final List<IngredientStack> inputStacks;
	public final ItemStack outputItemStack;
	public final Item tappingItem;
	public final FluidIngredient fluid;
	
	public final int minFermentationTimeHours;
	public final FermentationData fermentationData;
	
	public TitrationBarrelRecipe(
			String group,
			boolean secret,
			Identifier requiredAdvancementIdentifier,
			List<IngredientStack> inputStacks,
			FluidIngredient fluid,
			ItemStack outputItemStack,
			Item tappingItem,
			int minFermentationTimeHours,
			FermentationData fermentationData
	) {
		super(group, secret, requiredAdvancementIdentifier);
		
		this.inputStacks = inputStacks;
		this.fluid = fluid;
		this.minFermentationTimeHours = minFermentationTimeHours;
		this.outputItemStack = outputItemStack;
		this.tappingItem = tappingItem;
		this.fermentationData = fermentationData;
		
		registerInToastManager(getType(), this);
	}
	
	@Override
	public boolean matches(TitrationBarrelBlockEntity inventory, World world) {
		SingleVariantStorage<FluidVariant> fluidStorage = inventory.getFluidStorage();
		if (!this.fluid.test(fluidStorage.variant)) {
			return false;
		}
		if (this.fluid != FluidIngredient.EMPTY) {
			if (fluidStorage.getAmount() != fluidStorage.getCapacity()) {
				return false;
			}
		}
		return matchIngredientStacksExclusively(inventory, getIngredientStacks());
	}
	
	@Override
	public List<IngredientStack> getIngredientStacks() {
		return this.inputStacks;
	}
	
	@Override
	public Item getTappingItem() {
		return tappingItem;
	}
	
	@Override
	public int getMinFermentationTimeHours() {
		return this.minFermentationTimeHours;
	}
	
	@Override
	public FermentationData getFermentationData() {
		return this.fermentationData;
	}
	
	@Override
	@Deprecated
	public ItemStack craft(TitrationBarrelBlockEntity inventory, RegistryWrapper.WrapperLookup drm) {
		return getDefaultTap(1).copy();
	}
	
	public ItemStack getPreviewTap(int timeMultiplier) {
		return tapWith(1.0F, this.minFermentationTimeHours * 60L * 60L * timeMultiplier, 0.4F); // downfall equals the one in plains
	}
	
	public ItemStack getDefaultTap(int timeMultiplier) {
		ItemStack stack = getPreviewTap(timeMultiplier);
		stack.setCount(this.outputItemStack.getCount());
		FermentedItem.setPreviewStack(stack);
		return stack;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registryManager) {
		return getDefaultTap(1);
	}
	
	// used for recipe viewers to show recipe outputs with a few example fermentation times
	public Collection<ItemStack> getOutputVariations(List<Integer> timeMultipliers) {
		List<ItemStack> list = new ArrayList<>();
		for (int timeMultiplier : timeMultipliers) {
			list.add(getDefaultTap(timeMultiplier));
		}
		return list;
	}
	
	@Override
	public FluidIngredient getFluidInput() {
		return fluid;
	}
	
	@Override
	public float getAngelsSharePerMcDay() {
		if (this.fermentationData == null) {
			return 0;
		}
		return this.fermentationData.angelsSharePercentPerMcDay();
	}
	
	@Override
	public ItemStack tap(Inventory inventory, long secondsFermented, float downfall) {
		int contentCount = InventoryHelper.countItemsInInventory(inventory);
		float thickness = getThickness(contentCount);
		return tapWith(thickness, secondsFermented, downfall);
	}
	
	private ItemStack tapWith(float thickness, long secondsFermented, float downfall) {
		ItemStack stack = this.outputItemStack.copy();
		stack.setCount(1);
		
		if (this.fermentationData == null) {
			return stack;
		} else {
			return getFermentedStack(this.fermentationData, thickness, secondsFermented, downfall, stack);
		}
	}
	
	public static ItemStack getFermentedStack(@NotNull FermentationData fermentationData, float thickness, long secondsFermented, float downfall, ItemStack inputStack) {
		float ageIngameDays = TimeHelper.minecraftDaysFromSeconds(secondsFermented);
		double alcPercent = 0;
		if (fermentationData.fermentationSpeedMod() > 0) {
			alcPercent = getAlcPercent(fermentationData.fermentationSpeedMod(), thickness, downfall, ageIngameDays);
			alcPercent = Math.max(0, alcPercent);
		}
		
		if (alcPercent >= 100) {
			return SpectrumItems.PURE_ALCOHOL.getDefaultStack();
		}
		
		// if it's not a set beverage (custom recipe) mark it as unknown
		if (!(inputStack.getItem() instanceof FermentedItem))
			inputStack.set(SpectrumDataComponentTypes.INFUSED_BEVERAGE, InfusedBeverageComponent.DEFAULT);
		
		var potionContents = inputStack.get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents != null) {
			float durationMultiplier = (float) (Support.logBase(1 + thickness, 2));
			
			List<StatusEffectInstance> effects = new ArrayList<>();
			for (FermentationStatusEffectEntry entry : fermentationData.statusEffectEntries()) {
				int potency = -1;
				int durationTicks = entry.baseDuration();
				for (FermentationStatusEffectEntry.StatusEffectPotencyEntry potencyEntry : entry.potencyEntries()) {
					if (thickness >= potencyEntry.minThickness() && alcPercent >= potencyEntry.minAlcPercent()) {
						potency = potencyEntry.potency();
					}
				}
				if (potency > -1)
					effects.add(new StatusEffectInstance(Registries.STATUS_EFFECT.entryOf(Registries.STATUS_EFFECT.getKey(entry.statusEffect()).get()), (int) (durationTicks * durationMultiplier), potency));
			}
			
			inputStack.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.empty(), effects));
		}
		
		inputStack.set(SpectrumDataComponentTypes.BEVERAGE, new BeverageComponent((long) ageIngameDays, (int) alcPercent, thickness));
		return inputStack;
	}
	
	protected static double getAlcPercent(float fermentationSpeedMod, float thickness, float downfall, float ageIngameDays) {
		return Support.logBase(1 + fermentationSpeedMod, ageIngameDays * (0.5D + thickness / 2D) * (0.5D + downfall / 2D));
	}
	
	protected float getThickness(int contentCount) {
		int inputStacksCount = 0;
		for (IngredientStack stack : inputStacks) {
			inputStacksCount += stack.getCount();
		}
		return contentCount / (float) inputStacksCount;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.TITRATION_BARREL;
	}
	
	// sadly we cannot use text.append() here, since the guidebook does not support it
	// but this way it might be easier for translations either way
	public static MutableText getDurationText(int minFermentationTimeHours, FermentationData fermentationData) {
		MutableText text;
		if (fermentationData == null) {
			if (minFermentationTimeHours == 1) {
				text = Text.translatable("container.spectrum.rei.titration_barrel.time_hour");
			} else if (minFermentationTimeHours == 24) {
				text = Text.translatable("container.spectrum.rei.titration_barrel.time_day");
			} else if (minFermentationTimeHours >= 72) {
				text = Text.translatable("container.spectrum.rei.titration_barrel.time_days", Support.getWithOneDecimalAfterComma(minFermentationTimeHours / 24F));
			} else {
				text = Text.translatable("container.spectrum.rei.titration_barrel.time_hours", minFermentationTimeHours);
			}
		} else {
			if (minFermentationTimeHours == 1) {
				text = Text.translatable("container.spectrum.rei.titration_barrel.at_least_time_hour");
			} else if (minFermentationTimeHours == 24) {
				text = Text.translatable("container.spectrum.rei.titration_barrel.at_least_time_day");
			} else if (minFermentationTimeHours > 72) {
				text = Text.translatable("container.spectrum.rei.titration_barrel.at_least_time_days", Support.getWithOneDecimalAfterComma(minFermentationTimeHours / 24F));
			} else {
				text = Text.translatable("container.spectrum.rei.titration_barrel.at_least_time_hours", minFermentationTimeHours);
			}
		}
		return text;
	}
	
	@Override
	public Identifier getRecipeTypeUnlockIdentifier() {
		return ITitrationBarrelRecipe.UNLOCK_ADVANCEMENT_IDENTIFIER;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "titration_barrel";
	}
	
	public static class Serializer implements RecipeSerializer<TitrationBarrelRecipe> {
		
		public static final MapCodec<TitrationBarrelRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
				Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
				Identifier.CODEC.optionalFieldOf("required_advancement", null).forGetter(recipe -> recipe.requiredAdvancementIdentifier),
				IngredientStack.Serializer.CODEC.codec().listOf().fieldOf("ingredient").forGetter(recipe -> recipe.inputStacks),
				FluidIngredient.CODEC.fieldOf("fluid").forGetter(recipe -> recipe.fluid),
				ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.outputItemStack),
				Registries.ITEM.getCodec().fieldOf("tapping_item").forGetter(recipe -> recipe.tappingItem),
				Codec.INT.fieldOf("min_fermentation_time_hours").forGetter(recipe -> recipe.minFermentationTimeHours),
				FermentationData.CODEC.fieldOf("fermentation_data").forGetter(recipe -> recipe.fermentationData)
		).apply(i, TitrationBarrelRecipe::new));
		
		private static final PacketCodec<RegistryByteBuf, TitrationBarrelRecipe> PACKET_CODEC = PacketCodecHelper.tuple(
				PacketCodecs.STRING, c -> c.group,
				PacketCodecs.BOOL, c -> c.secret,
				PacketCodecHelper.nullable(Identifier.PACKET_CODEC), c -> c.requiredAdvancementIdentifier,
				IngredientStack.Serializer.PACKET_CODEC.collect(PacketCodecs.toList()), c -> c.inputStacks,
				FluidIngredient.PACKET_CODEC, c -> c.fluid,
				ItemStack.PACKET_CODEC, c -> c.outputItemStack,
				PacketCodecs.registryValue(RegistryKeys.ITEM), recipe -> recipe.tappingItem,
				PacketCodecs.VAR_INT, recipe -> recipe.minFermentationTimeHours,
				FermentationData.PACKET_CODEC, recipe -> recipe.fermentationData,
				TitrationBarrelRecipe::new
		);
		
		@Override
		public MapCodec<TitrationBarrelRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, TitrationBarrelRecipe> packetCodec() {
			return PACKET_CODEC;
		}
		
	}
	
}
