package techguns.client.render.math;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

public class TGMatrixOps {
	public static void rotate(MatrixStack m, float deg, float x, float y, float z) {
		m.multiply(new Quaternion(new Vec3f(x, y, z), deg, true));
	}
}
