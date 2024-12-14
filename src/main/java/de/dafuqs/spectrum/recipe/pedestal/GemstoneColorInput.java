package de.dafuqs.spectrum.recipe.pedestal;

import de.dafuqs.spectrum.api.item.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;

import java.util.*;

public record GemstoneColorInput(GemstoneColor gemstoneColor, int amount) {

	public static final StructEndec<GemstoneColorInput> ENDEC = StructEndecBuilder.of(
		BuiltinGemstoneColor.STRING_ENDEC.flatFieldOf(
			colorInput -> BuiltinGemstoneColor.of(colorInput.gemstoneColor.getDyeColor())
		),
		Endec.INT.optionalFieldOf("amount", GemstoneColorInput::amount, 0),
		GemstoneColorInput::new
	);

	public static List<GemstoneColorInput> convertToList(Map<GemstoneColor, Integer> input) {
		ArrayList<GemstoneColorInput> list = new ArrayList<>();
		for (Map.Entry<GemstoneColor, Integer> entry : input.entrySet()) {
			list.add(new GemstoneColorInput(entry.getKey(), entry.getValue()));
		}
		return list;
	}
}
