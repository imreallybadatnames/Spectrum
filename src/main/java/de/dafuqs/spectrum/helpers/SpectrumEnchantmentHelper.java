package de.dafuqs.spectrum.helpers;

import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.enchantments.*;
import de.dafuqs.spectrum.mixin.accessors.EnchantmentAccessor;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SpectrumEnchantmentHelper {

	public static void addRevealedEnchantments(Enchantment enchantment, ServerWorld world, int level, Entity user, ItemEnchantmentsComponent.Builder builder) {
		EnchantmentAccessor.invokeApplyEffects(
				enchantment.getEffect(SpectrumEnchantmentEffectComponentTypes.CLOAKED),
				EnchantmentAccessor.invokeCreateEnchantedEntityLootContext(world, level, user, user.getPos()),
				e -> builder.add(e, level));
	}

	public static Pair<Boolean, ItemStack> addOrUpgradeEnchantment(RegistryWrapper.WrapperLookup registryLookup, ItemStack stack, RegistryKey<Enchantment> enchantmentKey, int level, boolean forceEvenIfNotApplicable, boolean allowEnchantmentConflicts) {
		return getEntry(registryLookup, enchantmentKey)
				.map(entry -> addOrUpgradeEnchantment(stack, entry, level, forceEvenIfNotApplicable, allowEnchantmentConflicts))
				.orElse(new Pair<>(false, stack));
	}

	/**
	 * Adds an enchantment to an ItemStack. If the stack already has that enchantment, it gets upgraded instead
	 *
	 * @param stack                     the stack that receives the enchantments
	 * @param enchantment               the enchantment to add
	 * @param level                     the level of the enchantment
	 * @param forceEvenIfNotApplicable  add enchantments to the item, even if the item does usually not support that enchantment
	 * @param allowEnchantmentConflicts add enchantments to the item, even if there are enchantment conflicts
	 * @return the enchanted stack and a boolean if the enchanting was successful
	 */
	public static Pair<Boolean, ItemStack> addOrUpgradeEnchantment(ItemStack stack, RegistryEntry<Enchantment> enchantment, int level, boolean forceEvenIfNotApplicable, boolean allowEnchantmentConflicts) {
		boolean isAcceptable = enchantment.value().isAcceptableItem(stack) || forceEvenIfNotApplicable;
		boolean isConflicting = !allowEnchantmentConflicts && !EnchantmentHelper.isCompatible(stack.getEnchantments().getEnchantments(), enchantment);
		boolean isEnchantedBook = stack.isOf(Items.ENCHANTED_BOOK) || SpectrumEnchantmentHelper.isEnchantableBook(stack);

		// Can this enchant even go on that tool?
		if (!isAcceptable && !isEnchantedBook) {
			return new Pair<>(false, stack);
		}
		
		// Are there any conflicting enchantments?
		if (isConflicting) {
			return new Pair<>(false, stack);
		}

		// Convert enchantable books into enchanted books
		if (isEnchantedBook && !stack.isOf(Items.ENCHANTED_BOOK)) {
			ItemStack enchantedBookStack = new ItemStack(Items.ENCHANTED_BOOK, stack.getCount());
			enchantedBookStack.applyChanges(stack.getComponentChanges());
			stack = enchantedBookStack;
		}

		// Is the existing enchantment the same or better than the new one?
		var builder = new ItemEnchantmentsComponent.Builder(EnchantmentHelper.getEnchantments(stack));
		if (level <= builder.getLevel(enchantment)) {
			return new Pair<>(false, stack);
		}

		// Add the enchantment
		builder.set(enchantment, level);
		EnchantmentHelper.set(stack, builder.build());
		return new Pair<>(true, stack);
	}
	
	public static void setStoredEnchantments(Map<Enchantment, Integer> enchantments, ItemStack stack) {
		stack.removeSubNbt(EnchantedBookItem.STORED_ENCHANTMENTS_KEY); // clear existing enchantments
		for (Map.Entry<Enchantment, Integer> enchantmentIntegerEntry : enchantments.entrySet()) {
			Enchantment enchantment = enchantmentIntegerEntry.getKey();
			if (enchantment != null) {
				EnchantedBookItem.addEnchantment(stack, new EnchantmentLevelEntry(enchantment, enchantmentIntegerEntry.getValue()));
			}
		}
	}
	
	/**
	 * Clears all enchantments of receiverStack and replaces them with the ones present in sourceStacks
	 * The enchantments are applied in order, so if there are conflicts, the first enchantment in sourceStacks gets chosen
	 *
	 * @param receiverStack             the stack that receives the enchantments
	 * @param forceEvenIfNotApplicable  add enchantments to the item, even if the item does usually not support that enchantment
	 * @param allowEnchantmentConflicts add enchantments to the item, even if there are enchantment conflicts
	 * @param sourceStacks   sourceStacks the stacks that supply the enchantments
	 * @return the resulting stack
	 */
	public static ItemStack clearAndCombineEnchantments(ItemStack receiverStack, boolean forceEvenIfNotApplicable, boolean allowEnchantmentConflicts, ItemStack... sourceStacks) {
		EnchantmentHelper.set(receiverStack, ItemEnchantmentsComponent.DEFAULT); // clear current ones
		for (ItemStack stack : sourceStacks) {
			for (var entry : EnchantmentHelper.getEnchantments(stack).getEnchantmentEntries()) {
				receiverStack = addOrUpgradeEnchantment(receiverStack, entry.getKey(), entry.getIntValue(), forceEvenIfNotApplicable, allowEnchantmentConflicts).getRight();
			}
		}
		return receiverStack;
	}
	
	/**
	 * Checks if an itemstack can be used as the source to create an enchanted book
	 *
	 * @param stack The itemstack to check
	 * @return true if it is a book that can be turned into an enchanted book by enchanting
	 */
	public static boolean isEnchantableBook(@NotNull ItemStack stack) {
		return stack.isIn(SpectrumItemTags.ENCHANTABLE_BOOKS) || stack.getItem() instanceof BookItem;
	}
	
	public static ItemEnchantmentsComponent collectHighestEnchantments(List<ItemStack> itemStacks) {
		var builder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);
		for (ItemStack itemStack : itemStacks) {
			for (var entry : EnchantmentHelper.getEnchantments(itemStack).getEnchantmentEntries()) {
				builder.add(entry.getKey(), entry.getIntValue());
			}
		}
		return builder.build();
	}
	
	public static boolean canCombineAny(ItemStack existingStack, ItemStack newStack) {
		var existingEnchantments = EnchantmentHelper.getEnchantments(existingStack).getEnchantments();
		var newEnchantments = EnchantmentHelper.getEnchantments(newStack).getEnchantments();
		return existingEnchantments.isEmpty()
				|| newEnchantments.stream().anyMatch(newEnchantment ->
						EnchantmentHelper.isCompatible(existingEnchantments, newEnchantment));
	}

	@SafeVarargs
	public static Pair<ItemStack, Integer> removeEnchantments(RegistryWrapper.WrapperLookup registryLookup, @NotNull ItemStack itemStack, RegistryKey<Enchantment>... enchantmentKeys) {
		if (!EnchantmentHelper.hasEnchantments(itemStack)) {
			return new Pair<>(itemStack, 0);
		}

		var wrapper = getRegistry(registryLookup).orElse(null);
		if (wrapper == null) {
			return new Pair<>(itemStack, 0);
		}

		return removeEnchantments(itemStack, Arrays.stream(enchantmentKeys)
				.map(key -> wrapper.getOptional(key).orElse(null))
				.filter(Objects::nonNull)
				.toList());
	}

	@SafeVarargs
	public static Pair<ItemStack, Integer> removeEnchantments(@NotNull ItemStack itemStack, RegistryEntry<Enchantment>... enchantments) {
		return removeEnchantments(itemStack, Arrays.stream(enchantments).toList());
	}

	/**
	 * Removes the enchantments on a stack of items / enchanted book
	 * @param itemStack    the stack
	 * @param enchantments the enchantments to remove
	 * @return The resulting stack & the count of enchants that were removed
	 */
    public static <T extends RegistryEntry<Enchantment>> Pair<ItemStack, Integer> removeEnchantments(@NotNull ItemStack itemStack, List<T> enchantments) {
		var removals = new AtomicInteger(0);
		var builder = new ItemEnchantmentsComponent.Builder(EnchantmentHelper.getEnchantments(itemStack));
		enchantments.forEach(enchantment -> {
			if (builder.getLevel(enchantment) > 0) {
				builder.set(enchantment, 0);
				removals.getAndIncrement();
			}
		});

		var component = builder.build();
		if (itemStack.isOf(Items.ENCHANTED_BOOK) && component.isEmpty()) {
			itemStack = new ItemStack(Items.BOOK, itemStack.getCount());
		}
		EnchantmentHelper.set(itemStack, builder.build());

		return new Pair<>(itemStack, removals.get());
	}
	
	public static <T extends Item & ExtendedEnchantable> ItemStack getMaxEnchantedStack(@NotNull T item) {
		ItemStack itemStack = item.getDefaultStack();
		for (Enchantment enchantment : Registries.ENCHANTMENT.stream().toList()) {
			if (item.acceptsEnchantment(enchantment)) {
				int maxLevel = enchantment.getMaxLevel();
				itemStack = addOrUpgradeEnchantment(itemStack, enchantment, maxLevel, true, true).getRight();
			}
		}
		return itemStack;
	}

	public static int getLevel(RegistryWrapper.WrapperLookup registryLookup, RegistryKey<Enchantment> enchantment, ItemStack stack) {
		return getRegistry(registryLookup)
				.flatMap(impl -> impl.getOptional(enchantment))
				.map(entry -> EnchantmentHelper.getLevel(entry, stack))
				.orElse(0);
	}

	public static int getUsableLevel(SpectrumEnchantment enchantment, ItemStack itemStack, Entity entity) {
		int level = EnchantmentHelper.getLevel(enchantment, itemStack);
		if (level > 0 && !enchantment.canEntityUse(entity)) {
			level = 0;
		}
		return level;
	}

	public static Optional<RegistryWrapper.Impl<Enchantment>> getRegistry(RegistryWrapper.WrapperLookup registryLookup) {
		return registryLookup.getOptionalWrapper(RegistryKeys.ENCHANTMENT);
	}

	public static Optional<RegistryEntry<Enchantment>> getEntry(RegistryWrapper.WrapperLookup lookup, RegistryKey<Enchantment> key) {
		return getRegistry(lookup).flatMap(impl -> impl.getOptional(key));
	}

	public static int getEquipmentLevel(RegistryWrapper.WrapperLookup lookup, RegistryKey<Enchantment> key, LivingEntity entity) {
		return getEntry(lookup, key).map(e -> EnchantmentHelper.getEquipmentLevel(e, entity)).orElse(0);
	}

	public static void doTreasureHunterForPlayer(ServerPlayerEntity thisEntity, DamageSource source) {
		if (!thisEntity.isSpectator() && source.getAttacker() instanceof LivingEntity) {
			ServerWorld serverWorld = ((ServerWorld) thisEntity.getWorld());
			int damageSourceTreasureHunt = getEquipmentLevel(
					serverWorld.getRegistryManager(),
					SpectrumEnchantments.TREASURE_HUNTER,
					(LivingEntity) source.getAttacker());
			if (damageSourceTreasureHunt > 0) {
				boolean shouldDropHead = serverWorld.getRandom().nextFloat() < 0.2 * damageSourceTreasureHunt;
				if (shouldDropHead) {
					ItemStack headItemStack = new ItemStack(Items.PLAYER_HEAD);

					NbtCompound compoundTag = new NbtCompound();
					compoundTag.putString("SkullOwner", thisEntity.getName().getString());

					headItemStack.setNbt(compoundTag);

					ItemEntity headEntity = new ItemEntity(serverWorld, thisEntity.getX(), thisEntity.getY(), thisEntity.getZ(), headItemStack);
					serverWorld.spawnEntity(headEntity);
				}
			}
		}
	}

}
