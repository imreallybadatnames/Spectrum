package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.sound.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import org.jetbrains.annotations.*;

public record PlayTakeOffBeltSoundInstancePayload() implements CustomPayload {
	
	public static final Id<PlayTakeOffBeltSoundInstancePayload> ID = SpectrumC2SPackets.makeId("play_take_off_belt_sound_instance");
	public static final PacketCodec<PacketByteBuf, PlayTakeOffBeltSoundInstancePayload> CODEC = PacketCodec.tuple(PlayTakeOffBeltSoundInstancePayload::new);
	
	public static void sendPlayTakeOffBeltSoundInstance(ServerPlayerEntity playerEntity) {
		PacketByteBuf buf = PacketByteBufs.create();
		ServerPlayNetworking.send(playerEntity, new PlayTakeOffBeltSoundInstancePayload());
	}
	
	@Environment(EnvType.CLIENT)
	public static ClientPlayNetworking.@NotNull PlayPayloadHandler<PlayTakeOffBeltSoundInstancePayload> getPayloadHandler() {
		return (playTakeOffBeltSoundInstancePayload, context) -> client.execute(TakeOffBeltSoundInstance::startSoundInstance);
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
