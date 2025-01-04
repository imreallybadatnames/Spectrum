package de.dafuqs.spectrum.recipe.titration_barrel;

import com.google.gson.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

import java.util.*;

public record FermentationData(
	float fermentationSpeedMod,
	float angelsSharePercentPerMcDay,
	List<FermentationStatusEffectEntry> statusEffectEntries
) {
	
	private static final String FERMENTATION_SPEED_MOD_STRING = "fermentation_speed_mod";
	private static final String ANGELS_SHARE_STRING = "angels_share_percent_per_mc_day";
	private static final String EFFECTS_STRING = "effects";
	
	public static final StructEndec<FermentationData> ENDEC = StructEndecBuilder.of(
		Endec.FLOAT.fieldOf(FERMENTATION_SPEED_MOD_STRING, FermentationData::fermentationSpeedMod),
		Endec.FLOAT.fieldOf(ANGELS_SHARE_STRING, FermentationData::angelsSharePercentPerMcDay),
		FermentationStatusEffectEntry.FERMENTATION_ENTRY_ENDEC.listOf().fieldOf(EFFECTS_STRING, FermentationData::statusEffectEntries),
		FermentationData::new
	);
	
	// TODO - Once the loot functions that use this, E.G. FermentRandomlyLootFunction
	// TODO - then all of these, including the nested data structures, should fall away
	public static FermentationData fromJson(JsonObject jsonObject) {
		float fermentationSpeedMod = JsonHelper.getFloat(jsonObject, FERMENTATION_SPEED_MOD_STRING, 1.0F);
		float angelsSharePerMcDay = JsonHelper.getFloat(jsonObject, ANGELS_SHARE_STRING, 0.01F);
		List<FermentationStatusEffectEntry> statusEffectEntries = new ArrayList<>();
		if (JsonHelper.hasArray(jsonObject, EFFECTS_STRING)) {
			JsonArray effectsArray = JsonHelper.getArray(jsonObject, EFFECTS_STRING);
			for (int i = 0; i < effectsArray.size(); i++) {
				JsonObject object = effectsArray.get(i).getAsJsonObject();
				statusEffectEntries.add(FermentationStatusEffectEntry.fromJson(object));
			}
		}
		return new FermentationData(fermentationSpeedMod, angelsSharePerMcDay, statusEffectEntries);
	}
	
	public void write(PacketByteBuf packetByteBuf) {
		packetByteBuf.writeFloat(this.fermentationSpeedMod);
		packetByteBuf.writeFloat(this.angelsSharePercentPerMcDay);
		packetByteBuf.writeInt(this.statusEffectEntries.size());
		for (FermentationStatusEffectEntry fermentationStatusEffectEntry : this.statusEffectEntries) {
			fermentationStatusEffectEntry.write(packetByteBuf);
		}
	}
	
	public static FermentationData read(PacketByteBuf packetByteBuf) {
		float fermentationSpeedMod = packetByteBuf.readFloat();
		float angelsSharePerMcDay = packetByteBuf.readFloat();
		int statusEffectEntryCount = packetByteBuf.readInt();
		List<FermentationStatusEffectEntry> statusEffectEntries = new ArrayList<>(statusEffectEntryCount);
		for (int i = 0; i < statusEffectEntryCount; i++) {
			statusEffectEntries.add(FermentationStatusEffectEntry.read(packetByteBuf));
		}
		return new FermentationData(fermentationSpeedMod, angelsSharePerMcDay, statusEffectEntries);
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		
		json.addProperty(FERMENTATION_SPEED_MOD_STRING, this.fermentationSpeedMod);
		json.addProperty(ANGELS_SHARE_STRING, this.angelsSharePercentPerMcDay);
		JsonArray effects = new JsonArray();
		for (FermentationStatusEffectEntry entry : this.statusEffectEntries) {
			effects.add(entry.toJson());
		}
		json.add(EFFECTS_STRING, effects);
		
		return json;
	}
	
}
