package de.dafuqs.spectrum.api.predicate.world;

import com.google.gson.*;
import net.minecraft.predicate.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public class LightPredicate {
    public static final LightPredicate ANY = new LightPredicate(null, null);
	
	@Nullable
	private final NumberRange.IntRange blockLight;
	@Nullable
	private final NumberRange.IntRange skyLight;
	
	public LightPredicate(@Nullable NumberRange.IntRange blockLight, @Nullable NumberRange.IntRange skyLight) {
		this.blockLight = blockLight;
		this.skyLight = skyLight;
	}
	
	public boolean test(ServerWorld world, BlockPos pos) {
		if (this == ANY) {
			return true;
		}
		
		if (this.blockLight != null && !this.blockLight.test(world.getLightLevel(LightType.BLOCK, pos))) {
			return false;
		}
		if (this.skyLight != null && !this.skyLight.test(world.getLightLevel(LightType.SKY, pos))) {
			return false;
		}
		
		return true;
	}
	
	public static LightPredicate fromJson(@Nullable JsonElement json) {
		if (json == null || json.isJsonNull()) {
            return ANY;
        }
		
        JsonObject jsonObject = JsonHelper.asObject(json, "light");
        
		NumberRange.IntRange blockLight = NumberRange.IntRange.fromJson(jsonObject.get("block"));
		NumberRange.IntRange skyLight = NumberRange.IntRange.fromJson(jsonObject.get("sky"));
        
		return new LightPredicate(blockLight, skyLight);
	}
}
