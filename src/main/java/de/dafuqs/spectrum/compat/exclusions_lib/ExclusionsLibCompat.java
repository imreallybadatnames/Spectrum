package de.dafuqs.spectrum.compat.exclusions_lib;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.compat.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.blockpredicate.*;

/**
 * Loaded when Exclusions Lib is *not* present
 * So the game can be loaded without complaining about a missing Block Predicate
 */
public class ExclusionsLibCompat {
	
	public static class AlwaysFalseBlockPredicate implements BlockPredicate {
		
		public static AlwaysFalseBlockPredicate instance = new AlwaysFalseBlockPredicate();
		public static final MapCodec<AlwaysFalseBlockPredicate> CODEC = MapCodec.unit(() -> instance);
		
		private AlwaysFalseBlockPredicate() {
		}
		
		public boolean test(StructureWorldAccess structureWorldAccess, BlockPos blockPos) {
			return false;
		}
		
		public BlockPredicateType<?> getType() {
			return BlockPredicateType.TRUE;
		}
	}
	
	public static BlockPredicateType<AlwaysFalseBlockPredicate> OVERLAPS_STRUCTURE_DUMMY;
	
	public static void registerNotPresent() {
		OVERLAPS_STRUCTURE_DUMMY = registerBlockPredicate(Identifier.of(SpectrumIntegrationPacks.EXCLUSIONS_LIB_ID, "overlaps_structure"), AlwaysFalseBlockPredicate.CODEC);
	}
	
	private static <P extends BlockPredicate> BlockPredicateType<P> registerBlockPredicate(Identifier id, MapCodec<P> codec) {
		return Registry.register(Registries.BLOCK_PREDICATE_TYPE, id, () -> {
			return codec;
		});
	}
	
}