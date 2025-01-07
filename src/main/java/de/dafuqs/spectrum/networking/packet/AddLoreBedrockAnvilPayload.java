package de.dafuqs.spectrum.networking.packet;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.inventories.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;

public record AddLoreBedrockAnvilPayload(String lore) implements CustomPayload {
	
	public static final Id<AddLoreBedrockAnvilPayload> ID = SpectrumC2SPackets.makeId("add_lore_to_item_in_bedrock_anvil");
	public static final PacketCodec<PacketByteBuf, AddLoreBedrockAnvilPayload> CODEC = PacketCodec.tuple(PacketCodecs.STRING, AddLoreBedrockAnvilPayload::lore, AddLoreBedrockAnvilPayload::new);
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
	public static ServerPlayNetworking.PlayPayloadHandler<AddLoreBedrockAnvilPayload> getPayloadHandler() {
		return (payload, context) -> {
			if (context.player().currentScreenHandler instanceof BedrockAnvilScreenHandler bedrockAnvilScreenHandler) {
				if (!bedrockAnvilScreenHandler.canUse(context.player())) {
					SpectrumCommon.LOGGER.debug("Player {} interacted with invalid menu {} while setting lore", context.player(), bedrockAnvilScreenHandler);
				}
				bedrockAnvilScreenHandler.setNewItemLore(payload.lore());
			}
		};
	}
	
}
