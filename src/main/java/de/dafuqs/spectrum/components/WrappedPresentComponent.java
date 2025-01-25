package de.dafuqs.spectrum.components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.blocks.present.*;
import de.dafuqs.spectrum.helpers.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.*;
import net.minecraft.util.dynamic.*;

import java.util.*;

public record WrappedPresentComponent(boolean wrapped, PresentBlock.WrappingPaper variant, Map<DyeColor, Integer> colors) {
	
	public static final WrappedPresentComponent DEFAULT = new WrappedPresentComponent(false, PresentBlock.WrappingPaper.RED, Map.of());
	
	public static final Codec<WrappedPresentComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.BOOL.optionalFieldOf("wrapped", false).forGetter(WrappedPresentComponent::wrapped),
			StringIdentifiable.createCodec(PresentBlock.WrappingPaper::values).fieldOf("variant").forGetter(WrappedPresentComponent::variant),
			Codec.unboundedMap(DyeColor.CODEC, Codecs.POSITIVE_INT).fieldOf("colors").forGetter(WrappedPresentComponent::colors)
	).apply(instance, WrappedPresentComponent::new));
	
	public static final PacketCodec<RegistryByteBuf, WrappedPresentComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.BOOL, WrappedPresentComponent::wrapped,
			PacketCodecHelper.enumOf(PresentBlock.WrappingPaper.class), WrappedPresentComponent::variant,
			PacketCodecs.map(HashMap::new, DyeColor.PACKET_CODEC, PacketCodecs.VAR_INT), WrappedPresentComponent::colors,
			WrappedPresentComponent::new
	);
	
	@Override
	public boolean equals(Object o) {
		return this == o || o instanceof WrappedPresentComponent(boolean oWrapped, PresentBlock.WrappingPaper oVariant, Map<DyeColor, Integer> oColors)
				&& oWrapped == wrapped
				&& oVariant == variant
				&& oColors.equals(colors);
	}
	
}
