package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.predicate.item.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import java.util.*;

@Mixin(ItemPredicate.class)
public abstract class ItemPredicateMixin {
	
	// thank you so, so much @williewillus / @Botania for this snippet of code
	// https://github.com/VazkiiMods/Botania/blob/1.18.x/Fabric/src/main/java/vazkii/botania/fabric/mixin/FabricMixinItemPredicate.java
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@ModifyVariable(at = @At("HEAD"), method = "<init>(Ljava/util/Optional;Lnet/minecraft/predicate/NumberRange$IntRange;Lnet/minecraft/predicate/ComponentPredicate;Ljava/util/Map;)V", argsOnly = true)
	private static Optional<RegistryEntryList<Item>> addSpectrumShears(Optional<RegistryEntryList<Item>> items) {
		if (items.isEmpty()) return items;
		List<RegistryEntry<Item>> entries = new ArrayList<>();
		for (var entry : items.get()) {
			entries.add(entry);
			if (entry.value() == Items.SHEARS)
				entries.add(Registries.ITEM.getEntry(SpectrumItems.BEDROCK_SHEARS));
		}
		return Optional.of(RegistryEntryList.of(entries));
	}
	
}