package de.dafuqs.spectrum.helpers;

import com.mojang.datafixers.util.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.encoding.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

import java.util.function.*;

public class PacketCodecHelper {
	
	public static final PacketCodec<ByteBuf, Vec3i> VEC3I = PacketCodec.tuple(PacketCodecs.VAR_INT, Vec3i::getX, PacketCodecs.VAR_INT, Vec3i::getY, PacketCodecs.VAR_INT, Vec3i::getZ, Vec3i::new);
	public static final PacketCodec<ByteBuf, Vec3d> VEC3D = PacketCodec.tuple(PacketCodecs.DOUBLE, Vec3d::getX, PacketCodecs.DOUBLE, Vec3d::getY, PacketCodecs.DOUBLE, Vec3d::getZ, Vec3d::new);
	
	/**
	 * Use this if you can't use PacketCodecs.registryValue, such as when it isn't a RegistryByteBuf,
	 * since it writes a whole string instead of an int.
	 */
	public static <T> PacketCodec<ByteBuf, T> registryValueByName(Registry<T> registry) {
		return Identifier.PACKET_CODEC.xmap(registry::get, registry::getId);
	}
	
	public static <B extends ByteBuf, C> PacketCodec<B, C> nullableOf(PacketCodec<B, C> codec) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				return buf.readBoolean() ? codec.decode(buf) : null;
			}
			
			@Override
			public void encode(B buf, C value) {
				buf.writeBoolean(value != null);
				if (value != null) codec.encode(buf, value);
			}
		};
	}
	
	public static <E extends Enum<E>> PacketCodec<ByteBuf, E> enumOf(Class<E> clazz) {
		var values = clazz.getEnumConstants();
		return new PacketCodec<>() {
			public E decode(ByteBuf byteBuf) {
				return values[VarInts.read(byteBuf)];
			}
			
			public void encode(ByteBuf byteBuf, E value) {
				VarInts.write(byteBuf, value.ordinal());
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4, T5, T6, T7, T8> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			PacketCodec<? super B, T5> codec5, Function<C, T5> from5,
			PacketCodec<? super B, T6> codec6, Function<C, T6> from6,
			PacketCodec<? super B, T7> codec7, Function<C, T7> from7,
			PacketCodec<? super B, T8> codec8, Function<C, T8> from8,
			Function8<T1, T2, T3, T4, T5, T6, T7, T8, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var arg1 = codec1.decode(buf);
				var arg2 = codec2.decode(buf);
				var arg3 = codec3.decode(buf);
				var arg4 = codec4.decode(buf);
				var arg5 = codec5.decode(buf);
				var arg6 = codec6.decode(buf);
				var arg7 = codec7.decode(buf);
				var arg8 = codec8.decode(buf);
				return to.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
				codec5.encode(buf, from5.apply(value));
				codec6.encode(buf, from6.apply(value));
				codec7.encode(buf, from7.apply(value));
				codec8.encode(buf, from8.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			PacketCodec<? super B, T5> codec5, Function<C, T5> from5,
			PacketCodec<? super B, T6> codec6, Function<C, T6> from6,
			PacketCodec<? super B, T7> codec7, Function<C, T7> from7,
			PacketCodec<? super B, T8> codec8, Function<C, T8> from8,
			PacketCodec<? super B, T9> codec9, Function<C, T9> from9,
			PacketCodec<? super B, T10> codec10, Function<C, T10> from10,
			Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var arg1 = codec1.decode(buf);
				var arg2 = codec2.decode(buf);
				var arg3 = codec3.decode(buf);
				var arg4 = codec4.decode(buf);
				var arg5 = codec5.decode(buf);
				var arg6 = codec6.decode(buf);
				var arg7 = codec7.decode(buf);
				var arg8 = codec8.decode(buf);
				var arg9 = codec9.decode(buf);
				var arg10 = codec10.decode(buf);
				return to.apply(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
				codec5.encode(buf, from5.apply(value));
				codec6.encode(buf, from6.apply(value));
				codec7.encode(buf, from7.apply(value));
				codec8.encode(buf, from8.apply(value));
				codec9.encode(buf, from9.apply(value));
				codec10.encode(buf, from10.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			PacketCodec<? super B, T5> codec5, Function<C, T5> from5,
			PacketCodec<? super B, T6> codec6, Function<C, T6> from6,
			PacketCodec<? super B, T7> codec7, Function<C, T7> from7,
			PacketCodec<? super B, T8> codec8, Function<C, T8> from8,
			PacketCodec<? super B, T9> codec9, Function<C, T9> from9,
			PacketCodec<? super B, T10> codec10, Function<C, T10> from10,
			PacketCodec<? super B, T11> codec11, Function<C, T11> from11,
			PacketCodec<? super B, T12> codec12, Function<C, T12> from12,
			PacketCodec<? super B, T13> codec13, Function<C, T13> from13,
			PacketCodec<? super B, T14> codec14, Function<C, T14> from14,
			Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				var a3 = codec3.decode(buf);
				var a4 = codec4.decode(buf);
				var a5 = codec5.decode(buf);
				var a6 = codec6.decode(buf);
				var a7 = codec7.decode(buf);
				var a8 = codec8.decode(buf);
				var a9 = codec9.decode(buf);
				var a10 = codec10.decode(buf);
				var a11 = codec11.decode(buf);
				var a12 = codec12.decode(buf);
				var a13 = codec13.decode(buf);
				var a14 = codec14.decode(buf);
				return to.apply(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
				codec5.encode(buf, from5.apply(value));
				codec6.encode(buf, from6.apply(value));
				codec7.encode(buf, from7.apply(value));
				codec8.encode(buf, from8.apply(value));
				codec9.encode(buf, from9.apply(value));
				codec10.encode(buf, from10.apply(value));
				codec11.encode(buf, from11.apply(value));
				codec12.encode(buf, from12.apply(value));
				codec13.encode(buf, from13.apply(value));
				codec14.encode(buf, from14.apply(value));
			}
		};
	}
}
