package de.dafuqs.spectrum.spells;

import com.google.common.collect.*;
import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.event.*;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.*;

import java.util.*;

public class MoonstoneStrike {

	private final World world;
	private final double x;
	private final double y;
    private final double z;
    public final @Nullable Entity entity;
    public final float power;
    public final float knockbackMod;
    private final DamageSource damageSource;
	protected final Map<PlayerEntity, Vec3d> affectedPlayers;

    public MoonstoneStrike(World world, @Nullable Entity entity, @Nullable DamageSource damageSource, double x, double y, double z, float power, float knockbackMod) {
        this.affectedPlayers = Maps.newHashMap();
        this.world = world;
        this.entity = entity;
        this.power = power;
        this.knockbackMod = knockbackMod;
        this.x = x;
        this.y = y;
		this.z = z;
		this.damageSource = damageSource == null ? SpectrumDamageTypes.moonstoneStrike(world, this) : damageSource;
    }

	public static void create(World world, Entity entity, @Nullable DamageSource damageSource, double x, double y, double z, float power) {
        create(world, entity, damageSource, x, y, z, power, power);
    }

	public static void create(World world, Entity entity, @Nullable DamageSource damageSource, double x, double y, double z, float power, float knockbackMod) {
        MoonstoneStrike moonstoneStrike = new MoonstoneStrike(world, entity, damageSource, x, y, z, power, knockbackMod);

		if (world.isClient) {
            world.playSound(x, y, z, SpectrumSoundEvents.MOONSTONE_STRIKE, SoundCategory.BLOCKS, 4.0F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F, false);
			world.playSound(x, y, z, SpectrumSoundEvents.SOFT_HUM, SoundCategory.BLOCKS, 0.5F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F, false);
			world.addParticle(SpectrumParticleTypes.MOONSTONE_STRIKE, x, y, z, 1.0, 0.0, 0.0);
        } else {
            moonstoneStrike.damageAndKnockbackEntities();
			MoonstoneBlastPayload.sendMoonstoneBlast((ServerWorld) world, moonstoneStrike);
            moonstoneStrike.affectWorld();
        }
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getPower() {
        return power;
    }

	public float getKnockbackMod() {
        return knockbackMod;
    }

	public DamageSource getDamageSource() {
        return this.damageSource;
    }

	public Map<PlayerEntity, Vec3d> getAffectedPlayers() {
        return this.affectedPlayers;
    }

	public void damageAndKnockbackEntities() {
        this.world.emitGameEvent(this.entity, GameEvent.EXPLODE, new Vec3d(this.x, this.y, this.z));
        
        float reach = this.power * 2.0F;
        int minX = MathHelper.floor(this.x - (double) reach - 1.0);
        int maxX = MathHelper.floor(this.x + (double) reach + 1.0);
        int minY = MathHelper.floor(this.y - (double) reach - 1.0);
        int maxY = MathHelper.floor(this.y + (double) reach + 1.0);
        int minZ = MathHelper.floor(this.z - (double) reach - 1.0);
        int maxZ = MathHelper.floor(this.z + (double) reach + 1.0);
        Vec3d center = new Vec3d(this.x, this.y, this.z);

		for (Entity entity : world.getOtherEntities(this.entity, new Box(minX, minY, minZ, maxX, maxY, maxZ))) {
            //TODO: Can we convert this into an explosion subclass?
            if (!entity.isImmuneToExplosion(null)) {
                double unitDist = Math.sqrt(entity.squaredDistanceTo(center)) / (double) reach;

                if (unitDist <= 1.0) { // Within a sphere of the explosion
                    double dx = entity.getX() - this.x;
                    double dy = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - this.y;
                    double dz = entity.getZ() - this.z;
                    double dLen = Math.sqrt(dx * dx + dy * dy + dz * dz);

                    if (dLen != 0.0) { // Don't explode the strike entity itself
                        // Now these are a unit vector, the direction from the explosion to the entity's eyes
                        dx /= dLen;
                        dy /= dLen;
                        dz /= dLen;

                        double scaledExposure = (1.0F - unitDist) * Explosion.getExposure(center, entity);
                        entity.damage(this.damageSource, (float)((scaledExposure * scaledExposure + scaledExposure) / 2.0 * 7.0 * reach + 1.0));

                        double knockback = scaledExposure * this.knockbackMod;
                        if (entity instanceof LivingEntity livingEntity) {
                            knockback *= 1.0 - livingEntity.getAttributeValue(EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE);
                        }

                        dx *= knockback;
                        dy *= knockback;
                        dz *= knockback;
                        Vec3d impact = new Vec3d(dx, dy, dz);
                        entity.setVelocity(entity.getVelocity().add(impact));
                        if (entity instanceof PlayerEntity playerEntity) {
                            if (!playerEntity.isSpectator() && (!playerEntity.isCreative() || !playerEntity.getAbilities().flying)) {
                                this.affectedPlayers.put(playerEntity, impact);
                            }
                        }

                        entity.onExplodedBy(this.entity);
                    }
                }
            }
        }
    }

	public void affectWorld() {
        LivingEntity cause = getCausingEntity();
        int range = Math.max(2, (int) this.power / 2);
		for (BlockPos pos : BlockPos.iterateOutwards(BlockPos.ofFloored(this.x, this.y, this.z), range, range, range)) {
			BlockState blockState = world.getBlockState(pos);
			Block block = blockState.getBlock();
			if (block instanceof MoonstoneStrikeableBlock moonstoneStrikeableBlock) {
				moonstoneStrikeableBlock.onMoonstoneStrike(world, pos, cause);
			}
		}
    }

	public @Nullable LivingEntity getCausingEntity() {
        if (this.entity instanceof LivingEntity livingEntity) {
            return livingEntity;
        } else if (this.entity instanceof ProjectileEntity projectileEntity && projectileEntity.getOwner() instanceof LivingEntity livingEntity) {
            return livingEntity;
        }
        return null;
    }
    
}
