package de.dafuqs.spectrum.mixin.client;

import net.fabricmc.api.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.*;


@Environment(EnvType.CLIENT)
@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientEntityMixin {
	
	// TODO: fixme - low prio
	// cape loading is spread over lots of classes now
	/*
	@Inject(
            method = "getCapeTexture",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getCapeTexture(CallbackInfoReturnable<Identifier> cir) {
        var cape = WorthinessChecker.getCapeType(((Entity) (Object) (this)).getUuid());
        if (cape.render) {
            cir.setReturnValue(cape.capePath);
            cir.cancel();
        }
    }*/
	
}
