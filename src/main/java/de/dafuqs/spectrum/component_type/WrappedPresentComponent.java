package de.dafuqs.spectrum.component_type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.blocks.present.PresentBlock;
import de.dafuqs.spectrum.helpers.CodecHelper;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.DyeColor;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.dynamic.Codecs;

import java.util.HashMap;
import java.util.Map;

public record WrappedPresentComponent(
        boolean wrapped,
        PresentBlock.WrappingPaper variant,
        Map<DyeColor, Integer> colors
) {

    public static final WrappedPresentComponent DEFAULT = new WrappedPresentComponent(false, PresentBlock.WrappingPaper.RED, Map.of());

    public static final Codec<WrappedPresentComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("wrapped", false).forGetter(WrappedPresentComponent::wrapped),
            StringIdentifiable.createCodec(PresentBlock.WrappingPaper::values).fieldOf("variant").forGetter(WrappedPresentComponent::variant),
            Codec.unboundedMap(DyeColor.CODEC, Codecs.POSITIVE_INT).fieldOf("colors").forGetter(WrappedPresentComponent::colors)
    ).apply(instance, WrappedPresentComponent::new));

    public static final PacketCodec<RegistryByteBuf, WrappedPresentComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL,
            WrappedPresentComponent::wrapped,
            CodecHelper.ofPacketEnum(PresentBlock.WrappingPaper.class),
            WrappedPresentComponent::variant,
            PacketCodecs.map(HashMap::new, DyeColor.PACKET_CODEC, PacketCodecs.VAR_INT),
            WrappedPresentComponent::colors,
            WrappedPresentComponent::new
    );

}
