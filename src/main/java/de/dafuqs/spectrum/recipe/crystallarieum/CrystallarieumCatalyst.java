package de.dafuqs.spectrum.recipe.crystallarieum;

import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.*;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.util.*;

public class CrystallarieumCatalyst {

	public static final MapCodec<> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(

	).apply(CrystallarieumCatalyst::new));
	public static final PacketCodec<RegistryByteBuf, CrystallarieumCatalyst> PACKET_CODEC = PacketCodec.ofStatic(CrystallarieumCatalyst::write, CrystallarieumCatalyst::read);
	
	public static final CrystallarieumCatalyst EMPTY = new CrystallarieumCatalyst(Ingredient.EMPTY, 1.0F, 1.0F, 0.0F);
	
	public final Ingredient ingredient;
	public final float growthAccelerationMod;
	public final float inkConsumptionMod;
	public final float consumeChancePerSecond;
	
	protected CrystallarieumCatalyst(Ingredient ingredient, float growthAccelerationMod, float inkConsumptionMod, float consumeChancePerSecond) {
		this.ingredient = ingredient;
		this.growthAccelerationMod = growthAccelerationMod;
		this.inkConsumptionMod = inkConsumptionMod;
		this.consumeChancePerSecond = consumeChancePerSecond;
	}
	
	public static CrystallarieumCatalyst fromJson(JsonObject jsonObject) {
		Ingredient ingredient = Ingredient.DISALLOW_EMPTY_CODEC.decode(JsonOps.INSTANCE, JsonHelper.getElement(jsonObject, "ingredient"));
		float growthAccelerationMod = JsonHelper.getFloat(jsonObject, "growth_acceleration_mod");
		float inkConsumptionMod = JsonHelper.getFloat(jsonObject, "ink_consumption_mod");
		float consumeChancePerSecond = JsonHelper.getFloat(jsonObject, "consume_chance_per_second");
		return new CrystallarieumCatalyst(ingredient, growthAccelerationMod, inkConsumptionMod, consumeChancePerSecond);
	}
	
	public static void write(RegistryByteBuf buf, CrystallarieumCatalyst catalyst) {
		Ingredient.PACKET_CODEC.encode(buf, ingredient);
		buf.writeFloat(growthAccelerationMod);
		buf.writeFloat(inkConsumptionMod);
		buf.writeFloat(consumeChancePerSecond);
	}
	
	public static CrystallarieumCatalyst read(RegistryByteBuf buf) {
		Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
		float growthAccelerationMod = buf.readFloat();
		float inkConsumptionMod = buf.readFloat();
		float consumeChancePerSecond = buf.readFloat();
		return new CrystallarieumCatalyst(ingredient, growthAccelerationMod, inkConsumptionMod, consumeChancePerSecond);
	}
	
}
