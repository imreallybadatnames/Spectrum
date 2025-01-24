package de.dafuqs.spectrum.helpers;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import net.minecraft.nbt.*;

import java.util.*;
import java.util.function.*;

public class CodecHelper {
	
	public static <T> Codec<List<T>> singleOrList(Codec<T> codec) {
		return Codec.withAlternative(codec.listOf(), codec, List::of);
	}
	
	public static <T> void fromNbt(Codec<T> codec, NbtElement nbt, Consumer<? super T> ifValid) {
		if (nbt != null)
			codec.decode(NbtOps.INSTANCE, nbt).result().map(Pair::getFirst).ifPresent(ifValid);
	}
	
	public static <T> void toNbt(Codec<T> codec, T value, Consumer<? super NbtElement> ifValid) {
		codec.encodeStart(NbtOps.INSTANCE, value).result().ifPresent(ifValid);
	}
	
	public static <T> void writeNbt(NbtCompound nbt, String key, Codec<T> codec, T value) {
		toNbt(codec, value, elem -> nbt.put(key, elem));
	}
	
}
