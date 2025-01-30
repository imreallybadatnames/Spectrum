package de.dafuqs.spectrum.helpers;

import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.mixin.accessors.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.util.*;

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
	
	public static <T, R extends Registry<T>> Codec<T> spectrumRegistryValue(R registry) {
		return Codec.STRING
				.xmap(SpectrumCommon::ofSpectrum, Identifier::toString)
				.comapFlatMap(
						id -> registry.getEntry(id).map(DataResult::success).orElse(DataResult.error(
								() -> "Unknown registry key in " + registry.getKey() + ": " + id
						)),
						entry -> entry.registryKey().getValue()
				).flatComapMap(
						RegistryEntry.Reference::value,
						(value) -> {
							var entry = registry.getEntry(value);
							if (entry instanceof RegistryEntry.Reference<T> reference) {
								return DataResult.success(reference);
							} else {
								return DataResult.error(() -> "Unregistered holder in " + registry.getKey() + ": " + entry);
							}
						}
				);
	}
	
	public static <T> Codec<List<T>> singleOrList(Codec<T> codec) {
		return Codec.withAlternative(codec.listOf(), codec, List::of);
	}
	
	public static <T, D> Optional<T> from(DynamicOps<D> ops, Codec<T> codec, D elem) {
		if (elem == null) return Optional.empty();
		return codec.decode(ops, elem).result().map(Pair::getFirst);
	}
	
	public static <T> Optional<T> fromNbt(Codec<T> codec, NbtElement nbt) {
		return from(NbtOps.INSTANCE, codec, nbt);
	}
	
	public static <T> T fromNbt(Codec<T> codec, NbtElement nbt, T defaultValue) {
		return fromNbt(codec, nbt).orElse(defaultValue);
	}
	
	public static <T> Optional<T> fromJson(Codec<T> codec, JsonElement json) {
		return from(JsonOps.INSTANCE, codec, json);
	}
	
	public static <T> T fromJson(Codec<T> codec, JsonElement json, T defaultValue) {
		return fromJson(codec, json).orElse(defaultValue);
	}
	
	public static <T> void toNbt(Codec<T> codec, T value, Consumer<? super NbtElement> ifValid) {
		codec.encodeStart(NbtOps.INSTANCE, value).result().ifPresent(ifValid);
	}
	
	public static <T> void writeNbt(NbtCompound nbt, String key, Codec<T> codec, T value) {
		toNbt(codec, value, elem -> nbt.put(key, elem));
	}
	
}
