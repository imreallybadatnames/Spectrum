package de.dafuqs.spectrum.recipe.spirit_instiller.dynamic.spawner_manipulation;


import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.type.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.recipe.*;
import net.minecraft.text.*;

public class SpawnerMaxNearbyEntitiesChangeRecipe extends SpawnerChangeRecipe {
	
	public static final RecipeSerializer<SpawnerMaxNearbyEntitiesChangeRecipe> SERIALIZER = new EmptyRecipeSerializer<>(SpawnerMaxNearbyEntitiesChangeRecipe::new);
	protected static final int DEFAULT_MAX_ENTITIES = 6;
	protected static final int MAX_MAX_ENTITIES = 40;
	
	public SpawnerMaxNearbyEntitiesChangeRecipe() {
		super(IngredientStack.ofItems(4, SpectrumItems.MERMAIDS_GEM));
	}
	
	@Override
	public boolean canCraftWithBlockEntityTag(NbtComponent spawnerBlockEntityNbt, ItemStack leftBowlStack, ItemStack rightBowlStack) {
		if (spawnerBlockEntityNbt.contains("MaxNearbyEntities")) {
			return spawnerBlockEntityNbt.copyNbt().getShort("MaxNearbyEntities") < MAX_MAX_ENTITIES;
		}
		return true;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}
	
	@Override
	public Text getOutputLoreText() {
		return Text.translatable("recipe.spectrum.spawner.lore.increased_max_nearby_entities");
	}
	
	@Override
	public NbtCompound getSpawnerResultNbt(NbtCompound spawnerBlockEntityNbt, ItemStack firstBowlStack, ItemStack secondBowlStack) {
		// Default spawner tag:
		/* BlockEntityTag: {
			MaxNearbyEntities: 6s,
			RequiredPlayerRange: 16s,
			SpawnCount: 4s,
			SpawnData: {entity: {id: "minecraft:xxx"}},
			MaxSpawnDelay: 800s,
			SpawnRange: 4s,
			MinSpawnDelay: 200s,
			SpawnPotentials: []
		   }
		 */
		
		short maxNearbyEntities = DEFAULT_MAX_ENTITIES;
		if (spawnerBlockEntityNbt.contains("MaxNearbyEntities", NbtElement.SHORT_TYPE)) {
			maxNearbyEntities = spawnerBlockEntityNbt.getShort("MaxNearbyEntities");
		}
		spawnerBlockEntityNbt.putShort("MaxNearbyEntities", (short) Math.min(MAX_MAX_ENTITIES, maxNearbyEntities + 1));
		
		return spawnerBlockEntityNbt;
	}
	
}
