package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.predicate.item.*;
import net.minecraft.registry.entry.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import java.util.*;

@Mixin(ItemPredicate.class)
public abstract class ItemPredicateMixin {
	
	// thank you so, so much @williewillus / @Botania for this snippet of code
	// https://github.com/VazkiiMods/Botania/blob/1.18.x/Fabric/src/main/java/vazkii/botania/fabric/mixin/FabricMixinItemPredicate.java
	@ModifyVariable(at = @At("HEAD"), method = "Lnet/minecraft/predicate/item/ItemPredicate;<init>(Ljava/util/Optional;Lnet/minecraft/predicate/NumberRange$IntRange;Lnet/minecraft/predicate/ComponentPredicate;Ljava/util/Map;)V", argsOnly = true)
	private static Optional<RegistryEntryList<Item>> addSpectrumShears(Optional<RegistryEntryList<Item>> optional) {
		if (optional.isPresent() && optional.get().contains(Items.SHEARS.getRegistryEntry())) {
			List<RegistryEntry<Item>> list = new LinkedList<>(optional.get().stream().toList());
			list.add(SpectrumItems.BEDROCK_SHEARS.getRegistryEntry());
			optional = Optional.of(RegistryEntryList.of(list));
		}
		return optional;
	}
	
}