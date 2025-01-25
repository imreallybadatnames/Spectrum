package de.dafuqs.spectrum.components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.energy.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;

import java.util.*;

public record InkPoweredComponent(List<InkPoweredStatusEffectInstance> effects) {
	
	public static final InkPoweredComponent DEFAULT = new InkPoweredComponent(List.of());
	
	public static final Codec<InkPoweredComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			InkPoweredStatusEffectInstance.CODEC.listOf().fieldOf("effects").forGetter(InkPoweredComponent::effects)
	).apply(i, InkPoweredComponent::new));
	
	public static final PacketCodec<RegistryByteBuf, InkPoweredComponent> PACKET_CODEC = PacketCodec.tuple(
			InkPoweredStatusEffectInstance.PACKET_CODEC.collect(PacketCodecs.toList()), InkPoweredComponent::effects,
			InkPoweredComponent::new
	);
	
	@Override
	public boolean equals(Object o) {
		return this == o || o instanceof InkPoweredComponent(List<InkPoweredStatusEffectInstance> oEffects)
				&& oEffects.equals(effects);
	}
	
}
