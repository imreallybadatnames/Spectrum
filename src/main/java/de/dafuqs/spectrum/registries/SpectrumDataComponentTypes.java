package de.dafuqs.spectrum.registries;

import com.mojang.serialization.Codec;
import de.dafuqs.spectrum.blocks.bottomless_bundle.BottomlessBundleItem;
import de.dafuqs.spectrum.component_type.WrappedPresentComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

import static de.dafuqs.spectrum.registries.DeferredRegistrar.defer;

public class SpectrumDataComponentTypes {

    static { DeferredRegistrar.setClass(SpectrumDataComponentTypes.class); }

    public static ComponentType<Boolean> SOCKETED = register("socketed", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));
    public static ComponentType<BottomlessBundleItem.BottomlessStack> BOTTOMLESS_STACK = register("bottomless_stack", builder -> builder.codec(BottomlessBundleItem.BottomlessStack.CODEC).packetCodec(BottomlessBundleItem.BottomlessStack.PACKET_CODEC));
    public static ComponentType<WrappedPresentComponent> WRAPPED_PRESENT = register("wrapped_present", builder -> builder.codec(WrappedPresentComponent.CODEC).packetCodec(WrappedPresentComponent.PACKET_CODEC));

    public static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return defer(builderOperator.apply(ComponentType.builder()).build())
                .withCommon(type -> Registry.register(Registries.DATA_COMPONENT_TYPE, id, type))
                .value();
    }

    public static void register() {
        DeferredRegistrar.registerCommon(SpectrumDataComponentTypes.class);
    }

}
