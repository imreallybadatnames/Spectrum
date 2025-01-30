package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.particle.*;
import net.minecraft.util.math.*;

import java.util.*;

public class PastelTransmissionParticleEffect implements ParticleEffect {
	
	public static final MapCodec<PastelTransmissionParticleEffect> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.list(BlockPos.CODEC).fieldOf("positions").forGetter((particleEffect) -> particleEffect.nodePositions),
            ItemStack.CODEC.fieldOf("stack").forGetter((effect) -> effect.stack),
            Codec.INT.fieldOf("travel_time").forGetter((particleEffect) -> particleEffect.travelTime),
            Codec.INT.fieldOf("color").forGetter((particleEffect) -> particleEffect.color)
    ).apply(instance, PastelTransmissionParticleEffect::new));
	
	
	public static PacketCodec<RegistryByteBuf, PastelTransmissionParticleEffect> PACKET_CODEC = PacketCodec.tuple(
			SpectrumPacketCodecs.BLOCKPOS_LIST, PastelTransmissionParticleEffect::getNodePositions,
			ItemStack.PACKET_CODEC, PastelTransmissionParticleEffect::getStack,
			PacketCodecs.INTEGER, PastelTransmissionParticleEffect::getTravelTime,
			PacketCodecs.INTEGER, PastelTransmissionParticleEffect::getColor,
			PastelTransmissionParticleEffect::new
	);

    private final List<BlockPos> nodePositions;
    private final ItemStack stack;
	private final int travelTime;
	private final int color;
	
	public PastelTransmissionParticleEffect(List<BlockPos> nodePositions, ItemStack stack, Integer travelTime, Integer color) {
		this.nodePositions = nodePositions;
		this.stack = stack;
		this.travelTime = travelTime;
		this.color = color;
	}
	
	@Override
	public ParticleType<PastelTransmissionParticleEffect> getType() {
		return SpectrumParticleTypes.PASTEL_TRANSMISSION;
	}

    public List<BlockPos> getNodePositions() {
        return this.nodePositions;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public int getTravelTime() {
        return this.travelTime;
    }

    public int getColor() {
        return this.color;
    }

}
