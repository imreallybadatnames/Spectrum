package de.dafuqs.spectrum.api.predicate.location;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.helpers.TimeHelper;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.predicate.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;

public record TimeOfDayPredicate(TimeHelper.TimeOfDay name, NumberRange.IntRange range) {
	
	private static final Codec<TimeOfDayPredicate> NAMED_CODEC = StringIdentifiable.createCodec(TimeHelper.TimeOfDay::values)
			.xmap(t -> new TimeOfDayPredicate(t, NumberRange.IntRange.between(t.from, t.to-1)), TimeOfDayPredicate::name);
	
	private static final Codec<TimeOfDayPredicate> RANGED_CODEC = NumberRange.IntRange.CODEC
			.xmap(t -> new TimeOfDayPredicate(null, t), TimeOfDayPredicate::range);
	
	private static final Codec<TimeOfDayPredicate> VALUED_CODEC = Codec.INT
			.xmap(t -> new TimeOfDayPredicate(null, NumberRange.IntRange.exactly(t)), p -> p.range.min().orElse(0));
	
	public static final Codec<TimeOfDayPredicate> CODEC = Codec.withAlternative(NAMED_CODEC, Codec.withAlternative(RANGED_CODEC, VALUED_CODEC));
	
	public static final PacketCodec<ByteBuf, TimeOfDayPredicate> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, range -> range.min().orElse(Integer.MIN_VALUE),
			PacketCodecs.VAR_INT, range -> range.max().orElse(Integer.MAX_VALUE),
			NumberRange.IntRange::between
	).xmap(
			range -> new TimeOfDayPredicate(null, range),
			TimeOfDayPredicate::range
	);

	public boolean test(ServerWorld world) {
		return range.test((int) (world.getTimeOfDay() % 24000));
	}
	
}