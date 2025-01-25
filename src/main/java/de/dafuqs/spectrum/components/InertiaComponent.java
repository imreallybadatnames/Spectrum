package de.dafuqs.spectrum.components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.block.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.registry.*;

public record InertiaComponent(Block lastMined, long count) {
	
	public static final InertiaComponent DEFAULT = new InertiaComponent(Blocks.AIR, 0);
	
	public static final Codec<InertiaComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			Registries.BLOCK.getCodec().fieldOf("last_mined").forGetter(InertiaComponent::lastMined),
			Codec.LONG.fieldOf("count").forGetter(InertiaComponent::count)
	).apply(i, InertiaComponent::new));
	
	public static final PacketCodec<RegistryByteBuf, InertiaComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.registryValue(RegistryKeys.BLOCK),
			InertiaComponent::lastMined,
			PacketCodecs.VAR_LONG,
			InertiaComponent::count,
			InertiaComponent::new
	);
	
	@Override
	public boolean equals(Object o) {
		return this == o || o instanceof InertiaComponent(Block oLastMined, long oCount)
				&& oLastMined.equals(lastMined)
				&& oCount == count;
	}
	
}
