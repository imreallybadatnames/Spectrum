package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.item.map.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

import java.util.*;

@Mixin(MapState.class)
public interface MapStateAccessor {
	
	@Accessor
	boolean getShowDecorations();
	
	@Accessor
	boolean getUnlimitedTracking();
	
	@Accessor
	Map<String, MapBannerMarker> getBanners();
	
	@Accessor
	Map<String, MapDecoration> getDecorations();
	
	@Accessor
	int getDecorationCount();
	
	@Accessor
	void setDecorationCount(int decorationCount);
	
	@Invoker
	void invokeMarkDecorationsDirty();
	
	@Invoker
	void invokeRemoveDecoration(String id);
	
}
