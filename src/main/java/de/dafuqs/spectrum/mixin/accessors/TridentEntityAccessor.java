package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.entity.data.*;
import net.minecraft.entity.projectile.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(TridentEntity.class)
public interface TridentEntityAccessor {
	
	@Accessor("LOYALTY")
	static TrackedData<Byte> spectrum$getLoyalty() {
		return null;
	}
	
	@Accessor("ENCHANTED")
	static TrackedData<Boolean> spectrum$getEnchanted() {
		return null;
	}
	
	@Accessor("dealtDamage")
	void spectrum$setDealtDamage(boolean dealtDamage);
	
}