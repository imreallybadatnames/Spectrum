package de.dafuqs.spectrum.loot.functions;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.loot.*;
import net.minecraft.component.*;
import net.minecraft.item.*;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.*;
import net.minecraft.loot.function.*;

import java.util.*;

public class SetComponentsRandomlyLootFunction extends ConditionalLootFunction {
	
	public static final MapCodec<SetComponentsRandomlyLootFunction> CODEC = RecordCodecBuilder.mapCodec(i -> addConditionsField(i).and(
			ComponentChanges.CODEC.listOf().fieldOf("options").forGetter(c -> c.options)
	).apply(i, SetComponentsRandomlyLootFunction::new));
	
	private final List<ComponentChanges> options;
	
	SetComponentsRandomlyLootFunction(List<LootCondition> conditions, List<ComponentChanges> options) {
		super(conditions);
		this.options = options;
	}
	
	@Override
	public LootFunctionType<? extends ConditionalLootFunction> getType() {
		return SpectrumLootFunctionTypes.SET_COMPONENTS_RANDOMLY;
	}
	
	@Override
	public ItemStack process(ItemStack stack, LootContext context) {
		if (options.isEmpty()) return stack;
		
		var changes = options.get(context.getRandom().nextInt(options.size()));
		stack.applyChanges(changes);
		
		return stack;
	}
	
	public static ConditionalLootFunction.Builder<?> builder(List<ComponentChanges> options) {
		return builder(conditions -> new SetComponentsRandomlyLootFunction(conditions, options));
	}
	
	public static class Builder extends ConditionalLootFunction.Builder<SetComponentsRandomlyLootFunction.Builder> {
		private final List<ComponentChanges> options = new ArrayList<>();
		
		@Override
		protected SetComponentsRandomlyLootFunction.Builder getThisBuilder() {
			return this;
		}
		
		public SetComponentsRandomlyLootFunction.Builder add(ComponentChanges changes) {
			options.add(changes);
			return this;
		}
		
		@Override
		public LootFunction build() {
			return new SetComponentsRandomlyLootFunction(this.getConditions(), options);
		}
	}
	
}
