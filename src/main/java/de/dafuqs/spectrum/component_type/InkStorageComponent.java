package de.dafuqs.spectrum.component_type;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.energy.color.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.dynamic.*;

import java.util.*;

public record InkStorageComponent(long maxEnergyTotal, long maxPerColor, Map<InkColor, Long> storedEnergy) {
	
	public static final Codec<InkStorageComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.LONG.fieldOf("max_energy_total").forGetter(c -> c.maxEnergyTotal),
			Codec.LONG.fieldOf("max_per_color").forGetter(c -> c.maxPerColor),
			Codecs.strictUnboundedMap(InkColor.CODEC, Codec.LONG).fieldOf("stored_energy").forGetter(c -> c.storedEnergy)
	).apply(instance, InkStorageComponent::new));
	
	public static final PacketCodec<ByteBuf, InkStorageComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_LONG,
			c -> c.maxEnergyTotal,
			PacketCodecs.VAR_LONG,
			c -> c.maxPerColor,
			PacketCodecs.map(HashMap::new, InkColor.PACKET_CODEC, PacketCodecs.VAR_LONG),
			c -> c.storedEnergy,
			InkStorageComponent::new
	);
	
	public InkStorageComponent(InkStorage storage) {
		this(storage.getMaxTotal(), storage.getMaxPerColor(), storage.getEnergy());
	}
	
}
