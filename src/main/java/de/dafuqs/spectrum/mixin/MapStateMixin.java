package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.items.map.*;
import net.minecraft.item.map.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(MapState.class)
public class MapStateMixin {

    // Caches the created state between the two mixins
    @Nullable
    private static ArtisansAtlasState atlasState = null;

    @Inject(method = "fromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/map/MapState;<init>(IIBZZZLnet/minecraft/registry/RegistryKey;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void spectrum$fromNbt_newMapState(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, CallbackInfoReturnable<MapState> cir, RegistryKey<World> registryKey, int centerX, int centerZ, byte scale, boolean showIcons, boolean unlimitedTracking, boolean locked) {
        if (nbt.contains("isArtisansAtlas", NbtElement.BYTE_TYPE) && nbt.getBoolean("isArtisansAtlas")) {
			atlasState = new ArtisansAtlasState(centerX, centerZ, scale, showIcons, unlimitedTracking, locked, registryKey, nbt);
        }
    }

    @ModifyVariable(
            method = "fromNbt",
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/item/map/MapState;<init>(IIBZZZLnet/minecraft/registry/RegistryKey;)V"),
                    to = @At(value = "TAIL")
            ),
            at = @At(value = "STORE")
    )
    private static MapState spectrum$fromNbt_storeMapState(MapState vanillaState) {
        if (atlasState != null) {
            ArtisansAtlasState state = atlasState;
            atlasState = null;
            return state;
        }
        return vanillaState;
    }

}
