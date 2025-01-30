package de.dafuqs.spectrum.networking;

import com.mojang.datafixers.util.*;
import io.netty.buffer.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.math.*;

import java.util.*;
import java.util.function.*;

public class SpectrumPacketCodecs {
	
	public static final PacketCodec<ByteBuf, Vec3i> VEC_3I = new PacketCodec<>() {
		public Vec3i decode(ByteBuf byteBuf) {
			return new Vec3i(byteBuf.readInt(), byteBuf.readInt(), byteBuf.readInt());
		}
		
		public void encode(ByteBuf byteBuf, Vec3i vec3i) {
			byteBuf.writeInt(vec3i.getX());
			byteBuf.writeInt(vec3i.getY());
			byteBuf.writeInt(vec3i.getZ());
		}
	};
	
	public static final PacketCodec<ByteBuf, Vec3d> VEC_3D = new PacketCodec<>() {
		public Vec3d decode(ByteBuf byteBuf) {
			return new Vec3d(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
		}
		
		public void encode(ByteBuf byteBuf, Vec3d vec3d) {
			byteBuf.writeDouble(vec3d.getX());
			byteBuf.writeDouble(vec3d.getY());
			byteBuf.writeDouble(vec3d.getZ());
		}
	};
	
	public static final PacketCodec<ByteBuf, List<BlockPos>> BLOCKPOS_LIST = BlockPos.PACKET_CODEC.collect(PacketCodecs.toCollection(ObjectArrayList::new));
	
	
	public static final PacketCodec<ByteBuf, List<Direction>> DIRECTION_LIST = Direction.PACKET_CODEC.collect(PacketCodecs.toCollection(ObjectArrayList::new));
	
	// Since PacketCodec.tuple() only supports a max of 6 entries, we have some custom ones with more params here
	
	// 7 properties
	public static <B, C, T1, T2, T3, T4, T5, T6, T7> PacketCodec<B, C> tuple(
			final PacketCodec<? super B, T1> codec1, final Function<C, T1> from1,
			final PacketCodec<? super B, T2> codec2, final Function<C, T2> from2,
			final PacketCodec<? super B, T3> codec3, final Function<C, T3> from3,
			final PacketCodec<? super B, T4> codec4, final Function<C, T4> from4,
			final PacketCodec<? super B, T5> codec5, final Function<C, T5> from5,
			final PacketCodec<? super B, T6> codec6, final Function<C, T6> from6,
			final PacketCodec<? super B, T7> codec7, final Function<C, T7> from7,
			final Function7<T1, T2, T3, T4, T5, T6, T7, C> to) {
		
		return new PacketCodec<>() {
			public C decode(B object) {
				T1 object2 = codec1.decode(object);
				T2 object3 = codec2.decode(object);
				T3 object4 = codec3.decode(object);
				T4 object5 = codec4.decode(object);
				T5 object6 = codec5.decode(object);
				T6 object7 = codec6.decode(object);
				T7 object8 = codec7.decode(object);
				return to.apply(object2, object3, object4, object5, object6, object7, object8);
			}
			
			public void encode(B object, C object2) {
				codec1.encode(object, from1.apply(object2));
				codec2.encode(object, from2.apply(object2));
				codec3.encode(object, from3.apply(object2));
				codec4.encode(object, from4.apply(object2));
				codec5.encode(object, from5.apply(object2));
				codec6.encode(object, from6.apply(object2));
				codec7.encode(object, from7.apply(object2));
			}
		};
	}
	
	// 14 properties
	public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> PacketCodec<B, C> tuple(
			final PacketCodec<? super B, T1> codec1, final Function<C, T1> from1,
			final PacketCodec<? super B, T2> codec2, final Function<C, T2> from2,
			final PacketCodec<? super B, T3> codec3, final Function<C, T3> from3,
			final PacketCodec<? super B, T4> codec4, final Function<C, T4> from4,
			final PacketCodec<? super B, T5> codec5, final Function<C, T5> from5,
			final PacketCodec<? super B, T6> codec6, final Function<C, T6> from6,
			final PacketCodec<? super B, T7> codec7, final Function<C, T7> from7,
			final PacketCodec<? super B, T8> codec8, final Function<C, T8> from8,
			final PacketCodec<? super B, T9> codec9, final Function<C, T9> from9,
			final PacketCodec<? super B, T10> codec10, final Function<C, T10> from10,
			final PacketCodec<? super B, T11> codec11, final Function<C, T11> from11,
			final PacketCodec<? super B, T12> codec12, final Function<C, T12> from12,
			final PacketCodec<? super B, T13> codec13, final Function<C, T13> from13,
			final PacketCodec<? super B, T14> codec14, final Function<C, T14> from14,
			final Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, C> to) {
		
		return new PacketCodec<>() {
			public C decode(B object) {
				T1 object2 = codec1.decode(object);
				T2 object3 = codec2.decode(object);
				T3 object4 = codec3.decode(object);
				T4 object5 = codec4.decode(object);
				T5 object6 = codec5.decode(object);
				T6 object7 = codec6.decode(object);
				T7 object8 = codec7.decode(object);
				T8 object9 = codec8.decode(object);
				T9 object10 = codec9.decode(object);
				T10 object11 = codec10.decode(object);
				T11 object12 = codec11.decode(object);
				T12 object13 = codec12.decode(object);
				T13 object14 = codec13.decode(object);
				T14 object15 = codec14.decode(object);
				return to.apply(object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15);
			}
			
			public void encode(B object, C object2) {
				codec1.encode(object, from1.apply(object2));
				codec2.encode(object, from2.apply(object2));
				codec3.encode(object, from3.apply(object2));
				codec4.encode(object, from4.apply(object2));
				codec5.encode(object, from5.apply(object2));
				codec6.encode(object, from6.apply(object2));
				codec7.encode(object, from7.apply(object2));
				codec8.encode(object, from8.apply(object2));
				codec9.encode(object, from9.apply(object2));
				codec10.encode(object, from10.apply(object2));
				codec11.encode(object, from11.apply(object2));
				codec12.encode(object, from12.apply(object2));
				codec13.encode(object, from13.apply(object2));
				codec14.encode(object, from14.apply(object2));
			}
		};
	}
	
}
