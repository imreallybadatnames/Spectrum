package de.dafuqs.spectrum.blocks.gemstone;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.sound.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class SpectrumGemstoneBlock extends AmethystBlock {

	public static final MapCodec<SpectrumGemstoneBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			createSettingsCodec(),
			SoundEvent.CODEC.fieldOf("hit_sound_event").forGetter(b -> b.hitSoundEvent),
			SoundEvent.CODEC.fieldOf("chime_sound_event").forGetter(b -> b.chimeSoundEvent)
	).apply(i, SpectrumGemstoneBlock::new));

	private final SoundEvent hitSoundEvent;
	private final SoundEvent chimeSoundEvent;
	
	public SpectrumGemstoneBlock(Settings settings, SoundEvent hitSoundEvent, SoundEvent chimeSoundEvent) {
		super(settings);
		this.hitSoundEvent = hitSoundEvent;
		this.chimeSoundEvent = chimeSoundEvent;
	}

	@Override
	public MapCodec<? extends SpectrumGemstoneBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
		if (!world.isClient) {
			BlockPos blockPos = hit.getBlockPos();
			world.playSound(null, blockPos, hitSoundEvent, SoundCategory.BLOCKS, 1.0F, 0.5F + world.random.nextFloat() * 1.2F);
			world.playSound(null, blockPos, chimeSoundEvent, SoundCategory.BLOCKS, 1.0F, 0.5F + world.random.nextFloat() * 1.2F);
		}
	}
	
}
