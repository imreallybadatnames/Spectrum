package de.dafuqs.spectrum.api.energy;

import de.dafuqs.spectrum.api.energy.color.*;
import net.minecraft.nbt.*;

import java.util.*;

public record InkCost(InkColor color, long cost) {
	
	public void writeNbt(NbtCompound nbt) {
		nbt.putString("InkColor", color.getID().toString());
		nbt.putLong("InkCost", cost);
	}
	
	public static InkCost fromNbt(NbtCompound nbt) {
		Optional<InkColor> inkColor = InkColor.ofIdString(nbt.getString("InkColor"));
		long inkCost = nbt.getLong("InkCost");
		return new InkCost(inkColor.orElse(InkColors.CYAN), inkCost);
	}
	
}