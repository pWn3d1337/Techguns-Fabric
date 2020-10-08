package techguns.client.render.math;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Quaternion;

public class TGMatrixOps {
	public static void rotate(MatrixStack m, float deg, float x, float y, float z) {
		m.multiply(new Quaternion(new Vector3f(x, y, z), deg, true));
	}
}
