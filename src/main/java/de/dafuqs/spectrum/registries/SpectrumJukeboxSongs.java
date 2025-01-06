package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import net.minecraft.block.jukebox.*;
import net.minecraft.registry.*;

@SuppressWarnings("unused")
public class SpectrumJukeboxSongs {

    public static final RegistryKey<JukeboxSong> CREDITS = of("credits");
    public static final RegistryKey<JukeboxSong> DISCOVERY = of("discovery");
    public static final RegistryKey<JukeboxSong> DIVINITY = of("divinity");

    private static RegistryKey<JukeboxSong> of(String id) {
		return RegistryKey.of(RegistryKeys.JUKEBOX_SONG, SpectrumCommon.locate(id));
    }

}
