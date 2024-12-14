package de.dafuqs.spectrum.blocks.particle_spawner;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.particle.effect.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;
import org.joml.*;

public record ParticleSpawnerConfiguration(
	ParticleType<?> particleType,
	Vec3i cmyColor, // 0-100 cmy
	boolean glowing,
	float particlesPerSecond, /* >1 = every xth tick */
	Vec3d sourcePosition,
	Vec3d sourcePositionVariance,
	Vec3d velocity,
	Vec3d velocityVariance,
	float scale,
	float scaleVariance,
	int lifetimeTicks,
	int lifetimeVariance,
	float gravity,
	boolean collisions
) {

	public static final Codec<ParticleSpawnerConfiguration> CODEC = RecordCodecBuilder.create(i -> i.group(
			Registries.PARTICLE_TYPE.getCodec().fieldOf("particle_type_identifier").forGetter(ParticleSpawnerConfiguration::particleType),
			Vec3i.CODEC.fieldOf("color").forGetter(ParticleSpawnerConfiguration::cmyColor),
			Codec.BOOL.fieldOf("glowing").forGetter(ParticleSpawnerConfiguration::glowing),
			Codec.FLOAT.fieldOf("particles_per_tick").forGetter(ParticleSpawnerConfiguration::particlesPerSecond),
			Vec3d.CODEC.fieldOf("source_pos").forGetter(ParticleSpawnerConfiguration::sourcePosition),
			Vec3d.CODEC.fieldOf("source_pos_variance").forGetter(ParticleSpawnerConfiguration::sourcePositionVariance),
			Vec3d.CODEC.fieldOf("source_velocity").forGetter(ParticleSpawnerConfiguration::velocity),
			Vec3d.CODEC.fieldOf("source_velocity_variance").forGetter(ParticleSpawnerConfiguration::velocityVariance),
			Codec.FLOAT.fieldOf("scale").forGetter(ParticleSpawnerConfiguration::scale),
			Codec.FLOAT.fieldOf("scale_variance").forGetter(ParticleSpawnerConfiguration::scaleVariance),
			Codec.INT.fieldOf("lifetime").forGetter(ParticleSpawnerConfiguration::lifetimeTicks),
			Codec.INT.fieldOf("lifetime_variance").forGetter(ParticleSpawnerConfiguration::lifetimeVariance),
			Codec.FLOAT.fieldOf("gravity").forGetter(ParticleSpawnerConfiguration::gravity),
			Codec.BOOL.fieldOf("collisions").forGetter(ParticleSpawnerConfiguration::collisions)
	).apply(i, ParticleSpawnerConfiguration::new));

	public static final PacketCodec<PacketByteBuf, ParticleSpawnerConfiguration> PACKET_CODEC = PacketCodec.ofStatic(ParticleSpawnerConfiguration::write, ParticleSpawnerConfiguration::fromBuf);

	public static Vector3fc CMYtoRGB(Vec3i cmy) {
		float r = 1F - cmy.getX() / 100F;
		float g = 1F - cmy.getY() / 100F;
		float b = 1F - cmy.getZ() / 100F;
		return new Vector3f(r, g, b);
	}
	
	public static void write(PacketByteBuf buf, ParticleSpawnerConfiguration configuration) {
		buf.writeString(Registries.PARTICLE_TYPE.getId(configuration.particleType).toString());
		buf.writeInt(configuration.cmyColor.getX());
		buf.writeInt(configuration.cmyColor.getY());
		buf.writeInt(configuration.cmyColor.getZ());
		buf.writeBoolean(configuration.glowing);
		buf.writeFloat(configuration.particlesPerSecond);
		buf.writeDouble(configuration.sourcePosition.x);
		buf.writeDouble(configuration.sourcePosition.y);
		buf.writeDouble(configuration.sourcePosition.z);
		buf.writeDouble(configuration.sourcePositionVariance.x);
		buf.writeDouble(configuration.sourcePositionVariance.y);
		buf.writeDouble(configuration.sourcePositionVariance.z);
		buf.writeDouble(configuration.velocity.x);
		buf.writeDouble(configuration.velocity.y);
		buf.writeDouble(configuration.velocity.z);
		buf.writeDouble(configuration.velocityVariance.x);
		buf.writeDouble(configuration.velocityVariance.y);
		buf.writeDouble(configuration.velocityVariance.z);
		buf.writeFloat(configuration.scale);
		buf.writeFloat(configuration.scaleVariance);
		buf.writeInt(configuration.lifetimeTicks);
		buf.writeInt(configuration.lifetimeVariance);
		buf.writeFloat(configuration.gravity);
		buf.writeBoolean(configuration.collisions);
	}
	
	public static ParticleSpawnerConfiguration fromBuf(PacketByteBuf buf) {
		Identifier particleIdentifier = Identifier.of(buf.readString());
		ParticleType<?> particleType = Registries.PARTICLE_TYPE.get(particleIdentifier);
		Vec3i cmyColor = new Vec3i(buf.readInt(), buf.readInt(), buf.readInt());
		boolean glowing = buf.readBoolean();
		float particlesPerSecond = buf.readFloat();
		Vec3d sourcePosition = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
		Vec3d sourcePositionVariance = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
		Vec3d velocity = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
		Vec3d velocityVariance = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
		float scale = buf.readFloat();
		float scaleVariance = buf.readFloat();
		int lifetimeTicks = buf.readInt();
		int lifetimeVariance = buf.readInt();
		float gravity = buf.readFloat();
		boolean collisions = buf.readBoolean();
		
		return new ParticleSpawnerConfiguration(particleType, cmyColor, glowing, particlesPerSecond, sourcePosition, sourcePositionVariance,
				velocity, velocityVariance, scale, scaleVariance, lifetimeTicks, lifetimeVariance, gravity, collisions);
	}

	public void spawnParticles(World world, @NotNull BlockPos pos) {
		float particlesToSpawn = particlesPerSecond / 20F;
		while (particlesToSpawn >= 1 || world.random.nextFloat() < particlesToSpawn) {
			spawnParticle(world, pos, world.random);
			particlesToSpawn--;
		}
	}
	
	private void spawnParticle(World world, @NotNull BlockPos pos, Random random) {
		float randomScale = scaleVariance == 0 ? scale : (float) (scale + scaleVariance - random.nextDouble() * scaleVariance * 2.0D);
		int randomLifetime = lifetimeVariance == 0 ? lifetimeTicks : (int) (lifetimeTicks + lifetimeVariance - random.nextDouble() * lifetimeVariance * 2.0D);
		
		if (randomScale > 0 && randomLifetime > 0) {
			double randomOffsetX = sourcePositionVariance.x == 0 ? 0 : sourcePositionVariance.x - random.nextDouble() * sourcePositionVariance.x * 2.0D;
			double randomOffsetY = sourcePositionVariance.y == 0 ? 0 : sourcePositionVariance.y - random.nextDouble() * sourcePositionVariance.y * 2.0D;
			double randomOffsetZ = sourcePositionVariance.z == 0 ? 0 : sourcePositionVariance.z - random.nextDouble() * sourcePositionVariance.z * 2.0D;
			
			double randomVelocityX = velocityVariance.x == 0 ? 0 : velocityVariance.x - random.nextDouble() * velocityVariance.x * 2.0D;
			double randomVelocityY = velocityVariance.y == 0 ? 0 : velocityVariance.y - random.nextDouble() * velocityVariance.y * 2.0D;
			double randomVelocityZ = velocityVariance.z == 0 ? 0 : velocityVariance.z - random.nextDouble() * velocityVariance.z * 2.0D;

			var rgbColor = CMYtoRGB(cmyColor);

			world.addParticle(
					new DynamicParticleEffect(particleType, gravity, new Vector3f(rgbColor), randomScale, randomLifetime, collisions, glowing),
					(double) pos.getX() + 0.5 + sourcePosition.x + randomOffsetX,
					(double) pos.getY() + 0.5 + sourcePosition.y + randomOffsetY,
					(double) pos.getZ() + 0.5 + sourcePosition.z + randomOffsetZ,
					velocity.x + randomVelocityX,
					velocity.y + randomVelocityY,
					velocity.z + randomVelocityZ
			);
		}
	}
	
}
