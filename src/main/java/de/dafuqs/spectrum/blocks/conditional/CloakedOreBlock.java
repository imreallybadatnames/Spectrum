package de.dafuqs.spectrum.blocks.conditional;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.revelationary.api.revelations.*;
import de.dafuqs.spectrum.mixin.accessors.ExperienceDroppingBlockAccessor;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.loot.context.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.intprovider.*;

import java.util.*;

public class CloakedOreBlock extends ExperienceDroppingBlock implements RevelationAware {

	public static final MapCodec<CloakedOreBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			IntProvider.createValidatingCodec(0, 10).fieldOf("experience").forGetter(b -> ((ExperienceDroppingBlockAccessor) b).getExperienceDropped()),
			createSettingsCodec(),
			Identifier.CODEC.fieldOf("advancement").forGetter(CloakedOreBlock::getCloakAdvancementIdentifier),
			BlockState.CODEC.fieldOf("cloak").forGetter(b -> b.getBlockStateCloaks().get(b.getDefaultState()))
	).apply(instance, CloakedOreBlock::new));

	protected static boolean dropXP;
	protected final Identifier cloakAdvancementIdentifier;
	protected final BlockState cloakBlockState;
	
	public CloakedOreBlock(IntProvider experienceDropped, Settings settings, Identifier cloakAdvancementIdentifier, BlockState cloakBlockState) {
		super(experienceDropped, settings);
		this.cloakAdvancementIdentifier = cloakAdvancementIdentifier;
		this.cloakBlockState = cloakBlockState;
		RevelationAware.register(this);
	}

	@Override
	public MapCodec<? extends CloakedOreBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	public Map<BlockState, BlockState> getBlockStateCloaks() {
		return Map.of(this.getDefaultState(), cloakBlockState);
	}
	
	@Override
	public Identifier getCloakAdvancementIdentifier() {
		return cloakAdvancementIdentifier;
	}
	
	@Override
	public Pair<Item, Item> getItemCloak() {
		return new Pair<>(this.asItem(), cloakBlockState.getBlock().asItem());
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
		// workaround: since onStacksDropped() has no way of checking if it was
		// triggered by a player we have to cache that information here
		PlayerEntity lootPlayerEntity = RevelationAware.getLootPlayerEntity(builder);
		dropXP = lootPlayerEntity != null && isVisibleTo(lootPlayerEntity);
		
		return super.getDroppedStacks(state, builder);
	}

	@Override
	public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience) {
		super.onStacksDropped(state, world, pos, stack, dropExperience && dropXP);
	}
	
}
