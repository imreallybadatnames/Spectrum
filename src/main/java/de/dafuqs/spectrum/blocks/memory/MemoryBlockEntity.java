package de.dafuqs.spectrum.blocks.memory;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.progression.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MemoryBlockEntity extends BlockEntity implements PlayerOwned {
	
	protected ItemStack memoryItemStack = ItemStack.EMPTY; // zero or negative values: never hatch
	protected UUID ownerUUID;
	
	//  color rendering cache
	private int tint1 = -1;
	private int tint2 = -1;
	
	public MemoryBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.MEMORY, pos, state);
	}
	
	@Contract("_ -> new")
	public static @NotNull Pair<Integer, Integer> getEggColorsForEntity(EntityType<?> entityType) {
		SpawnEggItem spawnEggItem = SpawnEggItem.forEntity(entityType);
		if (spawnEggItem != null) {
			return new Pair<>(spawnEggItem.getColor(0), spawnEggItem.getColor(1));
		}
		return new Pair<>(0x222222, 0xDDDDDD);
	}
	
	public static int getManifestAdvanceSteps(@NotNull World world, @NotNull BlockPos blockPos) {
		BlockState belowBlockState = world.getBlockState(blockPos.down());
		if (belowBlockState.isIn(SpectrumBlockTags.MEMORY_NEVER_MANIFESTERS)) {
			return 0;
		} else if (belowBlockState.isIn(SpectrumBlockTags.MEMORY_VERY_FAST_MANIFESTERS)) {
			return 8;
		} else if (belowBlockState.isIn(SpectrumBlockTags.MEMORY_FAST_MANIFESTERS)) {
			return 3;
		} else {
			return 1;
		}
	}
	
	public void setData(LivingEntity livingEntity, @NotNull ItemStack creatureSpawnItemStack) {
		if (livingEntity instanceof PlayerEntity playerEntity)
			setOwner(playerEntity);
		
		if (creatureSpawnItemStack.getItem() instanceof MemoryItem) {
			this.memoryItemStack = creatureSpawnItemStack.copy();
			this.memoryItemStack.setCount(1);
		}
		
		if (livingEntity.getWorld() instanceof ServerWorld serverWorld)
			serverWorld.getChunkManager().markForUpdate(pos);
		
		this.markDirty();
	}
	
	@Override
	public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		
		this.ownerUUID = PlayerOwned.readOwnerUUID(nbt);
		if (nbt.contains("MemoryItem", NbtElement.COMPOUND_TYPE)) {
			this.memoryItemStack = ItemStack.fromNbtOrEmpty(registryLookup, nbt.getCompound("MemoryItem"));
		}
	}
	
	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		PlayerOwned.writeOwnerUUID(nbt, this.ownerUUID);
		if (this.memoryItemStack.isEmpty()) {
			CodecHelper.writeNbt(nbt, "MemoryItem", ItemStack.CODEC, memoryItemStack);
		}
	}
	
	public void advanceManifesting(ServerWorld world, BlockPos blockPos) {
		int ticksToManifest = MemoryItem.getTicksToManifest(this.memoryItemStack);
		if (ticksToManifest > 0) {
			int additionalManifestAdvanceSteps = getManifestAdvanceSteps(world, blockPos);
			if (additionalManifestAdvanceSteps > 0) {
				int newTicksToManifest = ticksToManifest - additionalManifestAdvanceSteps;
				if (newTicksToManifest <= 0) {
					this.manifest(world, blockPos);
				} else {
					Optional<EntityType<?>> entityTypeOptional = MemoryItem.getEntityType(this.memoryItemStack);
					if (entityTypeOptional.isPresent()) {
						MemoryItem.setTicksToManifest(this.memoryItemStack, newTicksToManifest);
						PlayMemoryManifestingParticlesPayload.playMemoryManifestingParticles(world, blockPos, entityTypeOptional.get(), 3);
						world.playSound(null, this.pos, SpectrumSoundEvents.BLOCK_MEMORY_ADVANCE, SoundCategory.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
						this.markDirty();
					}
				}
			}
		}
	}
	
	protected void manifest(@NotNull ServerWorld world, BlockPos blockPos) {
		manifest(world, blockPos, this.memoryItemStack, this.ownerUUID);
	}
	
	public static boolean manifest(@NotNull ServerWorld world, BlockPos blockPos, ItemStack memoryItemStack, @Nullable UUID ownerUUID) {
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.getBlock() instanceof Waterloggable && blockState.get(Properties.WATERLOGGED)) {
			world.setBlockState(blockPos, Blocks.WATER.getDefaultState());
		} else {
			world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
		}
		
		Optional<Entity> hatchedEntityOptional = hatchEntity(world, blockPos, memoryItemStack);
		
		if (hatchedEntityOptional.isPresent()) {
			Entity hatchedEntity = hatchedEntityOptional.get();
			
			PlayMemoryManifestingParticlesPayload.playMemoryManifestingParticles(world, blockPos, hatchedEntity.getType(), 10);
			
			if (hatchedEntity instanceof MobEntity hatchedMobEntity) {
				hatchedMobEntity.setPersistent();
				hatchedMobEntity.playAmbientSound();
				hatchedMobEntity.playSpawnEffects();
			}
			if (ownerUUID != null) {
				EntityHelper.addPlayerTrust(hatchedEntity, ownerUUID);
			}
			
			PlayerEntity owner = PlayerOwned.getPlayerEntityIfOnline(ownerUUID);
			if (owner instanceof ServerPlayerEntity serverPlayerEntity) {
				SpectrumAdvancementCriteria.MEMORY_MANIFESTING.trigger(serverPlayerEntity, hatchedEntity);
			}
			
			return true;
		}
		
		return false;
	}
	
	public int getEggColor(int tintIndex) {
		if (tint1 == -1) {
			if (this.memoryItemStack == null) {
				this.tint1 = 0x222222;
				this.tint2 = 0xDDDDDD;
			} else {
				this.tint1 = MemoryItem.getEggColor(this.memoryItemStack, 0);
				this.tint2 = MemoryItem.getEggColor(this.memoryItemStack, 1);
			}
		}
		
		if (tintIndex == 0) {
			return tint1;
		} else {
			return tint2;
		}
	}
	
	// Called when the chunk is first loaded to initialize this be
	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		NbtCompound nbtCompound = new NbtCompound();
		this.writeNbt(nbtCompound, registryLookup);
		return nbtCompound;
	}
	
	public static Optional<Entity> hatchEntity(ServerWorld world, BlockPos blockPos, ItemStack memoryItemStack) {
		return Optional.ofNullable(memoryItemStack.get(SpectrumDataComponentTypes.MEMORY))
				.flatMap(memory -> MemoryItem.getEntityType(memoryItemStack)
						.map(entityType -> {
							// alignPosition: center the mob in the center of the blockPos
							Entity entity = entityType.spawnFromItemStack(world, memoryItemStack, null, blockPos, SpawnReason.SPAWN_EGG, true, false);
							if (entity instanceof MobEntity mobEntity && !memory.spawnAsAdult())
								mobEntity.setBaby(true);
							return entity;
						})
				);
	}
	
	@Override
	public UUID getOwnerUUID() {
		return this.ownerUUID;
	}
	
	@Override
	public void setOwner(@NotNull PlayerEntity playerEntity) {
		this.ownerUUID = playerEntity.getUuid();
		markDirty();
	}
	
	public ItemStack getMemoryItemStack() {
		return this.memoryItemStack;
	}
	
}
