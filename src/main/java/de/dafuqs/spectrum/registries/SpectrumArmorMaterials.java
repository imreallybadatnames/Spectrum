package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.*;

import java.util.*;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import net.minecraft.util.Util;

import static de.dafuqs.spectrum.SpectrumCommon.locate;

public class SpectrumArmorMaterials {

	public static RegistryEntry<ArmorMaterial> GEMSTONE;
	public static RegistryEntry<ArmorMaterial> BEDROCK;

	public static void register() {
		GEMSTONE = register("gemstone",
				Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
					map.put(ArmorItem.Type.BOOTS, SpectrumCommon.CONFIG.GemstoneArmorBootsProtection);
					map.put(ArmorItem.Type.LEGGINGS, SpectrumCommon.CONFIG.GemstoneArmorLeggingsProtection);
					map.put(ArmorItem.Type.CHESTPLATE, SpectrumCommon.CONFIG.GemstoneArmorChestplateProtection);
					map.put(ArmorItem.Type.HELMET, SpectrumCommon.CONFIG.GemstoneArmorHelmetProtection);
				}),
				15,
				Registries.SOUND_EVENT.getEntry(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME),
				SpectrumCommon.CONFIG.GemstoneArmorToughness,
				SpectrumCommon.CONFIG.GemstoneArmorKnockbackResistance,
				() -> Ingredient.fromTag(SpectrumItemTags.GEMSTONE_SHARDS));

		BEDROCK = register("bedrock",
				Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
					map.put(ArmorItem.Type.BOOTS, SpectrumCommon.CONFIG.BedrockArmorBootsProtection);
					map.put(ArmorItem.Type.LEGGINGS, SpectrumCommon.CONFIG.BedrockArmorLeggingsProtection);
					map.put(ArmorItem.Type.CHESTPLATE, SpectrumCommon.CONFIG.BedrockArmorChestplateProtection);
					map.put(ArmorItem.Type.HELMET, SpectrumCommon.CONFIG.BedrockArmorHelmetProtection);
				}),
				5,
				SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
				SpectrumCommon.CONFIG.BedrockArmorToughness,
				SpectrumCommon.CONFIG.BedrockArmorKnockbackResistance,
				() -> Ingredient.ofItems(SpectrumItems.BEDROCK_DUST));
	}

	public static RegistryEntry<ArmorMaterial> register(
			String id,
			EnumMap<ArmorItem.Type, Integer> defense,
			int enchantability,
			RegistryEntry<SoundEvent> equipSound,
			float toughness,
			float knockbackResistance,
			Supplier<Ingredient> repairIngredient
	) {
		List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(locate(id)));

		EnumMap<ArmorItem.Type, Integer> enumMap = new EnumMap<>(ArmorItem.Type.class);

		for (ArmorItem.Type type : ArmorItem.Type.values()) {
			enumMap.put(type, defense.get(type));
		}

		return Registry.registerReference(
				Registries.ARMOR_MATERIAL,
				locate(id),
				new ArmorMaterial(enumMap, enchantability, equipSound, Suppliers.memoize(repairIngredient::get), layers, toughness, knockbackResistance));
	}
	
}
