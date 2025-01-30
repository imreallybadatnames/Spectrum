package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.particle.*;
import net.minecraft.registry.*;
import net.minecraft.util.dynamic.*;
import org.joml.*;

public class DynamicParticleEffect implements ParticleEffect {
	
	public static final MapCodec<DynamicParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
			(instance) -> instance.group(
					Registries.PARTICLE_TYPE.getCodec().fieldOf("particle_type").forGetter((effect) -> effect.particleType),
					Codec.FLOAT.fieldOf("gravity").forGetter((effect) -> effect.gravity),
					Codecs.VECTOR_3F.fieldOf("color").forGetter((effect) -> effect.color),
					Codec.FLOAT.fieldOf("scale").forGetter((effect) -> effect.scale),
					Codec.INT.fieldOf("lifetime_ticks").forGetter((effect) -> effect.lifetimeTicks),
					Codec.BOOL.fieldOf("collisions").forGetter((effect) -> effect.collisions),
					Codec.BOOL.fieldOf("glow_in_the_dark").forGetter((effect) -> effect.glowing)
			).apply(instance, DynamicParticleEffect::new));
	
	public static PacketCodec<RegistryByteBuf, DynamicParticleEffect> PACKET_CODEC = SpectrumPacketCodecs.tuple(
			PacketCodecs.registryValue(RegistryKeys.PARTICLE_TYPE), DynamicParticleEffect::getParticleType,
			PacketCodecs.FLOAT, DynamicParticleEffect::getGravity,
			PacketCodecs.VECTOR3F, DynamicParticleEffect::getColor,
			PacketCodecs.FLOAT, DynamicParticleEffect::getScale,
			PacketCodecs.INTEGER, DynamicParticleEffect::getLifetimeTicks,
			PacketCodecs.BOOL, DynamicParticleEffect::isCollisions,
			PacketCodecs.BOOL, DynamicParticleEffect::isGlowing,
			DynamicParticleEffect::new
	);
	
	public ParticleType<?> particleType;
	public Vector3f color;
	public float scale;
	public int lifetimeTicks;
	public float gravity;
	public boolean collisions;
	public boolean glowing;
	
	public DynamicParticleEffect(float gravity, Vector3f color, float scale, int lifetimeTicks, boolean collisions, boolean glowing) {
		this(SpectrumParticleTypes.SHOOTING_STAR, gravity, color, scale, lifetimeTicks, collisions, glowing);
	}
	
	public DynamicParticleEffect(ParticleType<?> particleType, float gravity, Vector3f color, float scale, int lifetimeTicks, boolean collisions, boolean glowing) {
		this.particleType = particleType;
		this.gravity = gravity;
		this.color = color;
		this.scale = scale;
		this.lifetimeTicks = lifetimeTicks;
		this.collisions = collisions;
		this.glowing = glowing;
	}

	@Override
	public ParticleType<?> getType() {
		return SpectrumParticleTypes.DYNAMIC;
	}
	
	public float getGravity() {
		return this.gravity;
	}
	
	public ParticleType<?> getParticleType() {
		return particleType;
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public float getScale() {
		return scale;
	}
	
	public int getLifetimeTicks() {
		return lifetimeTicks;
	}
	
	public boolean isCollisions() {
		return collisions;
	}
	
	public boolean isGlowing() {
		return glowing;
	}
	
}
