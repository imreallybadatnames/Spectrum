package de.dafuqs.spectrum.api.block;

import de.dafuqs.spectrum.api.energy.color.*;
import net.minecraft.block.entity.*;
import net.minecraft.registry.entry.*;
import org.jetbrains.annotations.*;

public interface InkColorSelectedPacketReceiver {
	
	void onInkColorSelectedPacket(@Nullable RegistryEntry<InkColor> inkColor);
	
	BlockEntity getBlockEntity();
	
}
