package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.component.type.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

import java.util.*;

@Mixin(BundleContentsComponent.Builder.class)
public interface BundleContentsComponentBuilderAccessor {
	
	@Accessor
	List<ItemStack> getStacks();
	
}
