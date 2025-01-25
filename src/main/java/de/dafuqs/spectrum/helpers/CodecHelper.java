package de.dafuqs.spectrum.helpers;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import de.dafuqs.spectrum.mixin.accessors.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class CodecHelper {
	
	public static MapCodec<RegistryWrapper.WrapperLookup> LOOKUP = new MapCodec<>() {
		@Override
		public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
			return Stream.empty();
		}
		
		@Override
		public <T> DataResult<RegistryWrapper.WrapperLookup> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
			if (dynamicOps instanceof RegistryOps<T> registryOps) {
				var infoGetter = ((RegistryOpsAccessor) registryOps).getRegistryInfoGetter();
				var lookup = ((CachedRegistryInfoGetterAccessor) infoGetter).getRegistriesLookup();
				return DataResult.success(lookup);
			}
			return DataResult.error(() -> "Error: The LOOKUP codec requires RegistryOps.");
		}
		
		@Override
		public <T> RecordBuilder<T> encode(RegistryWrapper.WrapperLookup wrapperLookup, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
			return recordBuilder;
		}
	};
	
	public static <T> Codec<List<T>> singleOrList(Codec<T> codec) {
		return Codec.withAlternative(codec.listOf(), codec, List::of);
	}
	
	public static <T> Optional<T> fromNbt(Codec<T> codec, NbtElement nbt) {
		if (nbt == null) return Optional.empty();
		return codec.decode(NbtOps.INSTANCE, nbt).result().map(Pair::getFirst);
	}
	
	public static <T> T fromNbt(Codec<T> codec, NbtElement nbt, T defaultValue) {
		return fromNbt(codec, nbt).orElse(defaultValue);
	}
	
	public static <T> void toNbt(Codec<T> codec, T value, Consumer<? super NbtElement> ifValid) {
		codec.encodeStart(NbtOps.INSTANCE, value).result().ifPresent(ifValid);
	}
	
	public static <T> void writeNbt(NbtCompound nbt, String key, Codec<T> codec, T value) {
		toNbt(codec, value, elem -> nbt.put(key, elem));
	}
	
}
