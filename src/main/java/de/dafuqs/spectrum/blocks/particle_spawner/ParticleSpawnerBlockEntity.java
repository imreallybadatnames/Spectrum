package de.dafuqs.spectrum.blocks.particle_spawner;

import de.dafuqs.spectrum.blocks.BlockPosDelegate;
import de.dafuqs.spectrum.inventories.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.network.listener.*;
import net.minecraft.network.packet.*;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.*;
import net.minecraft.text.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public class ParticleSpawnerBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
	
	protected ParticleSpawnerConfiguration configuration;
	protected boolean initialized = false;
	private final PropertyDelegate propertyDelegate = new BlockPosDelegate(pos);

	public ParticleSpawnerBlockEntity(BlockPos blockPos, BlockState blockState) {
		this(SpectrumBlockEntities.PARTICLE_SPAWNER, blockPos, blockState);
	}
	
	public ParticleSpawnerBlockEntity(BlockEntityType<ParticleSpawnerBlockEntity> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
		
		this.configuration = new ParticleSpawnerConfiguration(
				SpectrumParticleTypes.SHOOTING_STAR,
				new Vec3i(80, 40, 0),
				false,
				10.0F,
				new Vec3d(0.0, 1.0, 0.0),
				new Vec3d(0.0, 0.0, 0.0),
				new Vec3d(0.0, 0.1, 0.0),
				new Vec3d(0.1, 0.1, 0.1),
				1.0F,
				0.2F,
				20,
				10,
				0.02F,
				true);
	}

	@SuppressWarnings("unused")
    public static void clientTick(World world, BlockPos pos, BlockState state, ParticleSpawnerBlockEntity blockEntity) {
		BlockState blockState = world.getBlockState(pos);
		if (blockState.getBlock() instanceof AbstractParticleSpawnerBlock particleSpawnerBlock && particleSpawnerBlock.shouldSpawnParticles(world, pos)) {
			blockEntity.configuration.spawnParticles(world, pos);
		}
	}

	// Called when the chunk is first loaded to initialize this be
	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		NbtCompound nbtCompound = new NbtCompound();
		this.writeNbt(nbtCompound, registryLookup);
		return nbtCompound;
	}
	
	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}
	
	public void updateInClientWorld() {
		if (world != null) {
			world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), Block.NO_REDRAW);
		}
	}
	
	@Override
	public void writeNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(tag, registryLookup);
		tag.put("particle_config", ParticleSpawnerConfiguration.CODEC.encodeStart(NbtOps.INSTANCE, this.configuration).result().orElse(new NbtCompound()));
	}
	
	@Override
	public void readNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(tag, registryLookup);
		this.initialized = false;
		var config = ParticleSpawnerConfiguration.CODEC.decode(NbtOps.INSTANCE, tag.getCompound("particle_config")).result();
		if (config.isPresent()) {
			this.configuration = config.get().getFirst();
			this.initialized = true;
		}
	}
	
	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new ParticleSpawnerScreenHandler(syncId, inv, propertyDelegate);
	}
	
	@Override
	public Text getDisplayName() {
		return Text.translatable("block.spectrum.particle_spawner");
	}
	
	public void applySettings(ParticleSpawnerConfiguration configuration) {
		this.configuration = configuration;
		this.initialized = true;
		
		this.updateInClientWorld();
		this.markDirty();
	}

	public ParticleSpawnerConfiguration getConfiguration() {
		return configuration;
	}

}
