package techguns.client.render.item;

import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import techguns.client.models.ModelMultipart;
import techguns.client.render.math.TGMatrixOps;

public class RenderGunBaseObj extends RenderGunBase {

    protected final float rotY;

    public RenderGunBaseObj(ModelMultipart model, int parts, Identifier texture, float rotY) {
        super(model, parts, texture);
        this.rotY=rotY;
    }

    @Override
    protected void setBaseRotation(MatrixStack matrices, ModelTransformation.Mode transform) {
        TGMatrixOps.rotate(matrices, rotY, 0f,1.0f,0f);
    }
}
