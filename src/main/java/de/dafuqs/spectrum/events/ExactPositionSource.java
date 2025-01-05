package de.dafuqs.spectrum.events;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.event.*;

import java.util.*;

public class ExactPositionSource implements PositionSource {
	
	public static final MapCodec<ExactPositionSource> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			Vec3d.CODEC.fieldOf("pos").forGetter((blockPositionSource) -> blockPositionSource.pos)
	).apply(instance, ExactPositionSource::new));
	
	public static final PacketCodec<ByteBuf, Vec3d> VEC_PACKET_CODEC = new PacketCodec<>() {
		public Vec3d decode(ByteBuf byteBuf) {
			return new Vec3d(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
		}
		
		public void encode(ByteBuf byteBuf, Vec3d blockPos) {
			byteBuf.writeDouble(blockPos.getX());
			byteBuf.writeDouble(blockPos.getY());
			byteBuf.writeDouble(blockPos.getZ());
		}
	};
	
	public static final PacketCodec<ByteBuf, ExactPositionSource> PACKET_CODEC = PacketCodec.tuple(VEC_PACKET_CODEC, (source) -> source.pos, ExactPositionSource::new);
	
	final Vec3d pos;
	
	public ExactPositionSource(Vec3d pos) {
		this.pos = pos;
	}
	
	@Override
	public Optional<Vec3d> getPos(World world) {
		return Optional.of(this.pos);
	}
	
	@Override
	public PositionSourceType<?> getType() {
		return SpectrumPositionSources.EXACT;
	}
	
	public static class Type implements PositionSourceType<ExactPositionSource> {
		public Type() {
		}
		
		public MapCodec<ExactPositionSource> getCodec() {
			return ExactPositionSource.CODEC;
		}
		
		public PacketCodec<ByteBuf, ExactPositionSource> getPacketCodec() {
			return ExactPositionSource.PACKET_CODEC;
		}
	}
	
}
