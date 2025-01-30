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

public class DynamicParticleEffectAlwaysShow extends DynamicParticleEffect {
	
	public static final MapCodec<DynamicParticleEffectAlwaysShow> CODEC = RecordCodecBuilder.mapCodec(
			(instance) -> instance.group(
					Registries.PARTICLE_TYPE.getCodec().fieldOf("particle_type").forGetter((effect) -> effect.particleType),
					Codec.FLOAT.fieldOf("gravity").forGetter((effect) -> effect.gravity),
					Codecs.VECTOR_3F.fieldOf("color").forGetter((effect) -> effect.color),
					Codec.FLOAT.fieldOf("scale").forGetter((effect) -> effect.scale),
					Codec.INT.fieldOf("lifetime_ticks").forGetter((effect) -> effect.lifetimeTicks),
					Codec.BOOL.fieldOf("collisions").forGetter((effect) -> effect.collisions),
					Codec.BOOL.fieldOf("glow_in_the_dark").forGetter((effect) -> effect.glowing)
			).apply(instance, DynamicParticleEffectAlwaysShow::new));
	
	public static PacketCodec<RegistryByteBuf, DynamicParticleEffectAlwaysShow> PACKET_CODEC = SpectrumPacketCodecs.tuple(
			PacketCodecs.registryValue(RegistryKeys.PARTICLE_TYPE), DynamicParticleEffectAlwaysShow::getParticleType,
			PacketCodecs.FLOAT, DynamicParticleEffectAlwaysShow::getGravity,
			PacketCodecs.VECTOR3F, DynamicParticleEffectAlwaysShow::getColor,
			PacketCodecs.FLOAT, DynamicParticleEffectAlwaysShow::getScale,
			PacketCodecs.INTEGER, DynamicParticleEffectAlwaysShow::getLifetimeTicks,
			PacketCodecs.BOOL, DynamicParticleEffectAlwaysShow::isCollisions,
			PacketCodecs.BOOL, DynamicParticleEffectAlwaysShow::isGlowing,
			DynamicParticleEffectAlwaysShow::new
	);
	
	public DynamicParticleEffectAlwaysShow(float gravity, Vector3f color, float scale, int lifetimeTicks, boolean collisions, boolean glowInTheDark) {
		this(SpectrumParticleTypes.SHOOTING_STAR, gravity, color, scale, lifetimeTicks, collisions, glowInTheDark);
	}
	
	public DynamicParticleEffectAlwaysShow(ParticleType<?> particleType, float gravity, Vector3f color, float scale, int lifetimeTicks, boolean collisions, boolean glowInTheDark) {
		super(particleType, gravity, color, scale, lifetimeTicks, collisions, glowInTheDark);
	}
	
	@Override
	public ParticleType<?> getType() {
		return SpectrumParticleTypes.DYNAMIC_ALWAYS_SHOW;
	}
	
}
