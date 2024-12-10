package de.dafuqs.spectrum.helpers;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.function.Consumer;

public class CodecHelper {

    public static <E extends Enum<?>> PacketCodec<ByteBuf, E> ofPacketEnum(Class<E> clazz) {
        return PacketCodecs.VAR_INT.xmap(
                ordinal -> clazz.getEnumConstants()[ordinal],
                enumInst -> enumInst.ordinal()
        );
    }

    public static <T> void fromNbt(Codec<T> codec, NbtElement nbt, Consumer<? super T> ifValid) {
        codec.decode(NbtOps.INSTANCE, nbt).result().map(Pair::getFirst).ifPresent(ifValid);
    }

    public static <T> void toNbt(Codec<T> codec, T value, Consumer<? super NbtElement> ifValid) {
        codec.encodeStart(NbtOps.INSTANCE, value).result().ifPresent(ifValid);
    }

}
