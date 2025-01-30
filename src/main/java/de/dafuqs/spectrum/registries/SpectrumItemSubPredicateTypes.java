package de.dafuqs.spectrum.registries;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.predicate.item.*;
import net.minecraft.predicate.item.*;
import net.minecraft.registry.*;

@SuppressWarnings("unused")
public class SpectrumItemSubPredicateTypes {
	
	private static final Deferrer DEFERRER = new Deferrer();
	
	public static ItemSubPredicate.Type<SweetenedPredicate> SWEETENED = register("sweetened", SweetenedPredicate.CODEC);
	
	private static <T extends ItemSubPredicate> ItemSubPredicate.Type<T> register(String id, Codec<T> codec) {
		return DEFERRER.defer(new ItemSubPredicate.Type<>(codec), type -> Registry.register(Registries.ITEM_SUB_PREDICATE_TYPE, SpectrumCommon.locate(id), type));
	}
	
	public static void register() {
		DEFERRER.flush();
	}
	
}
