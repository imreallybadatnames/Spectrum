package de.dafuqs.spectrum.recipe.titration_barrel;

import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;

import java.util.*;

public record FermentationData(
	float fermentationSpeedMod,
	float angelsSharePercentPerMcDay,
	List<FermentationStatusEffectEntry> statusEffectEntries
) {
	
	public static final StructEndec<FermentationData> ENDEC = StructEndecBuilder.of(
		Endec.FLOAT.fieldOf("fermentation_speed_mod", FermentationData::fermentationSpeedMod),
		Endec.FLOAT.fieldOf("angels_share_percent_per_mc_day", FermentationData::angelsSharePercentPerMcDay),
		FermentationStatusEffectEntry.FERMENTATION_ENTRY_ENDEC.listOf().fieldOf("effects", FermentationData::statusEffectEntries),
		FermentationData::new
	);
	
}
