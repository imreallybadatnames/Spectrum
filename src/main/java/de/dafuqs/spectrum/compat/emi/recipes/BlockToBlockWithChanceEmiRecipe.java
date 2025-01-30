package de.dafuqs.spectrum.compat.emi.recipes;

import de.dafuqs.spectrum.compat.emi.*;
import dev.emi.emi.api.recipe.*;
import dev.emi.emi.api.render.*;
import dev.emi.emi.api.stack.*;
import dev.emi.emi.api.widget.*;
import net.minecraft.util.*;

import java.util.*;

public class BlockToBlockWithChanceEmiRecipe extends SpectrumEmiRecipe {
	
	public BlockToBlockWithChanceEmiRecipe(EmiRecipeCategory category, Identifier id, EmiIngredient in, EmiStack out, Identifier unlock) {
		super(category, unlock, id, 78, 26);
		this.inputs = List.of(in);
		this.outputs = List.of(out);
	}
	
	@Override
	public void addUnlockedWidgets(WidgetHolder widgets) {
		widgets.addTexture(EmiTexture.EMPTY_ARROW, 23, 4);
		widgets.addSlot(inputs.getFirst(), 0, 4);
		widgets.addSlot(outputs.getFirst(), 52, 0).large(true).recipeContext(this);
	}
}
