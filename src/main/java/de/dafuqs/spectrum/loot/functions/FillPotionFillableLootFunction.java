package de.dafuqs.spectrum.loot.functions;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.loot.*;
import net.minecraft.entity.effect.*;
import net.minecraft.item.*;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.*;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;

import java.util.*;

public class FillPotionFillableLootFunction extends ConditionalLootFunction {
	
	public static final MapCodec<FillPotionFillableLootFunction> CODEC = RecordCodecBuilder.mapCodec(i -> addConditionsField(i).and(
			InkPoweredPotionTemplate.CODEC.forGetter(c -> c.template)
	).apply(i, FillPotionFillableLootFunction::new));
	
	public record InkPoweredPotionTemplate(
			boolean ambient, boolean showParticles, LootNumberProvider duration,
			List<RegistryEntry<StatusEffect>> statusEffects, int color, LootNumberProvider amplifier,
			List<InkColor> inkColors, LootNumberProvider inkCost, boolean unidentifiable, boolean incurable
	) {
		
		public static final MapCodec<InkPoweredPotionTemplate> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.BOOL.optionalFieldOf("ambient", false).forGetter(InkPoweredPotionTemplate::ambient),
				Codec.BOOL.optionalFieldOf("show_particles", false).forGetter(InkPoweredPotionTemplate::showParticles),
				LootNumberProviderTypes.CODEC.fieldOf("duration").forGetter(InkPoweredPotionTemplate::duration),
				CodecHelper.singleOrList(Registries.STATUS_EFFECT.getEntryCodec()).fieldOf("status_effect").forGetter(InkPoweredPotionTemplate::statusEffects),
				Codec.INT.optionalFieldOf("color", -1).forGetter(InkPoweredPotionTemplate::color),
				LootNumberProviderTypes.CODEC.fieldOf("amplifier").forGetter(InkPoweredPotionTemplate::amplifier),
				CodecHelper.singleOrList(InkColor.CODEC).fieldOf("ink_color").forGetter(InkPoweredPotionTemplate::inkColors),
				LootNumberProviderTypes.CODEC.fieldOf("ink_cost").forGetter(InkPoweredPotionTemplate::inkCost),
				Codec.BOOL.optionalFieldOf("unidentifiable", false).forGetter(InkPoweredPotionTemplate::unidentifiable),
				Codec.BOOL.optionalFieldOf("incurable", false).forGetter(InkPoweredPotionTemplate::incurable)
		).apply(i, InkPoweredPotionTemplate::new));
		
		public InkPoweredStatusEffectInstance get(LootContext context) {
			RegistryEntry<StatusEffect> statusEffect = this.statusEffects.get(context.getRandom().nextInt(this.statusEffects.size()));
			StatusEffectInstance statusEffectInstance = new StatusEffectInstance(statusEffect, this.duration.nextInt(context), this.amplifier.nextInt(context), ambient, showParticles, true);
			InkColor inkColor = this.inkColors.get(context.getRandom().nextInt(this.inkColors.size()));
			int cost = this.inkCost.nextInt(context);
			return new InkPoweredStatusEffectInstance(statusEffectInstance, new InkCost(inkColor, cost), this.color, this.unidentifiable, this.incurable);
		}
		
	}
	
	private final InkPoweredPotionTemplate template;
	
	FillPotionFillableLootFunction(List<LootCondition> conditions, InkPoweredPotionTemplate template) {
		super(conditions);
		this.template = template;
	}
	
	@Override
	public LootFunctionType<? extends ConditionalLootFunction> getType() {
		return SpectrumLootFunctionTypes.FILL_POTION_FILLABLE;
	}
	
	@Override
	public ItemStack process(ItemStack stack, LootContext context) {
		if (this.template == null)
			return stack;

		if (!(stack.getItem() instanceof InkPoweredPotionFillable inkPoweredPotionFillable))
			return stack;

		if (inkPoweredPotionFillable.isFull(stack))
			return stack;
		
		InkPoweredStatusEffectInstance effect = template.get(context);
		inkPoweredPotionFillable.addOrUpgradeEffects(stack, List.of(effect));
		
		return stack;
	}
	
	public static ConditionalLootFunction.Builder<?> builder(InkPoweredPotionTemplate template) {
		return builder((conditions) -> new FillPotionFillableLootFunction(conditions, template));
	}
	
}
