package techguns.client.render.math;

import net.minecraft.client.util.math.MatrixStack;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;

public class TGMatrixOps {
	public static void rotate(MatrixStack m, float deg, float x, float y, float z) {
		m.multiply(new Quaternionf(new AxisAngle4f(deg, x,y, z)));
	}
}
