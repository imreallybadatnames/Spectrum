package de.dafuqs.spectrum.recipe.crystallarieum;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;

public record CrystallarieumCatalyst(Ingredient ingredient, float growthAccelerationMod, float inkConsumptionMod, float consumeChancePerSecond) {

	public static final CrystallarieumCatalyst EMPTY = new CrystallarieumCatalyst(Ingredient.EMPTY, 0, 0, 0);
	
	public static final Codec<CrystallarieumCatalyst> CODEC = RecordCodecBuilder.create(i -> i.group(
		Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(CrystallarieumCatalyst::ingredient),
		Codec.FLOAT.fieldOf("growth_acceleration_mod").forGetter(CrystallarieumCatalyst::growthAccelerationMod),
		Codec.FLOAT.fieldOf("ink_consumption_mod").forGetter(CrystallarieumCatalyst::inkConsumptionMod),
		Codec.FLOAT.fieldOf("consume_chance_per_second").forGetter(CrystallarieumCatalyst::consumeChancePerSecond)
	).apply(i, CrystallarieumCatalyst::new));
	
	public static final PacketCodec<RegistryByteBuf, CrystallarieumCatalyst> PACKET_CODEC = PacketCodec.tuple(
		Ingredient.PACKET_CODEC, CrystallarieumCatalyst::ingredient,
		PacketCodecs.FLOAT, CrystallarieumCatalyst::growthAccelerationMod,
		PacketCodecs.FLOAT, CrystallarieumCatalyst::inkConsumptionMod,
		PacketCodecs.FLOAT, CrystallarieumCatalyst::consumeChancePerSecond,
		CrystallarieumCatalyst::new
	);
	
}
