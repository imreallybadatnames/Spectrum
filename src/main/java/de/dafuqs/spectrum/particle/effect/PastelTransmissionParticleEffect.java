package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.particle.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.particle.*;
import net.minecraft.registry.*;
import net.minecraft.util.math.*;

import java.util.*;

public record PastelTransmissionParticleEffect(List<BlockPos> nodePositions, ItemStack stack, int travelTime, int color) implements ParticleEffect {
	
	public static final MapCodec<PastelTransmissionParticleEffect> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			BlockPos.CODEC.listOf().fieldOf("positions").forGetter((particleEffect) -> particleEffect.nodePositions),
			ItemStack.CODEC.fieldOf("stack").forGetter((effect) -> effect.stack),
			Codec.INT.fieldOf("travel_time").forGetter((particleEffect) -> particleEffect.travelTime),
			Codec.INT.fieldOf("color").forGetter((particleEffect) -> particleEffect.color)
	).apply(i, PastelTransmissionParticleEffect::new));
	
	public static final PacketCodec<RegistryByteBuf, PastelTransmissionParticleEffect> PACKET_CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC.collect(PacketCodecs.toList()), c -> c.nodePositions,
			ItemStack.PACKET_CODEC, c -> c.stack,
			PacketCodecs.VAR_INT, c -> c.travelTime,
			PacketCodecs.VAR_INT, c -> c.color,
			PastelTransmissionParticleEffect::new
	);
	
	@Override
	public ParticleType<PastelTransmissionParticleEffect> getType() {
		return SpectrumParticleTypes.PASTEL_TRANSMISSION;
	}
	
	@Override
	public String toString() {
		int nodeCount = this.nodePositions.size();
		BlockPos source = this.nodePositions.getFirst();
		BlockPos destination = this.nodePositions.getLast();
		int d = source.getX();
		int e = source.getY();
		int f = source.getZ();
		int g = destination.getX();
		int h = destination.getY();
		int i = destination.getZ();
		return String.format(Locale.ROOT, "%s %d %d %d %d %d %d %d %d %d", Registries.PARTICLE_TYPE.getId(this.getType()), this.travelTime, nodeCount, d, e, f, g, h, i, this.color);
	}
	
}
