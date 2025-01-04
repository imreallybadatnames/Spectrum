package de.dafuqs.spectrum.entity.entity;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.damage.*;
import net.minecraft.entity.data.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.server.network.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.event.*;
import org.jetbrains.annotations.*;

public class SpectrumBossEntity extends PathAwareEntity {
	
	private static final TrackedData<Integer> INVINCIBILITY_TICKS = DataTracker.registerData(SpectrumBossEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private final ServerBossBar bossBar;
	
	protected SpectrumBossEntity(EntityType<? extends SpectrumBossEntity> entityType, World world) {
		super(entityType, world);
		this.bossBar = (ServerBossBar) (new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS)).setDarkenSky(true);
		this.setHealth(this.getMaxHealth());
	}
	
	public boolean hasInvincibilityTicks() {
		return this.dataTracker.get(INVINCIBILITY_TICKS) > 0;
	}
	
	public void setInvincibilityTicks(int ticks) {
		this.dataTracker.set(INVINCIBILITY_TICKS, ticks);
	}
	
	public void tickInvincibility() {
		dataTracker.set(INVINCIBILITY_TICKS, Math.max(0, this.dataTracker.get(INVINCIBILITY_TICKS) - 1));
	}
	
	@Override
	public boolean canHit() {
		return super.canHit() && !hasInvincibilityTicks();
	}
	
	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(INVINCIBILITY_TICKS, 0);
	}
	
	@Override
	public boolean shouldRender(double distance) {
		return true;
	}
	
	@Override
	public boolean canTarget(EntityType<?> type) {
		return true;
	}
	
	protected boolean isNonVanillaKillCommandDamage(DamageSource source, float amount) {
		if (source.isOf(DamageTypes.OUT_OF_WORLD) || amount != Float.MAX_VALUE) {
			return false;
		}
		
		Thread currentThread = Thread.currentThread();
		StackTraceElement[] stackTrace = currentThread.getStackTrace();
		
		int i = 0;
		for (StackTraceElement element : stackTrace) {
			if (element.getClassName().contains("net.minecraft")) {
				// this is a vanilla or admin /kill
				this.remove(RemovalReason.KILLED);
				this.emitGameEvent(GameEvent.ENTITY_DIE);
				return false;
			}
			if (i > 3) {
				// not called from KillCommand? heresy
				return true;
			}
			i++;
		}
		return false;
	}
	
	@Override
	public void applyDamage(DamageSource source, float amount) {
		// called when damage was dealt
		Entity dealer = source.getAttacker();
		if (!hasInvincibilityTicks() && dealer instanceof PlayerEntity && EntityHelper.isRealPlayerProjectileOrPet(dealer)) {
			super.applyDamage(source, amount);
			this.setInvincibilityTicks(20);
		}
	}
	
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
	}
	
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (this.hasCustomName()) {
			this.bossBar.setName(this.getDisplayName());
		}
	}
	
	@Override
	public void onDeath(DamageSource damageSource) {
		super.onDeath(damageSource);

		// grant the kill to all players close by players
		// => should they battle in a team the kill counts for all players
		// instead of just the one that did the killing blow like in vanilla
		World world = this.getWorld();
		if (!world.isClient) {
			for (PlayerEntity closeByPlayer : this.getWorld().getEntitiesByType(EntityType.PLAYER, getBoundingBox().expand(24), Entity::isAlive)) {
				Criteria.ENTITY_KILLED_PLAYER.trigger((ServerPlayerEntity) closeByPlayer, this, damageSource);
			}
		}
	}
	
	@Override
	protected void drop(DamageSource source) {
		Entity entity = source.getAttacker();
		if (EntityHelper.isRealPlayerProjectileOrPet(entity)) {
			super.drop(source);
		}
	}
	
	@Override
	public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
		return true;
	}
	
	@Override
	public boolean cannotDespawn() {
		return true;
	}
	
	@Override
	protected boolean isDisallowedInPeaceful() {
		return false;
	}
	
	@Override
	public void setCustomName(@Nullable Text name) {
		super.setCustomName(name);
		this.bossBar.setName(this.getDisplayName());
	}
	
	@Override
	public void onStartedTrackingBy(ServerPlayerEntity player) {
		super.onStartedTrackingBy(player);
		this.bossBar.addPlayer(player);
	}
	
	@Override
	public void onStoppedTrackingBy(ServerPlayerEntity player) {
		super.onStoppedTrackingBy(player);
		this.bossBar.removePlayer(player);
	}
	
	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}
	
	@Override
	public void checkDespawn() {
		if (this.getWorld().getDifficulty() == Difficulty.PEACEFUL && this.isDisallowedInPeaceful()) {
			this.discard();
		} else {
			this.despawnCounter = 0;
		}
	}
	
	@Override
	protected void mobTick() {
		super.mobTick();
		this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
	}
	
	@Override
	protected float getSoundVolume() {
		return 4.0F;
	}
	
	@Override
	public boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity source) {
		return false;
	}
	
	@Override
	protected boolean canStartRiding(Entity entity) {
		return false;
	}
	
	@Override
	public boolean canUsePortals() {
		return false;
	}
	
	@Override
	public boolean canTarget(LivingEntity target) {
		return target.canTakeDamage();
	}
	
	@Override
	public SoundCategory getSoundCategory() {
		return SoundCategory.HOSTILE;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return SpectrumSoundEvents.ENTITY_MONSTROSITY_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SpectrumSoundEvents.ENTITY_MONSTROSITY_HURT;
	}
	
	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.ENTITY_HOSTILE_SWIM;
	}
	
	@Override
	protected SoundEvent getSplashSound() {
		return SoundEvents.ENTITY_HOSTILE_SPLASH;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_HOSTILE_DEATH;
	}
	
	@Override
	public LivingEntity.FallSounds getFallSounds() {
		return new LivingEntity.FallSounds(SoundEvents.ENTITY_HOSTILE_SMALL_FALL, SoundEvents.ENTITY_HOSTILE_BIG_FALL);
	}
	
	@Override
	public boolean canBeLeashedBy(PlayerEntity player) {
		return false;
	}
	
	@Override
	public void slowMovement(BlockState state, Vec3d multiplier) {
	}
	
	@Override
	public boolean shouldDropXp() {
		return true;
	}
	
	@Override
	protected boolean shouldDropLoot() {
		return true;
	}
	
}
