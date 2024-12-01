package de.dafuqs.spectrum.explosion.modifier;

import de.dafuqs.spectrum.explosion.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.particle.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;

public class EnchantmentAddingModifier extends ExplosionModifier {
	
	private final RegistryKey<Enchantment> enchantment;
	private final int level;
	
	public EnchantmentAddingModifier(ExplosionModifierType type, RegistryKey<Enchantment> enchantment, int level, ParticleEffect particleEffect, int displayColor) {
		super(type, displayColor);
		this.enchantment = enchantment;
		this.level = level;
	}
	
	@Override
	public void addEnchantments(ServerWorld world, ItemStack stack) {
		world.getRegistryManager()
				.getOptionalWrapper(RegistryKeys.ENCHANTMENT)
				.flatMap(impl -> impl.getOptional(enchantment))
				.ifPresent(e -> stack.addEnchantment(e, level));
	}
	
}
