package techguns.client.render.math;

import net.minecraft.client.util.math.MatrixStack;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import techguns.client.render.TGRenderHelper;

public class TGMatrixOps {
	private static final float DEG2RAD = (float) (Math.PI / 180.0);

	public static void rotate(MatrixStack m, float deg, float x, float y, float z) {

		m.multiply(new Quaternionf(new AxisAngle4f(DEG2RAD*deg, x, y, z)));
	}
}
