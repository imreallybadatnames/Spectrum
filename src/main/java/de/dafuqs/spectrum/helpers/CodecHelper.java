package de.dafuqs.spectrum.helpers;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public class CodecHelper {

    public static <E extends Enum<?>> PacketCodec<ByteBuf, E> ofPacketEnum(Class<E> clazz) {
        return PacketCodecs.VAR_INT.xmap(
                ordinal -> clazz.getEnumConstants()[ordinal],
                enumInst -> enumInst.ordinal()
        );
    }

}
