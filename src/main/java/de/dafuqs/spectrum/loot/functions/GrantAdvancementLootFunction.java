package de.dafuqs.spectrum.loot.functions;

import com.google.common.collect.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.loot.*;
import de.dafuqs.spectrum.progression.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.*;
import net.minecraft.loot.context.LootContext.*;
import net.minecraft.loot.function.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class GrantAdvancementLootFunction extends ConditionalLootFunction {
	
	public static final MapCodec<GrantAdvancementLootFunction> CODEC = RecordCodecBuilder.mapCodec((instance) -> addConditionsField(instance).and(instance.group(
			EntityTarget.CODEC.fieldOf("entity").forGetter((function) -> function.entity),
			Identifier.CODEC.listOf().fieldOf("tags").forGetter((function) -> function.ids)
	)).apply(instance, GrantAdvancementLootFunction::new));
	
	private final LootContext.EntityTarget entity;
	private final List<Identifier> ids;
	
	public GrantAdvancementLootFunction(List<LootCondition> conditions, LootContext.EntityTarget entity, List<Identifier> ids) {
		super(conditions);
		this.entity = entity;
		this.ids = ids;
	}
	
	public LootFunctionType<GrantAdvancementLootFunction> getType() {
		return SpectrumLootFunctionTypes.GRANT_ADVANCEMENT;
	}
	
	public Set<LootContextParameter<?>> getRequiredParameters() {
		return ImmutableSet.of(this.entity.getParameter());
	}
	
	public ItemStack process(ItemStack stack, LootContext context) {
		Entity entity = context.get(this.entity.getParameter());
		if (entity instanceof ServerPlayerEntity player) {
			for (Identifier id : this.ids) {
				SpectrumAdvancementCriteria.LOOT_FUNCTION_TRIGGER.trigger(player, id);
			}
		}
		return stack;
	}
	
	public static ConditionalLootFunction.Builder<?> builder(LootContext.EntityTarget target, List<Identifier> ids) {
		return builder((conditions) -> new GrantAdvancementLootFunction(conditions, target, ids));
	}
}