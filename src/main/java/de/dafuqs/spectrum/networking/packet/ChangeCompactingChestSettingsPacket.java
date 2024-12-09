package de.dafuqs.spectrum.networking.packet;

import de.dafuqs.spectrum.helpers.CodecHelper;
import de.dafuqs.spectrum.inventories.AutoCompactingInventory;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record ChangeCompactingChestSettingsPacket(AutoCompactingInventory.AutoCraftingMode mode) implements CustomPayload {

    public static final CustomPayload.Id<ChangeCompactingChestSettingsPacket> ID = SpectrumC2SPackets.makeId("change_compacting_chest_settings");
    public static final PacketCodec<PacketByteBuf, ChangeCompactingChestSettingsPacket> CODEC = PacketCodec.tuple(
            CodecHelper.ofPacketEnum(AutoCompactingInventory.AutoCraftingMode.class),
            ChangeCompactingChestSettingsPacket::mode,
            ChangeCompactingChestSettingsPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
