package de.dafuqs.spectrum.items.tools;

import de.dafuqs.arrowhead.api.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;

import java.util.*;
import java.util.function.*;

import static net.minecraft.nbt.NbtElement.*;

public class MalachiteCrossbowItem extends CrossbowItem implements Preenchanted, ArrowheadCrossbow {
	
	public static final Predicate<ItemStack> PROJECTILES = (stack) -> stack.isIn(ItemTags.ARROWS) || stack.isIn(SpectrumItemTags.GLASS_ARROWS);
	
	public MalachiteCrossbowItem(Settings settings) {
        super(settings);
    }
	
	@Override
	public Map<Enchantment, Integer> getDefaultEnchantments() {
		return Map.of(Enchantments.PIERCING, 5);
	}
	
	@Override
	public ItemStack getDefaultStack() {
		return getDefaultEnchantedStack(this);
	}
	
	public static ItemStack getFirstProjectile(RegistryWrapper.WrapperLookup wrapperLookup, ItemStack crossbow) {
		var nbtComp = crossbow.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
		if (nbtComp.contains("ChargedProjectiles")) {
			NbtList nbtList = nbtComp.copyNbt().getList("ChargedProjectiles", COMPOUND_TYPE);
			if (nbtList != null && nbtList.size() > 0) {
				return ItemStack.fromNbt(wrapperLookup, nbtList.getCompound(0)).orElse(ItemStack.EMPTY);
			}
		}
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return SpectrumToolMaterials.Material.MALACHITE.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
	}
	
	@Override
	public Predicate<ItemStack> getProjectiles() {
		return PROJECTILES;
	}

	@Override
	public float getProjectileVelocityModifier(ItemStack stack) {
		return 1.5F;
	}

	@Override
	public float getPullTimeModifier(ItemStack stack) {
		return 2.0F;
	}

	@Override
	public float getDivergenceMod(ItemStack stack) {
		return 0.75F;
	}
	
}
