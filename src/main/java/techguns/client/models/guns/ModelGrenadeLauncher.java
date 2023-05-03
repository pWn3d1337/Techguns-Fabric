package techguns.client.models.guns;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techguns.TGIdentifier;
import techguns.client.models.ModelBaseBaked;
import techguns.client.render.math.TGMatrixOps;

public class ModelGrenadeLauncher extends ModelBaseBaked {

    public static final ModelIdentifier model_part1 = new ModelIdentifier(new TGIdentifier("item/grenadelauncher.obj"), "");
    public static final ModelIdentifier model_part2 = new ModelIdentifier(new TGIdentifier("item/grenadelauncher_1.obj"), "");

    public ModelGrenadeLauncher() {
        super(RenderLayer::getEntitySolid, model_part1);
    }

    @Override
    public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft, float reloadProgress,
                       ModelTransformationMode transformType, int part, float fireProgress, float chargeProgress,
                       int light, int overlay) {

        if(part ==0) {
            this.renderModel(matrices, vertices, model_part1, light);
        } else {
            matrices.push();
            matrices.translate(0, -2f, 0);
            TGMatrixOps.rotate(matrices, 60.0f*fireProgress, 1, 0, 0);
            matrices.translate(0, 2f, 0);
            this.renderModel(matrices, vertices, model_part2, light);
            matrices.pop();
        }
    }
}
