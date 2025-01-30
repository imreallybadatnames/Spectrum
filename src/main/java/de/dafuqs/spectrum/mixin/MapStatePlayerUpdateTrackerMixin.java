package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.sugar.*;
import de.dafuqs.spectrum.items.map.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.map.*;
import net.minecraft.network.packet.*;
import net.minecraft.network.packet.s2c.common.*;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Mixin(MapState.PlayerUpdateTracker.class)
public class MapStatePlayerUpdateTrackerMixin {

    @Shadow
    @Final
    public PlayerEntity player;

    @Inject(method = "getPacket", at = @At(value = "NEW", target = "(Lnet/minecraft/component/type/MapIdComponent;BZLjava/util/Collection;Lnet/minecraft/item/map/MapState$UpdateData;)Lnet/minecraft/network/packet/s2c/play/MapUpdateS2CPacket;"), cancellable = true)
    private void spectrum$getArtisansAtlasPacket(MapIdComponent mapId, CallbackInfoReturnable<Packet<?>> cir, @Local MapState.UpdateData updateData, @Local Collection<MapDecoration> icons) {
        World world = player.getWorld();
		if (world != null && world.getMapState(mapId) instanceof ArtisansAtlasState state) {
			var mapPacket = new MapUpdateS2CPacket(mapId, state.scale, state.locked, icons, updateData);
			var payload = new SyncArtisansAtlasPayload(Optional.ofNullable(state.getTargetId()), mapPacket);
			var customPacket = new CustomPayloadS2CPacket(payload);
			cir.setReturnValue(customPacket);
		}
    }
}
