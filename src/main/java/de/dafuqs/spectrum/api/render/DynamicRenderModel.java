package de.dafuqs.spectrum.api.render;

import net.fabricmc.api.*;
import net.fabricmc.fabric.api.renderer.v1.model.*;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.*;
import net.minecraft.client.texture.*;
import net.minecraft.client.util.*;
import net.minecraft.client.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

@Environment(EnvType.CLIENT)
public class DynamicRenderModel extends ForwardingBakedModel implements UnbakedModel {
    private static class WrappingOverridesList extends ModelOverrideList {
        private final ModelOverrideList wrapped;
        private WrappingOverridesList(ModelOverrideList orig) {
            super(null, null, List.of());
            this.wrapped = orig;
        }

        @Nullable
        @Override
        public BakedModel apply(BakedModel model, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed) {
            BakedModel newModel = wrapped.apply(model, stack, world, entity, seed);
            return newModel == model ? model : new DynamicRenderModel(newModel);
        }
    }
    // only used pre-bake
    private UnbakedModel baseUnbaked;

    // could be used again if pre-bake model problems get figured out
    public DynamicRenderModel(UnbakedModel base) {
        this.baseUnbaked = base;
    }

    // post-bake post-override constructor
    public DynamicRenderModel(BakedModel base) {
        this.wrapped = base instanceof DynamicRenderModel fm ? fm.getWrappedModel() : base;
    }

    // avoid FAPI builtin model lookup
    @Override
    public boolean isBuiltin() {
        return false;
    }

    private DynamicRenderModel wrap(BakedModel model) {
        this.wrapped = model instanceof DynamicRenderModel fm ? fm.getWrappedModel() : model;
        return this;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return this.baseUnbaked.getModelDependencies();
    }

    // override so wrap persists over override
    // ensures that renderer is called
    @Override
    public ModelOverrideList getOverrides() {
        return new WrappingOverridesList(super.getOverrides());
    }

    // return empty transform to prevent double apply in render
    @Override
    public ModelTransformation getTransformation() {
        return ModelTransformation.NONE;
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {
        this.baseUnbaked.setParents(modelLoader);
    }
    
    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        return this.wrap(this.baseUnbaked.bake(baker, textureGetter, rotationContainer));
    }
    
}
