package de.dafuqs.spectrum.networking;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.networking.packet.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.packet.*;
import net.minecraft.util.*;

public class SpectrumC2SPackets {
	
	public static final Identifier GUIDEBOOK_HINT_BOUGHT = SpectrumCommon.locate("guidebook_tip_used");
	public static final Identifier BIND_ENDER_SPLICE_TO_PLAYER = SpectrumCommon.locate("bind_ender_splice_to_player");
	public static final Identifier INK_COLOR_SELECTED = SpectrumCommon.locate("ink_color_select");
	public static final Identifier WORKSTAFF_TOGGLE_SELECTED = SpectrumCommon.locate("workstaff_toggle_select");
	public static final Identifier CONFIRMATION_BUTTON_PRESSED = SpectrumCommon.locate("confirmation_button_pressed");
	public static final Identifier SET_SHADOW_SLOT = SpectrumCommon.locate("set_shadow_slot");

	public static <T extends CustomPayload> CustomPayload.Id<T> makeId(String id) {
		return new CustomPayload.Id<>(SpectrumCommon.locate(id));
	}

	public static void register() {
		PayloadTypeRegistry.playC2S().register(ChangeCompactingChestSettingsPacket.ID, ChangeCompactingChestSettingsPacket.CODEC);
		PayloadTypeRegistry.playC2S().register(ParticleSpawnerConfigurationC2SPacket.ID, ParticleSpawnerConfigurationC2SPacket.CODEC);
		PayloadTypeRegistry.playC2S().register(AddLoreBedrockAnvilPayload.ID, AddLoreBedrockAnvilPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(RenameItemInBedrockAnvilPayload.ID, RenameItemInBedrockAnvilPayload.CODEC);
	}

}
