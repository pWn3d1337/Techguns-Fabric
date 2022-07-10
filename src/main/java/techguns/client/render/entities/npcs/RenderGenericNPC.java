package techguns.client.render.entities.npcs;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import techguns.client.models.entities.GenericNPCModel;
import techguns.entities.npcs.GenericNPC;


public class RenderGenericNPC<T extends GenericNPC> extends MobEntityRenderer<T, GenericNPCModel<T>> {
    protected Identifier texture;

    public RenderGenericNPC(EntityRendererFactory.Context context, Identifier texture, EntityModelLayer modelLayer,  @Nullable EntityModelLayer legsArmorLayer,  @Nullable EntityModelLayer bodyArmorLayer) {
        this(context, texture, modelLayer, legsArmorLayer, bodyArmorLayer, 0.5F);
    }

    public RenderGenericNPC(EntityRendererFactory.Context context, Identifier texture, EntityModelLayer modelLayer, @Nullable EntityModelLayer legsArmorLayer, @Nullable EntityModelLayer bodyArmorLayer, float f) {
        super(context, new GenericNPCModel(context.getPart(modelLayer)), f);
        this.texture = texture;
        if(legsArmorLayer!=null && bodyArmorLayer !=null) {
            this.addFeature(new ArmorFeatureRenderer(this, new GenericNPCModel(context.getPart(legsArmorLayer)), new GenericNPCModel(context.getPart(bodyArmorLayer))));
        }
    }

    @Override
    public Identifier getTexture(GenericNPC entity) {
        return texture;
    }
}
