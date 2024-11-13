package de.dafuqs.spectrum.registries;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static de.dafuqs.spectrum.registries.DeferredRegistrar.defer;

public class SpectrumDataComponentTypes {

    static { DeferredRegistrar.setClass(SpectrumDataComponentTypes.class); }

    public static ComponentType<Boolean> SOCKETED = defer(new ComponentType.Builder<Boolean>().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build())
            .withCommon((type) -> register("socketed", type))
            .value();

    public static <T> ComponentType<T> register(String id, ComponentType<T> componentType) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, componentType);
    }

    public static void register() {
        DeferredRegistrar.registerCommon(SpectrumDataComponentTypes.class);
    }

}
