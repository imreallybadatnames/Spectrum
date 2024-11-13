package de.dafuqs.spectrum.recipe;

import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.*;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;

import java.util.function.*;

/**
 * A copy of the old SpecialRecipeSerializer, which simply ignores any meaningful recipe serialization.
 * <p>Recipes that use this serializer do not transport any data over the network, besides their ID.
 */
public class EmptyRecipeSerializer<T extends Recipe<?>> implements RecipeSerializer<T> {
	private final MapCodec<T> codec;
	private final PacketCodec<RegistryByteBuf, T> packetCodec;

	public EmptyRecipeSerializer(Supplier<T> factory) {
		this.codec = MapCodec.of(Encoder.empty(), Decoder.unit(factory));
		this.packetCodec = PacketCodec.of((val, buf) -> {},buf -> factory.get());
	}

	@Override
	public MapCodec<T> codec() {
		return codec;
	}

	@Override
	public PacketCodec<RegistryByteBuf, T> packetCodec() {
		return packetCodec;
	}

}
