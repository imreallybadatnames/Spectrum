package de.dafuqs.spectrum.api.predicate.block;

import com.google.common.collect.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.block.*;
import net.minecraft.predicate.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import org.jetbrains.annotations.*;

import java.util.*;

/**
 * Since BlockPredicate requires world and pos as input we can not use that in BrokenBlockCriterion
 * When the predicate would be checked the block would already be broken, unable to be tested
 * here we require a block state, that can be checked against.
 * Since block entities are already destroyed at this stage the only things that can be checked is
 * block, state and block tag. Should suffice for 99 % of cases
 * <br>
 * TODO - Review as of 1.21 port
 */
public record BrokenBlockPredicate(Optional<TagKey<Block>> tag, Optional<List<Block>> blocks, StatePredicate state) {

	public static final BrokenBlockPredicate ANY = new BrokenBlockPredicate(null, null, StatePredicate.Builder.create().build().get());
	public static final Codec<BrokenBlockPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			TagKey.codec(RegistryKeys.BLOCK).optionalFieldOf("tag").forGetter(BrokenBlockPredicate::tag),
			Registries.BLOCK.getCodec().listOf().optionalFieldOf("blocks").forGetter(BrokenBlockPredicate::blocks),
			StatePredicate.CODEC.fieldOf("state").forGetter(BrokenBlockPredicate::state)
		).apply(instance, BrokenBlockPredicate::new)
	);

	public boolean test(BlockState blockState) {
		if (this == ANY) {
			return true;
		} else {
			if (this.tag.isPresent() && !blockState.isIn(this.tag.get())) {
				return false;
			} else if (this.blocks.isPresent() && !this.blocks.get().contains(blockState.getBlock())) {
				return false;
			} else {
				return this.state.test(blockState);
			}
		}
	}

	public static class Builder {
		private @Nullable List<Block> blocks;
		private @Nullable TagKey<Block> tag;
		private StatePredicate state;

		private Builder() {
			this.state = StatePredicate.Builder.create().build().get();
		}

		public static BrokenBlockPredicate.Builder create() {
			return new BrokenBlockPredicate.Builder();
		}

		public BrokenBlockPredicate.Builder blocks(Block... blocks) {
			this.blocks = ImmutableList.copyOf(blocks);
			return this;
		}

		public BrokenBlockPredicate.Builder blocks(Iterable<Block> blocks) {
			this.blocks = ImmutableList.copyOf(blocks);
			return this;
		}

		public BrokenBlockPredicate.Builder tag(TagKey<Block> tag) {
			this.tag = tag;
			return this;
		}

		public BrokenBlockPredicate.Builder state(StatePredicate state) {
			this.state = state;
			return this;
		}

		public BrokenBlockPredicate build() {
			return new BrokenBlockPredicate(Optional.ofNullable(this.tag), Optional.ofNullable(this.blocks), this.state);
		}
	}
}
