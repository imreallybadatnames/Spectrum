package de.dafuqs.spectrum.blocks.memory;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.item.ItemGroup.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MemoryItem extends BlockItem {
	
	// There are a few entities in vanilla that do not have a corresponding spawn egg
	// therefore to make it nicer we specify custom colors for them here
	private static final HashMap<EntityType<?>, Pair<Integer, Integer>> customColors = new HashMap<>() {{
		put(EntityType.BAT, new Pair<>(0x463d2b, 0x191307));
		put(EntityType.SNOW_GOLEM, new Pair<>(0xc9cbcf, 0xa26e28));
		put(EntityType.WITHER, new Pair<>(0x101211, 0x3e4140));
		put(EntityType.ILLUSIONER, new Pair<>(0x29578d, 0x4b4e4f));
		put(EntityType.ENDER_DRAGON, new Pair<>(0x111111, 0x856c8f));
		put(EntityType.IRON_GOLEM, new Pair<>(0x9a9a9a, 0x8b7464));
	}};
	
	public MemoryItem(Block block, Settings settings) {
		super(block, settings);
	}
	
	public static MemoryComponent getMemory(ItemStack stack) {
		return stack.getOrDefault(SpectrumDataComponentTypes.MEMORY, MemoryComponent.DEFAULT);
	}
	
	public static ItemStack getMemoryForEntity(LivingEntity entity) {
		NbtCompound tag = new NbtCompound();
		entity.saveSelfNbt(tag);
		tag.remove("Pos"); // yeet everything that we don't need and could interfere when spawning
		tag.remove("OnGround");
		tag.remove("Rotation");
		tag.remove("Motion");
		tag.remove("FallDistance");
		tag.remove("InLove");
		tag.remove("UUID");
		tag.remove("Health");
		tag.remove("Fire");
		tag.remove("HurtByTimestamp");
		tag.remove("DeathTime");
		tag.remove("AbsorptionAmount");
		tag.remove("Air");
		tag.remove("FallFlying");
		tag.remove("PortalCooldown");
		tag.remove("HurtTime");
		
		ItemStack stack = SpectrumBlocks.MEMORY.asItem().getDefaultStack();
		stack.set(DataComponentTypes.ENTITY_DATA, NbtComponent.of(tag));
		return stack;
	}
	
	public static ItemStack getForEntityType(EntityType<?> entityType, int ticksToManifest) {
		ItemStack stack = SpectrumBlocks.MEMORY.asItem().getDefaultStack();
		
		stack.set(SpectrumDataComponentTypes.MEMORY, new MemoryComponent.Builder(MemoryComponent.DEFAULT).ticksToManifest(ticksToManifest).build());
		
		NbtCompound entityCompound = new NbtCompound();
		entityCompound.putString("id", Registries.ENTITY_TYPE.getId(entityType).toString());
		stack.set(DataComponentTypes.ENTITY_DATA, NbtComponent.of(entityCompound));

		return stack;
	}
	
	public static NbtCompound getEntityData(ItemStack stack) {
		return stack.getOrDefault(DataComponentTypes.ENTITY_DATA, NbtComponent.DEFAULT).copyNbt();
	}
	
	public static Optional<EntityType<?>> getEntityType(ItemStack stack) {
		var data = getEntityData(stack);
		if (!data.contains("id", NbtElement.STRING_TYPE)) return Optional.empty();
		return EntityType.get(data.getString("id"));
	}
	
	public static @Nullable Text getMemoryEntityCustomName(ItemStack stack, RegistryWrapper.WrapperLookup drm) {
		var data = getEntityData(stack);
		if (!data.contains("CustomName", NbtElement.STRING_TYPE)) return null;
		return Text.Serialization.fromJson(data.getString("CustomName"), drm);
	}
	
	public static int getTicksToManifest(ItemStack stack) {
		return getMemory(stack).ticksToManifest();
	}
	
	public static void setTicksToManifest(@NotNull ItemStack itemStack, int newTicksToManifest) {
		itemStack.apply(SpectrumDataComponentTypes.MEMORY, MemoryComponent.DEFAULT, comp -> new MemoryComponent.Builder(comp).ticksToManifest(newTicksToManifest).build());
	}
	
	public static void setSpawnAsAdult(@NotNull ItemStack itemStack, boolean spawnAsAdult) {
		itemStack.apply(SpectrumDataComponentTypes.MEMORY, MemoryComponent.DEFAULT, comp -> new MemoryComponent.Builder(comp).spawnAsAdult(spawnAsAdult).build());
	}
	
	public static void markAsBrokenPromise(ItemStack itemStack, boolean isBrokenPromise) {
		itemStack.apply(SpectrumDataComponentTypes.MEMORY, MemoryComponent.DEFAULT, comp -> new MemoryComponent.Builder(comp).brokenPromise(isBrokenPromise).build());
	}
	
	public static boolean isBrokenPromise(ItemStack stack) {
		return getMemory(stack).brokenPromise();
	}
	
	public static boolean isUnrecognizable(ItemStack stack) {
		return getMemory(stack).unrecognizable();
	}
	
	public static void makeUnrecognizable(@NotNull ItemStack itemStack) {
		itemStack.apply(SpectrumDataComponentTypes.MEMORY, MemoryComponent.DEFAULT, comp -> new MemoryComponent.Builder(comp).unrecognizable().build());
	}
	
	public static int getEggColor(ItemStack stack, int tintIndex) {
		if (stack.contains(SpectrumDataComponentTypes.MEMORY) && !isUnrecognizable(stack)) {
			var entityType = getEntityType(stack);
			if (entityType.isPresent()) {
				EntityType<?> type = entityType.get();
				if (customColors.containsKey(type)) {
					// statically defined: fetch from map
					return tintIndex == 0 ? customColors.get(type).getLeft() : customColors.get(type).getRight();
				} else {
					// dynamically defined: fetch from spawn egg
					SpawnEggItem spawnEggItem = SpawnEggItem.forEntity(entityType.get());
					if (spawnEggItem != null) {
						return spawnEggItem.getColor(tintIndex);
					}
				}
			}
		}
		
		return tintIndex == 0 ? 0x222222 : 0xDDDDDD;
	}
	
	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		
		getEntityType(stack).ifPresentOrElse(entityType -> {
			if (isUnrecognizable(stack)) {
				tooltip.add(Text.translatable("item.spectrum.memory.tooltip.unrecognizable_entity_type").formatted(Formatting.GRAY));
			} else {
				boolean isBrokenPromise = isBrokenPromise(stack);
				Text customName = getMemoryEntityCustomName(stack, context.getRegistryLookup());
				if (isBrokenPromise) {
					if (customName == null) {
						tooltip.add(Text.translatable("item.spectrum.memory.tooltip.entity_type_broken_promise", entityType.getName()));
					} else {
						tooltip.add(Text.translatable("item.spectrum.memory.tooltip.named_broken_promise").append(customName).formatted(Formatting.WHITE, Formatting.ITALIC));
					}
				} else {
					if (customName == null) {
						tooltip.add(Text.translatable("item.spectrum.memory.tooltip.entity_type", entityType.getName()));
					} else {
						tooltip.add(Text.translatable("item.spectrum.memory.tooltip.named").append(customName).formatted(Formatting.WHITE, Formatting.ITALIC));
					}
				}
			}
			
			int ticksToHatch = getTicksToManifest(stack);
			if (ticksToHatch <= 0) {
				tooltip.add(Text.translatable("item.spectrum.memory.tooltip.does_not_manifest").formatted(Formatting.GRAY));
			} else if (ticksToHatch > 100) {
				tooltip.add(Text.translatable("item.spectrum.memory.tooltip.extra_long_time_to_manifest").formatted(Formatting.GRAY));
			} else if (ticksToHatch > 20) {
				tooltip.add(Text.translatable("item.spectrum.memory.tooltip.long_time_to_manifest").formatted(Formatting.GRAY));
			} else if (ticksToHatch > 5) {
				tooltip.add(Text.translatable("item.spectrum.memory.tooltip.medium_time_to_manifest").formatted(Formatting.GRAY));
			} else {
				tooltip.add(Text.translatable("item.spectrum.memory.tooltip.short_time_to_manifest").formatted(Formatting.GRAY));
			}
		}, () -> tooltip.add(Text.translatable("item.spectrum.memory.tooltip.unset_entity_type").formatted(Formatting.GRAY)));
	}
	
	public static void appendEntries(RegistryWrapper.WrapperLookup lookup, Entries entries) {
		// adding all memories that have spirit instiller recipes
		Set<MemoryComponent> encountered = new HashSet<>();
		//TODO does this work on dedicated servers?
		if (SpectrumCommon.minecraftServer != null) {
			Item memoryItem = SpectrumBlocks.MEMORY.asItem();
			for (var recipe : SpectrumCommon.minecraftServer.getRecipeManager().listAllOfType(SpectrumRecipeTypes.SPIRIT_INSTILLING)) {
				ItemStack output = recipe.value().getResult(lookup);
				var memory = output.get(SpectrumDataComponentTypes.MEMORY);
				if (output.isOf(memoryItem) && memory != null && !encountered.contains(memory)) {
					entries.add(output);
					encountered.add(memory);
				}
			}
		}
	}
	
}
