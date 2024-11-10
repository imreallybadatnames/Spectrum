package de.dafuqs.spectrum.entity.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.entity.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.entity.variants.*;
import net.minecraft.entity.*;
import net.minecraft.predicate.entity.EntitySubPredicate;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

import java.util.*;

public record KindlingPredicate(Optional<Boolean> clipped, Optional<Boolean> angry, Optional<KindlingVariant> variant) implements EntitySubPredicate {

	public static final MapCodec<KindlingPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			Codec.BOOL.optionalFieldOf("clipped").forGetter(KindlingPredicate::clipped),
			Codec.BOOL.optionalFieldOf("angry").forGetter(KindlingPredicate::angry),
			KindlingVariant.CODEC.optionalFieldOf("variant").forGetter(KindlingPredicate::variant)
	).apply(instance, KindlingPredicate::new));

	@Override
	public boolean test(Entity entity, ServerWorld world, @Nullable Vec3d pos) {
		if (!(entity instanceof KindlingEntity kindling)) {
			return false;
		} else {
			return (this.clipped.isEmpty() || this.clipped.get() == kindling.isClipped())
					&& (this.angry.isEmpty() || this.angry.get() == (kindling.getAngerTime() == 0)
					&& (this.variant.isEmpty() || this.variant.get() == kindling.getKindlingVariant()));
		}
	}

	@Override
	public MapCodec<KindlingPredicate> getCodec() {
		return SpectrumEntitySubPredicateTypes.KINDLING;
	}

}
