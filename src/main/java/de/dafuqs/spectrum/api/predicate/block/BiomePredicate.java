package de.dafuqs.spectrum.api.predicate.block;

import com.google.gson.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.biome.*;
import org.jetbrains.annotations.*;

public class BiomePredicate {
    public static final BiomePredicate ANY = new BiomePredicate(null, null);
    @Nullable
    private final TagKey<Biome> tag;
    @Nullable
    private final Biome biome;

    public BiomePredicate(@Nullable TagKey<Biome> tag, @Nullable Biome biome) {
        this.tag = tag;
        this.biome = biome;
    }

    public boolean test(ServerWorld world, BlockPos pos) {
        if (this == ANY) {
            return true;
        }
        if (this.tag != null && world.getBiome(pos).isIn(this.tag)) {
            return true;
        }
        return this.biome != null && world.getBiome(pos).value() == this.biome;
    }

    public static BiomePredicate fromJson(@Nullable JsonElement json) {
        if (json == null || json.isJsonNull()) {
            return ANY;
        }
        
        JsonObject biomeObject = JsonHelper.asObject(json, "biome");
        
        Biome biome = null;
        if (biomeObject.has("biome")) {
            Identifier biomeId = Identifier.of(JsonHelper.getString(biomeObject, "biome"));
            biome = BuiltinRegistries.createWrapperLookup().getWrapperOrThrow(RegistryKeys.BIOME).getOrThrow(RegistryKey.of(RegistryKeys.BIOME, biomeId)).value();
        }
        
        TagKey<Biome> tagKey = null;
        if (biomeObject.has("tag")) {
            Identifier tagId = Identifier.of(JsonHelper.getString(biomeObject, "tag"));
            tagKey = TagKey.of(RegistryKeys.BIOME, tagId);
        }
        
        return new BiomePredicate(tagKey, biome);
    }
}
