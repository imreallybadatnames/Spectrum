package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.energy.color.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.predicate.item.*;
import net.minecraft.registry.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class InkContainerInteractionCriterion extends AbstractCriterion<InkContainerInteractionCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("ink_container_interaction");
	
	public void trigger(ServerPlayerEntity player, ItemStack stack, InkStorage storage, InkColor changeColor, long changeAmount) {
		this.trigger(player, (conditions) -> conditions.matches(
			stack,
			storage.getEnergy(InkColors.BLACK),
			storage.getEnergy(InkColors.BLUE),
			storage.getEnergy(InkColors.BROWN),
			storage.getEnergy(InkColors.CYAN),
			storage.getEnergy(InkColors.GRAY),
			storage.getEnergy(InkColors.GREEN),
			storage.getEnergy(InkColors.LIGHT_BLUE),
			storage.getEnergy(InkColors.LIGHT_GRAY),
			storage.getEnergy(InkColors.LIME),
			storage.getEnergy(InkColors.MAGENTA),
			storage.getEnergy(InkColors.ORANGE),
			storage.getEnergy(InkColors.PINK),
			storage.getEnergy(InkColors.PURPLE),
			storage.getEnergy(InkColors.RED),
			storage.getEnergy(InkColors.WHITE),
			storage.getEnergy(InkColors.YELLOW),
			changeColor,
			changeAmount
		));
	}
	
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
		Optional<LootContextPredicate> player,
		ItemPredicate itemPredicate,
		Map<InkColor, LongRange> colorRanges,
		ColorPredicate changeColor,
		LongRange changeRange
	
	) implements AbstractCriterion.Conditions {
		// FIXME - I hit the Codec limit of 16 fields (this needs 19).
		// Noaaan: I am working on a solution for this. Likely a proper InkColor -> LongRange predicate
//		public static final Codec<Map<RegistryKey<InkColor>, LongRange>> INK_TO_RANGE_CODEC = RecordCodecBuilder.mapCodec(instance ->
//			instance.<RegistryKey<InkColor>>map(
//				key -> key.getValue().toString(),
//					)
//
//		.apply(instance, ));
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
			ItemPredicate.CODEC.fieldOf("item").forGetter(Conditions::itemPredicate),
			
			LongRange.CODEC.fieldOf("black").forGetter(Conditions::blackRange),
			LongRange.CODEC.fieldOf("blue").forGetter(Conditions::blueRange),
			LongRange.CODEC.fieldOf("brown").forGetter(Conditions::brownRange),
			LongRange.CODEC.fieldOf("cyan").forGetter(Conditions::cyanRange),
			LongRange.CODEC.fieldOf("gray").forGetter(Conditions::grayRange),
			LongRange.CODEC.fieldOf("green").forGetter(Conditions::greenRange),
			LongRange.CODEC.fieldOf("light_blue").forGetter(Conditions::lightBlueRange),
			LongRange.CODEC.fieldOf("light_gray").forGetter(Conditions::lightGrayRange),
			LongRange.CODEC.fieldOf("lime").forGetter(Conditions::limeRange),
			LongRange.CODEC.fieldOf("magenta").forGetter(Conditions::magentaRange),
			LongRange.CODEC.fieldOf("orange").forGetter(Conditions::orangeRange),
			LongRange.CODEC.fieldOf("pink").forGetter(Conditions::pinkRange),
			LongRange.CODEC.fieldOf("purple").forGetter(Conditions::purpleRange),
			LongRange.CODEC.fieldOf("red").forGetter(Conditions::redRange),
			LongRange.CODEC.fieldOf("white").forGetter(Conditions::whiteRange),
			ColorPredicate.CODEC.fieldOf("change_color").forGetter(Conditions::changeColor),
			LongRange.CODEC.fieldOf("change_range").forGetter(Conditions::changeRange)
		).apply(instance, Conditions::new));
		
		public boolean matches(ItemStack stack, long black, long blue, long brown, long cyan, long gray, long green, long lightBlue, long lightGray, long lime, long magenta, long o, long pink, long purple, long red, long white, long yellow, InkColor color, long change) {
			return itemPredicate.test(stack) && changeRange.test(change) && changeColor.test(color)
				&& blackRange.test(black) && blueRange.test(blue) && brownRange.test(brown)
				&& cyanRange.test(cyan) && grayRange.test(gray) && greenRange.test(green)
				&& lightBlueRange.test(lightBlue) && lightGrayRange.test(lightGray) && limeRange.test(lime)
				&& magentaRange.test(magenta) && orangeRange.test(o) && pinkRange.test(pink)
				&& purpleRange.test(purple) && redRange.test(red) && whiteRange.test(white) && yellowRange.test(yellow);
			
		}
	}
	
}