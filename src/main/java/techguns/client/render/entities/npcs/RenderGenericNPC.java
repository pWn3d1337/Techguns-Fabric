package techguns.client.render.entities.npcs;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import techguns.client.models.entities.GenericNPCModel;
import techguns.entities.npcs.GenericNPC;

import java.util.function.Function;

public class RenderGenericNPC<T extends GenericNPC> extends MobEntityRenderer<T, GenericNPCModel<T>> {
    protected Identifier texture;

    public RenderGenericNPC(EntityRendererFactory.Context context, Identifier texture, EntityModelLayer modelLayer, @Nullable EntityModelLayer legsArmorLayer, @Nullable EntityModelLayer bodyArmorLayer, Function<ModelPart, GenericNPCModel<T>> modelConstructor) {
        this(context, texture, modelLayer, legsArmorLayer, bodyArmorLayer, modelConstructor,0.5F);
    }

    public RenderGenericNPC(EntityRendererFactory.Context context, Identifier texture, EntityModelLayer modelLayer, @Nullable EntityModelLayer legsArmorLayer, @Nullable EntityModelLayer bodyArmorLayer, Function<ModelPart, GenericNPCModel<T>> modelConstructor, float f) {
        super(context, modelConstructor.apply(context.getPart(modelLayer)), f);
        this.texture = texture;
        //this.addFeature(new HeadFeatureRenderer(this, context.getModelLoader(), 1.0F, 1.0F, 1.0F, context.getHeldItemRenderer()));
        //this.addFeature(new ElytraFeatureRenderer(this, context.getModelLoader()));
        if(legsArmorLayer!=null && bodyArmorLayer !=null) {
            this.addFeature(new ArmorFeatureRenderer(this, modelConstructor.apply(context.getPart(legsArmorLayer)), modelConstructor.apply(context.getPart(bodyArmorLayer))));
        }
        this.addFeature(new HeldItemFeatureRenderer(this, context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(GenericNPC entity) {
        return texture;
    }

    @Override
    public void render(T mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
