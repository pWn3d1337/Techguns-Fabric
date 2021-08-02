package techguns.mixin;

import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import techguns.client.modelloader.TGObjLoader;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {

    @Shadow
    protected abstract void addModel(ModelIdentifier modelId);

    @Inject(at=@At("RETURN"), method="addModel")
    public void addModel(ModelIdentifier modelIdentifier, CallbackInfo info){
        //Only add models at early call of addModel, this is when the missing model is added
        if (modelIdentifier == ModelLoader.MISSING_ID) {
            TGObjLoader.INSTANCE.getManuallyLoadedModels().forEach(this::addModel);
        }
    }
}