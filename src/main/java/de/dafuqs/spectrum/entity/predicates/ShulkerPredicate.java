package de.dafuqs.spectrum.entity.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.predicate.entity.EntitySubPredicate;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

import java.util.*;

public record ShulkerPredicate(Optional<DyeColor> color) implements EntitySubPredicate {

	public static final MapCodec<ShulkerPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			DyeColor.CODEC.optionalFieldOf("color").forGetter(ShulkerPredicate::color)
	).apply(instance, ShulkerPredicate::new));

	@Override
	public boolean test(Entity entity, ServerWorld world, @Nullable Vec3d pos) {
		if (!(entity instanceof ShulkerEntity shulkerEntity)) {
			return false;
		} else {
			return (this.color.isEmpty() || this.color.get() == shulkerEntity.getColor());
		}
	}

	@Override
	public MapCodec<ShulkerPredicate> getCodec() {
		return SpectrumEntitySubPredicateTypes.SHULKER;
	}

}
