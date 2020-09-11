package techguns.client.render.item;

import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import techguns.client.models.ModelMultipart;
import techguns.client.render.math.TGMatrixOps;

public class RenderGunBase90 extends RenderGunBase {

	public RenderGunBase90(ModelMultipart model, Identifier texture) {
		super(model, texture);
	}
	
	/*public RenderGunBase90(ModelMultipart model, int parts) {
		super(model, parts);
	}*/

	public RenderGunBase90(ModelMultipart model, int parts, Identifier texture) {
		super(model, parts, texture);
	}

	@Override
	protected void setBaseRotation(MatrixStack matrices, ModelTransformation.Mode transform) {
		TGMatrixOps.rotate(matrices, -180.0f, 1.0f, 0, 0);
		TGMatrixOps.rotate(matrices, -90.0f, 0f, 1.0f, 0);
	}	
}
