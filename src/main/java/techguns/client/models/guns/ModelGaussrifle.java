package techguns.client.models.guns;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import techguns.TGIdentifier;
import techguns.api.guns.IGenericGun;
import techguns.client.models.ModelBaseBaked;

import java.util.function.Function;

public class ModelGaussrifle extends ModelBaseBaked {

    public static ModelIdentifier modelIdentifier = new ModelIdentifier(new TGIdentifier("item/gaussrifle"), null);

    public ModelGaussrifle() {
        super(RenderLayer::getEntitySolid, modelIdentifier);
    }

    @Override
    public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft, float reloadProgress,
                       ModelTransformation.Mode transformType, int part, float fireProgress, float chargeProgress,
                       int light, int overlay) {
        this.renderModel(matrices,vertices, modelIdentifier, light);
    }
}
