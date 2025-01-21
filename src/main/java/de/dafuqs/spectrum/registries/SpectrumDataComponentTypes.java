package de.dafuqs.spectrum.registries;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.blocks.bottomless_bundle.*;
import de.dafuqs.spectrum.component_type.*;
import net.minecraft.component.*;
import net.minecraft.network.codec.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import java.util.function.*;

import static de.dafuqs.spectrum.registries.DeferredRegistrar.*;

@SuppressWarnings("unused")
public class SpectrumDataComponentTypes {

    static { DeferredRegistrar.setClass(SpectrumDataComponentTypes.class); }

    public static ComponentType<Boolean> ACTIVATED = register("activated", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));
    public static ComponentType<BottomlessBundleItem.BottomlessStack> BOTTOMLESS_STACK = register("bottomless_stack", builder -> builder.codec(BottomlessBundleItem.BottomlessStack.CODEC).packetCodec(BottomlessBundleItem.BottomlessStack.PACKET_CODEC));
    public static ComponentType<Identifier> BOUND_ITEM = register("bound_item", builder -> builder.codec(Identifier.CODEC).packetCodec(Identifier.PACKET_CODEC));
    public static ComponentType<Boolean> HIDE_USAGE_TOOLTIP = register("hide_usage_tooltip", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));
    public static ComponentType<InkStorageComponent> INK_STORAGE = register("ink_storage", builder -> builder.codec(InkStorageComponent.CODEC).packetCodec(InkStorageComponent.PACKET_CODEC));
    public static ComponentType<Boolean> SOCKETED = register("socketed", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));
    public static ComponentType<Integer> STORED_EXPERIENCE = register("stored_experiene", builder -> builder.codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT));
    public static ComponentType<PipeBombComponent> PIPE_BOMB = register("pipe_bomb", builder -> builder.codec(PipeBombComponent.CODEC).packetCodec(PipeBombComponent.PACKET_CODEC));
    public static ComponentType<WrappedPresentComponent> WRAPPED_PRESENT = register("wrapped_present", builder -> builder.codec(WrappedPresentComponent.CODEC).packetCodec(WrappedPresentComponent.PACKET_CODEC));
	public static ComponentType<ShootingStarComponent> SHOOTING_STAR = register("shooting_star", builder -> builder.codec(ShootingStarComponent.CODEC).packetCodec(ShootingStarComponent.PACKET_CODEC));
	public static ComponentType<OptionalInkColorComponent> OPTIONAL_INK_COLOR = register("optional_ink_color", builder -> builder.codec(OptionalInkColorComponent.CODEC).packetCodec(OptionalInkColorComponent.PACKET_CODEC));

    public static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return defer(builderOperator.apply(ComponentType.builder()).build())
                .withCommon(type -> Registry.register(Registries.DATA_COMPONENT_TYPE, id, type))
                .value();
    }

    public static void register() {
        DeferredRegistrar.registerCommon(SpectrumDataComponentTypes.class);
    }

}
