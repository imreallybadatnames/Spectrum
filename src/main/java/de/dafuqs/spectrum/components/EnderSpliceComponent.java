package de.dafuqs.spectrum.components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.helpers.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public record EnderSpliceComponent(Optional<Vec3d> pos, Optional<RegistryKey<World>> dimension, Optional<String> targetName, Optional<UUID> targetUUID) {
	
	public static final EnderSpliceComponent DEFAULT = new EnderSpliceComponent(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
	
	public static final Codec<EnderSpliceComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			Vec3d.CODEC.optionalFieldOf("pos").forGetter(c -> c.pos),
			RegistryKey.createCodec(RegistryKeys.WORLD).optionalFieldOf("dimension").forGetter(c -> c.dimension),
			Codec.STRING.optionalFieldOf("target_name").forGetter(c -> c.targetName),
			Uuids.CODEC.optionalFieldOf("target_uuid").forGetter(c -> c.targetUUID)
	).apply(i, EnderSpliceComponent::new));
	
	public static final PacketCodec<ByteBuf, EnderSpliceComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.optional(PacketCodecHelper.VEC3D), EnderSpliceComponent::pos,
			PacketCodecs.optional(RegistryKey.createPacketCodec(RegistryKeys.WORLD)), EnderSpliceComponent::dimension,
			PacketCodecs.optional(PacketCodecs.STRING), EnderSpliceComponent::targetName,
			PacketCodecs.optional(Uuids.PACKET_CODEC), EnderSpliceComponent::targetUUID,
			EnderSpliceComponent::new
	);
	
	public EnderSpliceComponent(Vec3d pos, RegistryKey<World> dimension) {
		this(Optional.of(pos), Optional.of(dimension), Optional.empty(), Optional.empty());
	}
	
	public EnderSpliceComponent(String targetName, UUID targetUUID) {
		this(Optional.empty(), Optional.empty(), Optional.of(targetName), Optional.of(targetUUID));
	}
	
}
