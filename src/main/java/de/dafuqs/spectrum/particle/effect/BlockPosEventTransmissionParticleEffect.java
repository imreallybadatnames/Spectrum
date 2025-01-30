package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.particle.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.particle.*;
import net.minecraft.world.event.*;

public class BlockPosEventTransmissionParticleEffect extends SimpleTransmissionParticleEffect {
	
	public static final MapCodec<BlockPosEventTransmissionParticleEffect> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			PositionSource.CODEC.fieldOf("destination").forGetter((effect) -> effect.destination),
			Codec.INT.fieldOf("arrival_in_ticks").forGetter((vibrationParticleEffect) -> vibrationParticleEffect.arrivalInTicks)
	).apply(instance, BlockPosEventTransmissionParticleEffect::new));
	
	public static PacketCodec<RegistryByteBuf, BlockPosEventTransmissionParticleEffect> PACKET_CODEC = PacketCodec.tuple(
			PositionSource.PACKET_CODEC, BlockPosEventTransmissionParticleEffect::getDestination,
			PacketCodecs.INTEGER, BlockPosEventTransmissionParticleEffect::getArrivalInTicks,
			BlockPosEventTransmissionParticleEffect::new
	);
	
	public BlockPosEventTransmissionParticleEffect(PositionSource positionSource, Integer arrivalInTicks) {
		super(positionSource, arrivalInTicks);
	}
	
	@Override
	public ParticleType<BlockPosEventTransmissionParticleEffect> getType() {
		return SpectrumParticleTypes.BLOCK_POS_EVENT_TRANSMISSION;
	}
	
}
