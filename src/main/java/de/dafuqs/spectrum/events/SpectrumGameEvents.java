package de.dafuqs.spectrum.events;

import de.dafuqs.spectrum.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.world.event.*;

public class SpectrumGameEvents {
	
	public static RegistryEntry<GameEvent> ENTITY_SPAWNED;
	public static RegistryEntry<GameEvent> BLOCK_CHANGED;

	public static RegistryEntry<GameEvent> HUMMINGSTONE_HUMMING;
	public static RegistryEntry<GameEvent> HUMMINGSTONE_HYMN;

	public static RegistryEntry<GameEvent> WIRELESS_REDSTONE_SIGNAL;

	public static void register() {
		ENTITY_SPAWNED = register("entity_spawned", 16);
		BLOCK_CHANGED = register("block_changed", 16);
		
		HUMMINGSTONE_HUMMING = register("hummingstone_humming", 16);
		HUMMINGSTONE_HYMN = register("hummingstone_hymn", 16);
		
		WIRELESS_REDSTONE_SIGNAL = register("wireless_redstone_signal", 16);
	}
	
	private static RegistryEntry<GameEvent> register(String id, int range) {
		return Registry.registerReference(Registries.GAME_EVENT, SpectrumCommon.locate(id), new GameEvent(range));
	}
	
}