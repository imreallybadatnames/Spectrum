package de.dafuqs.spectrum.recipe;

import com.mojang.serialization.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
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
		T instance = factory.get();
		this.codec = MapCodec.unit(instance);
		this.packetCodec = PacketCodec.unit(instance);
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
