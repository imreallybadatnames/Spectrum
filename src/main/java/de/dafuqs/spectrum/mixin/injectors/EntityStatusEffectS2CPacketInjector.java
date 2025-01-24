package de.dafuqs.spectrum.mixin.injectors;

import net.minecraft.entity.effect.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.s2c.play.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

public interface EntityStatusEffectS2CPacketInjector {
	
	default boolean spectrum$isIncurable() { return false; }
	
	@Mixin(EntityStatusEffectS2CPacket.class)
	class _Mixin implements EntityStatusEffectS2CPacketInjector {
		
		@Unique private boolean incurable;
		
		@Inject(method = "<init>(ILnet/minecraft/entity/effect/StatusEffectInstance;Z)V", at = @At("RETURN"))
		public void initIncurable(int entityId, StatusEffectInstance effect, boolean keepFading, CallbackInfo ci) {
			this.incurable = StatusEffectInstanceInjector.isIncurable(effect);
		}
		
		@Inject(method = "<init>(Lnet/minecraft/network/RegistryByteBuf;)V", at = @At("RETURN"))
		public void initIncurable(RegistryByteBuf buf, CallbackInfo ci) {
			this.incurable = buf.readBoolean();
		}
		
		@Inject(method = "write(Lnet/minecraft/network/RegistryByteBuf;)V", at = @At("RETURN"))
		public void writeIncurable(RegistryByteBuf buf, CallbackInfo ci) {
			buf.writeBoolean(incurable);
		}
		
		@Override
		public boolean spectrum$isIncurable() {
			return incurable;
		}
	}
	
}
