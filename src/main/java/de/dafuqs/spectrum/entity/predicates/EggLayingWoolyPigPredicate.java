package de.dafuqs.spectrum.entity.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.entity.*;
import de.dafuqs.spectrum.entity.entity.*;
import net.minecraft.entity.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

import java.util.*;

public record EggLayingWoolyPigPredicate(Optional<DyeColor> color, Optional<Boolean> hatless, Optional<Boolean> sheared) implements EntitySubPredicate {

	public static final MapCodec<EggLayingWoolyPigPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			DyeColor.CODEC.optionalFieldOf("color").forGetter(EggLayingWoolyPigPredicate::color),
			Codec.BOOL.optionalFieldOf("hatless").forGetter(EggLayingWoolyPigPredicate::hatless),
			Codec.BOOL.optionalFieldOf("sheared").forGetter(EggLayingWoolyPigPredicate::sheared)
	).apply(instance, EggLayingWoolyPigPredicate::new));

	@Override
	public boolean test(Entity entity, ServerWorld world, @Nullable Vec3d pos) {
		if (!(entity instanceof EggLayingWoolyPigEntity wooly)) {
			return false;
		} else {
			return (this.color.isEmpty() || this.color.get() == wooly.getColor())
					&& (this.hatless.isEmpty() || this.hatless.get() == wooly.isHatless())
					&& (this.sheared.isEmpty() || this.sheared.get() == wooly.isSheared());
		}
	}

	@Override
	public MapCodec<EggLayingWoolyPigPredicate> getCodec() {
		return SpectrumEntitySubPredicateTypes.EGG_LAYING_WOOLY_PIG;
	}

}
