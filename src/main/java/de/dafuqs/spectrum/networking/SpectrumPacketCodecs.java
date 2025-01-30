package de.dafuqs.spectrum.networking;

import io.netty.buffer.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.math.*;

import java.util.*;

public class SpectrumPacketCodecs {
	
	public static final PacketCodec<ByteBuf, Vec3d> VEC_3D = new PacketCodec<>() {
		public Vec3d decode(ByteBuf byteBuf) {
			return new Vec3d(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
		}
		
		public void encode(ByteBuf byteBuf, Vec3d blockPos) {
			byteBuf.writeDouble(blockPos.getX());
			byteBuf.writeDouble(blockPos.getY());
			byteBuf.writeDouble(blockPos.getZ());
		}
	};
	
	public static final PacketCodec<ByteBuf, List<BlockPos>> BLOCKPOS_LIST = BlockPos.PACKET_CODEC.collect(PacketCodecs.toCollection(ObjectArrayList::new));
	
}
