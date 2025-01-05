package de.dafuqs.spectrum.loot;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.loot.functions.*;
import net.minecraft.loot.function.*;
import net.minecraft.registry.*;

public class SpectrumLootFunctionTypes {
	
	public static LootFunctionType<DyeRandomlyLootFunction> DYE_RANDOMLY = register("dye_randomly", DyeRandomlyLootFunction.CODEC);
	public static LootFunctionType<FermentRandomlyLootFunction> FERMENT_RANDOMLY = register("ferment_randomly", FermentRandomlyLootFunction.CODEC);
	public static LootFunctionType<MergeNbtRandomlyLootFunction> MERGE_NBT_RANDOMLY = register("merge_nbt_randomly", MergeNbtRandomlyLootFunction.CODEC);
	public static LootFunctionType<FillPotionFillableLootFunction> FILL_POTION_FILLABLE = register("fill_potion_fillable", FillPotionFillableLootFunction.CODEC);
	
	private static <T extends LootFunction> LootFunctionType<T> register(String id, MapCodec<T> codec) {
		return Registry.register(Registries.LOOT_FUNCTION_TYPE, SpectrumCommon.locate(id), new LootFunctionType<>(codec));
	}
	
}
