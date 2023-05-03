package techguns.client.models;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import java.util.function.Function;

public class ModelBaseBaked extends ModelMultipart {

    protected ModelIdentifier model;

    public ModelBaseBaked(Function<Identifier, RenderLayer> layerFactory, ModelIdentifier model) {
        super(layerFactory);
        this.model = model;
    }

    @Override
    public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft, float reloadProgress, ModelTransformationMode transformType, int part, float fireProgress, float chargeProgress, int light, int overlay) {
        this.renderModel(matrices, vertices, model, light);
    }

    protected void renderModel(MatrixStack matrices, VertexConsumer vertices, ModelIdentifier id, int light){
        MatrixStack.Entry entry = matrices.peek();
        BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getModel(id);

        for (BakedQuad quad : model.getQuads(null, null, null)){
            vertices.quad(entry, quad, 1.0f, 1.0f, 1.0f, light, OverlayTexture.DEFAULT_UV);
        }
    }


}
