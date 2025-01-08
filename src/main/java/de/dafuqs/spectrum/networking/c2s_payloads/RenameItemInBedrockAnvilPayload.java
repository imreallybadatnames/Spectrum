package de.dafuqs.spectrum.networking.c2s_payloads;

import de.dafuqs.spectrum.inventories.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import org.jetbrains.annotations.*;

public record RenameItemInBedrockAnvilPayload(String name) implements CustomPayload {
	
	public static final Id<RenameItemInBedrockAnvilPayload> ID = SpectrumC2SPackets.makeId("rename_item_in_bedrock_anvil");
	public static final PacketCodec<PacketByteBuf, RenameItemInBedrockAnvilPayload> CODEC = PacketCodec.tuple(PacketCodecs.STRING, RenameItemInBedrockAnvilPayload::name, RenameItemInBedrockAnvilPayload::new);
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
	public static ServerPlayNetworking.@NotNull PlayPayloadHandler<RenameItemInBedrockAnvilPayload> getPayloadHandler() {
		return (payload, context) -> {
			if (context.player().currentScreenHandler instanceof BedrockAnvilScreenHandler bedrockAnvilScreenHandler) {
				String string = SharedConstants.stripInvalidChars(payload.name);
				if (string.length() <= 50) {
					bedrockAnvilScreenHandler.setNewItemName(string);
				}
			}
		};
	}
	
}
