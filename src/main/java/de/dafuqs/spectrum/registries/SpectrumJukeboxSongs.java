package de.dafuqs.spectrum.registries;

import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class SpectrumJukeboxSongs {

    public static final RegistryKey<JukeboxSong> CREDITS = of("credits");
    public static final RegistryKey<JukeboxSong> DISCOVERY = of("discovery");
    public static final RegistryKey<JukeboxSong> DIVINITY = of("divinity");

    private static RegistryKey<JukeboxSong> of(String id) {
        return RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.ofVanilla(id));
    }

}
