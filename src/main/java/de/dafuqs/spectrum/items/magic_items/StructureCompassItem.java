package de.dafuqs.spectrum.items.magic_items;

import com.mojang.datafixers.util.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.structure.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class StructureCompassItem extends CompassItem {
	
	protected final TagKey<Structure> locatedStructures;
	
	public StructureCompassItem(Settings settings, TagKey<Structure> locatedStructures) {
		super(settings);
		this.locatedStructures = locatedStructures;
	}
	
	@Override
	public void inventoryTick(@NotNull ItemStack stack, @NotNull World world, Entity entity, int slot, boolean selected) {
		if (!world.isClient && world.getTime() % 200 == 0) {
			locateStructure(stack, world, entity);
		}
	}

	protected void locateStructure(@NotNull ItemStack stack, @NotNull World world, Entity entity) {
		Pair<BlockPos, RegistryEntry<Structure>> foundStructure = locateStructure((ServerWorld) world, entity.getBlockPos());
		if (foundStructure != null) {
			saveStructurePos(stack, world.getRegistryKey(), foundStructure.getFirst());
		} else {
			removeStructurePos(stack);
		}
	}

	public @Nullable Pair<BlockPos, RegistryEntry<Structure>> locateStructure(@NotNull ServerWorld world, @NotNull BlockPos pos) {
		Optional<RegistryEntryList.Named<Structure>> registryEntryList = SpectrumStructureTags.entriesOf(world, locatedStructures);
		return registryEntryList.map(registryEntries ->
				world.getChunkManager().getChunkGenerator().locateStructure(world, registryEntries, pos, 100, false))
				.orElse(null);
	}
	
	public static @Nullable GlobalPos getStructurePos(ItemStack stack) {
		return stack.getOrDefault(SpectrumDataComponentTypes.TARGETED_STRUCTURE, null);
	}
	
	protected void saveStructurePos(ItemStack stack, @NotNull RegistryKey<World> worldKey, @NotNull BlockPos pos) {
		stack.set(SpectrumDataComponentTypes.TARGETED_STRUCTURE, new GlobalPos(worldKey, pos));
	}
	
	protected void removeStructurePos(@NotNull ItemStack stack) {
		stack.remove(SpectrumDataComponentTypes.TARGETED_STRUCTURE);
	}
	
}
