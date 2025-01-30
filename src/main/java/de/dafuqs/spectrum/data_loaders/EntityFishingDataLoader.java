package de.dafuqs.spectrum.data_loaders;

import com.google.gson.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.predicate.location.*;
import de.dafuqs.spectrum.helpers.*;
import net.fabricmc.fabric.api.resource.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.resource.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.util.math.*;
import net.minecraft.util.profiler.*;

import java.util.*;

public class EntityFishingDataLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
	
	public static final String ID = "entity_fishing";
	public static final EntityFishingDataLoader INSTANCE = new EntityFishingDataLoader();
	
	protected static final List<EntityFishingEntry> ENTITY_FISHING_ENTRIES = new ArrayList<>();
	
	public record EntityFishingEntity(RegistryEntry<EntityType<?>> entityType, NbtCompound nbt) {
		
		public static final MapCodec<EntityFishingEntity> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Registries.ENTITY_TYPE.getEntryCodec().fieldOf("id").forGetter(EntityFishingEntity::entityType),
				NbtCompound.CODEC.optionalFieldOf("nbt", new NbtCompound()).forGetter(EntityFishingEntity::nbt)
		).apply(i, EntityFishingEntity::new));
		
		public static final Codec<Weighted.Present<EntityFishingEntity>> WEIGHTED_CODEC = RecordCodecBuilder.create(i -> i.group(
				CODEC.forGetter(Weighted.Present::data),
				Weight.CODEC.optionalFieldOf("id", Weight.of(1)).forGetter(Weighted.Present::weight)
		).apply(i, Weighted.Present::new));
		
	}

	public record EntityFishingEntry(List<WorldConditionsPredicate> predicates, float entityChance, Pool<Weighted.Present<EntityFishingEntity>> weightedEntities) {
		
		public static final Codec<EntityFishingEntry> CODEC = RecordCodecBuilder.create(i -> i.group(
				CodecHelper.singleOrList(WorldConditionsPredicate.CODEC).fieldOf("location").forGetter(EntityFishingEntry::predicates),
				Codec.FLOAT.fieldOf("chance").forGetter(EntityFishingEntry::entityChance),
				EntityFishingEntity.WEIGHTED_CODEC.listOf().xmap(Pool::of, Pool::getEntries).fieldOf("entities").forGetter(EntityFishingEntry::weightedEntities)
		).apply(i, EntityFishingEntry::new));
		
	}
	
	private EntityFishingDataLoader() {
		super(new Gson(), ID);
	}
	
	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		ENTITY_FISHING_ENTRIES.clear();
		prepared.forEach((identifier, jsonElement) ->
			CodecHelper.fromJson(EntityFishingEntry.CODEC, jsonElement.getAsJsonObject())
					.ifPresent(ENTITY_FISHING_ENTRIES::add));
	}
	
	@Override
	public Identifier getFabricId() {
		return SpectrumCommon.locate(ID);
	}
	
	public static Optional<EntityFishingEntity> tryCatchEntity(ServerWorld world, BlockPos pos, int bigCatchLevel) {
		for (EntityFishingEntry entry : ENTITY_FISHING_ENTRIES) {
			if (entry.predicates.stream().anyMatch(p -> p.test(world, pos))) {
				if (world.random.nextFloat() < entry.entityChance * (1 + bigCatchLevel)) {
					var x = entry.weightedEntities.getOrEmpty(world.random);
					if (x.isPresent()) {
						return Optional.of(x.get().data());
					}
				}
				return Optional.empty();
			}
		}
		return Optional.empty();
	}
	
}
