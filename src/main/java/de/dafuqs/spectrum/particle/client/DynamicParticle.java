package de.dafuqs.spectrum.particle.client;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.mixin.client.accessors.*;
import de.dafuqs.spectrum.particle.effect.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.*;
import net.minecraft.client.world.*;
import net.minecraft.registry.*;
import org.jetbrains.annotations.*;

@Environment(EnvType.CLIENT)
public class DynamicParticle extends SpriteBillboardParticle {
	
	protected boolean glowInTheDark = false;
	
	public DynamicParticle(ClientWorld clientWorld, double d, double e, double f, double velocityX, double velocityY, double velocityZ) {
		super(clientWorld, d, e, f, velocityX, velocityY, velocityZ);
		// Override the default random particle velocities again.
		// Not performant, but super() has to be called here :/
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.velocityZ = velocityZ;
	}
	
	@Override
	public int getBrightness(float tint) {
		if (glowInTheDark) {
			return LightmapTextureManager.MAX_LIGHT_COORDINATE;
		} else {
			return super.getBrightness(tint);
		}
	}
	
	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
	}
	
	public void apply(@NotNull DynamicParticleEffect effect) {
		this.setSprite(sprite);
		this.setMaxAge(effect.lifetimeTicks());
		this.scale(effect.scale());
		this.setColor(effect.color().x(), effect.color().y(), effect.color().z());
		this.gravityStrength = effect.gravity();
		this.collidesWithWorld = effect.collisions();
		this.glowInTheDark = effect.glowing();
	}
	
	public static class Factory<P extends DynamicParticleEffect> implements ParticleFactory<P> {
		
		private final SpriteProvider spriteProvider;
		
		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}
		
		@Override
        public Particle createParticle(P parameters, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			MinecraftClient client = MinecraftClient.getInstance();
			DynamicParticle particle = new DynamicParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
			
			var particleTypeIdentifier = Registries.PARTICLE_TYPE.getId(parameters.particleType());
			SpriteProvider dynamicProvider = ((ParticleManagerAccessor) client.particleManager).getSpriteAwareFactories().get(particleTypeIdentifier);
			if (dynamicProvider == null) {
				SpectrumCommon.logError("Trying to use a non-existent sprite provider for particle spawner particle: " + particleTypeIdentifier);
				particle.setSprite(spriteProvider);
			} else {
				particle.setSprite(dynamicProvider);
			}
			
			particle.apply(parameters);
			return particle;
		}
		
	}
	
}
