package techguns.client.render.item;


import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import techguns.client.models.ModelMultipart;
import techguns.client.render.math.TGMatrixOps;
public class RenderItemBaseRocketItem extends RenderItemBase {

	public RenderItemBaseRocketItem(ModelMultipart model, Identifier texture) {
		super(model, texture);
	}

	@Override
	protected void setBaseRotation(MatrixStack matrices, Mode transform) {
		TGMatrixOps.rotate(matrices, -180.0f, 1.0f, 0, 0);
		TGMatrixOps.rotate(matrices, -90.0f, 0f, 1.0f, 0);
		TGMatrixOps.rotate(matrices, -90.0f, 0f, 0f, 1);
	}
}
