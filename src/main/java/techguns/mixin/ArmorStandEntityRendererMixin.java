package techguns.mixin;

import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.ArmorStandArmorEntityModel;
import net.minecraft.entity.decoration.ArmorStandEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import techguns.client.models.armor.TGArmorModelRegistry;
import techguns.client.render.entities.npcs.TGArmorFeatureRenderer;

@Mixin(ArmorStandEntityRenderer.class)
public abstract class ArmorStandEntityRendererMixin extends LivingEntityRenderer<ArmorStandEntity, ArmorStandArmorEntityModel> {

    public ArmorStandEntityRendererMixin(EntityRendererFactory.Context ctx, ArmorStandArmorEntityModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ArmorStandEntityRenderer;addFeature(Lnet/minecraft/client/render/entity/feature/FeatureRenderer;)Z", ordinal = 0), method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)V")
    private void ctor(EntityRendererFactory.Context context, CallbackInfo ci){
        this.addFeature(new TGArmorFeatureRenderer(this, new TGArmorModelRegistry(context)));
    }
}
