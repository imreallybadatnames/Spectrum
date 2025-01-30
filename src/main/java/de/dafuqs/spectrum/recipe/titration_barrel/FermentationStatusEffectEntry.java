package de.dafuqs.spectrum.recipe.titration_barrel;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import io.netty.buffer.*;
import net.minecraft.entity.effect.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.registry.*;

import java.util.*;

public record FermentationStatusEffectEntry(
	StatusEffect statusEffect,
	int baseDuration,
	List<StatusEffectPotencyEntry> potencyEntries
) {
	
	public static final Codec<FermentationStatusEffectEntry> CODEC = RecordCodecBuilder.create(i -> i.group(
			Registries.STATUS_EFFECT.getCodec().fieldOf("status_effect").forGetter(FermentationStatusEffectEntry::statusEffect),
			Codec.INT.fieldOf("base_duration").forGetter(FermentationStatusEffectEntry::baseDuration),
			StatusEffectPotencyEntry.CODEC.listOf().fieldOf("potency_entries").forGetter(FermentationStatusEffectEntry::potencyEntries)
	).apply(i, FermentationStatusEffectEntry::new));
	
	public static final PacketCodec<RegistryByteBuf, FermentationStatusEffectEntry> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.registryValue(RegistryKeys.STATUS_EFFECT), FermentationStatusEffectEntry::statusEffect,
			PacketCodecs.VAR_INT, FermentationStatusEffectEntry::baseDuration,
			StatusEffectPotencyEntry.PACKET_CODEC.collect(PacketCodecs.toList()), FermentationStatusEffectEntry::potencyEntries,
			FermentationStatusEffectEntry::new
	);
	
	public record StatusEffectPotencyEntry(int minAlcPercent, int minThickness, int potency) {
		
		public static final Codec<StatusEffectPotencyEntry> CODEC = RecordCodecBuilder.create(i -> i.group(
				Codec.INT.fieldOf("min_alc").forGetter(StatusEffectPotencyEntry::minAlcPercent),
				Codec.INT.fieldOf("min_thickness").forGetter(StatusEffectPotencyEntry::minThickness),
				Codec.INT.fieldOf("potency").forGetter(StatusEffectPotencyEntry::potency)
		).apply(i, StatusEffectPotencyEntry::new));
		
		public static final PacketCodec<ByteBuf, StatusEffectPotencyEntry> PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.VAR_INT, StatusEffectPotencyEntry::minAlcPercent,
				PacketCodecs.VAR_INT, StatusEffectPotencyEntry::minThickness,
				PacketCodecs.VAR_INT, StatusEffectPotencyEntry::potency,
				StatusEffectPotencyEntry::new
		);
		
	}
	
}
